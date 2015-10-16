/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

import java.util.*;
import java.io.File;

/**
 *
 * @author Luca
 */
public class Automato {

    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private String[] alfabeto;

    public Automato(String Arquivo) {

        try {
            File arquivo = new File(Arquivo);
            Scanner scanner = new Scanner(arquivo);

            alfabeto = scanner.next().split(",");

            String t = scanner.next();

            while (!t.split(":")[0].equals("f")) {
                estados.add(new Estado(this, t.split(";")[0], alfabeto.length));
                t = scanner.next();

            }

            for (String s : t.split(":")[1].split(",")) {
                for (Estado e : estados) {
                    if (e.getNome().equals(s)) {
                        e.setFinal();
                    }
                }
            }

            t = scanner.next();

            for (String s : t.split(":")[1].split(",")) {
                for (Estado e : estados) {
                    if (e.getNome().equals(s)) {
                        e.setInicial();
                    }
                }
            }

            scanner = new Scanner(arquivo);

            t = scanner.next();
            t = scanner.next();

            while (!t.startsWith("f:")) {
                String[] temp = t.split(";");
                Estado e = getEstado(temp[0]);
                System.out.println(e.getNome());

                for (int i = 1; i < temp.length; i++) {
                    String r = temp[i];
                    String[] h = r.split(",");
                    if (h.length > 1) {
                        for (int j = 0; j < h.length; j++) {
                            e.addTransicaoPorIndice(getEstado(h[j]), i - 1);
                        }
                    } else {

                        e.addTransicaoPorIndice(getEstado(r), i - 1);
                    }
                }

                t = scanner.next();

            }

            System.out.println("");
            //print();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void print() {
        System.out.print(alfabeto[0]);
        for (int i = 1; i < alfabeto.length; i++) {
            System.out.print("," + alfabeto[i]);
        }
        System.out.println("");

        for (Estado e : estados) {
            System.out.println(e.getNome());

            for (String a : alfabeto) {
                System.out.print(a + ": ");
                ArrayList<Estado> l = e.getTransicaoPorAlfa(a);

                if (l.size() != 0) {
                    System.out.print(l.get(0).getNome());
                }
                for (int i = 1; i < l.size(); i++) {
                    System.out.print("," + l.get(i).getNome());
                }
                System.out.println("");
            }
            System.out.println(e.getInicial());
            System.out.println(e.getFinal());
            System.out.println("");
        }
    }

    public static Estado getEstado(String nome, ArrayList<Estado> estados) {
        for (Estado e : estados) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
    }

    public Estado getEstado(String nome) {
        for (Estado e : this.estados) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
    }

    public String[] getAlfa() {
        return alfabeto;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void addEstados(Estado r) {
        estados.add(r);
    }

}
