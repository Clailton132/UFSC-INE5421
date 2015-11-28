/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirdCore;

/**
 *
 * @author Luca
 */
public class Producao {
    
    private char Cabeca;
    private String Corpo;
    
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
    
    
}
