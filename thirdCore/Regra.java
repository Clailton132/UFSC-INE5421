/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirdCore;

import java.util.ArrayList;

/**
 *
 * @author gabluz
 */
public class Regra {
    
    private String cabeca;  
    private ArrayList<Producao> producoes;
    
    public Regra(String head, ArrayList<Producao> prod) {
        cabeca = head;
        producoes = prod;
    }
    public String getCabeca() {
        return cabeca;
    }
    public ArrayList<Producao> getProducoes() {
        return producoes;
    }
    public Producao getProducaoIndex(int i) {
        return producoes.get(i);
    }
    
}
