package ArbolesAVL;

/**
 * @author Victor Osornio
 */
public class NodoAVL<T extends Comparable<T>> 
{

    T dato;
    int balance;
    NodoAVL izq, der, papa;

    /**
     * 
     * @param dato Comparable
     * @param p Nodo Papa 
     */
    NodoAVL(T d, NodoAVL p) 
    {
        dato = d;
        papa = p;
    }
    
    public static void main(String[] args) {
        
    }
}
