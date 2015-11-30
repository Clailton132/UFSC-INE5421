/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thirdCore;

import Formais2.TokensL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author gabluz
 */
public class Sintatico {
    private static HashMap<String, HashMap<String, Producao>> tabela;
    
    
    /*Metodo para fazer uma conversão do formato utilizado para tokens do trabalho 2 para o trabalho 3*/
    public static ArrayList<Token> tokensToToken(TokensL tokensOLD) {
        
        ArrayList<String[]> listTokensOLD = tokensOLD.getAll();
        ArrayList<Token> newTokens = new ArrayList();
        for(String[] a : listTokensOLD) {
            Token newToken = new Token(a[0], a[1]);
            newTokens.add(newToken);
        }
        
        return newTokens;
    }
    
    /*AnaliseSintatic Preditiva Nao Recursiva
    Parametros
    Lista com os tokens feita pelo trabalho 2 e adaptada pelo metodo tokensToToken, tabela de produções feitas a mão e criadas no main de Formais 3
    */
    public static TreeNode<Token> analise(ArrayList<Token> tokens, HashMap<String, HashMap<String, Producao>> tab) {
        
        /*Teste, se tirar o ultimo ; ou qualquer outro elemento deste programa basico acusará erro sintatico*/
        Token teste = new Token("prg", "PR");
        tokens.add(teste);
        teste = new Token(null, "ID");
        tokens.add(teste);
        teste = new Token("var", "PR");
        tokens.add(teste);
        teste = new Token(null, "ID");
        tokens.add(teste);
        teste = new Token(":", "SIMB");
        tokens.add(teste);
        teste = new Token("int", "TIPO");
        tokens.add(teste);
        teste = new Token(";", "SIMB");
        tokens.add(teste);
        teste = new Token("go", "PR");
        tokens.add(teste);
        teste = new Token(null, "ID");
        tokens.add(teste);
        teste = new Token("=", "SIMB");
        tokens.add(teste);
        teste = new Token("9", "NUM");
        tokens.add(teste);
        teste = new Token(";", "SIMB");
        tokens.add(teste);
        teste = new Token("end", "PR");
        tokens.add(teste);
        teste = new Token(";", "SIMB");
        tokens.add(teste);
        //teste = new Token("end", "PR");
        //tokens.add(teste);
        
        
        
        tabela = tab;
        Stack pilha = new Stack();
        Token t = new Token("$", "PR");
        tokens.add(t); //Adiciona $ no fim da entrada
        pilha.push(t);//Adiciona $ no fundo da pilha
        t = new Token(null, "S");
        pilha.push(t); //Pilha agora é $S
        
        Token topoPilha;
        String X;
        Producao prod;
        ArrayList<Token> producao;
        Token helpwhile;
        int apontador = 0;
        Token entrada;
        
        ArrayList<Producao> saida = new ArrayList();
        Token ultimoTopo = new Token(null, "PR");
        
        do {
            topoPilha = (Token) pilha.peek();
            entrada = tokens.get(apontador);
            if(topoPilha.eFinal()) {
                X = topoPilha.getUsarNaGramatica();
                if(X.compareTo(entrada.getUsarNaGramatica()) == 0) { 
                    ultimoTopo = (Token) pilha.pop();
                    apontador++;
                } else {
                    System.out.println("Expected '" + topoPilha.getToken() + "' After '" + ultimoTopo.getToken() + "'");
                    return null;
                }
            } else {
                prod = tabela.get(topoPilha.getUsarNaGramatica()).get(entrada.getUsarNaGramatica());
                if(prod != null) {
                    pilha.pop();
                    producao = prod.getCorpo();
                    if(prod.getCorpo().get(0).getUsarNaGramatica().compareTo("&") != 0) {
                        for (int i = producao.size() - 1; i > -1; i--) {
                            pilha.push(producao.get(i));  
                        }
                        saida.add(prod);
                    }
                } else {
                    System.out.println("Erro. Missing Something between '" + ultimoTopo.getToken() + "' and '" + entrada.getUsarNaGramatica() +"'" );
                    return null;
                }
            }
            helpwhile = (Token) pilha.peek();
        } while(helpwhile.getUsarNaGramatica().compareTo("$") != 0);
        
        if(tokens.get(apontador).getUsarNaGramatica().compareTo("$") != 0) {
            System.out.println("DEU TRETA");
            return null;
        }
        
        
        
        
        System.out.println("Everything is OK");
        //Gerar arvore gramatical
        TreeNode<Token> tree = new TreeNode(new Token(null, "S"));
        Sintatico.doTree(tree, saida);
        return tree;
    }
    
    public static void doTree(TreeNode nodoatual, ArrayList<Producao> saida ) {
        if(!saida.isEmpty()) {
            Token t = (Token) nodoatual.data;
            if(!t.eFinal()) {
                for(int i = 0; i < saida.get(0).getCorpo().size(); i++) {
                    nodoatual.addChild(saida.get(0).getCorpo().get(i));
                }
                saida.remove(0);
                for(int i = 0; i < nodoatual.children.size(); i++) {
                    TreeNode filho = (TreeNode) nodoatual.children.get(0);
                    Sintatico.doTree(filho, saida);
                }
                
            }
        }
        
    }
    
}//
