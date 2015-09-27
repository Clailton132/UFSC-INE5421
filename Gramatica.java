import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Gabriel Luz
 */

public class Gramatica {
	
	String gramatica;
	ArrayList<String> rules
	String linguage;
	public Gramatica() {
		gramatica = "";
	}
	/*
	* Cria gramática vindo de arquivo, Argumentos(Directório do arquivo com a gramática*/
	public Gramatica(String directory) {
		gramatica = "";
		rules = new ArrayList<String>();
		linguage = "";

		File file = new File(directory);
		Scanner s;

		try {
			s = new Scanner(file);
			String lineseparator = System.getProperty("line.separator");//Quebra de linha do SO
			 String text = s.useDelimiter("\\A").next();
			 text = text.replace(" ", "");//Tira todos os espaços
			 text = text.replace(lineseparator, "");//Tira todos as quebras de linha
			 String[] temp = text.split("L="); //Tira a parte que diz qual a linguagem da gramática
			 text = temp[0]; //text = Gramática formatada
			 linguage = temp[1]; //Linguage recebe a linguagem (a,b) ou (0,1)
			gramatica = text; 
			String[] rule = text.split(";"); //Array com todas as regras da gramática
			rules = new ArrayList<String>(Arrays.asList(rule));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not Found");
		}
		
	}
	/*
	* Qual é a posição da regra na lista de regras, Argumentos(String nome da regra)*/
	public int positionState(String stat) {
		String rulestates = ""; //String para manipular
		for(int i = 0; i < rules.size(); i ++) {
			rulestates += rules.get(i).substring(0, 1) + ","; /*Passa por todas as regras e vai botando elas
			em uma string as separando por uma ','*/
		}
		rulestates = rulestates.substring(0,rulestates.length()-1); //Tira a ultima virgula
		String[] states = rulestates.split(","); //Array de Strings, cada string é o nome do estado.
		for (int i = 0; i < states.length; i ++)
			if(states[i].compareTo(stat) == 0) //Procura o nome do estado e retorna a posição
				return i;
		
		return -1; //Se não achar retorna a posição -1
	}
	/*
	* Qual é a posição da palavra na linguagem, Argumentos(String palavra)*/
	public int positionLanguage(String chara) {
		String temp = linguage.substring(1,linguage.length()-1); //Tira os () da linguagem
		int ret = temp.indexOf(chara); //Procura pela posição da palavra nas palavras separadas por virgula
		ret /= 2; //Tira as virgulas
		return ret + 1; //Adiciona 1 pois e contado a partir do 1 e não do 0
	}
	/*Retorna o Automato Finito gerado pela propria Gramatica*/
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
		return new AutomatoFinito(estados);	
	}
	
}//
