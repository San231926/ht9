package uvg.edu.gt;

import java.io.*;
import java.util.*;
import java.nio.file.Files;

public class HuffmanCompressor {

    public static Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        return freq;
    }

    public static void compress(String inputFile, String outputHuff, String outputTree) throws IOException {
        String text = new String(Files.readAllBytes(new File(inputFile).toPath()));

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
