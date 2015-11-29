/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirdCore;

import Formais2.Tokens;
import java.util.ArrayList;

/**
 *
 * @author Luca
 */
public class Producao {
    /*
    private char Cabeca;
    private String Corpo;
    */
    
    
    private ArrayList<Token> tokens;
    
    public Producao(ArrayList<Token> t) {
        tokens = t;
    }
    public Producao() {
        tokens = new ArrayList();
    }
    public Token getCabeca() {
        return tokens.get(0);
    }
    public ArrayList<Token> getCorpo() {
        ArrayList<Token> temp = tokens;
        temp.remove(0);
        return temp;
    }
    /*
    public Producao(String producao){
        Cabeca = producao.split("->")[0].charAt(0);
        Corpo = producao.split("->")[1];
    }

    public char getCabeca() {
        return Cabeca;
    }

    public void setCabeca(char Cabeca) {
        this.Cabeca = Cabeca;
    }

    public char[] getCorpoCA() {
        return Corpo.toCharArray();
    }
    
    public String getCorpo() {
        return Corpo;
    }

    public void setCorpo(String Corpo) {
        this.Corpo = Corpo;
    }
    */
    
    
}
