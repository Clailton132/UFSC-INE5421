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
    String[] alfa;
    int max;
    int counter;

    public Testes() {
        
        Estado i = new Estado("q0", 2);
        Estado j = new Estado("q1", 2);
        Estado k = new Estado("q2", 2);

        i.setInicial();

        k.setFinal();

        i.addTransicoes("q1", 1);
        i.addTransicoes("q1", 2);
        j.addTransicoes("q2", 1);
        j.addTransicoes("q1", 2);
        k.addTransicoes("q1", 1);
        k.addTransicoes("q1", 2);

        es.add(i);
        es.add(j);
        es.add(k);

        alfa = new String[i.getTransicoes().length];
        alfa[0] = "a";
        alfa[1] = "b";

        max = es.size();
        counter = 0;
        
        DeterminizarE();
        Determinizar();
        
        
        for(Estado e : es){
            System.out.println(e.getNome());
            System.out.println(e.isFinal());
            System.out.println(e.isInicial());
            
            for(int l = 0; l < e.getTransicoes().length; l++){
                System.out.println(e.getTransicoes()[l]);
            }
        }


    }

    public void Determinizar() {
        String[] j = null;
        Estado Temp = null;
        ArrayList<Estado> Det = new ArrayList<Estado>();

        for (Estado e : es) {
            if (e.getTransicoes() != null) {

                String[] s = e.getTransicoes();                                 //s recebe as transicoes do estado atual no loop

                for (int i = 0; i < s.length; i++) {                            //para i menor que o tamanho de s (numero de alfabetos)
                    String t = Estado.ordena(s[i]);                             //ordena a entrada
                    if (t.split(",").length > 1) {                              //se uma transicao tiver mais que um estado no nome, cria ele
                        if (!AutomatoFinito.checkExists(es, t) && !AutomatoFinito.checkExists(Det, t)) {      //se esse estado ja existir, ignora
                            Det.add(new Estado(t, s.length));
                        }
                    }
                }
            }
        }

        for (int k = 0; k < es.size(); k++) {                                   //Para uma quantidade de vezes igual aos estados existentes.

            for (Estado e : Det) {                                              //para cada estado de Det
                for (int i = 0; i < e.getTransicoes().length; i++) {
                    if (e.getTransicoes()[i] == null) {                         //se este estado não possuir transicoes (eh um estado recem criado)
                        j = e.getNome().split(",");                             //separa o nome em suas componentes <nome> -> [<estado1>],[<estado2>]
                        Temp = e;                                               //variavel temporaria recebe o estado
                        break;
                    }
                }
            }

            if (j != null) {
                for (int t = 0; t < j.length; t++) {                                //para um t ateh o numero de estados no nome do estado
                    for (Estado e1 : es) {                                          //para cada estado
                        if (e1.getNome().equals(j[t])) {                            //se o nome do estado encontrado for igual a componente do nome
                            for (int l = 0; l < e1.getTransicoes().length; l++) {   //para cada transicao desse estado encontrado
                                String tran = Estado.ordena(e1.getTransicoes()[l]); //ordena a transicao
                                Temp.addTransicoes(tran, l + 1);                    //pega as transicoes do estado encontrado e adiciona no outro estado
                                if (e1.isFinal()) {                                 //se o estado componente for final
                                    Temp.setFinal();                                //seta o novo como final
                                }
                            }
                        }
                    }
                }
            }
        }
        es.addAll(Det);                                                         //adiciona tudo no es
        Det.clear();                                                            //limpa o det

        if (counter < max - 1) {                                                //sem esse contador, ele roda em loop infinito
            counter += 1;
            Determinizar();
        }

        if (counter == max - 1) {                                               //se ja chegou no fim criar arquivo

            //criarArquivo();
        }

    }
    
    public void DeterminizarE() {

        ArrayList<String> A1 = new ArrayList<String>();                                 //A1 de Strings

        for (Estado e : es) {
            if (!e.getTransicoes()[0].equals("") || e.getTransicoes()[0] != null) {       //se a transicao por epsilon nao for nula

                findWay(A1, e);                                                     //encontra todas as epsilon transicoes existentes

            }

            String[] j = new String[A1.size()];

            for (int i = 0; i < A1.size(); i++) {
                j[i] = A1.get(i);
            }

            Arrays.sort(j);

            String result = j[0];

            for (int i = 0; i < j.length; i++) {
                result = Estado.unirTransicoes(result, j[i]);
            }

            e.setFecho(result);

            A1.clear();
        }

        for (Estado e : es) {
            for (int i = 0; i < e.getTransicoes().length; i++) {

                String[] l = e.getTransicoes()[i].split(",");

                for (Estado e1 : es) {
                    for (int m = 0; m < l.length; m++) {

                        if (e1.getNome().equals(l[m])) {
                            e.addTransicoes(e1.getFecho(), i + 1);

                        }
                    }
                }
            }
        }

        for (Estado e : es) {
            e.retirarE();
        }

    }
    
    public String toRegex() {                                                     //daqui

        aumentarTrans(es);

        Estado ini = new Estado("qi", 2);
        Estado fim = new Estado("qf", 2);

        for (Estado e : es) {
            if (e.isInicial()) {
                ini.addTransicoes(e.getNome(), ini.getTransicoes().length);
            }
            if (e.isFinal()) {
                e.addTransicoes("qf", e.getTransicoes().length);
            }
        }

        es.add(ini);
        es.add(fim);

        int l = es.size();

        for (int i = 0; i < l - 2; i++) {   //próxima vez usa um while

            find2Remove(es.get(0));
        }
        
        return ini.getRegex()[ini.getRegex().length - 1];
    }

    public void find2Remove(Estado e) {
        String retorno = "";
        

        Estado[] before = antes(e, this.es);
        Estado[] next = depois(e, this.es);


        for (int i = 0; i < before.length; i++) {
            for (int j = 0; j < next.length; j++) {
                if (!e.getNome().equals("qi") && !e.getNome().equals("qf")) {
                    cortarEstado(before[i], e, next[j]);
                }
            }
        }

        es.remove(e);

    }

    public static Estado[] antes(Estado e, ArrayList<Estado> es) {
        ArrayList<Estado> temp = new ArrayList<Estado>();

        for (Estado l : es) {
            for (int i = 0; i < l.getTransicoes().length; i++) {
                if (l.getTransicoes()[i] != null) {
                    if (l.getTransicoes()[i].equals(e.getNome())) {
                        if (!AutomatoFinito.checkExists(temp, l.getNome())) {
                            if (!l.getNome().equals(e.getNome())) {
                                temp.add(l);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < l.getRegex().length; i++) {
                if (!AutomatoFinito.checkExists(temp, l.getNome())) {
                    if(l.getRegexS(e.getNome()).length != 0){
                        if (!l.getNome().equals(e.getNome())) {
                                temp.add(l);
                            }
                    }
                }
            }

        }

        Estado[] retorno = new Estado[temp.size()];

        for (int i = 0; i < temp.size(); i++) {
            retorno[i] = temp.get(i);
        }

        return retorno;

    }

    public static Estado[] depois(Estado e, ArrayList<Estado> es) {
        ArrayList<Estado> temp = new ArrayList<Estado>();

        for (Estado l : es) {
            for (int i = 0; i < e.getTransicoes().length; i++) {
                if (e.getTransicoes()[i] != null) {
                    if (e.getTransicoes()[i].equals(l.getNome())) {
                        if (!AutomatoFinito.checkExists(temp, l.getNome())) {
                            if (!l.getNome().equals(e.getNome())) {
                                temp.add(l);
                            }
                        }
                    }
                }
            }
        }

        Estado[] retorno = new Estado[temp.size()];

        for (int i = 0; i < temp.size(); i++) {
            retorno[i] = temp.get(i);
        }

        return retorno;

    }

    public static void aumentarTrans(ArrayList<Estado> es) {
        for (Estado e : es) {
            String[] temp = e.getTransicoes();
            e.blowT(new String[temp.length + 1]);

            for (int i = 1; i < temp.length + 1; i++) {
                e.addTransicoes(temp[i - 1], i);
            }

        }
    }

    public void cortarEstado(Estado a, Estado b, Estado c) {
        String R1 = "";
        String R2 = "";
        String R3 = "";
        String R4 = "";

        

        for (int i = 0; i < a.getTransicoes().length; i++) {
            if (a.getTransicoes()[i] == null) {
                
            } else {//if (a.getTransicoes()[i].equals(b.getNome())) {

                String[] t = a.getRegexS(b.getNome());

                
                if (R1.equals("")) {
                    R1 = t[0].split(":")[1];
                    for (int j = 1; j < t.length; j++) {
                        if (t[j] == null) {
                            break;
                        }
                        R1 = R1 + "U" + t[j].split(":")[1];
                    }
                }
            }
            
        }

        for (int i = 0; i < b.getRegex().length; i++) {
            if (b.getRegex()[i] == null) {

            } else if (b.getRegex()[i].split(":")[0].equals(b.getNome())) {

                String[] t = b.getRegexS(b.getNome());
                
                
                if (R2.equals("")) {
                    R2 = t[0].split(":")[1];
                    for (int j = 1; j < t.length; j++) {
                        if (t[j] == null) {
                            break;
                        }
                        R2 = R2 + "U" + t[j].split(":")[1];
                    }
                }
                R2 = "(" + R2 + ")*";
            }

        }

        for (int i = 0; i < b.getTransicoes().length; i++) {
            if (b.getTransicoes()[i] == null) {

            } else if (b.getTransicoes()[i].equals(c.getNome())) {

                String[] t = b.getRegexS(c.getNome());

                if (R3.equals("")) {
                    R3 = t[0].split(":")[1];
                    for (int j = 1; j < t.length; j++) {
                        if (t[j] == null) {
                            break;
                        }
                        R3 = R3 + "U" + t[j].split(":")[1];
                    }
                }
            }

        }

        for (int i = 0; i < a.getTransicoes().length; i++) {
            if (a.getTransicoes()[i] == null) {

            } else if (a.getTransicoes()[i].equals(c.getNome())) {

                String[] t = a.getRegexS(c.getNome());

                if (R4.equals("")) {

                    R4 = t[0].split(":")[1];
                    for (int j = 1; j < t.length; j++) {
                        if (t[j] == null) {
                            break;
                        }
                        R4 = R4 + "U" + t[j].split(":")[1];
                    }
                }
            }

        }

        String Regex = "";
        if (!R1.trim().equals("")) {
            Regex = Regex + "(" + R1 + ")";
        }
        if (!R2.trim().equals("")) {
            Regex = Regex + R2;
        }
        if (!R3.trim().equals("")) {
            if(!R3.trim().equals((c.getTransicoes().length )+ ""))
            Regex = Regex + "(" + R3 + ")";
        }

        Regex = Regex + "";

        if (!R4.trim().equals("")) {
            Regex = Regex + " U " + "(" + R4 + ")";
        }

        
        a.addRegex(c.getNome(), Regex);                                         //quando refazer só usar transiçoes
        
    }

    public static Estado findEstado(String nome, ArrayList<Estado> es) {
        for (Estado e : es) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
    }                                                                           // até aqui


    public void findWay(ArrayList<String> Ae, Estado in) {

        for (String e : Ae) {
            if (in.getNome().equals(e)) {
                return;
            }
        }

        Ae.add(in.getNome());

        String[] temp = in.getTransicoes();

        if (temp[0].split(",").length > 1) {

            for (Estado e : es) {

                for (int l = 0; l < temp[0].split(",").length; l++) {

                    if (e.getNome().equals(temp[0].split(",")[l])) {

                        findWay(Ae, e);

                    }
                }
            }
        } else {

            for (Estado e : es) {
                if (e.getNome().equals(temp[0])) {
                    findWay(Ae, e);
                }
            }
        }
    }

}