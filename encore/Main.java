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
        Automato a1 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\beg.txt");
        Automato a2 = new Automato("C:\\Programations\\Exemplo\\Automatos Reconhecedores\\simbolos.txt");
        //Automato a2 = new Automato("C:\\Programations\\Exemplo\\Teste 4.txt");
        //Automato a1 = new Automato("/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/TESTEMINIMIZAR.txt");
        
        //Determinizador.Determinizar(a1);
        /*
        Automato a2 = new Automato("C:\\Programations\\Exemplo\\Teste 2.txt");
        
        Automato[] automatos = new Automato[2];
        automatos[0] = a1;
        automatos[1] = a2;
        
        Automato a3 = OperacoesComAutomatos.UniaoMinimizacaoDeAutomatos(automatos);
        a3.print();
        a3.escreverArquivo("C:\\Programations\\Exemplo\\Resultado.txt");
        */
        //Determinizador.prepararEpsilons(a1.getEstados());
        //a1.print();
        //a2.print();
        
        a1.print();
        
        a1 = OperacoesComAutomatos.criarAutomatoTotal(a1);
        a2 = OperacoesComAutomatos.criarAutomatoTotal(a2);
        
        a1.print();
        a2.print();
        
        Automato[] automatos = new Automato[2];
        automatos[0] = a1;
        automatos[1] = a2;
        Automato a3 = OperacoesComAutomatos.UniaoDeAutomatos(automatos);
        Determinizador.Determinizar(a3);
        a3.print();
        
        OperacoesComAutomatos.analiseLexica(a3,"C:\\Programations\\Exemplo\\Testes p2\\Programa.txt");
        System.out.println(OperacoesComAutomatos.percorrerAutomato(a1, "abab"));
        System.out.println(OperacoesComAutomatos.percorrerAutomato(a2, "abab"));
        System.out.println(OperacoesComAutomatos.percorrerAutomato(a3, "abab"));
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a4, "abab"));
        //System.out.println(OperacoesComAutomatos.analiseLexica(a1, "/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/Programa.txt"));
        
        /*
        for(ArrayList<Estado> a : a3) {
            System.out.print("{");
            for(Estado e : a){
                System.out.print(e.getNome());
            }
            System.out.println("}");
        }
                */
    //yaaaaaaaaaaaaay
    }
    
}
