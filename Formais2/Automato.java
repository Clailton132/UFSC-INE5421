/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formais2;

import java.util.*;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author Luca
 */
public class Automato {

    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private String[] alfabeto;
    private String ID;

    public Automato() {

    }

    public Automato(String Arquivo) {

        try {
            Scanner scanner = new Scanner(getClass().getResourceAsStream(Arquivo));

            alfabeto = scanner.next().split(",");

            String t = scanner.next();

            while (!t.split(":")[0].equals("f")) {
                estados.add(new Estado(this, t.split(";")[0], alfabeto.length));
                t = scanner.next();

            }

            for (String s : t.split(":")[1].split(",")) {
                for (Estado e : estados) {
                    if (e.getNome().equals(s)) {
                        e.setFinal(true);
                    }
                }
            }

            t = scanner.next();

            for (String s : t.split(":")[1].split(",")) {
                for (Estado e : estados) {
                    if (e.getNome().equals(s)) {
                        e.setInicial(true);
                    }
                }
            }

            scanner = new Scanner(getClass().getResourceAsStream(Arquivo));

            t = scanner.next();
            t = scanner.next();

            while (!t.startsWith("f:")) {
                String[] temp = t.split(";");
                Estado e = getEstado(temp[0]);

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

            //print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Automato(ArrayList<Estado> estados, String[] alfa) {
        this.estados = estados;
        this.alfabeto = alfa;
    }

    public void print() {
        System.out.print(alfabeto[0]);
        for (int i = 1; i < alfabeto.length; i++) {
            System.out.print("," + alfabeto[i]);
        }
        System.out.println("");
        System.out.println("");

        for (Estado e : estados) {
            System.out.println(e.getNome());

            for (String a : alfabeto) {
                System.out.print(a + ": ");
                ArrayList<Estado> l = e.getTransicaoPorAlfa(a);

                if (l.size() != 0) {
                    System.out.print(l.get(0).getNome());
                }
                //*
                for (int i = 1; i < l.size(); i++) {
                    System.out.print(";" + l.get(i).getNome());
                }
                //*/
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
    
    public Estado getEstadoInicial(){
        for(Estado e : this.estados){
            if(e.getInicial()){
                return e;
            }
        }
        return null;
    }

    public String[] getAlfabeto() {
        return alfabeto;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void addEstados(Estado r) {
        if(!Determinizador.checaSeExisteNoArray(estados, r))
        estados.add(r);
    }

    public void setAlfabeto(String[] alfa) {
        this.alfabeto = alfa;
    }
    
    public void trocarEstados(ArrayList<Estado> estados){
        this.estados = estados;
    }

    public void SetID(String ID){
        this.ID = ID;
    }
    
    public String GetID(){
        return this.ID;
    }
    
    public void escreverArquivo(String diretorio) {

        ArrayList<Estado> buffer = new ArrayList<Estado>();
        String inicial = "";

        try {
            File file = new File(diretorio);
            PrintWriter writer = new PrintWriter(file);

            writer.print(alfabeto[0]);

            for (int i = 1; i < alfabeto.length; i++) {
                writer.print("," + alfabeto[i]);
            }

            writer.println("");

            for (Estado e : estados) {
                
                writer.print(e.getNome());

                for (int i = 0; i < e.getTransicoes().length; i++) {
                    writer.print(";");

                    if (e.getTransicaoPorIndice(i).size() != 0) {
                        writer.print(e.getTransicaoPorIndice(i).get(0).getNome());
                    }
                    
                    if (e.getTransicaoPorIndice(i).size() > 1) {

                        for (int j = 1; j < e.getTransicaoPorIndice(i).size(); j++) {
                            writer.print("," + e.getTransicaoPorIndice(i).get(j).getNome());
                        }
                    }

                }
                
                if (e.getInicial()) {
                    inicial = e.getNome();
                }
                
                if (e.getFinal()) {
                    buffer.add(e);
                }
                writer.println("");
            }

            writer.print("f:" + buffer.get(0).getNome());

            for (int i = 1; i < buffer.size(); i++) {
                writer.print(";" + buffer.get(i).getNome());
            }

            writer.println("");
            writer.print("i:" + inicial);

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
