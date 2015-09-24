import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Gramatica {
	
	String gramatica;
	ArrayList<String> rules = new ArrayList<String>();
	
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
				System.out.println(text);
			String[] rule = text.split(";");
			rules = new ArrayList<String>(Arrays.asList(rule));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not Found");
		}
		
		

		/*
		while(s.hasNext()) {
			gramatica = s.next();
			
		}
		*/
		//String content = readFile(directory, StandardCharsets.UTF_8);
		//FileUtils.readFileToString(file);
		
		
		
		
	}
	public AutomatoFinito transformToAutomato() {
		ArrayList<Estado> estados = new ArrayList<Estado>(); //Estados criados por cada regra
		for(int i = 0; i < rules.size(); i ++) { //Até numero total de regras
			estados.add(new Estado((rules.get(i).substring(0, 0)), rules.size())); //Adicionar estado com primeiro nome da regra que é o S ou A ou B
		}
		estados.add(new Estado("aceitador",0)); //Adiciona aceitador aos estados
		
		for(int i = 0; i < estados.size(); i++) {
			System.out.println(estados.get(i).getNome() + " ");
		}
		
		ArrayList<String> possibilidades = new ArrayList<String>(Arrays.asList(rules.get(0).split("|"))); //Array de cada tipo a ou sS etc
	
		for(int i = 0; i < possibilidades.size(); i ++)
		System.out.println(possibilidades.get(i));
		return null;
		
	}
	
	

}
