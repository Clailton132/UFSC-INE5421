/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

/**
 *
 * @author Luca
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Automato a1 = new Automato("C:\\Programations\\Exemplo\\Teste 1.txt");
        Determinizador.Determinizar(a1);
        a1.print();
        //Determinizador.prepararEpsilons(a1.getEstados());
    }
    
}
