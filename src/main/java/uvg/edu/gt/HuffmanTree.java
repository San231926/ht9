package uvg.edu.gt;

import java.util.*;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Character, String> huffmanCodes = new HashMap<>();

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

    private void buildCodeMap(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
        } else {
            buildCodeMap(node.left, code + "0");
            buildCodeMap(node.right, code + "1");
        }
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public void serializeTree(StringBuilder builder) {
        serializeTree(root, builder);
    }

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
