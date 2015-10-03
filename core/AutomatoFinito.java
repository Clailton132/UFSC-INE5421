
import java.io.*;
import java.util.*;

/**
 *
 * @author Luca
 */
public class AutomatoFinito {

    ArrayList<Estado> estados = new ArrayList<Estado>();
    int counter;
    int max;
    String[] alfa = null;

    public String[] getAlfa() {
    	return alfa;
    }
    public AutomatoFinito() {
    }

    
    public static AutomatoFinito expressaoRegularToAF() {
    	String regex = "a.(((a+(a.b))*)+((a.b).(b.a)))*";
    	System.out.println(regex);
    	regex = regex.replace("+", "J");
    	regex = regex.replace("(", "K");
    	regex = regex.replace(")", "l");
    	String[] test = regex.split("K");
    	for (int i = 0; i < test.length; i ++)
    		System.out.println(test[i]);
    	return null;
    	
    }
    public Estado getEstado(String state) {
    	for(int i = 0; i < estados.size(); i ++) {
    		if(estados.get(i).getNome().compareTo(state) == 0 )
    			return this.estados.get(i);
    	}
    	return null;
    }
    
    public AutomatoFinito(ArrayList<Estado> a) {
        estados = a;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    /**
     * Cria o automato finito baseado no arquivo de entrada
     * @param directory 
     */
    
    public AutomatoFinito(String directory) {

        File file = new File(directory);
        Scanner s;
        int y;

        try {
            s = new Scanner(file);                                              //cria um scanner para ler arquivo

            while (s.hasNext()) {                                               //enquanto houver o que ler

                String temp = s.next() + ",";                                   // temp recebe a linha inteira

                y = temp.split(",").length;                                     // int y recebe o numero de simbolos existente
                alfa = temp.split(",");                                         // alfa[0] recebe o primeiro simbolo do alfabeto e assim por diante

                String[] temp1;

                temp = s.next();                                                //temp recebe a proxima linha

                while (!temp.split(":")[0].equals("f")) {                       //enquanto não encontrar um f na primeira posição antes de um ":"

                    temp1 = temp.split(";");                                    //temp1 = as divisões de <estado>;<transição1>;<transicao2>....

                    boolean ex = false;                                         //flag

                    for (Estado e : estados) {                                       //para cada estado
                        if (e.getNome().equals(temp1[0])) {                     //se existir um estado com o nome do estado em temp
                            ex = true;                                          //flag
                        }
                    }

                    if (!ex) {                                                  //caso não tenha encontrado

                        Estado novo = new Estado(temp1[0], y);                  //cria um novo estado com esse nome

                        estados.add(novo);                                           //adiciona no conjunto K ( adiciona no ArrayList)

                        for (Estado e : estados) {                                   //para cada estado

                            if (e.getNome().equals(temp1[0])) {                 //encontra esse estado novo

                                for (int l = 1; l < e.getTransicoes().length + 1; l++) {          //para cada transicao do novo estado

                                    e.addTransicoes(temp1[l], l);               //adiciona a transicao no novo estado, sob o simbolo de numero l

                                }
                            }
                        }

                    } else {

                        for (Estado e : estados) {                                   //para cada estado

                            if (e.getNome().equals(temp1[0])) {                 //encontra o estado da linha lida

                                for (int l = 1; l < e.getTransicoes().length + 1; l++) {          //para cada transicao dentro do estado

                                    e.addTransicoes(temp1[l], l);               //adiciona os estados lidos

                                }
                            }
                        }
                    }

                    temp = s.next();                                            //le a proxima linha

                }                                                               //repete
                                                                                //encontrou um f
                temp1 = temp.split(":")[1].split(",");                          //separa no ":" [f] : [<estado>,<estado>...] e no ","
                                                                                //[<estado>] , [<estado>]....
                for (int i = 0; i < temp1.length; i++) {                        //para cada estado que voce separou dos finais

                    for (Estado e : estados) {
                        if (e.getNome().equals(temp1[i])) {                     //encontra os estados
                            e.setFinal();                                       //seta o final nele
                        }
                    }
                }

                temp = s.next();                                                //le a proxima linha
                temp1 = temp.split(":")[1].split(",");                          //separa no ":" [s] : [<estado>,<estado>...] e no ","
                                                                                //[<estado>] , [<estado>]....
                for (int i = 0; i < temp1.length; i++) {                        //para cada estado

                    for (Estado e : estados) {                                       //encontra
                        if (e.getNome().equals(temp1[i])) {                     //o estado
                            e.setInicial();                                     //seta como inicial
                        }
                    }
                }
            }

            max = estados.size();
            counter = 0;

            if (alfa[0].equals("E")) {
                DeterminizarE();
            }

            Determinizar();

            //printAutomato();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Determiniza o automato, criando todos os estados necessários
     */
    public void Determinizar() {
        String[] j = null;
        Estado Temp = null;
        ArrayList<Estado> Det = new ArrayList<Estado>();

        for (Estado e : estados) {
            if (e.getTransicoes() != null) {

                String[] s = e.getTransicoes();                                 //s recebe as transicoes do estado atual no loop

                for (int i = 0; i < s.length; i++) {                            //para i menor que o tamanho de s (numero de alfabetos)
                    String t = Estado.ordena(s[i]);                             //ordena a entrada
                    if (t.split(",").length > 1) {                              //se uma transicao tiver mais que um estado no nome, cria ele
                        if (!checkExists(estados, t) && !checkExists(Det, t)) {      //se esse estado ja existir, ignora
                            Det.add(new Estado(t, s.length));
                        }
                    }
                }
            }
        }

        for (int k = 0; k < estados.size(); k++) {                                   //Para uma quantidade de vezes igual aos estados existentes.

            for (Estado e : Det) {                                              //para cada estado de Det
                for (int i = 0; i < e.getTransicoes().length; i++) {
                    if (e.getTransicoes()[i] == null) {                         //se este estado não possuir transicoes (eh um estado recem criado)
                        j = e.getNome().split(",");                             //separa o nome em suas componentes <nome> -> [<estado1>],[<estado2>]
                        Temp = e;                                               //variavel temporaria recebe o estado
                        break;
                    }
                }
            }

            if (j != null) {
                for (int t = 0; t < j.length; t++) {                                //para um t ateh o numero de estados no nome do estado
                    for (Estado e1 : estados) {                                          //para cada estado
                        if (e1.getNome().equals(j[t])) {                            //se o nome do estado encontrado for igual a componente do nome
                            for (int l = 0; l < e1.getTransicoes().length; l++) {   //para cada transicao desse estado encontrado
                                String tran = Estado.ordena(e1.getTransicoes()[l]); //ordena a transicao
                                Temp.addTransicoes(tran, l + 1);                    //pega as transicoes do estado encontrado e adiciona no outro estado
                                if (e1.isFinal()) {                                 //se o estado componente for final
                                    Temp.setFinal();                                //seta o novo como final
                                }
                            }
                        }
                    }
                }
            }
        }
        estados.addAll(Det);                                                         //adiciona tudo no es
        Det.clear();                                                            //limpa o det

        if (counter < max - 1) {                                                //sem esse contador, ele roda em loop infinito
            counter += 1;
            Determinizar();
        }

        if (counter == max - 1) {                                               //se ja chegou no fim criar arquivo

            criarArquivo();
        }

    }

    /**
     * imprime o automato na tela
     */
    public void printAutomato() {
        for (Estado e : estados) {
            System.out.println(e.getNome());

            for (int i = 0; i < e.getTransicoes().length; i++) {
                System.out.println(e.getNome() + ":" + e.getTransicoes()[i]);    //prints
            }
            System.out.println(e.isInicial());
            System.out.println(e.isFinal());

        }
    }

    /**
     * Método Determinizar E Prepara o Es para determinizar... retirando as
     * epsilon transicoes
     *
     */
    public void DeterminizarE() {                                                       //atualizar no git//////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> A1 = new ArrayList<String>();                                 //A1 de Strings

        for (Estado e : estados) {
            if (!e.getTransicoes()[0].equals("") || e.getTransicoes()[0] != null) {       //se a transicao por epsilon nao for nula

                findClosure(A1, e);                                                     //encontra todas as epsilon transicoes existentes

            }

            String[] j = new String[A1.size()];                                         //String temporario para receber os strings do array A1 criado pelas transicoes por epsilon

            for (int i = 0; i < A1.size(); i++) {
                j[i] = A1.get(i);                                                       //j recebe todos os valores de A1
            }

            Arrays.sort(j);                                                             //Sort

            String result = j[0];                                                       //começando pela primeira posicao do array, result = j[0]

            for (int i = 0; i < j.length; i++) {
                result = Estado.unirTransicoes(result, j[i]);                           //une transicoes com o resto dos estados, ja que
            }                                                                           //unir transicoes ja retira duplicatas e adiciona virgulas.

            e.setFecho(result);                                                         //coloca no fecho do estado

            A1.clear();                                                                 //limpa array A1
        }

        for (Estado e : estados) {
            for (int i = 0; i < e.getTransicoes().length; i++) {

                String[] l = e.getTransicoes()[i].split(",");

                for (Estado e1 : estados) {
                    for (int m = 0; m < l.length; m++) {

                        if (e1.getNome().equals(l[m])) {
                            e.addTransicoes(e1.getFecho(), i + 1);

                        }
                    }
                }
            }
        }

        for (Estado e : estados) {
            e.retirarE();
        }

    }

    /**
     *
     * @param Ae o ArrayList de todos os estados presentes
     * @param in o estado do qual se quer o fecho épsilon Método Find Closure
     * encontra o épsilon-fecho do estado in
     */
    public void findClosure(ArrayList<String> Ae, Estado in) {

        for (String e : Ae) {
            if (in.getNome().equals(e)) {
                return;
            }
        }

        Ae.add(in.getNome());

        String[] temp = in.getTransicoes();

        if (temp[0].split(",").length > 1) {

            for (Estado e : estados) {

                for (int l = 0; l < temp[0].split(",").length; l++) {

                    if (e.getNome().equals(temp[0].split(",")[l])) {

                        findClosure(Ae, e);

                    }
                }
            }
        } else {

            for (Estado e : estados) {
                if (e.getNome().equals(temp[0])) {
                    findClosure(Ae, e);
                }
            }
        }
    }

    /**
     *
     * @param Det um ArrayList de estados
     * @param nomeEstado o estado a ser checado
     * @return true se estado existe no automato false se não
     */
    public static boolean checkExists(ArrayList<Estado> Det, String nomeEstado) {      //Checa se um dado elemento ja existe no array
        for (Estado e : Det) {                                                  //para cada estado

            if (e.getNome().equals(nomeEstado)) {                               //se o nome do estado for igual ao nome do estado a ser adicionado
                return true;                                                    //retorna true
            }
        }

        return false;                                                           //else retorna false
    }

    /**
     * Método toRegex transforma o Automato Finito em uma Expressão Regular
     */
    public void toRegex() {
        
        Estado ini = new Estado("qi",1);
        Estado fim = new Estado("qf",1);
    }

    /**
     * Método Criar Arquivo imprime em um arquivo o automato finito
     */
    public void criarArquivo() {                                                //Cria o arquivo de saida
        ArrayList<Estado> buffer = new ArrayList<Estado>();
        Estado inicial = null;

        try {                                                                   //Tente
            File file = new File("C:\\Programations\\Exemplo\\Resposta 2.txt"); //crie um arquivo de resposta novo
            PrintWriter writer = new PrintWriter(file, "UTF-8");                //cria um printer

            writer.print(alfa[0]);

            for (int j = 1; j < alfa.length; j++) {                             //imprime cada simbolo do alfabeto
                writer.print("," + alfa[j]);
            }

            writer.println("");

            for (Estado e : estados) {                                               //para todo estado

                writer.print(e.getNome());

                for (int i = 0; i < e.getTransicoes().length; i++) {            //para cada transicao do estado
                    writer.print(";" + e.getTransicoes()[i]);                   //imprime
                }
                writer.println("");                                             //imprime um enter

                if (e.isFinal()) {
                    buffer.add(e);                                              //se o estado é final, adiciona no buffer de final
                }
                if (e.isInicial()) {
                    inicial = e;                                                //se o estado é inicial, guarda
                }
            }

            writer.print("f");                                                  //imprime um f
            int it = 0;
            for (Estado e1 : buffer) {                                            //se o iterador for 0, o primeiro simbolo sucede um :
                if (it == 0) {                                                    //se o iterado for diferente o simbolo sucede um ;
                    writer.print(":" + e1.getNome());
                } else {
                    writer.print(";" + e1.getNome());
                }
                it += 1;
            }

            writer.println("");                                                 //imprime um i: e o nome do estado inicial
            writer.println("i:" + inicial.getNome());

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
