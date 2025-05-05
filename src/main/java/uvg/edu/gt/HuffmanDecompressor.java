package uvg.edu.gt;

import java.io.*;
import java.util.*;
import java.nio.file.Files;

public class HuffmanDecompressor {

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