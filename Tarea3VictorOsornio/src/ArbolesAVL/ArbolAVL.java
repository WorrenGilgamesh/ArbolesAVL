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
     * Se crea un boolean que te dira si el dato es mayor = true y falso = menor
     * n= dependiendo si es mayo o menor es la direccion que tomara
     * si contiene algo entonces lo regresa y vuelve a tomar la decision de izq o der y si esta vacio se lo pasa
     * revalancea si es necesario, truena mal pex el while y te regre
     * @param dato Comparable
     * @return boolean
     */
    public boolean insertar(T d) 
    {
        boolean res=false;
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
                    res=true;
                    break;
                }
            }
        }
        return res;
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

    /**
     * Se le pone un balance a cada nodo, si su balance es -2 y la altura de la izq izq es mas grande o igual que la izq dere
     * se hace una rotacion derecha si no se hace una rotacion izqDer
     * si el balance es igual a 2 y la altura der der >= a der izq se hace una rotacion izq de lo contrario rotacion derizq
     * si sus balances no son igual a 2 o -2 entonces pasara al nodo de arriba y si no hay nodo arriba se vulve raiz
     * @param nodo 
     */
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
/**
 * iniciamos un nodo b que se comportara como un pivote tendra el hijoDer del nodo, 
 * el padre de b sera el mismo que el nodo para no perder la conexion
 * el hijoIzq de la b eliminara el hijoDer del nodo
 * el nodo se volvera el hijoIzq de b
 * y b pasara a ser el papa del nodo
 * si el hijo del papa de b es igual entonces se muda a ser el hijo der de su papa, si no se muda a ser el hijoIzq * 
 * 
 * @param nodo
 * @return el nuevo nodo
 */
    private NodoAVL<T> rotacionIzq(NodoAVL<T> n) 
    {
        NodoAVL<T> b        = n.der;
        b.papa              = n.papa;
        n.der               = b.izq;       
        
        b.izq               = n;
        n.papa              = b;

        if (b.papa != null) 
        {
            if (b.papa.der == n)            
                b.papa.der = b;
             else            
                b.papa.izq = b;            
        }
        setBalance(n, b);
        return b;
    }
/**
 *lo contrario del rotacionIzq
 * @param nodo 
 * @return el nuevo nodo
 */
    private NodoAVL<T> rotacionDer(NodoAVL<T> n) 
    {
        NodoAVL<T> b     = n.izq;
        b.papa           = n.papa;
        n.izq            = b.der;

        if (n.izq != null)         
            n.izq.papa   = n;        

        b.der            = n;
        n.papa           = b;

        if (b.papa != null) 
        {
            if (b.papa.der == n)             
                b.papa.der = b;
             else            
                b.papa.izq = b;           
        }
        setBalance(n, b);
        return b;
    }
/**
 * rotacion izq y luego derecha en un solo metodo
 * @param n
 * @return el nuevo nodo
 */
    private NodoAVL<T> rotacionIzqDer(NodoAVL<T> n) 
    {
        n.izq           = rotacionIzq(n.izq);
        return rotacionDer(n);
    }
/**
 * rotacion Der y luego izq en un solo metodo
 * @param n
 * @return el nuevo nodo
 */
    private NodoAVL<T> rotacionDerIzq(NodoAVL<T> n) 
    {
        n.der           = rotacionDer(n.der);
        return rotacionIzq(n);
    }
/**
 * clacula recursivamente la altura del izquierdo y derecho y despues deside cual es mas grande y le aumenta la cabeza
 * @param nodo
 * @return 
 */
    private int altura(NodoAVL<T> n) 
    {
        if (n == null)
            return -1;
        
        return 1 + Math.max(altura(n.izq), altura(n.der));
    }
//falta por construir
    public int diametro(NodoAVL<T> n)
    {
       NodoAVL<T> a        =raiz.izq;
       
       altura(raiz);
       return 0; 
    }

    /**
     * Se utiliza varargs para poderle pasar multiples parametros o incluso un arreglo,
     * para que despues con un for each lea todo los parametros pasados y a cada nodo le ponga
     * un balance particular 
     * @param varargs nodes 
     */
    private void setBalance(NodoAVL<T>... nodos) 
    {
        for (NodoAVL<T> n : nodos) 
            n.balance   = altura(n.der) - altura(n.izq);        
    }
/**
 * te da el balance desde raiz
 */
    public void printBalance() 
    {
        printBalance(raiz);
    }
/**
 * te imprime el balance izq der
 * @param n 
 */
    private void printBalance(NodoAVL<T> n) 
    {
        if (n != null) 
        {
            printBalance(n.izq);
            System.out.printf("%s ", n.balance);
            printBalance(n.der);
        }
    }
/**
 * Prueba inserta y elimina, el buscar esta implicito en el elimina
 * @param args 
 */
    public static void main(String[] args) 
    {
        ArbolAVL arbol          = new ArbolAVL();
//        int numeroDatos         = 15;
//
//        System.out.println("Valores insertados del 1 a " + numeroDatos);        
//        for (int i = 1; i <= numeroDatos; i++)        
//            arbol.insertar(i);
//        
//        Imprenta.impNodo(arbol.raiz);
//        System.out.print("Balance: ");
//        arbol.printBalance();
//        
//        System.out.println("");
//        int numElim = 4;
//        arbol.Elimina(numElim);
//        Imprenta.impNodo(arbol.raiz);
//        System.out.print("Balance: ");
//        arbol.printBalance();
//        System.out.println("");
//        System.out.println("El numero " + numElim + " ha sido eliminado");

            arbol.insertar(100);
            arbol.insertar(50);
            arbol.insertar(75);
            arbol.insertar(60);
            Imprenta.impNodo(arbol.raiz);
            arbol.Elimina(100);
            Imprenta.impNodo(arbol.raiz);
            arbol.insertar(40);
            arbol.insertar(56);
            arbol.insertar(80);
            arbol.insertar(45);
            arbol.insertar(70);
            arbol.insertar(72);
            arbol.insertar(58);
            arbol.insertar(30);
            arbol.insertar(35);
            Imprenta.impNodo(arbol.raiz);
            arbol.Elimina(80);
            arbol.Elimina(60);
            arbol.Elimina(30);
            arbol.Elimina(35);
            arbol.Elimina(45);
            Imprenta.impNodo(arbol.raiz);
    }
}
