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
        alfabeto = automato.getAlfabeto();

        if (alfabeto[0].equals("E")) {
            removerEpsilonTransicoes(estados);
        }

        for (Estado e : estados) {

            //como a transicao por epsilon eh sempre na posicao 0, da pra comecar com 1 aqui
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

                    if (!checaSeExisteNoArray(novosEstados, novoEstado)) {
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

        for (Estado e : automato.getEstados()) {
            for (Estado j : novosEstados) {
                e.acertarTransicoes(j);
            }
        }

    }

    public static boolean checaSeExisteNoArray(ArrayList<Estado> estados, Estado estado) {
        for (Estado e : estados) {
            if (estado.getNome().equals(e.getNome())) {
                return true;
            }
        }
        return false;
    }

    //nota, padrao Epsilon estar sempre na posicao 0; pode ser mudado depois
    private static ArrayList<Estado> EpsilonFechoDoEstado(Estado estado, ArrayList<Estado> estados, ArrayList<Estado> fecho) {

        if (checaSeExisteNoArray(fecho, estado)) {
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

    public static void removerEpsilonTransicoes(ArrayList<Estado> estados) {

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
