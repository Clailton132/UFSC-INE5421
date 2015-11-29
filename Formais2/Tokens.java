/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formais2;

import java.util.*;

/**
 *
 * @author Luca
 */
public class Tokens {
    
    ArrayList<String[]> Tokens;
    private boolean eFinal;
    
    public Tokens(){
        Tokens = new ArrayList();
    }
    
    public void addToken(String Token, String ID){
        String[] j = {Token,ID};
        Tokens.add(j);
        if (ID.compareTo("PR") == 0)
            eFinal = true;
    }
    
    public String getToken(String Token){
        for(String[] j : Tokens){
            if(j[0].equals(Token)){
                return j[1];
            }
        }
        return null;
    }
    
    public String[] getToken(int i){
        return Tokens.get(i);
    }
    
    public ArrayList<String[]> getAll(){
        return Tokens;
    }
    public boolean eFinal() {
        return eFinal;
    }
    
}
