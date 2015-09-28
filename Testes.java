


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

    public Testes() {
        Estado i = new Estado("q0", 2);
        Estado j = new Estado("q1", 2);
        Estado k = new Estado("q2", 2);
        Estado p = new Estado("q3", 2);

        i.addTransicoes("q1", 1);
        i.addTransicoes("q0,q1", 2);
        j.addTransicoes("q2", 1);
        j.addTransicoes("q1", 2);
        k.addTransicoes("q3", 1);
        k.addTransicoes("q1", 2);
        p.addTransicoes("", 1);
        p.addTransicoes("q1", 2);

        es.add(i);
        es.add(j);
        es.add(k);
        es.add(p);

        DeterminizarE();
        
        for(Estado e : es){
            System.out.println(e.getNome());
            System.out.println(e.getFecho());
            
            for(int b = 0; b < e.getTransicoes().length; b++){
                System.out.println(b + " : " + e.getTransicoes()[b]);
            }
        }
        
        

    }
    
    public void DeterminizarE() {
        
        ArrayList<String> A1 = new ArrayList<String>();                                 //A1 de Strings
       
        for(Estado e : es){
            if(!e.getTransicoes()[0].equals("") || e.getTransicoes()[0] != null){       //se a transicao por epsilon nao for nula
                
                findWay(A1, e);                                                     //encontra todas as epsilon transicoes existentes
                
            }
            
            String[] j = new String[A1.size()];
            
            
            for(int i = 0; i < A1.size(); i++){
                j[i] = A1.get(i);
            }
            
            Arrays.sort(j);

            
            String result = j[0];

            for(int i = 0; i < j.length; i++){
                result = Estado.unirTransicoes(result, j[i]);
            }
            
            
            e.setFecho(result);
            
            
            A1.clear();
        }
        
        for(Estado e : es){
            for(int i = 0; i < e.getTransicoes().length; i++){
                
                String[] l = e.getTransicoes()[i].split(",");
                
                for(Estado e1 : es){
                    for(int m = 0; m < l.length; m ++){
                    
                        if(e1.getNome().equals(l[m])){
                            e.addTransicoes(e1.getFecho(), i + 1);
                        
                        }
                    }
                }
            }
        }
        
        for(Estado e : es){
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
