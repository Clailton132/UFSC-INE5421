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

        for (Estado e : estados) {

            for (int i = 0; i < e.getTransicoes().length; i++) {
                ArrayList<Estado> temp = e.getTransicaoPorIndice(i);
                String novo = "";

                if (temp.size() > 1) {
                    for (Estado e1 : temp) {
                        if (novo == "") {
                            novo = e1.getNome();
                        } else {
                            novo = novo + " " + e1.getNome();
                        }
                    }

                    novo = novo.replace(" ", ",");
                    novo = Estado.OrdenaString(novo);
                    Estado novoEstado = new Estado(automato, novo, automato.getAlfa().length);
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
        
        if(automato.getEstados().size() > last.size()){
            Determinizar(automato);
        }
        
        automato.print();

    }

    public static boolean checkIfExistsInArray(ArrayList<Estado> estados, Estado estado) {
        for (Estado e : estados) {
            if (estado.getNome().equals(e.getNome())) {
                return true;
            }
        }
        return false;
    }

}
