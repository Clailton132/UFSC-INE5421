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
       // Automato a1 = new Automato("C:\\Programations\\Exemplo\\Teste 5.txt");
        //Automato a2 = new Automato("C:\\Programations\\Exemplo\\Teste 4.txt");
        Automato a1 = new Automato("/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/TESTEMINIMIZAR.txt");
        
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
        
        //a1.print();
        
        //ArrayList <ArrayList> a3 = OperacoesComAutomatos.MinimizarAutomato(a1);
        //System.out.println(OperacoesComAutomatos.percorrerAutomato(a1, "abab"));
        System.out.println(OperacoesComAutomatos.analiseLexica(a1, "/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/Programa.txt"));
        
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
