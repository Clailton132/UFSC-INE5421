/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

import java.util.*;

/**
 *
 * @author Luca
 */
public class Estado {

    private String nome = "";
    private ArrayList[] transicoes;                                                //a posição do elemento no array indica o símbolo do qual ele possui transição.
    private Automato automato;
    private boolean aceitador = false;
    private boolean inicial = false;
    private ArrayList<Estado> fecho;

    public Estado(Automato auto, String Nome, int numAlfa) {
        this.nome = Nome;
        this.automato = auto;
        this.transicoes = new ArrayList[numAlfa];
        this.fecho = new ArrayList<Estado>();

        for (int i = 0; i < transicoes.length; i++) {
            transicoes[i] = new ArrayList<Estado>();

        }

    }

    public void addTransicaoPorAlfa(Estado estado, String simbolo) {
        if (estado != null) {
            int i = 0;
            while (simbolo != automato.getAlfa()[i]) {
                i++;
            }
            this.transicoes[i].add(estado);
            this.transicoes[i].sort(Comparador);
        }
    }

    public void addTransicaoPorIndice(Estado estado, int indice) {
        if (estado != null) {
            if (!Determinizador.checkIfExistsInArray(transicoes[indice], estado)) {
                this.transicoes[indice].add(estado);
                this.transicoes[indice].sort(Comparador);
            }
        }
    }

    public ArrayList getTransicaoPorAlfa(String alfa) {
        int i = 0;
        for (String a : automato.getAlfa()) {
            if (a.equals(alfa)) {
                return transicoes[i];
            }
            i++;
        }
        return null;
    }

    public ArrayList<Estado> getTransicaoPorIndice(int i) {
        return transicoes[i];
    }

    public String getNome() {
        return this.nome;
    }

    public void determinizarTransicao(int i, Estado e) {

        ArrayList<Estado> temp = this.transicoes[i];

        for (Estado estado : temp) {

            for (int t = 0; t < estado.getTransicoes().length; t++) {
                for (int j = 0; j < estado.getTransicaoPorIndice(t).size(); j++) {
                    e.addTransicaoPorIndice(estado.getTransicaoPorIndice(t).get(j), t);
                }
            }

            if (estado.getFinal()) {
                e.setFinal();
            }
        }
        this.transicoes[i].clear();
        this.transicoes[i].add(e);
    }

    public static String OrdenaString(String a) {
        String[] temp = a.split(",");
        Arrays.sort(temp);
        String result = temp[0];

        for (int i = 1; i < temp.length; i++) {
            result = result + "," + temp[i];
        }
        return result;
    }

    public void setFecho(ArrayList<Estado> fecho) {
        this.fecho = fecho;
    }

    public ArrayList<Estado> getFecho() {
        return this.fecho;
    }

    public void setInicial() {
        this.inicial = true;
    }

    public void setFinal() {
        this.aceitador = true;
    }

    public boolean getInicial() {
        return this.inicial;
    }

    public boolean getFinal() {
        return this.aceitador;
    }

    public ArrayList[] getTransicoes() {
        return this.transicoes;
    }

    public Automato getAutomatoPertence() {
        return this.automato;
    }

    public void clearEpsilon() {
        this.transicoes[0].clear();
    }

    public static Comparator<Estado> Comparador = new Comparator<Estado>() {

        @Override
        public int compare(Estado o1, Estado o2) {
            String nome1 = o1.getNome();
            String nome2 = o2.getNome();

            return nome1.compareTo(nome2);

        }

    };

}