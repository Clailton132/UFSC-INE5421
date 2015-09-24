/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;


/**
 *
 * @author Luca
 */
public class Estado {

	String nome;
	String[] transicoes;
	boolean boolInicial = false;
	boolean boolFinal = false;
	
	public Estado() {
		
	}
	
//Argumentos(Nome do estado, Número de estados)
	public Estado(String nome, int y) {
		//this.nome = nome;
		transicoes = new String[y];
		
	}

	public String getNome() {
		return nome;
	}

	public String[] getTransicoes() {
		return transicoes;
	}

	//Argumentos(Para onde vai, com qual condição da linguagem)
	public void addTransicoes(String transicao, int l) {
		int i = l - 1;

		if (transicoes[i] == null) {
			transicoes[i] = transicao;
		} else {
			String[] temp = transicao.split(",");
			if (temp.length > 1) {
				for (int k = 0; k < temp.length; k++) {
					addTransicoes(temp[k], l);
				}
				return;
			}
			temp = transicoes[i].split(",");
			boolean ex = false;
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].equals(transicao)) {
					ex = true;
				}
			}

			if (ex == false) {
				transicoes[i] = transicoes[i] + "," + transicao;
			}
		}
	}

	public boolean isInicial() {
		return boolInicial;
	}

	public boolean isFinal() {
		return boolFinal;
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
	public String ordena(String transicao) {
		transicao = transicao.replace("q", "");
		transicao = transicao.replace(",", "");
		char[] chars = transicao.toCharArray();
		Arrays.sort(chars);
		String ordenada = new String(chars);
		ordenada = ordenada.replace("", ",q");
		ordenada = ordenada.substring(1,ordenada.length()-2);
		return ordenada;
	}
}
