
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

    public Testes() {
        Estado i = new Estado("q0", 2);
        Estado j = new Estado("q1", 2);
        Estado k = new Estado("q2", 2);
        Estado p = new Estado("q3", 2);

        i.setInicial();
        p.setFinal();
        k.setFinal();

        i.addTransicoes("q1", 1);
        i.addTransicoes("q1", 2);
        j.addTransicoes("q2", 1);
        j.addTransicoes("q1", 2);
        k.addTransicoes("q3", 1);
        k.addTransicoes("q1", 2);
        p.addTransicoes("q1", 1);
        p.addTransicoes("q1", 2);

        es.add(i);
        es.add(j);
        es.add(k);
        es.add(p);

        alfa = new String[i.getTransicoes().length];
        alfa[0] = "a";
        alfa[1] = "b";

        //DeterminizarE();
        toRegex();

        
         for (Estado e : es) {
         System.out.println(e.getNome());
         System.out.println(e.getFecho());
         System.out.println(e.isInicial());
         System.out.println(e.isFinal());
         for (int b = 0; b < e.getTransicoes().length; b++) {
         System.out.println(b + " : " + e.getTransicoes()[b]);
         }
         for (int b = 0; b < e.getRegex().length; b++) {
         System.out.println(b + " : " + e.getRegex()[b]);
         }
         }

    }

    public void toRegex() {

        aumentarTrans(es);

        Estado ini = new Estado("qi", 1);
        Estado fim = new Estado("qf", 1);

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

        find2Remove(ini);

    }

    public void find2Remove(Estado e) {
        Estado[] next = new Estado[e.getTransicoes().length];
        Estado[] next2 = new Estado[alfa.length];

        for (int i = 0; i < e.getTransicoes().length; i++) {
            next[i] = findEstado(e.getTransicoes()[i], es);

            for (int j = 0; j < alfa.length; j++) {
                next2[j] = findEstado(next[i].getTransicoes()[j], es);
                cortarEstado(e, next[i], next2[j]);
                break;
            }
            break;
        }

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

            } else if (a.getTransicoes()[i].equals(b.getNome())) {

                if (R1.equals("")) {
                    R1 = a.getRegex()[i].split(":")[1];

                } else {
                    R1 = R1 + "U " + a.getRegex()[i].split(":")[1];
                }
                R1 = "(" + R1 + ")";
            }
        }

        for (int i = 0; i < b.getTransicoes().length; i++) {
            if (b.getTransicoes()[i] == null) {

            } else if (b.getTransicoes()[i].equals(b.getNome())) {
                if (R2.equals("")) {
                    R2 = b.getRegex()[i].split(":")[1];

                } else {
                    R2 = R2 + "U " + b.getRegex()[i].split(":")[1];
                }
                R2 = "(" + R2 + ")*";
            }
        }

        for (int i = 0; i < b.getTransicoes().length; i++) {
            if (b.getTransicoes()[i] == null) {

            } else if (b.getTransicoes()[i].equals(c.getNome())) {
                if (R3.equals("")) {
                    R3 = b.getRegex()[i].split(":")[1];

                } else {
                    R3 = R3 + "U " + b.getRegex()[i].split(":")[1];
                }
                R3 = "(" + R3 + ")";
            }
        }

        for (int i = 0; i < a.getTransicoes().length; i++) {
            if (a.getTransicoes()[i] == null) {

            } else if (a.getTransicoes()[i].equals(c.getNome())) {
                if (R4.equals("")) {
                    R4 = a.getRegex()[i].split(":")[1];

                } else {
                    R4 = R4 + "U " + a.getRegex()[i].split(":")[1];

                }
                R4 = "U (" + R4 + ")";
            }
        }

        String Regex = "(" + R1 + R2 + R3 + R4 + ")";
        System.out.println(Regex);

    }

    public static Estado findEstado(String nome, ArrayList<Estado> es) {
        for (Estado e : es) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
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
