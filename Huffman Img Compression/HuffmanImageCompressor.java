import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import javax.imageio.ImageIO;

class Node implements Comparable<Node> {
    int freq;
    int pixel;
    Node left, right;

    Node(int pixel, int freq) {
        this.pixel = pixel;
        this.freq = freq;
    }

    public int compareTo(Node other) {
        return this.freq - other.freq;
    }
}

public class HuffmanImageCompressor {
    private Map<Integer, String> huffmanCodes = new HashMap<>();
    
    // Calculate frequency of each pixel
    private Map<Integer, Integer> calculateFrequency(int[][] imageArray) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int[] row : imageArray) {
            for (int pixel : row) {
                frequency.put(pixel, frequency.getOrDefault(pixel, 0) + 1);
            }
        }
        return frequency;
    }

    // Build Huffman Tree
    private Node buildHuffmanTree(Map<Integer, Integer> frequency) {
        PriorityQueue<Node> heap = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            heap.add(new Node(entry.getKey(), entry.getValue()));
        }
        
        while (heap.size() > 1) {
            Node left = heap.poll();
            Node right = heap.poll();
            Node parent = new Node(-1, left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            heap.add(parent);
        }
        
        return heap.poll();
    }

    // Assign codes to pixels based on Huffman Tree
    private void assignCodes(Node node, String code) {
        if (node == null) return;
        
        if (node.pixel != -1) {
            huffmanCodes.put(node.pixel, code);
        }
        
        assignCodes(node.left, code + "0");
        assignCodes(node.right, code + "1");
    }

    // Compress image using Huffman encoding
public void compressImage(String inputPath, String outputPath) throws IOException {
    BufferedImage image = ImageIO.read(new File(inputPath));
    int width = image.getWidth();
    int height = image.getHeight();
    
    // Convert image to grayscale array
    int[][] imageArray = new int[height][width];
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int pixel = image.getRGB(x, y) & 0xFF;
            imageArray[y][x] = pixel;
        }
    }
    
    // Calculate frequency and build Huffman Tree
    Map<Integer, Integer> frequency = calculateFrequency(imageArray);
    Node huffmanTree = buildHuffmanTree(frequency);
    assignCodes(huffmanTree, "");

    // Encode image data
    StringBuilder encodedData = new StringBuilder();
    for (int[] row : imageArray) {
        for (int pixel : row) {
            encodedData.append(huffmanCodes.get(pixel));
        }
    }

    // Save compressed data to file in correct order
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputPath))) {
        oos.writeObject(encodedData.toString());
        oos.writeObject(huffmanCodes);
        oos.writeInt(width);
        oos.writeInt(height);
    }
    
    System.out.println("Image compressed and saved as: " + outputPath);
}

// Decompress image from compressed file
public void decompressImage(String compressedPath, String outputPath) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(compressedPath))) {
        String encodedData = (String) ois.readObject();
        Map<Integer, String> codes = (Map<Integer, String>) ois.readObject();
        int width = ois.readInt();
        int height = ois.readInt();

        // Reverse the Huffman codes for decoding
        Map<String, Integer> reverseCodes = new HashMap<>();
        for (Map.Entry<Integer, String> entry : codes.entrySet()) {
            reverseCodes.put(entry.getValue(), entry.getKey());
        }

        // Decode the data
        int[][] decompressedArray = new int[height][width];
        String currentCode = "";
        int x = 0, y = 0;
        for (char bit : encodedData.toCharArray()) {
            currentCode += bit;
            if (reverseCodes.containsKey(currentCode)) {
                decompressedArray[y][x] = reverseCodes.get(currentCode);
                currentCode = "";
                x++;
                if (x == width) {
                    x = 0;
                    y++;
                }
            }
        }

        // Reconstruct and save decompressed image
        BufferedImage decompressedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixelValue = decompressedArray[i][j];
                int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue;
                decompressedImage.setRGB(j, i, rgb);
            }
        }
        
        ImageIO.write(decompressedImage, "png", new File(outputPath));
        System.out.println("Image decompressed and saved as: " + outputPath);
    }
}


    public static void main(String[] args) {
        HuffmanImageCompressor compressor = new HuffmanImageCompressor();
        String inputPath = "animal_img.png";
        String compressedPath = "compressed1_image.huff";
        String outputPath = "out2_image.png";

        try {
            compressor.compressImage(inputPath, compressedPath);
            compressor.decompressImage(compressedPath, outputPath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
