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
public class Token {
    ArrayList<String> token;
    private boolean eFinal;
    
    public Token(String tk, String ID) {
        token = new ArrayList();
        token.add(tk);
        token.add(ID);
        if(ID.compareTo("PR") == 0 || ID.compareTo("ID") == 0|| ID.compareTo("NUM") == 0 || ID.compareTo("STRING") == 0 || ID.compareTo("OP") == 0 || ID.compareTo("SIMB") == 0 || ID.compareTo("TIPO") == 0 ) // ID, NUM, STRING PR Rservada
            eFinal = true;
        else
            eFinal = false;  
    }
    
    public String getToken() {
        return token.get(0);
    }
    public String getID() {
        return token.get(1);
    }
    public boolean eFinal() {
        return eFinal;
    }
    public String getUsarNaGramatica() {
        if(token.get(1).compareTo("PR") == 0  || token.get(1).compareTo("TYPE") == 0 || token.get(1).compareTo("SIMB") == 0)
            return token.get(0);
        else
            return token.get(1);
    }
    
}
