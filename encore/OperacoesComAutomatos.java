/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static ArrayList<ArrayList> MinimizarAutomato(Automato automato) {
        //para minimizar, um automato deve ser deterministico, sem estados inalcansaveis, e total e ja esta tudo feito

        //Automato alvo = new Automato(RetirarEstadosInalcancaveis(automato, automato.getEstadoInicial(), new ArrayList<Estado>()), automato.getAlfabeto());
        //alvo = criarAutomatoTotal(alvo);
        //aqui, cria os dois grupos iniciais de estados
        ArrayList<ArrayList> grupos = new ArrayList();

        ArrayList<Estado> finais = new ArrayList();
        ArrayList<Estado> naoFinais = new ArrayList();

        grupos.add(naoFinais);
        grupos.add(finais);

        for (Estado e : automato.getEstados()) {
            if (e.getFinal()) {
                finais.add(e);
            } else {
                naoFinais.add(e);
            }
        }

        for (ArrayList<Estado> a : grupos) {
            for (Estado e : a) {
                System.out.println(grupos.indexOf(a) + " " + e.getNome());
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

            for (String simbolo : automato.getAlfabeto()) {
                if (!simbolo.equals("E")) {
                    for (Estado e : automato.getEstados()) {
                        for (ArrayList<Estado> a : grupos) {
                            if (a.contains(e.getTransicaoPorAlfa(simbolo).get(0))) {
                                map.put(e, grupos.indexOf(a));
                            }
                        }
                    }

                    for(Object o : map.keySet()){
                        Estado e = (Estado)o;
                        System.out.println(e.getNome() + " " + map.get(e));
                    }
                    
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
                        
                        System.out.println("novos " + novosGrupos.size());
                    }

                    lastSize = grupos.size();
                    grupos.clear();
                    grupos.addAll(novosGrupos);
                    novosGrupos.clear();
                    
                    
                    System.out.println("-");
                    for (ArrayList<Estado> a : grupos) {
                        for (Estado e : a) {
                            System.out.println(grupos.indexOf(a) + " " + e.getNome());   
                        }
                    }
                    System.out.println(simbolo + " " + grupos.size() + " " + lastSize);
                    System.out.println();
                }
                
            }
        }

        ArrayList<Estado> estadosNovos = new ArrayList();
        Automato newAutomatoMinimizado = new Automato();
        int estadoParaTransicao = -1;
        Estado tempEstado;
        
        //Adiciona o primeiro estado de cada grupo para o array de estados do automato minimizado
        for (ArrayList<Estado> a : grupos)
            estadosNovos.add(a.get(0));
        
        for (ArrayList<Estado> a : grupos) {
            tempEstado = a.get(0); //Primeiro elemento de cada grupo
            //Passa por todas as transições do estado
            for (int j = 0; j < tempEstado.getTransicoes().length; j++) {
                Estado est = tempEstado.getTransicaoPorIndice(j).get(0); //Já que está determinizado pode pegar só o primeiro, só vai ter um estado
                for(int k = 0; k < grupos.size(); k++) {
                    estadoParaTransicao = OperacoesComAutomatos.indiceEstadoNoGrupo(grupos.get(k), est);//Indice de qual estado é a transição
                }
                tempEstado.addTransicaoPorIndice(estadosNovos.get(estadoParaTransicao), j);//Adiciona a transição por indíce   
            }
            newAutomatoMinimizado.addEstados(tempEstado);
        }
        
        newAutomatoMinimizado.print();
        return grupos;
        
        
    }
    
    private static int indiceEstadoNoGrupo(ArrayList<Estado> grupo, Estado est) {
        for(int i = 0; i < grupo.size(); i ++)
            if(grupo.get(i).getNome().compareTo(est.getNome()) == 0)
                return i;
        return -1;
    }

    //public static void RetirarEstadosInalcansaveis(Gramatica gramatica){
    //Trabalho 3
    //}
}
