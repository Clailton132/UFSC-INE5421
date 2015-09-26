import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Gramatica {
	
	String gramatica; //teste
	ArrayList<String> rules = new ArrayList<String>();
	String linguage;
	public Gramatica() {
		gramatica = "";
	}
	
	public Gramatica(String directory) {
		
		File file = new File(directory);
		
		Scanner s;
		try {
			s = new Scanner(file);
			String lineseparator = System.getProperty("line.separator");
			 String text = s.useDelimiter("\\A").next();
			 text = text.replace(" ", "");
			 text = text.replace(lineseparator, "");
			 String[] temp = text.split("L=");
			 text = temp[0];
			 linguage = temp[1];
			 
			String[] rule = text.split(";");
			rules = new ArrayList<String>(Arrays.asList(rule));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not Found");
		}
		
	}
	
	
	
	public int positionState(String stat) {
		String rulestates = "";
		for(int i = 0; i < rules.size(); i ++) {
			rulestates += rules.get(i).substring(0, 1) + ",";
		}
		rulestates = rulestates.substring(0,rulestates.length()-1);
		System.out.println(rulestates);
		String[] states = rulestates.split(",");
		for (int i = 0; i < states.length; i ++)
			if(states[i].compareTo(stat) == 0)
				return i;
		
		return -1;
	}
	
	
	
	public int positionLanguage(String chara) {
		String temp = linguage.substring(1,linguage.length()-1);
		int ret = temp.indexOf(chara);
		ret /= 2;
		return ret + 1;
	}
	
	public AutomatoFinito transformToAutomato() {
		
		ArrayList<Estado> estados = new ArrayList<Estado>(); //Lista de todos os estados
		for(int i = 0; i < rules.size(); i ++) { //Faça com todas as regras
			//estados.add(new Estado((rules.get(i).substring(0, 1)), rules.size())); //Adicionar estado com primeiro nome da regra que é o S ou A ou B
			estados.add(new Estado("q" + i, rules.size())); //Adicione estado q + posição e.g. (0 1 2 3)
		}
		estados.add(new Estado("q" + rules.size(),0)); //Adiciona estado aceitador
		estados.get((estados.size()-1)).setFinal(); //Faz que o estado aceitador é final
		
		/*Lista de listas de opções, e.g. S:aA|b; A:a|b  Uma lista com as opções de A
		 * e de S, as de S são aA e b*/
		ArrayList<ArrayList<String>> rulessplit = new ArrayList<ArrayList<String>>();
		
		
		for(int i =0; i < rules.size(); i++) { //Faça para todas as regras da gramática
			// Separe todas as opções para cada estado*/
			ArrayList<String> a = new ArrayList<String>(Arrays.asList(rules.get(i).substring(2).replace(" ", "").replace("|", " ").split(" ")));//Tem que fazer esses dois replaces porque o split tem problema com |				
			rulessplit.add(a);
		}
			
		
		for(int i = 0; i < rulessplit.size(); i++)
			for(int j = 0; j < rulessplit.get(i).size(); j++) {
				//Se a opção tiver somente um tamanho, é para terminar, ir para o estado aceitador
				if(rulessplit.get(i).get(j).length() == 1)
					//Crie a transição do estado atual para o aceitador
					estados.get(i).addTransicoes("q" + i + ",q" + (estados.size()-1), positionLanguage(rulessplit.get(i).get(j).substring(0,1)));
				else
					//Não é terminal, então crie a transição para outro estado.
					estados.get(i).addTransicoes("q" + i + ",q" + this.positionState(rulessplit.get(i).get(j).substring(1)), positionLanguage(rulessplit.get(i).get(j).substring(0, 1)));	
			}
		
		System.out.println("Testando position state " + 
		"\nS: " +this.positionState("S")+
		"\nA: " + this.positionState("A") );

		return new AutomatoFinito(estados);
		
		
	}
	
	

}
