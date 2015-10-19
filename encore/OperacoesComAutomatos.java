/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encore;

/**
 *
 * @author Luca
 */
public class OperacoesComAutomatos {

    public static Automato Uniao(Automato[] automatos){
        Automato unido = new Automato();
        unido.setAlfa(automatos[0].getAlfa());                                  //como todos os automatos supostamente terão o mesmo alfabeto pode
        Estado novoComeco = new Estado(unido, "q0", automatos[0].getAlfa().length);
        novoComeco.setInicial(true);
        int counter = 1;
        
        unido.addEstados(novoComeco);
        
        for(Automato a : automatos){
            Determinizador.Determinizar(a);
            
            //minimizar(a);
            
            for(Estado e : a.getEstados()){
                
                e.rename("q" + counter);
                counter++;
                
                if(e.getInicial()){
                    novoComeco.addTransicaoPorIndice(e, 0);
                    e.setInicial(false);
                }
                
                unido.addEstados(e);
            }
        }
        
        return unido;
        
    }
}
