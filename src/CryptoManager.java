/**
 * Class description: Encrypts and decrypts a phrase using three classical ciphers:
 * 		Vigenere, Playfair (8x8 matrix) and Caesar. Every character must be in the
 * 		64-character range from ' ' (ASCII 32) to '_' (ASCII 95).
 *
 * Pledge: I pledge that I have completed the programming assignment
 * 		independently. I have not copied the code from a student or any
 * 		source. I have not given my code to any student.
 * 		Print your Name here: Eugene Loh
 * Course: CMSC203
 * Due Date: 10/25/2026
 * Platform/Compiler: Eclipse
 */

import java.util.ArrayList;

/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";
    private static final int MATRIX_SIZE = 8; // Playfair matrix is 8 x 8
    private static final char PAD_CHAR = 'X'; // Pads an odd-length Playfair message
    private static final String OUT_OF_BOUNDS = "The selected string is not in bounds, Try again.";

    /**
     * Determines if every character of a string is within the bounds of LOWER_RANGE and UPPER_RANGE.
     * 
     * @param plainText the string to be checked
     * @return true if all characters are in bounds, false otherwise
     */
    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText) || !isValidKeyword(key)) {
            return OUT_OF_BOUNDS;
        }

        String encrypted = "";
        for (int i = 0; i < plainText.length(); i++) {
            int shift = ALPHABET64.indexOf(key.charAt(i % key.length())); // % repeats the keyword
            encrypted += shiftChar(plainText.charAt(i), shift);
        }
        return encrypted;
    }

    // Vigenere Decryption
    /**
     * Decrypts a string encrypted with the Vigenere Cipher. This is the inverse of vigenereEncryption.
     * 
     * @param encryptedText the encrypted string to be decrypted
     * @param key           the keyword used to encrypt the original text
     * @return the original plain text, or the out of bounds message
     */
    public static String vigenereDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText) || !isValidKeyword(key)) {
            return OUT_OF_BOUNDS;
        }

        String decrypted = "";
        for (int i = 0; i < encryptedText.length(); i++) {
            int shift = ALPHABET64.indexOf(key.charAt(i % key.length()));
            decrypted += shiftChar(encryptedText.charAt(i), -shift); // Shift backwards to decrypt
        }
        return decrypted;
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
            return OUT_OF_BOUNDS;
        }
        return playfairProcess(plainText, key, 1); // 1 = move right / down
    }

    // Vigenere Decryption
    /**
     * Decrypts a string encrypted with the Playfair Cipher. This is the inverse of playfairEncryption, and it
     * removes the 'X' that was added as padding.
     * 
     * @param encryptedText the encrypted string to be decrypted
     * @param key           the keyword used to build the matrix
     * @return the original plain text, or the out of bounds message
     */
    public static String playfairDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
            return OUT_OF_BOUNDS;
        }

        String decrypted = playfairProcess(encryptedText, key, -1); // -1 = move left / up

        // Remove the padding 'X' that encryption added to an odd-length message
        if (decrypted.length() > 0 && decrypted.charAt(decrypted.length() - 1) == PAD_CHAR) {
            decrypted = decrypted.substring(0, decrypted.length() - 1);
        }
        return decrypted;
    }

    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key) {
        if (!isStringInBounds(plainText)) {
            return OUT_OF_BOUNDS;
        }

        String encrypted = "";
        for (int i = 0; i < plainText.length(); i++) {
            encrypted += shiftChar(plainText.charAt(i), key);
        }
        return encrypted;
    }

    // Caesar Decryption
    /**
     * Decrypts a string encrypted with the Caesar Cipher. This is the inverse of caesarEncryption.
     * 
     * @param encryptedText the encrypted string to be decrypted
     * @param key           the number of positions used to encrypt the original text
     * @return the original plain text, or the out of bounds message
     */
    public static String caesarDecryption(String encryptedText, int key) {
        if (!isStringInBounds(encryptedText)) {
            return OUT_OF_BOUNDS;
        }

        String decrypted = "";
        for (int i = 0; i < encryptedText.length(); i++) {
            decrypted += shiftChar(encryptedText.charAt(i), -key);
        }
        return decrypted;
    }

    // Additional methods

    /**
     * Shifts one character inside the ' ' to '_' range, wrapping around at either end.
     */
    private static char shiftChar(char ch, int shift) {
        int position = ch - LOWER_RANGE; // 0 to 63
        int newPosition = ((position + shift) % RANGE + RANGE) % RANGE; // + RANGE so negative shifts still work
        return (char) (LOWER_RANGE + newPosition);
    }

    /**
     * Checks that a Vigenere keyword is not empty and is in bounds.
     */
    private static boolean isValidKeyword(String key) {
        return key.length() > 0 && isStringInBounds(key);
    }

    /**
     * Builds the 8x8 Playfair matrix: the keyword letters first (each only once), then the rest of ALPHABET64.
     */
    private static char[][] buildMatrix(String key) {
        ArrayList<Character> used = new ArrayList<Character>();
        String source = key + ALPHABET64;

        for (int i = 0; i < source.length(); i++) {
            char ch = source.charAt(i);
            if (!used.contains(ch)) { // Skip characters already inserted
                used.add(ch);
            }
        }

        char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < used.size(); i++) {
            matrix[i / MATRIX_SIZE][i % MATRIX_SIZE] = used.get(i); // Fill left to right, top to bottom
        }
        return matrix;
    }

    /**
     * Finds the row and column of a character in the matrix.
     */
    private static int[] findPosition(char[][] matrix, char ch) {
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                if (matrix[row][col] == ch) {
                    return new int[] { row, col };
                }
            }
        }
        return new int[] { -1, -1 }; // Not reachable for in-bounds characters
    }

    /**
     * Encrypts (direction 1) or decrypts (direction -1) the text one pair at a time.
     */
    private static String playfairProcess(String text, String key, int direction) {
        char[][] matrix = buildMatrix(key);

        if (text.length() % 2 != 0) {
            text += PAD_CHAR; // Odd length, so pad the last pair
        }

        String result = "";
        for (int i = 0; i < text.length(); i += 2) {
            int[] first = findPosition(matrix, text.charAt(i));
            int[] second = findPosition(matrix, text.charAt(i + 1));
            int r1 = first[0];
            int c1 = first[1];
            int r2 = second[0];
            int c2 = second[1];

            if (r1 == r2) {
                // Same row: move right (encrypt) or left (decrypt)
                result += matrix[r1][wrap(c1 + direction)];
                result += matrix[r2][wrap(c2 + direction)];
            } else if (c1 == c2) {
                // Same column: move down (encrypt) or up (decrypt)
                result += matrix[wrap(r1 + direction)][c1];
                result += matrix[wrap(r2 + direction)][c2];
            } else {
                // Rectangle: keep own row, take the other letter's column
                result += matrix[r1][c2];
                result += matrix[r2][c1];
            }
        }
        return result;
    }

    /**
     * Wraps a row or column index around the 8x8 matrix.
     */
    private static int wrap(int index) {
        return (index + MATRIX_SIZE) % MATRIX_SIZE;
    }
}