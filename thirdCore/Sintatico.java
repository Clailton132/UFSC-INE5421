/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thirdCore;

import Formais2.Tokens;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author gabluz
 */
public class Sintatico {
    private static HashMap<String, HashMap<String, Producao>> tabela;
    
    public static boolean analise(ArrayList<Token> tokens) {
        
        Stack pilha = new Stack();
        Token t = new Token("$", "PR");
        tokens.add(t); //Adiciona $ no fim da entrada
        pilha.push(t);//Adiciona $ no fundo da pilha
        t = new Token("S", null);
        pilha.push(t); //Pilha agora é $S
        
        Token topoPilha;
        String X;
        Producao prod;
        ArrayList<Token> producao;
        Token helpwhile;
        int apontador = 0;
        Token entrada;
        
        ArrayList<Producao> saida = new ArrayList();

        do {
            topoPilha = (Token) pilha.peek();
            entrada = tokens.get(apontador);
            if(topoPilha.eFinal()) {
                X = topoPilha.getUsarNaGramatica();
                if(X.compareTo(entrada.getUsarNaGramatica()) == 0) {
                    pilha.pop();
                    apontador++;
                }  
            } else {
                prod = tabela.get(topoPilha.getUsarNaGramatica()).get(entrada.getUsarNaGramatica());
                if(prod != null) {
                    pilha.pop();
                    producao = prod.getCorpo();
                    for (int i = producao.size() - 1; i > -1; i--) {
                        pilha.push(producao.get(i));
                    }
                    //pilha.push(prod.getCabeca());
                    saida.add(prod);
                } else {
                    return false;
                }
            }
            helpwhile = (Token) pilha.peek();
            
        } while(helpwhile.getUsarNaGramatica().compareTo("$") != 0);
        
        return true;
        
    }
    
    /*
    public static boolean analiseLLONE(ArrayList<Tokens> tokens, Tokens allTokens) {
        //Stack semantic = new Stack();//SEMANTIC STACK e para arvore!
        
        Stack pilha = new Stack(); //Pilha
        
        Tokens t = new Tokens(); //Auxiliar para empilhar $ e S
        t.addToken("$", "PR"); //Cria token $ terminal
        tokens.add( t); //adiciona $ final da entrada
        pilha.push(t); //Adiciona $ na pilha
        t = new Tokens();
        t.addToken("S", null); //S inciial nao terminal na pilha
        pilha.push(t);

        
        Tokens topoPilha;
        String X;
        Producao prod;
        ArrayList<Tokens> producao;
        Tokens helpwhile;
        int apontador = 0;
        Tokens entrada;
        
        ArrayList<String[]> alltokens = allTokens.getAll();
        ArrayList<Producao> saida = new ArrayList();

        do {
            topoPilha = (Tokens) pilha.peek();
            entrada = tokens.get(apontador);
            if(topoPilha.eFinal()) {
                X = topoPilha.getToken(0)[0];
                if(X.compareTo(entrada.getToken(0)[0]) == 0) {
                    pilha.pop();
                    apontador++;
                }  
            } else {
                if(entrada.getToken(0)[1].compareTo("PR") == 0)
                    prod = tabela.get(topoPilha.getToken(1)[0]).get(entrada.getToken(0)[0]);
                else
                prod = tabela.get(topoPilha.getToken(1)[0]).get(entrada.getToken(1)[0]);
                if(prod != null) {
                    pilha.pop();
                   // producao = prod.getCorpo();
                   // for (int i = producao.size() - 1; i > -1; i--) {
                     //   pilha.push(producao.get(i));
                    }
                    pilha.push(prod.getCabeca());
                    saida.add(prod);
             //   } else {
               //     return false;
                //}
            }
            helpwhile = (Tokens) pilha.peek();
            
        } while(helpwhile.getToken(0)[0].compareTo("$") != 0);
        
        return true;
    }
    */
    
}
