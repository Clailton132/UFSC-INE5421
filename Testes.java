
import java.util.ArrayList;

/**
 *
 * @author Luca
 */

public class Testes {

    ArrayList<Estado> es = new ArrayList<Estado>();
    ArrayList<String> ans = new ArrayList<String>();

    public Testes() {
        Estado i = new Estado("q0", 2);
        Estado j = new Estado("q1", 2);
        Estado k = new Estado("q2", 2);
        Estado p = new Estado("q3", 2);

        i.addTransicoes("q1", 1);
        i.addTransicoes("q0,q1", 2);
        j.addTransicoes("q2", 1);
        j.addTransicoes("q1", 2);
        k.addTransicoes("q0,q1,q3", 1);
        k.addTransicoes("q1", 2);
        p.addTransicoes("", 1);
        p.addTransicoes("q1", 2);

        es.add(i);
        es.add(j);
        es.add(k);
        es.add(p);

        findWay(ans, i);

        for (String e : ans) {
            System.out.println(e);
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
