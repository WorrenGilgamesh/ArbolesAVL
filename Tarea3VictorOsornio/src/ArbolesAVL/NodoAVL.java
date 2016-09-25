package ArbolesAVL;

/**
 * @author Victor Osornio
 */
public class NodoAVL<T extends Comparable<T>> {

    T dato;
    int balance;
    NodoAVL izq, der, papa;

    NodoAVL(T d, NodoAVL p) {
        dato = d;
        papa = p;
    }
}
