/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirdCore;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Luca
 */
public class Gramatica {

    private ArrayList<Regra> regras;
    
    public Gramatica(String texto) {
        regras = new ArrayList();
    }
    public Gramatica(ArrayList<Regra> rules) {
        regras = rules;
    }
/*
    public Gramatica(String texto) {
        producoes = new ArrayList();

        Scanner scanner = new Scanner(getClass().getResourceAsStream(texto));

        while (scanner.hasNext()){
            String[] t1 = scanner.next().split("->");
            
            String[] t2 = t1[1].split(";");
            
            for(String t : t2){
                this.addProducao(t1[0] + "->" + t);
            }
        }
    }*/
/*
    public void addProducao(String producao) {
        producoes.add(new Producao(producao));
    }

    public ArrayList<Producao> getProducoesPorCabeca(char NaoTerminal) {
        ArrayList<Producao> resultado = new ArrayList();

        for (Producao p : this.producoes) {
            if (p.getCabeca() == NaoTerminal) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    public void print() {
        for(Producao p : producoes){
            System.out.println(p.getCabeca() + " -> " + p.getCorpo());
        }
    }
    */
}
