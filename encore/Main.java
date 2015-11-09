/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

import java.util.ArrayList;

/**
 *
 * @author Luca
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
        Automato a1 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\prg.txt");
        Automato a2 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\simbolos.txt");
        Automato a3 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\beg.txt");
        Automato a4 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\var.txt");
        Automato a5 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\str.txt");
        Automato a6 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\wrt.txt");
        Automato a7 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\end.txt");
        */
        
        
        Automato beg = new Automato("AutomatosReconhecedores.beg.txt");
        Automato boo = new Automato("AutomatosReconhecedores.boo.txt");
        Automato chr = new Automato("AutomatosReconhecedores.chr.txt");
        Automato els = new Automato("AutomatosReconhecedores.els.txt");
        Automato end = new Automato("AutomatosReconhecedores.end.txt");
        Automato if0 = new Automato("AutomatosReconhecedores.if.txt");
        Automato int0 = new Automato("AutomatosReconhecedores.int.txt");
        Automato prg = new Automato("AutomatosReconhecedores.prg.txt");
        Automato red = new Automato("AutomatosReconhecedores.red.txt");
        Automato simbolos = new Automato("AutomatosReconhecedores.simbolos.txt");
        Automato str = new Automato("AutomatosReconhecedores.str.txt");
        Automato var = new Automato("AutomatosReconhecedores.var.txt");
        Automato wil = new Automato("AutomatosReconhecedores.wil.txt");
        Automato wrt = new Automato("AutomatosReconhecedores.wrt.txt");
        
        
        beg = OperacoesComAutomatos.criarAutomatoTotal(beg);
        boo = OperacoesComAutomatos.criarAutomatoTotal(boo);
        chr = OperacoesComAutomatos.criarAutomatoTotal(chr);
        els = OperacoesComAutomatos.criarAutomatoTotal(els);
        end = OperacoesComAutomatos.criarAutomatoTotal(end);
        if0 = OperacoesComAutomatos.criarAutomatoTotal(if0);
        int0 = OperacoesComAutomatos.criarAutomatoTotal(int0);
        prg = OperacoesComAutomatos.criarAutomatoTotal(prg);
        red = OperacoesComAutomatos.criarAutomatoTotal(red);
        simbolos = OperacoesComAutomatos.criarAutomatoTotal(simbolos);
        str = OperacoesComAutomatos.criarAutomatoTotal(str);
        var = OperacoesComAutomatos.criarAutomatoTotal(var);
        wil = OperacoesComAutomatos.criarAutomatoTotal(wil);
        wrt = OperacoesComAutomatos.criarAutomatoTotal(wrt);
        

        beg = OperacoesComAutomatos.MinimizarAutomato(beg);
        boo = OperacoesComAutomatos.MinimizarAutomato(boo);
        chr = OperacoesComAutomatos.MinimizarAutomato(chr);
        els = OperacoesComAutomatos.MinimizarAutomato(els);
        end = OperacoesComAutomatos.MinimizarAutomato(end);
        if0 = OperacoesComAutomatos.MinimizarAutomato(if0);
        int0 = OperacoesComAutomatos.MinimizarAutomato(int0);
        prg = OperacoesComAutomatos.MinimizarAutomato(prg);
        red = OperacoesComAutomatos.MinimizarAutomato(red);
        simbolos = OperacoesComAutomatos.MinimizarAutomato(simbolos);
        str = OperacoesComAutomatos.MinimizarAutomato(str);
        var = OperacoesComAutomatos.MinimizarAutomato(var);
        wil = OperacoesComAutomatos.MinimizarAutomato(wil);
        wrt = OperacoesComAutomatos.MinimizarAutomato(wrt);
        
        Automato[] automatos = new Automato[14];
        automatos[0] = beg;
        automatos[1] = boo;
        automatos[2] = chr;
        automatos[3] = els;
        automatos[4] = end;
        automatos[5] = if0;
        automatos[6] = int0;
        automatos[7] = prg;
        automatos[8] = red;
        automatos[9] = simbolos;
        automatos[10] = str;
        automatos[11] = var;
        automatos[12] = wil;
        automatos[13] = wrt;
        
        Automato ar = OperacoesComAutomatos.UniaoDeAutomatos(automatos);
        OperacoesComAutomatos.ConsertoDeUnião(ar);
        Determinizador.Determinizar(ar);
        ar = new Automato(OperacoesComAutomatos.RetirarEstadosInalcancaveis(ar, ar.getEstadoInicial(), new ArrayList<Estado>()), ar.getAlfabeto());
        OperacoesComAutomatos.criarEstadoRejeitado(ar);
        new gui.AnalisadorLexico(ar).setVisible(true);
        
                ;
        //ar.print();
        //*/
        
        
        
        //System.out.print(OperacoesComAutomatos.analiseLexica(ar,"C:\\Programations\\Exemplo\\Testes p2\\Programa.txt"));
        
        /*
        
        a1 = OperacoesComAutomatos.criarAutomatoTotal(a1);
        a2 = OperacoesComAutomatos.criarAutomatoTotal(a2);
        a3 = OperacoesComAutomatos.criarAutomatoTotal(a3);
        a4 = OperacoesComAutomatos.criarAutomatoTotal(a4);
        a5 = OperacoesComAutomatos.criarAutomatoTotal(a5);
        a6 = OperacoesComAutomatos.criarAutomatoTotal(a6);
        a7 = OperacoesComAutomatos.criarAutomatoTotal(a7);
        
        
        
        //a1.print();
        
        
        a1 = OperacoesComAutomatos.MinimizarAutomato(a1);
        a2 = OperacoesComAutomatos.MinimizarAutomato(a2);
        a3 = OperacoesComAutomatos.MinimizarAutomato(a3);
        a4 = OperacoesComAutomatos.MinimizarAutomato(a4);
        a5 = OperacoesComAutomatos.MinimizarAutomato(a5);
        a6 = OperacoesComAutomatos.MinimizarAutomato(a6);
        a7 = OperacoesComAutomatos.MinimizarAutomato(a7);
        
        //a1.print();
        //a2.print();
        
      
        Automato[] automatos = new Automato[7];
        automatos[0] = a1;
        automatos[1] = a2;
        automatos[2] = a3;
        automatos[3] = a4;
        automatos[4] = a5;
        automatos[5] = a6;
        automatos[6] = a7;
        */
        
        //Automato ar = OperacoesComAutomatos.UniaoDeAutomatos(automatos);
       // OperacoesComAutomatos.ConsertoDeUnião(ar);
       // Determinizador.Determinizar(ar);
       // ar = new Automato(OperacoesComAutomatos.RetirarEstadosInalcancaveis(ar, ar.getEstadoInicial(), new ArrayList<Estado>()), ar.getAlfabeto());
       // OperacoesComAutomatos.criarEstadoRejeitado(a3);
        //ar.print();
        //*/
        
       // System.out.print(OperacoesComAutomatos.analiseLexica(ar,"C:\\Programations\\Exemplo\\Testes p2\\Programa.txt"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a1, "abab"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a2, "abab"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a3, "beg"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a3, ":"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a4, "abab"));
        //System.out.println(OperacoesComAutomatos.analiseLexica(a1, "/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/Programa.txt"));
        
    }
    
}
