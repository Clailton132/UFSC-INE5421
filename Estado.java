/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luca
 */
public class Estado {

    private String nome;
    private String[] transicoes;
    private boolean boolInicial = false;
    private boolean boolFinal = false;
    private boolean boolAlcancavel = false;

    public Estado(String nome, int y) {
        this.nome = nome;
        transicoes = new String[y];
    }

    public String getNome() {
        return nome;
    }

    public String[] getTransicoes() {
        return transicoes;
    }

    public void addTransicoes(String transicao, int l) {

        int i = l - 1;

        if (transicoes[i] == null) {                                            //se nao houver a transicao por aquele simbolo l do alfabeto

            transicoes[i] = ordena(transicao);                                  //a transicao por o simbolo l recebe a entrada

        } else {

            transicoes[i] = unirTransicoes(transicoes[i], transicao);
            
        }
    }

    public boolean isInicial() {
        return boolInicial;
    }

    public boolean isFinal() {
        return boolFinal;
    }

    public boolean isAlc() {
	    return boolAlcancavel;
	}

	public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInicial() {
        this.boolInicial = true;
    }

    public void setFinal() {
        this.boolFinal = true;
    }

    public void setAlc() {
	    boolAlcancavel = true;
	}

	public static String ordena(String transicao) {
        String[] lista = transicao.split(",");
        Arrays.sort(lista);
        String ordenada = lista[0];
        for (int i = 1; i < lista.length; i++) {
            ordenada = ordenada + "," + lista[i];
        }
        return ordenada;
    }

    public static String unirTransicoes(String a, String b) {
        String[] A;
        String[] B;
        String[] C;

        if (a != null) {
            A = a.split(",");
        } else {
            A = new String[0];
        }

        if (b != null) {
            B = b.split(",");
        } else {
            B = new String[0];
        }

        C = new String[A.length + B.length];

        int k = 0;

        for (int i = 0; i < A.length; i++, k++) {
            C[k] = A[i];
        }
        for (int i = 0; i < B.length; i++, k++) {
            C[k] = B[i];
        }

        Arrays.sort(C);
        String ret = C[0];

        for (int i = 1; i < C.length; i++) {
            if (!C[i].equals(C[i - 1])) {
                ret = ret + "," + C[i];
            }

        }
        return ret;

    }

}
