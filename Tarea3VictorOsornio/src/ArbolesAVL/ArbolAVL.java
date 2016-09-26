package ArbolesAVL;

/**
 * @author Victor Osornio
 */
public class ArbolAVL<T extends Comparable<T>> 
{
    private NodoAVL<T>          raiz;

    /**
     * Metodo de insercion de datos Comparables. Comienza checando que tenga raiz, si no, le crea una raiz. 
     * Si ya tiene raiz; raiz se pasa a n, busca que el dato no se encuentre ya, papa=n 
     * @param dato Comparable
     * @return boolean
     */
    public boolean insertar(T d) 
    {
        if (raiz == null)        
            raiz = new NodoAVL(d, null);
        else 
        {
            NodoAVL<T> n       =raiz;
            NodoAVL<T> papa;
            while (true) //debido a la flojera de construir un iterador se usa un while que se rompe por la mala !no hacer esto == malas practicas!
            {
                if (n.dato.equals(d))               
                    return false;
                
                papa            = n;
                boolean direc   = n.dato.compareTo(d) > 0;
                n               = direc ? n.izq : n.der;

                if (n == null) 
                {
                    if (direc)                   
                        papa.izq = new NodoAVL(d, papa);
                    else                   
                        papa.der = new NodoAVL(d, papa);
                    
                    rebalance(papa);
                    break;
                }
            }
        }
        return true;
    }

    public void Elimina(T dElim) 
    {
        if (raiz == null)       
            return;
        
        NodoAVL<T> n            = raiz;
        NodoAVL<T> papa         = raiz;
        NodoAVL<T> nodoElim     = null;
        NodoAVL<T> Hijo         = raiz;

        while (Hijo != null) 
        {
            papa                = n;
            n                   = Hijo;
            Hijo                = dElim.compareTo(n.dato) >= 0 ? n.der : n.izq;
            
            if (dElim == n.dato)           
                nodoElim        = n;            
        }

        if (nodoElim != null) 
        {
            nodoElim.dato       = n.dato;
            Hijo                = n.izq != null ? n.izq : n.der;

            if (raiz.dato == dElim)            
                raiz = Hijo;
            else 
            {
                if (papa.izq == n)                
                    papa.izq    = Hijo;
                else               
                    papa.der    = Hijo;
                
                rebalance(papa);
            }
        }
    }

    private void rebalance(NodoAVL<T> n) 
    {
        setBalance(n);

        if (n.balance == -2) 
        {
            if (altura(n.izq.izq) >= altura(n.izq.der))            
                n             = rotacionDer(n);
            else            
                n             = rotacionIzqDer(n);
        } else if (n.balance == 2) 
        {
            if (altura(n.der.der) >= altura(n.der.izq))           
                n             = rotacionIzq(n);
            else            
                n             = rotacionDerIzq(n);          
        }
        if (n.papa != null)        
            rebalance(n.papa);
        else        
            raiz = n;        
    }

    private NodoAVL<T> rotacionIzq(NodoAVL<T> a) 
    {

        NodoAVL<T> b        = a.der;
        b.papa              = a.papa;
        a.der               = b.izq;

        if (a.der != null)         
            a.der.papa      = a;
        
        b.izq               = a;
        a.papa              = b;

        if (b.papa != null) 
        {
            if (b.papa.der == a)            
                b.papa.der = b;
             else            
                b.papa.izq = b;            
        }
        setBalance(a, b);
        return b;
    }

    private NodoAVL<T> rotacionDer(NodoAVL<T> a) 
    {

        NodoAVL<T> b     = a.izq;
        b.papa           = a.papa;
        a.izq            = b.der;

        if (a.izq != null)         
            a.izq.papa   = a;        

        b.der            = a;
        a.papa           = b;

        if (b.papa != null) 
        {
            if (b.papa.der == a)             
                b.papa.der = b;
             else            
                b.papa.izq = b;           
        }
        setBalance(a, b);
        return b;
    }

    private NodoAVL<T> rotacionIzqDer(NodoAVL<T> n) 
    {
        n.izq           = rotacionIzq(n.izq);
        return rotacionDer(n);
    }

    private NodoAVL<T> rotacionDerIzq(NodoAVL<T> n) 
    {
        n.der           = rotacionDer(n.der);
        return rotacionIzq(n);
    }

    private int altura(NodoAVL<T> n) 
    {
        if (n == null)
            return -1;
        
        return 1 + Math.max(altura(n.izq), altura(n.der));
    }

    private void setBalance(NodoAVL<T>... nodes) 
    {
        for (NodoAVL<T> n : nodes) 
            n.balance   = altura(n.der) - altura(n.izq);        
    }

    public void printBalance() 
    {
        printBalance(raiz);
    }

    private void printBalance(NodoAVL<T> n) 
    {
        if (n != null) 
        {
            printBalance(n.izq);
            System.out.printf("%s ", n.balance);
            printBalance(n.der);
        }
    }

    public static void main(String[] args) 
    {
        ArbolAVL arbol          = new ArbolAVL();
        int numeroDatos         = 15;

        System.out.println("Valores insertados del 1 a " + numeroDatos);        
        for (int i = 1; i <= numeroDatos; i++)        
            arbol.insertar(i);
        
        Imprenta.impNodo(arbol.raiz);
        System.out.print("Balance: ");
        arbol.printBalance();
        
        System.out.println("");
        int numElim = 3;
        arbol.Elimina(numElim);
        Imprenta.impNodo(arbol.raiz);
        System.out.print("Balance: ");
        System.out.println("El numero " + numElim + " ha sido eliminado");
    }
}
