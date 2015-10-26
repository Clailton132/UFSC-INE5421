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
public class Estado implements Comparable{

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
            while (simbolo != automato.getAlfabeto()[i]) {
                i++;
            }
            this.transicoes[i].add(estado);
            this.transicoes[i].sort(null);
        }
    }

    public void addTransicaoPorIndice(Estado estado, int indice) {
        if (estado != null) {
            if (!Determinizador.checaSeExisteNoArray(transicoes[indice], estado)) {
                this.transicoes[indice].add(estado);
                this.transicoes[indice].sort(null);
            }
        }
    }

    public ArrayList getTransicaoPorAlfa(String alfa) {
        int i = 0;
        for (String a : automato.getAlfabeto()) {
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
                e.setFinal(true);
            }
        }
        this.transicoes[i].clear();
        this.transicoes[i].add(e);
    }

    public void acertarTransicoes(Estado e) {
        for (int i = 0; i < transicoes.length; i++) {

            ArrayList<Estado> temp = this.transicoes[i];
            if (temp.size() != 0) {
                String nome = temp.get(0).getNome();
                
                for (int j = 1; j < temp.size(); j++) {
                    nome = nome + "," + temp.get(j).getNome();
                }
                
                if(e.getNome().equals(nome)){
                    this.transicoes[i].clear();
                    this.transicoes[i].add(e);
                }
            }
        }

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

    public void setInicial(boolean i) {
        this.inicial = i;
    }

    public void setFinal(boolean f) {
        this.aceitador = f;
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

    public void rename(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Estado)){
            return 1;
        }
        Estado e = (Estado)o;
        return this.nome.compareTo(e.nome);
    }
            

}
