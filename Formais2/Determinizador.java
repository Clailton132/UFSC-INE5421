/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formais2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luca
 */
public class Determinizador {
    
    public static void Determinizar(Automato automato) {
        ArrayList<Estado> estados;
        int last;
        String[] alfabeto;
        ArrayList<Estado> novosEstados = new ArrayList<Estado>();
        
        estados = automato.getEstados();
        last = estados.size();
        alfabeto = automato.getAlfabeto();
        
        for(Estado e : estados){
            if(e.getTransicaoPorIndice(0).size() == 0){
                removerEpsilonTransicoes(automato);
                break;
            }
        }
        
        for (Estado e : estados) {

            //como a transicao por epsilon eh sempre na posicao 0, da pra comecar com 1 aqui
            for (int i = 0; i < e.getTransicoes().length; i++) {
                ArrayList<Estado> temp = e.getTransicaoPorIndice(i);
                String novo = "";
                
                if (temp.size() > 1) {
                    for (Estado e1 : temp) {
                        if (novo == "") {
                            novo = e1.getNome();
                        } else {
                            novo = novo + "," + e1.getNome();
                        }
                    }
                    
                    novo = Estado.OrdenaString(novo);
                    Estado novoEstado = new Estado(automato, novo, alfabeto.length);
                    e.determinizarTransicao(i, novoEstado);
                    
                    if (!checaSeExisteNoArray(novosEstados, novoEstado)) {
                        novosEstados.add(novoEstado);
                    }
                }
            }
        }
        
        for (Estado e : novosEstados) {
            automato.addEstados(e);
        }
        
        //System.out.println(automato.getEstados().size() + " " + last);
        
        for (Estado e : automato.getEstados()) {
            for (Estado j : novosEstados) {
                e.acertarTransicoes(j);
            }
        }
        
        if (automato.getEstados().size() > last) {
            Determinizar(automato);
        }
        
        
        
    }
    
    public static boolean checaSeExisteNoArray(ArrayList<Estado> estados, Estado estado) {
        for (Estado e : estados) {
            if (estado.getNome().equals(e.getNome())) {
                return true;
            }
        }
        return false;
    }

    //nota, padrao Epsilon estar sempre na posicao 0; pode ser mudado depois
    private static ArrayList<Estado> EpsilonFechoDoEstado(Estado estado, ArrayList<Estado> estados, ArrayList<Estado> fecho) {
        
        if (checaSeExisteNoArray(fecho, estado)) {
            return fecho;
        }
        
        fecho.add(estado);
        
        if (estado.getTransicaoPorIndice(0) == null) {
            return fecho;
        } else {
            for (Estado e : estado.getTransicaoPorIndice(0)) {
                fecho = (EpsilonFechoDoEstado(e, estados, fecho));
            }
            return fecho;
        }
        
    }
    
    public static void removerEpsilonTransicoes(Automato automato) {

        ArrayList<Estado> estados = automato.getEstados();
        ArrayList<Estado> Novos = new ArrayList();
        
        
        for (Estado e : estados) {
            e.setFecho(EpsilonFechoDoEstado(e, estados, new ArrayList<Estado>()));
            
            String Novonome = "";
            
            for (Estado f : e.getFecho()) {
                if (Novonome.equals("")) {
                    Novonome += f.getNome();
                } else {
                    Novonome += "," + f.getNome();
                }
            }
            
            String[] Temp= Novonome.split(",");
            Novonome = "";
            Arrays.sort(Temp);
            
            for(String s : Temp){
                if(Novonome.equals("")){
                    Novonome += s;
                } else {
                    Novonome += ',' + s;
                }
            }
            
            
            if (Novonome.equals(e.getNome())) {
                
            } else {
                Estado Novo = new Estado(e.getAutomatoPertencente(), Novonome, e.getTransicoes().length);
                
                for (int i = 0; i < e.getTransicoes().length; i++) {
                    
                    for (Estado f : e.getFecho()) {
                        
                        for (int j = 0; j < f.getTransicaoPorIndice(i).size(); j++) {
                            
                            Novo.addTransicaoPorIndice(f.getTransicaoPorIndice(i).get(j), i);
                            
                            if(f.getFinal()){
                                Novo.setFinal(true);
                            }
                            
                        }
                    }
                }
                
                if(e.getNome().equals("q0")){
                    Novo.setInicial(true);
                    e.setInicial(false);
                }
                
                Novos.add(Novo);
                
            }
            
        }
        
        for(Estado e : Novos){
            automato.addEstados(e);
        }
        
        for (Estado e : estados) {
            
            for (int i = 1; i < e.getTransicoes().length; i++) {
                
                if (e.getTransicaoPorIndice(i).size() != 0) {
                    ArrayList<Estado> transicaoAtual = e.getTransicaoPorIndice(i);
                    ArrayList<Estado> temp = new ArrayList<Estado>();
                    
                    for (Estado e1 : transicaoAtual) {
                        temp.addAll(e1.getFecho());
                    }
                    
                    for (Estado e1 : temp) {
                        e.addTransicaoPorIndice(e1, i);
                    }
                }
            }
            e.clearEpsilon();
        }
    }
}
