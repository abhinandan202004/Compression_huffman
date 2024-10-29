
---

# Huffman Encoding Compressor

This project repository contains two applications built in Java using Huffman encoding for compression:
1. **Image Compressor**: Compresses images using Huffman encoding.
2. **Text File Compressor**: Compresses text files using Huffman encoding.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
  - [Image Compressor](#image-compressor)
  - [Text File Compressor](#text-file-compressor)
- [Images and Examples](#images-and-examples)
- [Technical Details](#technical-details)

---

## Project Overview

Huffman encoding is a lossless data compression algorithm commonly used for reducing the size of data files. These two Java programs utilize Huffman encoding to:
- Compress and decompress images
- Compress and decompress text files

Each application encodes data, saving it in a compressed format, and can decode the compressed data back to its original format.

---

## Features

- **Lossless Compression**: Both compressors ensure data is not lost during compression and decompression.
- **Binary Encoding**: Uses binary encoding to minimize the space required for data storage.
- **Customizable Paths**: Allows users to specify input and output file paths.

---

## Setup and Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo-url.git
   cd your-repo-url
   ```

2. **Compile the Java Files**:
   ```bash
   javac HuffmanImageCompressor.java
   javac HuffmanTextCompressor.java
   ```

3. **Dependencies**: This project requires only Java 8+ (no external dependencies).

---

## Usage

### Image Compressor

1. **Compress an Image**:
   ```bash
   java HuffmanImageCompressor compress <input_image_path> <compressed_output_path>
   ```

   Example:
   ```bash
   java HuffmanImageCompressor compress sample.jpg compressed_image.huff
   ```

2. **Decompress an Image**:
   ```bash
   java HuffmanImageCompressor decompress <compressed_file_path> <decompressed_image_path>
   ```

   Example:
   ```bash
   java HuffmanImageCompressor decompress compressed_image.huff decompressed_image.jpg
   ```

### Text File Compressor

1. **Compress a Text File**:
   ```bash
   java HuffmanTextCompressor compress <input_text_file> <compressed_output_file>
   ```

   Example:
   ```bash
   java HuffmanTextCompressor compress input.txt compressed.huff
   ```

2. **Decompress a Text File**:
   ```bash
   java HuffmanTextCompressor decompress <compressed_file_path> <output_text_file>
   ```

   Example:
   ```bash
   java HuffmanTextCompressor decompress compressed.huff output.txt
   ```

---

## Images

Here’s an example of the image compression and decompression process:

### Original Image (Input)
<img src="Huffman Img Compression/animal_img.png" alt="Input Image" width="300">

### Compressed Image File

`compressed_image.huff` (binary file, not viewable as an image)

### Decompressed Image (Output)
<img src="Huffman Img Compression/out2_image.png" alt="Output Image" width="300">

The output image should resemble the input image closely, as Huffman encoding is a lossless compression method.

### Image Compressor
- **Input**: Original image file (e.g., `sample.jpg`)
- **Compressed Output**: Binary file `compressed_image.huff`
- **Decompressed Output**: Reconstructed image file (e.g., `decompressed_image.jpg`)

### Text File Compressor
- **Input**: Original text file (e.g., `input.txt`)
- **Compressed Output**: Binary file `compressed.huff`
- **Decompressed Output**: Reconstructed text file (e.g., `output.txt`)


---

## Technical Details

### Image Compressor
- **Huffman Tree Construction**: The compressor calculates pixel frequency and builds a Huffman tree to assign binary codes to each unique pixel value.
- **Binary Encoding**: Encodes the pixel data into binary format and saves it in a compressed file.

### Text File Compressor
- **Character Frequency Analysis**: Reads the file and counts the frequency of each character to build a Huffman tree.
- **Binary Compression**: Encodes text data, saving it in a compressed binary format that’s stored in the output file.

---

## Error Handling

- **File Not Found**: If input files are missing, the program outputs a descriptive error.
- **Unsupported Format**: Currently, the image compressor only supports raw image formats. (JPEGs and PNGs may not yield high compression results.)

---

Feel free to reach out if you encounter any issues or need help expanding the project!