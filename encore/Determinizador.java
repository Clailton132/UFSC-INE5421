/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

import java.util.ArrayList;

/**
 *
 * @author Luca
 */
public class Determinizador {

    public static void Determinizar(Automato automato) {
        ArrayList<Estado> estados;
        ArrayList<Estado> last;
        String[] alfabeto;
        ArrayList<Estado> novosEstados = new ArrayList<Estado>();

        estados = automato.getEstados();
        last = automato.getEstados();
        alfabeto = automato.getAlfa();

        if (alfabeto[0].equals("E")) {
            prepararEpsilons(estados);
        }

        for (Estado e : estados) {

            //como a transição por epsilon é sempre na posicao 0, da pra começar com 1 aqui
            for (int i = 0; i < e.getTransicoes().length; i++) {
                ArrayList<Estado> temp = e.getTransicaoPorIndice(i);
                String novo = "";

                if (temp.size() > 1) {
                    for (Estado e1 : temp) {
                        if (novo == "") {
                            novo = e1.getNome();
                        } else {
                            novo = novo + "," + e1.getNome();
                        }
                    }

                    novo = Estado.OrdenaString(novo);
                    Estado novoEstado = new Estado(automato, novo, alfabeto.length);
                    e.determinizarTransicao(i, novoEstado);

                    if (!checkIfExistsInArray(novosEstados, novoEstado)) {
                        novosEstados.add(novoEstado);
                    }
                }
            }
        }

        for (Estado e : novosEstados) {
            automato.addEstados(e);
        }

        if (automato.getEstados().size() > last.size()) {
            Determinizar(automato);
        }

    }

    public static boolean checkIfExistsInArray(ArrayList<Estado> estados, Estado estado) {
        for (Estado e : estados) {
            if (estado.getNome().equals(e.getNome())) {
                return true;
            }
        }
        return false;
    }

    //nota, padrão Epsilon estar sempre na posição 0; pode ser mudado depois
    private static ArrayList<Estado> EpsilonFechoDoEstado(Estado estado, ArrayList<Estado> estados, ArrayList<Estado> fecho) {

        if (checkIfExistsInArray(fecho, estado)) {
            return fecho;
        }

        fecho.add(estado);

        if (estado.getTransicaoPorIndice(0) == null) {
            return fecho;
        } else {
            for (Estado e : estado.getTransicaoPorIndice(0)) {
                fecho = (EpsilonFechoDoEstado(e, estados, fecho));
            }
            return fecho;
        }

    }

    public static void prepararEpsilons(ArrayList<Estado> estados) {

        for (Estado e : estados) {
            e.setFecho(EpsilonFechoDoEstado(e, estados, new ArrayList<Estado>()));
        }

        for (Estado e : estados) {

            for (int i = 1; i < e.getTransicoes().length; i++) {

                if (e.getTransicaoPorIndice(i) != null) {
                    ArrayList<Estado> transicaoAtual = e.getTransicaoPorIndice(i);
                    ArrayList<Estado> temp = new ArrayList<Estado>();

                    for (Estado e1 : transicaoAtual) {
                        temp.addAll(e1.getFecho());
                    }

                    for (Estado e1 : temp) {
                        e.addTransicaoPorIndice(e1, i);
                    }
                }
            }
            e.clearEpsilon();
        }
    }
}
