/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formais2;

import static Formais2.OperacoesComAutomatos.PercorrerFrase2;
import java.util.Scanner;

/**
 *
 * @author Luca
 */
public class Analisador {
    
    public Analisador(){
        
    }
    
    public void Analise(String programa, Automato automato, TokensL tokens){
        
        Scanner s = new Scanner(getClass().getResourceAsStream(programa));
        while(s.hasNext()){
            
            int i = 0;
            
            String frase = s.next();
            
            PercorrerFrase2(automato, frase, i, tokens);
            
            i++;
        }
    }
}
