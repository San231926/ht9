package uvg.edu.gt;

import java.io.Serializable;

/**
 * Clase que representa un nodo del árbol de Huffman.
 * Cada nodo contiene un carácter, su frecuencia de aparición, y referencias a sus nodos hijo.
 */
public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
    public char character;
    public int frequency;
    public HuffmanNode left, right;

    /**
     * Construye un nuevo nodo de Huffman con un carácter y su frecuencia.
     *
     * @param character El carácter almacenado en este nodo
     * @param frequency La frecuencia de aparición del carácter
     */
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * Determina si este nodo es una hoja (no tiene hijos).
     *
     * @return true si el nodo es una hoja, false en caso contrario
     */
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    /**
     * Compara este nodo con otro nodo según su frecuencia.
     * Utilizado para ordenar nodos en una cola de prioridad durante la construcción del árbol de Huffman.
     *
     * @param other El otro nodo a comparar
     * @return Un valor negativo si este nodo tiene menor frecuencia, cero si son iguales,
     *         o un valor positivo si este nodo tiene mayor frecuencia
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}