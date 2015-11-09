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
        Automato a1 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\prg.txt");
        Automato a2 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\simbolos.txt");
        Automato a3 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\beg.txt");
        Automato a4 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\var.txt");
        Automato a5 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\str.txt");
        Automato a6 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\wrt.txt");
        Automato a7 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\end.txt");
        //Automato a2 = new Automato("C:\\Programations\\Exemplo\\Teste 4.txt");
        //Automato a1 = new Automato("/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/TESTEMINIMIZAR.txt");
        

        
        //a1.print();
        
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
        
        //*
        Automato[] automatos = new Automato[7];
        automatos[0] = a1;
        automatos[1] = a2;
        automatos[2] = a3;
        automatos[3] = a4;
        automatos[4] = a5;
        automatos[5] = a6;
        automatos[6] = a7;
        
        Automato ar = OperacoesComAutomatos.UniaoDeAutomatos(automatos);
        OperacoesComAutomatos.ConsertoDeUnião(ar);
        Determinizador.Determinizar(ar);
        ar = new Automato(OperacoesComAutomatos.RetirarEstadosInalcancaveis(ar, ar.getEstadoInicial(), new ArrayList<Estado>()), ar.getAlfabeto());
        OperacoesComAutomatos.criarEstadoRejeitado(a3);
        ar.print();
        //*/
        
        System.out.print(OperacoesComAutomatos.analiseLexica(ar,"C:\\Programations\\Exemplo\\Testes p2\\Programa.txt"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a1, "abab"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a2, "abab"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a3, "beg"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a3, ":"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a4, "abab"));
        //System.out.println(OperacoesComAutomatos.analiseLexica(a1, "/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/Programa.txt"));
        
    }
    
}
