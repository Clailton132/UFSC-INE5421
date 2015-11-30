/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirdCore;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Luca
 */
public class Formais3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        HashMap<String, HashMap<String, Producao>> tabela = new HashMap();
        
        HashMap<String, Producao> ps = new HashMap();
        
        ArrayList<Token> tokens = new ArrayList();
        
        tokens.add(new Token("prg","PR"));
        tokens.add(new Token(null,"ID"));
        tokens.add(new Token(null, "VAR"));
        tokens.add(new Token("go", "PR"));
        tokens.add(new Token(null, "CMD"));
        tokens.add(new Token("end", "PR"));
        tokens.add(new Token(";", "SIMB"));
        
        Producao p = new Producao(tokens);
        
        ps.put("prg", p);
        
        tabela.put("S", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("var","PR"));
        tokens.add(new Token(null, "ID"));
        tokens.add(new Token(":", "SIMB"));
        tokens.add(new Token(null, "TIPO"));
        tokens.add(new Token(";", "SIMB"));

         p = new Producao(tokens);
        
        ps.put("var", p);
        
        tabela.put("VAR", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("hif", "PR"));
        tokens.add(new Token("(", "SIMB"));
        tokens.add(new Token(null, "N"));
        tokens.add(new Token(null, "ID"));
        tokens.add(new Token(null, "I"));
        tokens.add(new Token(")","SIMB"));
        tokens.add(new Token("{", "SIMB"));
        tokens.add(new Token(null, "CMD"));
        tokens.add(new Token("}", "SIMB"));
        tokens.add(new Token(null, "E"));
        

         p = new Producao(tokens);
        
        ps.put("hif", p);
        
        //tabela.put("CMD", ps);
        
        //
        
        //ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("lop", "PR"));
        tokens.add(new Token("(", "SIMB"));
        tokens.add(new Token(null, "N"));
        tokens.add(new Token(null, "ID"));
        tokens.add(new Token(null, "I"));
        tokens.add(new Token(")","SIMB"));
        tokens.add(new Token("{", "SIMB"));
        tokens.add(new Token(null, "CMD"));
        tokens.add(new Token("}", "SIMB"));

         p = new Producao(tokens);
        
        ps.put("lop", p);
        
        //tabela.put("CMD", ps);
        
        //
        
        //ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("red", "PR"));
        tokens.add(new Token("(", "SIMB"));
        tokens.add(new Token(null, "V"));
        tokens.add(new Token(")","SIMB"));
        tokens.add(new Token(";", "TIPO"));


         p = new Producao(tokens);
        
        ps.put("red", p);
        
        //tabela.put("CMD", ps);
        
        //
        
        //ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("wrt", "PR"));
        tokens.add(new Token("(", "SIMB"));
        tokens.add(new Token(null, "V"));
        tokens.add(new Token(")","SIMB"));
        tokens.add(new Token(";", "TIPO"));


         p = new Producao(tokens);
        
        ps.put("wrt", p);
        
        //tabela.put("CMD", ps);
        
        //
        
        //ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "ID"));
        tokens.add(new Token("=","SIMB"));
        tokens.add(new Token(null, "I"));
        tokens.add(new Token(";", "SIMB"));


         p = new Producao(tokens);
        
        ps.put("ID", p);
        
        tabela.put("CMD", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("yel", "PR"));
        tokens.add(new Token("{","SIMB"));
        tokens.add(new Token(null, "CMD"));
        tokens.add(new Token("}", "SIMB"));


         p = new Producao(tokens);
        
        ps.put("yel", p);
        
        //
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "&"));


         p = new Producao(tokens);
        
        ps.put("end", p);
        ps.put("}", p);
        
        tabela.put("E", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("int", "TYPE"));

         p = new Producao(tokens);
        
        ps.put("int", p);
        
        //
        
        tokens = new ArrayList();
        
        tokens.add(new Token("str", "TYPE"));

         p = new Producao(tokens);
        
        ps.put("str", p);
        
        //
        
        tokens = new ArrayList();
        
        tokens.add(new Token("boo", "TYPE"));

         p = new Producao(tokens);
        
        ps.put("boo", p);
        
        tabela.put("TIPO", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "V"));
        tokens.add(new Token(null, "T"));

         p = new Producao(tokens);
        
        ps.put("NUM", p);
        ps.put("STRING", p);
        
        tabela.put("I", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "SM"));
        tokens.add(new Token(null, "V"));

         p = new Producao(tokens);
        
        ps.put("+", p);
        ps.put("-", p);
        ps.put("*", p);
        ps.put("/", p);
        ps.put("and", p);
        ps.put("or", p);
        
 
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "&"));
        
         p = new Producao(tokens);
        
        ps.put(";", p);
        ps.put(")", p);
        
        tabela.put("T", ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("+", "OP"));

         p = new Producao(tokens);
         
        ps.put("+", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token("/", "OP"));

         p = new Producao(tokens);
         
        ps.put("/", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token("-", "OP"));

         p = new Producao(tokens);
         
        ps.put("-", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token("*", "OP"));

         p = new Producao(tokens);
         
        ps.put("*", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token("and", "OP"));

         p = new Producao(tokens);
         
        ps.put("and", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token("or", "OP"));

         p = new Producao(tokens);
         
        ps.put("or", p);
        
        tabela.put("SM",ps);
        
        //
        
        ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token("not", "OP"));

         p = new Producao(tokens);
         
        ps.put("not", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "&"));

         p = new Producao(tokens);
         
         ps.put("ID", p);
         
         tabela.put("N", ps);
         
         //
         
          ps = new HashMap();
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "NUM"));

         p = new Producao(tokens);
         
        ps.put("NUM", p);
        
        tokens = new ArrayList();
        
        tokens.add(new Token(null, "STRING"));

         p = new Producao(tokens);
         
         ps.put("STRING", p);
         
         tabela.put("V", ps);
         
         
        TreeNode<Token> arvoreGramatical = Sintatico.analise(new ArrayList(), tabela);
         
        //System.out.println(Sintatico.analise(new ArrayList(), tabela));
        
        
        
        
    }
    
}
