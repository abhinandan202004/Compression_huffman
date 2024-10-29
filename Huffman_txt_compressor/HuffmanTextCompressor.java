import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanTextCompressor {

    static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;

        public Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        public Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.freq, o.freq);
        }
    }

    private Map<Character, String> huffmanCodeMap = new HashMap<>();
    private Node root;

    // Compress text file
    public void compressFile(String inputPath, String outputPath) throws IOException {
        String text = readFile(inputPath);
        Map<Character, Integer> frequencyMap = buildFrequencyMap(text);
        root = buildHuffmanTree(frequencyMap);
        buildCodeMap(root, "");

        // Encode the text
        StringBuilder encodedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedText.append(huffmanCodeMap.get(ch));
        }

        // Write the encoded text and Huffman tree to output file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputPath))) {
            out.writeObject(frequencyMap);
            out.writeObject(encodedText.toString());
        }

        System.out.println("Text compressed and saved as: " + outputPath);
    }

    // Decompress text file
    public void decompressFile(String compressedPath, String outputPath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(compressedPath))) {
            Map<Character, Integer> frequencyMap = (Map<Character, Integer>) in.readObject();
            String encodedText = (String) in.readObject();

            // Rebuild the Huffman Tree
            root = buildHuffmanTree(frequencyMap);

            // Decode the text
            StringBuilder decodedText = new StringBuilder();
            Node current = root;
            for (char bit : encodedText.toCharArray()) {
                current = bit == '0' ? current.left : current.right;

                if (current.isLeaf()) {
                    decodedText.append(current.ch);
                    current = root;
                }
            }

            writeFile(outputPath, decodedText.toString());
            System.out.println("Text decompressed and saved as: " + outputPath);
        }
    }

    // Build frequency map for characters in the text
    private Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }
        return frequencyMap;
    }

    // Build Huffman Tree
    private Node buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (var entry : frequencyMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node(left.freq + right.freq, left, right));
        }
        return pq.poll();
    }

    // Build Huffman codes map
    private void buildCodeMap(Node node, String code) {
        if (node.isLeaf()) {
            huffmanCodeMap.put(node.ch, code.isEmpty() ? "0" : code);
        } else {
            buildCodeMap(node.left, code + '0');
            buildCodeMap(node.right, code + '1');
        }
    }

    // Read file content into a string
    private String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    // Write string content to a file
    private void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    // Main method for testing compression and decompression
    public static void main(String[] args) {
        HuffmanTextCompressor compressor = new HuffmanTextCompressor();

        String inputPath = "input.txt";        // Path to input text file
        String compressedPath = "compressed.huff"; // Path to save compressed file
        String decompressedPath = "output.txt"; // Path to save decompressed text

        try {
            // Compress
            compressor.compressFile(inputPath, compressedPath);

            // Decompress
            compressor.decompressFile(compressedPath, decompressedPath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
