/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

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

        Automato concatenado;

        int indice = a1.getEstados().size();

        ArrayList<Estado> novos = new ArrayList<Estado>();

        Estado inicial = null;

        for (Estado e : a2.getEstados()) {
            if (e.getInicial()) {
                inicial = e;
                e.setInicial(false);
            }
            e.rename("q" + indice);
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

        concatenado = new Automato(novos, a1.getAlfabeto());
        return concatenado;

    }

    public static Automato UniaoMinimizacaoDeAutomatos(Automato[] automatos) {
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

        return temp;
    }

    public static ArrayList<ArrayList> MinimizarAutomato(Automato automatoAlvo) {
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

        System.out.println();

        int lastSize = -1;

        //enquanto o numero de grupos de uma iteracao for diferente de outra,
        //para cada simbolo, cria um hash map para guardar as duplas das transicoes
        //depois cria os grupos novos, baseado nas duplas novas
        while (grupos.size() != lastSize) {

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
                    
                    //*
                    for (Object o : map.keySet()) {
                        Estado e = (Estado) o;
                        System.out.println(e.getNome() + " " + map.get(e));
                    }
                    //*/

                    //para cada grupo checar e comparar o primeiro elemento como resto, no maximo uma divisao por grupo.
                    //adicionando tudo que for diferente do primeiro ao segundo grupo, o resto mantem.
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

                    lastSize = grupos.size();
                    grupos.clear();
                    grupos.addAll(novosGrupos);
                    novosGrupos.clear();

                    //*
                    
                     System.out.println("-");
                     for (ArrayList<Estado> a : grupos) {
                     for (Estado e : a) {
                     System.out.println(grupos.indexOf(a) + " " + e.getNome());
                     }
                     }
                     System.out.println(simbolo + " " + grupos.size() + " " + lastSize);
                     System.out.println();
                     //*/
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
            for(int i = 0; i < a.size(); i++) {
                if(a.get(i).getFinal())
                    novo.setFinal(true);
                if(a.get(i).getInicial())
                    novo.setInicial(true);
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
                        estadoParaTransicao = grupos.get(k).indexOf(est);//Indice de qual estado é a transição
                        break;
                    }
                }
                
 
                
                tempEstado1.addTransicaoPorIndice(estadosNovos.get(estadoParaTransicao), j);//Adiciona a transição por indíce   
            }
            newAutomatoMinimizado.addEstados(tempEstado1);
        }

        newAutomatoMinimizado.setAlfabeto(automatoAlvo.getAlfabeto());
        newAutomatoMinimizado.print();
       // System.out.println("AGORA");
        //OperacoesComAutomatos.analiseLexica(newAutomatoMinimizado, "/home/luz/Dropbox/workspace/formais-e-compiladores/src/tests/Programa.txt");
        
        return grupos;

    }

    private static int indiceEstadoNoGrupo(ArrayList<Estado> grupo, Estado est) {
        for (int i = 0; i < grupo.size(); i++) {
            if (grupo.get(i).getNome().compareTo(est.getNome()) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    
    public static boolean percorrerAutomato(Automato af, String entrada) {
        
        Estado estadoAtual = af.getEstadoInicial();
        Estado estadoProximo;
        char charAtual;
        for(int i = 0; i < entrada.length(); i ++) {
            charAtual = entrada.charAt(i);
            ArrayList<Estado> arrayDeEstado = estadoAtual.getTransicaoPorAlfa("" + charAtual);
            estadoAtual = arrayDeEstado.get(0);   
        }
        return estadoAtual.getFinal();
    }
    
    
    public static String analiseLexica(Automato af, String diretorio) {
            String[] palavraReservada;
            int currentLine = 1;
            String linesWProblem = "Problema está nas linhas: ";
        try {
            File arquivo = new File(diretorio);
            Scanner scanner = new Scanner(arquivo);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                palavraReservada = line.split(" ");
               // System.out.println(palavraReservada[0]);
                //System.out.println("Aceita ou nao " + OperacoesComAutomatos.percorrerAutomato(af, palavraReservada[0]));
                if (palavraReservada[0] != null && palavraReservada[0] != "\n" && !OperacoesComAutomatos.percorrerAutomato(af, palavraReservada[0])) {
                    linesWProblem += (currentLine + ", ");
                    //System.out.println(linesWProblem);
                }
                 currentLine++;
            }
            scanner.close();
            linesWProblem = linesWProblem.substring(0, linesWProblem.length()-2);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacoesComAutomatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linesWProblem;
    }

    //public static void RetirarEstadosInalcansaveis(Gramatica gramatica){
    //Trabalho 3
    //}
}
