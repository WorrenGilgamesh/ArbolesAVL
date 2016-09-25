package ArbolesAVL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Imprenta <T extends Comparable<T>> 
{

    public static <T extends Comparable<T>> void impNodo(NodoAVL<T> raiz) 
    {
        int maxNvl = Imprenta.maxNvl(raiz);
        impNodoInterno(Collections.singletonList(raiz), 1, maxNvl);
    }

    public static <T extends Comparable<T>> void impNodoInterno(List<NodoAVL<T>> nodo, int nvl, int maxNvl) 
    {
        if (nodo.isEmpty() || Imprenta.TodosElemNull(nodo)) 
            return;        

        int piso                            = maxNvl - nvl;
        int lineasCerca                     = (int) Math.pow(2, (Math.max(piso - 1, 0)));
        int primerosEspacios                = (int) Math.pow(2, (piso)) - 1;
        int espaciosEntre                   = (int) Math.pow(2, (piso + 1)) - 1;

        Imprenta.impEspaciosBlancos(primerosEspacios);

        List<NodoAVL<T>> nuevosNodos        = new ArrayList<NodoAVL<T>>();
        for (NodoAVL<T> node : nodo) 
        {
            if (node != null) 
            {
                System.out.print(node.dato);
                nuevosNodos.add(node.izq);
                nuevosNodos.add(node.der);
            } else 
            {
                nuevosNodos.add(null);
                nuevosNodos.add(null);
                System.out.print(" ");
            }

            Imprenta.impEspaciosBlancos(espaciosEntre);
        }
        System.out.println("");

        for (int i = 1; i <= lineasCerca; i++) 
        {
            for (int j = 0; j < nodo.size(); j++) 
            {
                Imprenta.impEspaciosBlancos(primerosEspacios - i);
                if (nodo.get(j) == null) 
                {
                    Imprenta.impEspaciosBlancos(lineasCerca + lineasCerca + i + 1);
                    continue;
                }

                if (nodo.get(j).izq != null)                
                    System.out.print("/");
                 else                 
                    Imprenta.impEspaciosBlancos(1);
                
                Imprenta.impEspaciosBlancos(i + i - 1);

                if (nodo.get(j).der != null) 
                    System.out.print("\\");
                else 
                    Imprenta.impEspaciosBlancos(1);
                
                Imprenta.impEspaciosBlancos(lineasCerca + lineasCerca - i);
            }
            System.out.println("");
        }
        impNodoInterno(nuevosNodos, nvl + 1, maxNvl);
    }

    public static void impEspaciosBlancos(int cont) 
    {
        for (int i = 0; i < cont; i++) 
            System.out.print(" ");
        
    }

    public static <T extends Comparable<T>> int maxNvl(NodoAVL<T> nodo)
    {
        if (nodo == null) 
            return 0;
        return Math.max(Imprenta.maxNvl(nodo.izq), Imprenta.maxNvl(nodo.der)) + 1;
    }

    public static <T> boolean TodosElemNull(List<T> list)
    {
        for (Object object : list)
        {
            if (object != null) 
                return false;            
        }
        return true;
    }
}
