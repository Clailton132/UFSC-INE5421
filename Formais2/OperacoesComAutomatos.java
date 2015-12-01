/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formais2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca
 */
public class OperacoesComAutomatos {

    public static Automato concatenarAutomatos(Automato a1, Automato a2) {

        Automato concatenado = new Automato();

        int indice = a1.getEstados().size();

        ArrayList<Estado> novos = new ArrayList<Estado>();

        Estado inicial = null;

        for (Estado e : a2.getEstados()) {
            if (e.getInicial()) {
                inicial = e;
                e.setInicial(false);
            }
            e.rename("q" + indice);
            e.setAutomatoPertencente(concatenado);
            indice++;
        }

        for (Estado e : a1.getEstados()) {
            if (e.getFinal()) {
                e.addTransicaoPorIndice(inicial, 0);
                e.setFinal(false);
            }
            novos.add(e);
        }

        for (Estado e : a2.getEstados()) {

            novos.add(e);
        }

        concatenado.trocarEstados(novos);
        concatenado.setAlfabeto(a1.getAlfabeto());
        return concatenado;

    }

    public static Automato UniaoDeAutomatos(Automato[] automatos) {
        Automato unido = new Automato();

        unido.setAlfabeto(automatos[0].getAlfabeto());                                  //como todos os automatos supostamente terÃ£o o mesmo alfabeto pode

        Estado novoComeco = new Estado(unido, "q0", automatos[0].getAlfabeto().length);

        novoComeco.setInicial(true);

        int counter = 1;

        unido.addEstados(novoComeco);

        for (Automato a : automatos) {
            Determinizador.Determinizar(a);

            //minimizar(a);
            for (Estado e : a.getEstados()) {

                e.rename("q" + counter);
                e.setAutomatoPertencente(unido);
                counter++;

                if (e.getInicial()) {
                    novoComeco.addTransicaoPorIndice(e, 0);
                    e.setInicial(false);
                }

                unido.addEstados(e);
            }
        }

        return unido;
    }

    public static ArrayList<Estado> RetirarEstadosInalcancaveis(Automato automato, Estado inicio, ArrayList<Estado> alcancaveis) {

        if (alcancaveis.contains(inicio)) {
            return alcancaveis;
        }

        alcancaveis.add(inicio);

        for (int i = 0; i < inicio.getTransicoes().length; i++) {
            for (Estado e : inicio.getTransicaoPorIndice(i)) {
                RetirarEstadosInalcancaveis(automato, e, alcancaveis);
            }
        }

        return alcancaveis;

    }

    //boas praticas...: um mÃ©todo nÃ£o deve alterar diretamente as entradas, deve retornar algo para ser utilizado depois
    public static Automato criarAutomatoTotal(Automato automato) {
        //Em um automato total, todos os estados possuem transicoes com todos os sÃ­mbolos
        Automato temp = automato;
        Estado D = new Estado(temp, "q" + temp.getEstados().size(), temp.getAlfabeto().length);
        for (Estado e : temp.getEstados()) {
            for (int i = 1; i < e.getTransicoes().length; i++) {
                if (e.getTransicaoPorIndice(i).size() == 0) {
                    e.addTransicaoPorIndice(D, i);
                }
            }
        }
        for (String s : temp.getAlfabeto()) {
            if (!s.equals("E")) {
                D.addTransicaoPorAlfa(D, s);
            }
        }
        temp.addEstados(D);

        automato = new Automato(RetirarEstadosInalcancaveis(automato, automato.getEstadoInicial(), new ArrayList<Estado>()), automato.getAlfabeto());

        return temp;
    }

    public static Automato criarEstadoRejeitado(Automato automato) {
        //Em um automato total, todos os estados possuem transicoes com todos os sÃ­mbolos
        Automato temp = automato;
        Estado D = new Estado(temp, "qR", temp.getAlfabeto().length);
        D.setFinal(true);
        for (Estado e : temp.getEstados()) {
            for (int i = 1; i < e.getTransicoes().length; i++) {
                if (e.getTransicaoPorIndice(i).size() == 0) {
                    e.addTransicaoPorIndice(D, i);
                }
            }
        }

        for (String s : temp.getAlfabeto()) {
            if (!s.equals("E")) {
                D.addTransicaoPorAlfa(D, s);
            }
        }
        temp.addEstados(D);

        return temp;
    }

    public static Automato MinimizarAutomato(Automato automatoAlvo) {
        //para minimizar, um automato deve ser deterministico, sem estados inalcansaveis, e total e ja esta tudo feito

        Automato automato = new Automato(RetirarEstadosInalcancaveis(automatoAlvo, automatoAlvo.getEstadoInicial(), new ArrayList<Estado>()), automatoAlvo.getAlfabeto());
        automato = criarAutomatoTotal(automato);
        //aqui, cria os dois grupos iniciais de estados

        ArrayList<ArrayList> grupos = new ArrayList();

        ArrayList<Estado> finais = new ArrayList();
        ArrayList<Estado> naoFinais = new ArrayList();

        grupos.add(naoFinais);
        grupos.add(finais);

        for (Estado e : automatoAlvo.getEstados()) {
            if (e.getFinal()) {
                finais.add(e);
            } else {
                naoFinais.add(e);
            }
        }

        //System.out.println();
        int lastSize = -1;

        //enquanto o numero de grupos de uma iteracao for diferente de outra,
        //para cada simbolo, cria um hash map para guardar as duplas das transicoes
        //depois cria os grupos novos, baseado nas duplas novas
        while (grupos.size() != lastSize) {

            lastSize = grupos.size();

            ArrayList<ArrayList> novosGrupos = new ArrayList();

            HashMap map = new HashMap<Estado, Integer>();

            for (String simbolo : automatoAlvo.getAlfabeto()) {
                if (!simbolo.equals("E")) {
                    for (Estado e : automatoAlvo.getEstados()) {
                        for (ArrayList<Estado> a : grupos) {
                            if (a.contains(e.getTransicaoPorAlfa(simbolo).get(0))) {
                                map.put(e, grupos.indexOf(a));
                            }
                        }
                    }

                    for (int i = 0; i < grupos.size(); i++) {

                        ArrayList<Estado> a = grupos.get(i);

                        ArrayList<Estado> novogrupo1 = new ArrayList();
                        ArrayList<Estado> novogrupo2 = new ArrayList();

                        for (Estado e : a) {
                            if (map.get(e).equals(map.get(a.get(0)))) {
                                novogrupo1.add(e);
                            } else {
                                novogrupo2.add(e);
                            }

                        }

                        novosGrupos.add(novogrupo1);

                        if (novogrupo2.size() != 0) {
                            novosGrupos.add(novogrupo2);
                        }

                    }
                    grupos.clear();
                    grupos.addAll(novosGrupos);
                    novosGrupos.clear();

                }
            }
        }

        ArrayList<Estado> estadosNovos = new ArrayList();
        Automato newAutomatoMinimizado = new Automato();
        int estadoParaTransicao = -1;
        Estado tempEstado;
        Estado tempEstado1;

        //Adiciona o primeiro estado de cada grupo para o array de estados do automato minimizado
        int index = 0;

        for (ArrayList<Estado> a : grupos) {
            Estado novo = new Estado(newAutomatoMinimizado, "q" + index, automato.getAlfabeto().length);
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).getFinal()) {
                    novo.setFinal(true);
                }
                if (a.get(i).getInicial()) {
                    novo.setInicial(true);
                }
            }
            estadosNovos.add(novo);
            index++;
        }

        int i = 0;

        for (ArrayList<Estado> a : grupos) {
            tempEstado = a.get(0); //Primeiro elemento de cada grupo
            //Passa por todas as transições do estado

            tempEstado1 = estadosNovos.get(i);
            i++;

            for (int j = 1; j < tempEstado.getTransicoes().length; j++) {
                Estado est = tempEstado.getTransicaoPorIndice(j).get(0); //Já que está determinizado pode pegar só o primeiro, só vai ter um estado
                for (int k = 0; k < grupos.size(); k++) {
                    if (grupos.get(k).indexOf(est) != -1) {
                        //System.out.println(k);
                        estadoParaTransicao = k;
                        //estadoParaTransicao = grupos.get(k).indexOf(est);//Indice de qual estado é a transição
                        break;
                    }
                }

                tempEstado1.addTransicaoPorIndice(estadosNovos.get(estadoParaTransicao), j);//Adiciona a transição por indíce   
            }
            newAutomatoMinimizado.addEstados(tempEstado1);
        }

        newAutomatoMinimizado.setAlfabeto(automatoAlvo.getAlfabeto());
        //newAutomatoMinimizado.print();
        // System.out.println("AGORA");
        //OperacoesComAutomatos.analiseLexica(newAutomatoMinimizado, "/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/Programa.txt");

        return newAutomatoMinimizado;

    }

    public static void PercorrerFrase(Automato automato, String frase, int linha, TokensL tokens) {

        Estado antigo = automato.getEstadoInicial();
        Estado atual = automato.getEstadoInicial();
        String Token = "";

        for (char c : frase.toCharArray()) {

            if (c != ' ') {
                antigo = atual;
                atual = atual.getTransicaoPorAlfa(c + "").get(0);
            }

            if (c == ' ') {

                if (atual.getFinal()) {
                    //System.out.println("Espaço " + Token + " : " + atual.getAutomatoOriginal().GetID());
                    tokens.addToken(Token,atual.getAutomatoOriginal().GetID());
                    Token = "";
                    atual = automato.getEstadoInicial();
                    antigo = atual;

                } else if (atual.getAutomatoOriginal().GetID().equals("STRING")) {
                    Token += c;

                } else {
                    //System.out.println("ID " + Token + " : ID");
                    tokens.addToken(Token,"ID");
                    Token = "";
                    atual = automato.getEstadoInicial();
                    antigo = atual;
                }

            } else if (antigo.getFinal() && !atual.getFinal()) {

                //System.out.println("DIF " + Token + " : " + antigo.getAutomatoOriginal().GetID());
                tokens.addToken(Token,antigo.getAutomatoOriginal().GetID());
                Token = "";
                atual = automato.getEstadoInicial();
                antigo = atual;
                atual = atual.getTransicaoPorAlfa(c + "").get(0);
                Token += c;

            } else {
                Token += c;
            }

        }
        if (atual.getFinal()) {
            //System.out.println("END " + Token + " : " + atual.getAutomatoOriginal().GetID());
            tokens.addToken(Token,atual.getAutomatoOriginal().GetID());
        } else {
            //System.out.println("END " + Token + " : ID");
            tokens.addToken(Token,"ID");
        }
    }

    public static void PercorrerFrase1(Automato automato, String frase, int linha) {

        Estado antigo = automato.getEstadoInicial();
        Estado atual = automato.getEstadoInicial();
        String Token = "";

        for (char c : frase.toCharArray()) {

            if (c != ' ') {
                Token += c;

            } else {
                if (Token.startsWith("\"") && !Token.endsWith("\"")) {
                    Token += c;
                    antigo = atual;
                    atual = atual.getTransicaoPorAlfa(c + "").get(0);

                } else if (Token.endsWith("\"")) {

                    System.out.println(Token + " : " + atual.getAutomatoOriginal().GetID());
                    Token = "";
                    atual = automato.getEstadoInicial();
                    antigo = atual;

                } else {
                    atual = automato.getEstadoInicial();
                    antigo = atual;

                    boolean x = false;

                    for (char d : Token.toCharArray()) {
                        antigo = atual;
                        atual = atual.getTransicaoPorAlfa(d + "").get(0);
                        if (atual.getNome().equals("qR")) {
                            x = true;
                            atual = antigo;
                        }
                    }

                    if (x) {
                        System.out.println("ERRO Linha: " + linha);
                    } else {
                        if (atual.getFinal()) {
                            System.out.println(Token + " : " + atual.getAutomatoOriginal().GetID());
                        } else {
                            System.out.println(Token + " : ID");
                        }

                    }

                    Token = "";

                }
            }

        }
        System.out.println(Token + " : " + atual.getAutomatoOriginal().GetID());
    }

    public static void PercorrerFrase2(Automato automato, String frase, int linha, TokensL tokens) {

        Estado atual = automato.getEstadoInicial();
        frase += ' ';
        String Token = "";
        String True = "";
        boolean aspas = false;

        for (char c : frase.toCharArray()) {
            if (c != ' ') {
                if (c == '\"' && !aspas) {
                    aspas = true;
                } else if (c == '\"' && aspas) {
                    aspas = false;
                }

                Token += c;
                True += c;

            } else {
                if (aspas) {
                    True += c;
                } else {
                    for (char d : Token.toCharArray()) {
                        atual = atual.getTransicaoPorAlfa(d + "").get(0);
                    }
                    if (atual.getFinal()) {
                        if (!atual.getNome().equals("qR")) {
                            
                            tokens.addToken(True, atual.getAutomatoOriginal().GetID());
                            
                            System.out.println(True + " : " + atual.getAutomatoOriginal().GetID());
                        } else {
                            System.out.println("ERRO linha : " + linha);
                        }
                    } else {

                        tokens.addToken(True, "ID");
                        
                        System.out.println(True + " : ID");

                    }
                    Token = "";
                    True = "";
                    atual = automato.getEstadoInicial();
                }
                //System.out.println(Token);
            }
        }
    }
    
   

    public static Automato UniaoD(Automato[] automatos) {

        Automato unido = new Automato();
        unido.setAlfabeto(automatos[0].getAlfabeto());

        Estado inicial = new Estado(unido, "q0", unido.getAlfabeto().length);
        inicial.setInicial(true);
        unido.addEstados(inicial);
        int index = 1;

        for (int i = 0; i < automatos.length; i++) {
            for (Estado e : automatos[i].getEstados()) {
                if (e.getInicial()) {
                    inicial.addTransicaoPorIndice(e, 0);
                    e.setInicial(false);
                }
                e.rename("q" + index);
                index++;
                unido.addEstados(e);
            }
        }

        for (Estado e : inicial.getTransicaoPorIndice(0)) {
            for (int i = 0; i < e.getTransicoes().length; i++) {
                if (e.getTransicaoPorIndice(i).size() != 0) {
                    inicial.addTransicaoPorIndice(e.getTransicaoPorIndice(i).get(0), i);
                }
            }
        }

        inicial.clearEpsilon();
        return unido;

    }
}
