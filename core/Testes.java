package core;

import core.Estado;
import core.AutomatoFinito;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luca
 */
public class Testes {

    ArrayList<Estado> es = new ArrayList<Estado>();
    ArrayList<Estado> es1 = new ArrayList<Estado>();
    String[] alfa;

    public Testes() {

        Estado i = new Estado("q0", 3);
        Estado j = new Estado("q1", 3);
        Estado k = new Estado("q2", 3);
        
        Estado l = new Estado("q0", 3);
        Estado m = new Estado("q1", 3);
        Estado n = new Estado("q2", 3);

        i.setInicial();
        
        l.setInicial();

        k.setFinal();
        
        n.setInicial();

        i.addTransicoes("", 1);
        i.addTransicoes("q1", 2);
        i.addTransicoes("q1", 3);
        j.addTransicoes("", 1);
        j.addTransicoes("q2", 2);
        j.addTransicoes("q1", 3);
        k.addTransicoes("", 1);
        k.addTransicoes("q1", 2);
        k.addTransicoes("q1", 3);
        
        l.addTransicoes("", 1);
        l.addTransicoes("q1", 2);
        l.addTransicoes("q1", 3);
        n.addTransicoes("", 1);
        m.addTransicoes("q2", 2);
        m.addTransicoes("q1", 3);
        n.addTransicoes("", 1);
        n.addTransicoes("q1", 2);
        n.addTransicoes("q1", 3);

        es.add(i);
        es.add(j);
        es.add(k);
        
        es1.add(i);
        es1.add(j);
        es1.add(k);

        alfa = new String[i.getTransicoes().length];
        alfa[0] = "E";
        alfa[1] = "a";
        alfa[2] = "b";

        AutomatoFinito a = new AutomatoFinito(es);
        a.setAlfa(alfa);
        AutomatoFinito b = new AutomatoFinito(es1);
        b.setAlfa(alfa);
        
        AutomatoFinito[] automatos = new AutomatoFinito[2];
        
        automatos[0] = a;
        automatos[1] = b;
        
        AutomatoFinito c = Operacao.Uniao(automatos);

        c.printAutomato();

    }
}
