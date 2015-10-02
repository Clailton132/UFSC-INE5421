
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luca
 */
public class Estado {

    private String nome;
    private String[] transicoes;
    private boolean boolInicial = false;
    private boolean boolFinal = false;
    private boolean boolAlcancavel = false;
    private String fecho;
    private String[] regex;

    public Estado(String nome, int y) {
        this.nome = nome;
        this.transicoes = new String[y];
        this.regex = new String[y * y];
    }

    public String[] getRegex() {
        return regex;
    }
    
    public void clear(int i){
        this.transicoes[i] = "";
    }
    
    public String[] getRegexS(String nome){
        ArrayList<String> r = new ArrayList<String>();
        for(int i = 0; i < regex.length; i++){
            if(regex[i] == null){
            } else
            if(regex[i].split(":")[0].equals(nome)){
                r.add(regex[i]);
            }
        }
        
        String[] temp = new String[r.size()];
        for(int i = 0; i < r.size(); i++){
            temp[i] = r.get(i);
        }

        return temp;
    }

    public void addRegex(String nomeNextEstado, String tran) {

        for (int i = 0; i < regex.length; i++) {

            if (regex[i] == null) {
                regex[i] = nomeNextEstado + ":" + tran;
                return;
            }

            if (nomeNextEstado.equals(regex[i].split(":")[0])) {
                String t = "( " + regex[i].split(":")[1] + " ) " + tran;
                regex[i] = nomeNextEstado + ":" + t;
                return;
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public String[] getTransicoes() {
        return transicoes;
    }

    /*Adiciona transicao ao estado, Argumentos(String com de qual estado a tal separado por virgula
     Exemplo "q0,q1" de q0 a q1, int de qual posição da linguagem é feita a transição, exemplo 
     linguagem L=(a,b) transoção de q0 para q1 por b. ("q0,q1",1)*/
    public void addTransicoes(String transicao, int l) {
        int i = l - 1;

        if (transicoes[i] == null) { //se nao houver a transicao por aquele simbolo l do alfabeto
            if (!transicao.equals("qr")) {
                this.transicoes[i] = ordena(transicao); //a transicao por o simbolo l recebe a entrada
            } else {
                this.transicoes[i] = "";
            }
        } else {
            if (!transicao.equals("qr") && !transicao.equals("")) {
                if (this.transicoes[i].equals("")) {
                    this.transicoes[i] = transicao;
                } else {
                    this.transicoes[i] = unirTransicoes(this.transicoes[i], transicao);
                }
            }

        }

        this.regex[i] = this.transicoes[i] + ":" + i;
        if(this.getNome().equals("qi")){
            this.regex[i] = this.transicoes[i] + ":" + " ";
        }

    }

    public void blowT(String[] t) {
        this.transicoes = t;
    }

    public boolean isInicial() {
        return boolInicial;
    }

    public boolean isFinal() {
        return boolFinal;
    }

    public boolean isAlc() {
        return boolAlcancavel;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInicial() {
        this.boolInicial = true;
    }

    public void setFinal() {
        this.boolFinal = true;
    }

    public void setAlc() {
        boolAlcancavel = true;
    }

    /*Retorna string de nome de Estado ordenada, Argumentos(String transição a ser ordenada), Exemplo:
     Entra "q3q1q2" retorna "q1q2q3"*/
    public static String ordena(String transicao) {
        String[] lista = transicao.split(",");
        Arrays.sort(lista);
        String ordenada = lista[0];
        for (int i = 1; i < lista.length; i++) {
            ordenada = ordenada + "," + lista[i];
        }
        return ordenada;
    }

    public static String unirTransicoes(String a, String b) {
        String[] A;
        String[] B;
        String[] C;

        if (a != null) {
            A = a.split(",");
        } else {
            A = new String[0];
        }

        if (b != null) {
            B = b.split(",");
        } else {
            B = new String[0];
        }

        C = new String[A.length + B.length];

        int k = 0;

        for (int i = 0; i < A.length; i++, k++) {
            C[k] = A[i];
        }
        for (int i = 0; i < B.length; i++, k++) {
            C[k] = B[i];
        }

        Arrays.sort(C);
        String ret = C[0];

        for (int i = 1; i < C.length; i++) {
            if (!C[i].equals(C[i - 1])) {
                ret = ret + "," + C[i];
            }

        }
        return ret;

    }

    public void setFecho(String fecho) {
        this.fecho = fecho;
    }

    public String getFecho() {
        return this.fecho;
    }

    public void retirarE() {
        String[] novo = new String[this.transicoes.length - 1];
        for (int i = 1; i < this.transicoes.length; i++) {
            novo[i - 1] = this.transicoes[i];
        }
        this.transicoes = novo;
    }

}//
