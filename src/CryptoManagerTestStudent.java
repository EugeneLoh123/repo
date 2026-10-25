/**
 * Class description: JUnit tests for every public method of CryptoManager
 * 		(bounds check, Vigenere, Playfair and Caesar ciphers).
 *
 * Pledge: I pledge that I have completed the programming assignment
 * 		independently. I have not copied the code from a student or any
 * 		source. I have not given my code to any student.
 * 		Print your Name here: Eugene Loh
 * Course: CMSC203
 * Due Date: 10/25/2026
 * Platform/Compiler: Eclipse
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CryptoManagerTestStudent {

    // Attributes
    private static final String OUT_OF_BOUNDS = "The selected string is not in bounds, Try again.";

    /**
     * Nothing to set up because CryptoManager only has static methods.
     */
    @BeforeEach
    public void setUp() throws Exception {
    }

    /**
     * Nothing to clean up after each test.
     */
    @AfterEach
    public void tearDown() throws Exception {
    }

    // isStringInBounds tests

    /**
     * Tests strings that are in bounds and strings with characters below ' ' or above '_'.
     */
    @Test
    public void testIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("ROCKVILLE CAMPUS 2026"));
        assertTrue(CryptoManager.isStringInBounds("[CMSC_203]!?"));
        assertTrue(CryptoManager.isStringInBounds("")); // Empty string has no bad characters
        assertFalse(CryptoManager.isStringInBounds("Rockville")); // Lowercase letters
        assertFalse(CryptoManager.isStringInBounds("PRICE {10}")); // '{' is above '_'
        assertFalse(CryptoManager.isStringInBounds("TAB\tHERE")); // Tab is below ' '
    }

    // Vigenere tests

    /**
     * Tests Vigenere encryption with exact expected values, key repeating, wrapping and out of bounds input.
     */
    @Test
    public void testVigenereEncryption() {
        assertEquals("P", CryptoManager.vigenereEncryption("M", "D")); // Example from the assignment clarification
        assertEquals("QJ[[B", CryptoManager.vigenereEncryption("PIZZA", "B")); // Key "B" shifts by 1, Z wraps to [
        assertEquals("BCBC", CryptoManager.vigenereEncryption("AAAA", "BC")); // Key repeats: shifts 1, 2, 1, 2
        assertEquals(" ", CryptoManager.vigenereEncryption("_", "B")); // Wraps from '_' back to ' '
        assertEquals(OUT_OF_BOUNDS, CryptoManager.vigenereEncryption("pizza", "FROST"));
        assertEquals(OUT_OF_BOUNDS, CryptoManager.vigenereEncryption("PIZZA", "frost"));
    }

    /**
     * Tests Vigenere decryption with exact expected values, a full round trip and out of bounds input.
     */
    @Test
    public void testVigenereDecryption() {
        assertEquals("M", CryptoManager.vigenereDecryption("P", "D"));
        assertEquals("PIZZA", CryptoManager.vigenereDecryption("QJ[[B", "B"));
        assertEquals("_", CryptoManager.vigenereDecryption(" ", "B"));

        String text = "MEET AT GATE 7 @ NOON!";
        String key = "LIBRARY";
        String encrypted = CryptoManager.vigenereEncryption(text, key);
        String decrypted = CryptoManager.vigenereDecryption(encrypted, key);
        assertEquals(text, decrypted);
        assertEquals(OUT_OF_BOUNDS, CryptoManager.vigenereDecryption("abc", "FROST"));
    }

    // Playfair tests

    /**
     * Tests Playfair encryption with the clarification example, padding and out of bounds input.
     */
    @Test
    public void testPlayfairEncryption() {
        assertEquals("ORRGMNK3", CryptoManager.playfairEncryption("MEETYOU", "MONTGOMERY")); // Clarification example
        assertEquals(10, CryptoManager.playfairEncryption("TAKOMA PK", "SPRING").length()); // Odd length gets padded
        assertEquals(OUT_OF_BOUNDS, CryptoManager.playfairEncryption("hi there", "FROST"));
        assertEquals(OUT_OF_BOUNDS, CryptoManager.playfairEncryption("HI", "k{y"));
    }

    /**
     * Tests Playfair decryption with the clarification example, round trips and out of bounds input.
     */
    @Test
    public void testPlayfairDecryption() {
        assertEquals("MEETYOU", CryptoManager.playfairDecryption("ORRGMNK3", "MONTGOMERY"));

        String text = "GERMANTOWN 2026";
        String key = "DRAGON";
        String encrypted = CryptoManager.playfairEncryption(text, key);
        String decrypted = CryptoManager.playfairDecryption(encrypted, key);
        assertEquals(text, decrypted);

        String symbols = "#1 (TOP) = 100%";
        assertEquals(symbols,
            CryptoManager.playfairDecryption(
                CryptoManager.playfairEncryption(symbols, "QUIZ"), "QUIZ"));
        assertEquals(OUT_OF_BOUNDS, CryptoManager.playfairDecryption("ab", "FROST"));
    }

    // Caesar tests

    /**
     * Tests Caesar encryption with exact expected values, wrapping, a full loop and out of bounds input.
     */
    @Test
    public void testCaesarEncryption() {
        assertEquals("E", CryptoManager.caesarEncryption("A", 4));
        assertEquals("ZLQWHU", CryptoManager.caesarEncryption("WINTER", 3));
        assertEquals("!", CryptoManager.caesarEncryption("^", 3)); // Wraps past '_'
        assertEquals("ZEBRA", CryptoManager.caesarEncryption("ZEBRA", 64)); // Shift of 64 is a full loop
        assertEquals(OUT_OF_BOUNDS, CryptoManager.caesarEncryption("zebra", 5));
    }

    /**
     * Tests Caesar decryption with exact expected values, a large-shift round trip and out of bounds input.
     */
    @Test
    public void testCaesarDecryption() {
        assertEquals("A", CryptoManager.caesarDecryption("E", 4));
        assertEquals("WINTER", CryptoManager.caesarDecryption("ZLQWHU", 3));
        assertEquals("^", CryptoManager.caesarDecryption("!", 3));

        String text = "SILVER SPRING, MD 20910";
        int key = 125;
        String encrypted = CryptoManager.caesarEncryption(text, key);
        String decrypted = CryptoManager.caesarDecryption(encrypted, key);
        assertEquals(text, decrypted);
        assertEquals(OUT_OF_BOUNDS, CryptoManager.caesarDecryption("zlqwhu", 3));
    }
}