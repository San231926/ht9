package uvg.edu.gt;

import java.io.*;
import java.util.*;
import java.nio.file.Files;

/**
 * Clase que implementa la compresión de archivos utilizando el algoritmo de Huffman.
 */
public class HuffmanCompressor {

    /**
     * Construye un mapa de frecuencias para cada carácter en el texto.
     *
     * @param text El texto del cual se calcularán las frecuencias de caracteres
     * @return Un mapa donde las claves son los caracteres y los valores son sus frecuencias
     */
    public static Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        return freq;
    }

    /**
     * Comprime un archivo utilizando el algoritmo de Huffman.
     *
     * @param inputFile La ruta del archivo de entrada a comprimir
     * @param outputHuff La ruta donde se guardará el archivo comprimido (.huff)
     * @param outputTree La ruta donde se guardará la representación del árbol de Huffman (.hufftree)
     * @throws IOException Si ocurre un error durante la lectura o escritura de archivos
     */
    public static void compress(String inputFile, String outputHuff, String outputTree) throws IOException {
        // Leer el archivo de entrada
        String text = new String(Files.readAllBytes(new File(inputFile).toPath()));

        // Construir el árbol de Huffman
        Map<Character, Integer> freqMap = buildFrequencyMap(text);
        HuffmanTree huffmanTree = new HuffmanTree(freqMap);
        Map<Character, String> codes = huffmanTree.getHuffmanCodes();

        // Codificar texto
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codes.get(c));
        }

        // Escribir archivo .huff
        try (FileOutputStream fos = new FileOutputStream(outputHuff);
             BitOutputStream bos = new BitOutputStream(fos)) {
            for (char bit : encoded.toString().toCharArray()) {
                bos.writeBit(bit == '1' ? 1 : 0);
            }
        }

        // Escribir árbol en .hufftree (preorden)
        StringBuilder treeSerialized = new StringBuilder();
        huffmanTree.serializeTree(treeSerialized);
        try (FileWriter fw = new FileWriter(outputTree)) {
            fw.write(treeSerialized.toString());
        }
    }
}
