/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.*;

/**
 *
 * @author Luca
 */
public class AutomatoFinito {

	Scanner s; //scanner
	File file;
	ArrayList<Estado> es = new ArrayList<Estado>();

	public AutomatoFinito(){
		file = new File("Teste 1.txt");
		createAutomatoFromFile();
	}
	public AutomatoFinito(ArrayList<Estado> a) {
		es = a;
	}
	public void printAutomato() {
		for(int i = 0; i < es.size(); i ++) {
			System.out.println(es.get(i).getNome());
			for(int j = 0; j < es.get(i).getTransicoes().length; j ++) {
				System.out.println(" " + es.get(i).getTransicoes()[j]);
			}
		}
	}
	public void createAutomatoFromFile(){
		String[] a;
		int y;
		try{
			s = new Scanner(file);
			while(s.hasNext()){
				String temp = s.next();
				y = temp.split(",").length;
				a = temp.split(",");
				String[] temp1;
				temp = s.next();
				while(!temp.split(":")[0].equals("f")){
					temp1 = temp.split(";");
					boolean ex = false;
					for(Estado e : es){
						if(e.getNome().equals(temp1[0])){
							ex = true;
						}
					}
					if(!ex){
						Estado novo = new Estado(temp1[0], y);
						es.add(novo);
						for(Estado e : es){
							if(e.getNome().equals(temp1[0])){
								for(int l = 1; l < e.getTransicoes().length + 1; l++){
									e.addTransicoes(temp1[l], l);
								}
							}
						}
					} else {
						for(Estado e : es){
							if(e.getNome().equals(temp1[0])){
								for(int l = 1; l < e.getTransicoes().length + 1; l++){
									e.addTransicoes(temp1[l], l);
								}
							}
						}
					}
					temp = s.next();
				}
				temp1 = temp.split(":")[1].split(",");
				for(int i = 0; i < temp1.length; i++){

					for(Estado e : es){
						if(e.getNome().equals(temp1[i])){
							e.setFinal();
						}
					}
				}
				temp = s.next();
				temp1 = temp.split(":")[1].split(",");

				for(int i = 0; i < temp1.length; i++){

					for(Estado e : es){
						if(e.getNome().equals(temp1[i])){
							e.setInicial();
						}
					}
				}
			}
			for(Estado e : es){
				//System.out.println(e.getNome());
				for(int i = 0; i < e.getTransicoes().length; i++){
					System.out.println(e.getNome() + ":" +e.getTransicoes()[i]);
				}
				System.out.println(e.isInicial());
				System.out.println(e.isFinal());
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public AutomatoFinito determinizarAutomatoFinito(AutomatoFinito af) {
		
		return null;

	}
	
}
