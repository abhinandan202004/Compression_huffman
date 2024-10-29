
# Huffman Image Compressor

This project implements a simple image compression and decompression tool using **Huffman Encoding**. This method applies a lossless compression algorithm to reduce the file size of grayscale images. It encodes pixel values based on their frequency and creates a compressed binary file format.

## Features

- **Compress Grayscale Images**: Converts an image to grayscale and compresses it using Huffman encoding.
- **Decompress Compressed Images**: Restores the compressed image file back to its original form.
- **Lossless Compression**: Ensures that no image data is lost during compression and decompression.

## How It Works

1. **Huffman Tree Construction**: The program calculates pixel frequency in the grayscale image and constructs a Huffman tree.
2. **Binary Encoding**: Each pixel value is replaced by a variable-length code based on frequency (more frequent pixels get shorter codes).
3. **File Saving**: Encoded data, Huffman tree, and image dimensions are saved to a compressed `.huff` file.
4. **Decompression**: The `.huff` file is read to rebuild the image using the Huffman codes.

## Requirements

- **Java Development Kit (JDK)** 17 or later
- **Java I/O Libraries** and **javax.imageio** for handling image file operations

## Installation

1. Clone or download this repository.
2. Ensure you have the JDK installed and set up.

## Usage

### 1. Compile the Program
In the terminal, navigate to the project directory and compile the code:

```bash
javac HuffmanImageCompressor.java
```

### 2. Run the Compressor and Decompressor

Run the program, specifying the paths for input and output files within the code, or modify them as needed.

```bash
java HuffmanImageCompressor
```

### 3. Check Output

- The program saves the compressed file as `compressed_image.huff`.
- Decompressed output will be saved as `output_image.png`, mirroring the original `input_image.png`.

## Example

For an example grayscale image `input_image.png`, the command generates:
- **compressed_image.huff**: Compressed binary file
- **output_image.png**: Reconstructed image from compressed data

## Images

Here’s an example of the image compression and decompression process:

### Original Image (Input)
<img src="Huffman Img Compression/animal_img.png" alt="Input Image" width="300">

### Compressed Image File
`compressed_image.huff` (binary file, not viewable as an image)

### Decompressed Image (Output)
<img src="Huffman Img Compression/out2_image.png" alt="Output Image" width="300">

The output image should resemble the input image closely, as Huffman encoding is a lossless compression method.

## Limitations

Huffman encoding may not always yield a smaller file size for high-entropy images or images with a large variety of pixel values. For complex images, the storage required for Huffman tree metadata may offset the compression gains. This project demonstrates Huffman coding as a learning exercise and may not outperform image-specific algorithms like PNG compression.

## Troubleshooting

- **EOFException**: Ensure data is read in the correct order during decompression.
- **Large File Sizes**: Huffman encoding alone may not be efficient for all types of images; try simpler images for better results.

## License

This project is licensed under the MIT License 


