package uvg.edu.gt;

import java.io.*;
import java.util.*;
import java.nio.file.Files;

/**
 * Clase que implementa la descompresión de archivos utilizando el algoritmo de Huffman.
 * Permite descomprimir un archivo comprimido (.huff) utilizando su correspondiente
 * archivo de árbol (.hufftree) para recuperar el archivo de texto original.
 */
public class HuffmanDecompressor {

    /**
     * Reconstruye el árbol de Huffman a partir de su representación serializada.
     *
     * @param treeData La cadena que contiene la representación serializada del árbol de Huffman
     * @param index Un arreglo con un único valor que representa la posición actual en el proceso de deserialización
     * @return El nodo raíz del árbol de Huffman reconstruido
     */
    public static HuffmanNode deserializeTree(String treeData, int[] index) {
        char marker = treeData.charAt(index[0]++);
        if (marker == '1') {
            char character = treeData.charAt(index[0]++);
            return new HuffmanNode(character, 0);
        } else {
            HuffmanNode node = new HuffmanNode('\0', 0);
            node.left = deserializeTree(treeData, index);
            node.right = deserializeTree(treeData, index);
            return node;
        }
    }

    /**
     * Descomprime un archivo utilizando el algoritmo de Huffman.
     *
     * @param inputHuff La ruta del archivo comprimido (.huff)
     * @param inputTree La ruta del archivo que contiene el árbol de Huffman (.hufftree)
     * @param outputTxt La ruta donde se guardará el archivo de texto descomprimido
     * @throws IOException Si ocurre un error durante la lectura o escritura de archivos
     */
    public static void decompress(String inputHuff, String inputTree, String outputTxt) throws IOException {
        // Leer árbol de Huffman desde archivo
        String treeData = new String(Files.readAllBytes(new File(inputTree).toPath()));
        HuffmanNode root = deserializeTree(treeData, new int[]{0});

        // Leer archivo binario .huff
        StringBuilder decodedText = new StringBuilder();
        try (BitInputStream bis = new BitInputStream(new FileInputStream(inputHuff))) {
            HuffmanNode current = root;
            int bit;
            while ((bit = bis.readBit()) != -1) {
                current = (bit == 0) ? current.left : current.right;

                if (current.isLeaf()) {
                    decodedText.append(current.character);
                    current = root;
                }
            }
        }

        // Escribir texto descomprimido
        try (FileWriter fw = new FileWriter(outputTxt)) {
            fw.write(decodedText.toString());
        }
    }
}