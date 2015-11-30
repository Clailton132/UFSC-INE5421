/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formais2;

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
        //*
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


        
        /*
        a1 = OperacoesComAutomatos.criarAutomatoTotal(a1);
        a2 = OperacoesComAutomatos.criarAutomatoTotal(a2);
        a3 = OperacoesComAutomatos.criarAutomatoTotal(a3);
        a4 = OperacoesComAutomatos.criarAutomatoTotal(a4);
        a5 = OperacoesComAutomatos.criarAutomatoTotal(a5);
        a6 = OperacoesComAutomatos.criarAutomatoTotal(a6);
        a7 = OperacoesComAutomatos.criarAutomatoTotal(a7);
        a8 = OperacoesComAutomatos.criarAutomatoTotal(a8);
        a9 = OperacoesComAutomatos.criarAutomatoTotal(a9);
        a10 = OperacoesComAutomatos.criarAutomatoTotal(a10);
        a11 = OperacoesComAutomatos.criarAutomatoTotal(a11);
        a12 = OperacoesComAutomatos.criarAutomatoTotal(a12);
        a13 = OperacoesComAutomatos.criarAutomatoTotal(a13);
        a14 = OperacoesComAutomatos.criarAutomatoTotal(a14);
        a15 = OperacoesComAutomatos.criarAutomatoTotal(a15);
        a16 = OperacoesComAutomatos.criarAutomatoTotal(a16);
        a17 = OperacoesComAutomatos.criarAutomatoTotal(a17);
        a18 = OperacoesComAutomatos.criarAutomatoTotal(a18);
        a19 = OperacoesComAutomatos.criarAutomatoTotal(a19);
        a20 = OperacoesComAutomatos.criarAutomatoTotal(a20);

        
        //a1.print();
        
        /*
        a1 = OperacoesComAutomatos.MinimizarAutomato(a1);
        a2 = OperacoesComAutomatos.MinimizarAutomato(a2);
        a3 = OperacoesComAutomatos.MinimizarAutomato(a3);
        a4 = OperacoesComAutomatos.MinimizarAutomato(a4);
        a5 = OperacoesComAutomatos.MinimizarAutomato(a5);
        a6 = OperacoesComAutomatos.MinimizarAutomato(a6);
        a7 = OperacoesComAutomatos.MinimizarAutomato(a7);
        a8 = OperacoesComAutomatos.MinimizarAutomato(a8);
        a9 = OperacoesComAutomatos.MinimizarAutomato(a9);
        a10 = OperacoesComAutomatos.MinimizarAutomato(a10);
        a11 = OperacoesComAutomatos.MinimizarAutomato(a11);
        a12 = OperacoesComAutomatos.MinimizarAutomato(a12);
        a13 = OperacoesComAutomatos.MinimizarAutomato(a13);
        a14 = OperacoesComAutomatos.MinimizarAutomato(a14);
        a15 = OperacoesComAutomatos.MinimizarAutomato(a15);
        a16 = OperacoesComAutomatos.MinimizarAutomato(a16);
        a17 = OperacoesComAutomatos.MinimizarAutomato(a17);
        a18 = OperacoesComAutomatos.MinimizarAutomato(a18);
        a19 = OperacoesComAutomatos.MinimizarAutomato(a19);
        a20 = OperacoesComAutomatos.MinimizarAutomato(a20);
        //*/
        
        a1.SetID("PR");
        a2.SetID("SIMB");
        a3.SetID("PR");
        a4.SetID("PR");
        a5.SetID("TYPE");
        a6.SetID("PR");
        a7.SetID("PR");
        a8.SetID("TYPE");
        a9.SetID("COM");
        a10.SetID("STRING");
        a11.SetID("OP");
        a12.SetID("PR");
        a13.SetID("PR");
        a14.SetID("TYPE");
        a15.SetID("PR");
        a16.SetID("PR");
        a17.SetID("NUM");
        a18.SetID("OP");
        a19.SetID("OP");
        a20.SetID("OP");
        
        //a16.print();
        
      
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
        
        //Determinizador.Determinizar(ar);
        //*/
        //Automato Teste = new Automato("C:\\Programations\\Exemplo\\Testes p2\\Teste 1.txt");
        
        //Determinizador.Determinizar(Teste);
        
        //Teste.print();
        ar = new Automato(OperacoesComAutomatos.RetirarEstadosInalcancaveis(ar, ar.getEstadoInicial(), new ArrayList<Estado>()), ar.getAlfabeto());
        OperacoesComAutomatos.criarEstadoRejeitado(ar);
        //ar.print();
        
        TokensL tokens = new TokensL();
        Analisador n = new Analisador();
        n.Analise("Programa.txt", ar, tokens);

        
    }
    
}
