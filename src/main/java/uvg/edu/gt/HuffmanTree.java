package uvg.edu.gt;

import java.util.*;

/**
 * Clase que implementa un árbol de Huffman para la compresión y descompresión de datos.
 * Un árbol de Huffman asigna códigos de longitud variable a los caracteres según su frecuencia,
 * permitiendo una compresión eficiente de datos.
 */
public class HuffmanTree {
    private HuffmanNode root;
    private Map<Character, String> huffmanCodes = new HashMap<>();

    /**
     * Construye un árbol de Huffman basado en un mapa de frecuencias de caracteres.
     *
     * @param freqMap Un mapa que asocia cada carácter con su frecuencia de aparición
     */
    public HuffmanTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.offer(parent);
        }

        root = pq.poll();
        buildCodeMap(root, "");
    }

    /**
     *
     * @param node El nodo actual en el recorrido del árbol
     * @param code El código acumulado hasta el momento (0 para izquierda, 1 para derecha)
     */
    private void buildCodeMap(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
        } else {
            buildCodeMap(node.left, code + "0");
            buildCodeMap(node.right, code + "1");
        }
    }

    /**
     * Obtiene el mapa de códigos de Huffman generado para cada carácter.
     *
     * @return Un mapa que asocia cada carácter con su código binario de Huffman
     */
    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    /**
     * Obtiene el nodo raíz del árbol de Huffman.
     *
     * @return El nodo raíz del árbol
     */
    public HuffmanNode getRoot() {
        return root;
    }

    /**
     * Serializa el árbol de Huffman en una representación de texto.
     *
     * @param builder El StringBuilder donde se acumulará la representación del árbol
     */
    public void serializeTree(StringBuilder builder) {
        serializeTree(root, builder);
    }

    /**
     * Utiliza un recorrido en preorden, marcando nodos internos con '0' y hojas con '1' seguido del carácter.
     *
     * @param node El nodo actual en el recorrido del árbol
     * @param builder El StringBuilder donde se acumulará la representación del árbol
     */
    private void serializeTree(HuffmanNode node, StringBuilder builder) {
        if (node.isLeaf()) {
            builder.append('1').append(node.character);
        } else {
            builder.append('0');
            serializeTree(node.left, builder);
            serializeTree(node.right, builder);
        }
    }
}