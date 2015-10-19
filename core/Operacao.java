/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author Luca
 */
public class Operacao {

    public static AutomatoFinito Uniao(AutomatoFinito[] automatos) {

        String[] alfa = automatos[0].getAlfa();  //como todos os automatos deverão ter a mesma linguagem pode

        int count = 1;

        ArrayList<Estado> Retorno = new ArrayList<Estado>();

        Estado novoComeco = new Estado("q0", alfa.length);

        Retorno.add(novoComeco);

        for (int i = 0; i < automatos.length; i++) {

            automatos[i].DeterminizarE();                                                  //tecnicamente agora sempre tem E

            //automatos[i].minimizar();     a fazer
            ArrayList<Estado> Estados = automatos[i].getEstados();

            for (Estado e : Estados) {
                e.rename("q" + count);
                count++;

                if (e.isInicial()) {
                    novoComeco.addTransicoes(e.getNome(), 1);
                    e.desetInicial();
                }

                Retorno.add(e);
            }
            
            System.out.println("\n");
        }

        AutomatoFinito automatoFinal = new AutomatoFinito(Retorno);
        automatoFinal.setAlfa(alfa);

        return automatoFinal;
    }

}
