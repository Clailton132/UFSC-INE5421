/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirdCore;

import Formais2.Analisador;
import Formais2.Automato;
import Formais2.Estado;
import Formais2.OperacoesComAutomatos;
import Formais2.TokensL;
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
         
         
         //
         
         Automato a1 = new Automato("prg.txt");
        Automato a2 = new Automato("simbolos.txt");
        Automato a3 = new Automato("go.txt");
        Automato a4 = new Automato("var.txt");
        Automato a5 = new Automato("str.txt");
        Automato a6 = new Automato("wrt.txt");
        Automato a7 = new Automato("end.txt");
        Automato a8 = new Automato("boo.txt");
        Automato a9 = new Automato("Comentarios.txt");
        Automato a10 = new Automato("String.txt");
        Automato a11 = new Automato("operadores.txt");
        Automato a12 = new Automato("yel.txt");
        Automato a13 = new Automato("hif.txt");
        Automato a14 = new Automato("int.txt");
        Automato a15 = new Automato("red.txt");
        Automato a16 = new Automato("lop.txt");
        Automato a17 = new Automato("num.txt");
        Automato a18 = new Automato("and.txt");
        Automato a19 = new Automato("or.txt");
        Automato a20 = new Automato("not.txt");

        
        a1.SetID("PR");
        a2.SetID("SIMB");
        a3.SetID("PR");
        a4.SetID("PR");
        a5.SetID("TIPO");
        a6.SetID("PR");
        a7.SetID("PR");
        a8.SetID("TIPO");
        a9.SetID("COM");
        a10.SetID("STRING");
        a11.SetID("OP");
        a12.SetID("PR");
        a13.SetID("PR");
        a14.SetID("TIPO");
        a15.SetID("PR");
        a16.SetID("PR");
        a17.SetID("NUM");
        a18.SetID("OP");
        a19.SetID("OP");
        a20.SetID("OP");
        
        
      
        Automato[] automatos = new Automato[20];
        automatos[0] = a1;
        automatos[1] = a2;
        automatos[2] = a3;
        automatos[3] = a4;
        automatos[4] = a5;
        automatos[5] = a6;
        automatos[6] = a7;
        automatos[7] = a8;
        automatos[8] = a9;
        automatos[9] = a10;
        automatos[10] = a11;
        automatos[11] = a12;
        automatos[12] = a13;
        automatos[13] = a14;
        automatos[14] = a15;
        automatos[15] = a16;
        automatos[16] = a17;
        automatos[17] = a18;
        automatos[18] = a19;
        automatos[19] = a20;
        Automato ar = OperacoesComAutomatos.UniaoD(automatos);
        ar = OperacoesComAutomatos.criarAutomatoTotal(ar);
        ar.SetID("HOLLY");

        
        ar = new Automato(OperacoesComAutomatos.RetirarEstadosInalcancaveis(ar, ar.getEstadoInicial(), new ArrayList<Estado>()), ar.getAlfabeto());
        OperacoesComAutomatos.criarEstadoRejeitado(ar);
        
        TokensL tokensL = new TokensL();
        Analisador n = new Analisador();
        n.Analise("Programa.txt", ar, tokensL);
         
         //
        
        ArrayList<Token> t = Sintatico.tokensToToken(tokensL);
        
         
        TreeNode<Token> arvoreGramatical = Sintatico.analise(t, tabela);
         
        //System.out.println(Sintatico.analise(new ArrayList(), tabela));
        
        
        
        
    }
    
}
