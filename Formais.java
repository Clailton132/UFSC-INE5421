/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luca
 */
public class Formais {

	private static Estado estadoteste = new Estado("q0",2);
	public static void main(String[] args) {
		/*
        estadoteste.addTransicoes("q0,q1", 1);
        System.out.println(estadoteste.getTransicoes()[0]);
        System.out.println(estadoteste.getTransicoes()[1]);
        estadoteste.addTransicoes("q0", 1);
        System.out.println(estadoteste.getTransicoes()[0]);
        System.out.println(estadoteste.getTransicoes()[1]);
        estadoteste.addTransicoes("q1", 2);
        System.out.println(estadoteste.getTransicoes()[0]);
        System.out.println(estadoteste.getTransicoes()[1]);
        estadoteste.addTransicoes("q0,q1", 2);
        System.out.println(estadoteste.getTransicoes()[0]);
        System.out.println(estadoteste.getTransicoes()[1]);
		 */
		//new AutomatoFinito();
		//System.out.println(estadoteste.ordena("q2,q1"));
		estadoteste.ordena("q2,q1,q3,q7,q9");
		Gramatica lal = new Gramatica("Teste01.txt");
		AutomatoFinito lol = lal.transformToAutomato();
		lol.printAutomato();
	}

}
