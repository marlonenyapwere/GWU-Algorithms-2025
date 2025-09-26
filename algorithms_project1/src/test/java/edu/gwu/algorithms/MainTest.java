package edu.gwu.algorithms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Random fixedRandom;

    @BeforeEach
    void setUp() {
        // Fixed seed for deterministic output in tests
        fixedRandom = new Random(42);
    }

    // Tests for generateRandomArray()
    @Test
    void testGenerateRandomArraySizeZero() {
        int[] arr = Main.generateRandomArray(0, fixedRandom);
        assertEquals(0, arr.length, "Array size should be zero");
    }

    @Test
    void testGenerateRandomArraySizeOne() {
        int[] arr = Main.generateRandomArray(1, fixedRandom);
        assertEquals(1, arr.length, "Array size should be one");
        assertTrue(arr[0] >= 0 && arr[0] < 100, "Element should be in range [0,99]");
    }

    @Test
    void testGenerateRandomArrayValuesInRange() {
        int[] arr = Main.generateRandomArray(100, fixedRandom);
        for (int val : arr) {
            assertTrue(val >= 0 && val < 100, "Value should be in range [0,99]");
        }
    }


    // Tests for performExperiment()
    @Test
    void testPerformExperimentWithZeroSize() {
        int[] a = new int[0];
        int[] b = new int[0];
        long result = Main.performExperiment(a, b, 0);
        assertEquals(0, result, "Experiment with n=0 should return 0");
    }

    @Test
    void testPerformExperimentDoesNotThrowForLargeN() {
        int n = 10000;
        int[] a = Main.generateRandomArray(n, fixedRandom);
        int[] b = Main.generateRandomArray(n, fixedRandom);
        assertDoesNotThrow(() -> Main.performExperiment(a, b, n),
                "Should not throw exception for large n");
    }


    // Tests for printResultsTable()
    @Test
    void testPrintResultsTableOutput() {
        int[] sizes = {10, 100};
        long[] experimental = {123L, 456L};
        double[] theoretical = {1.1, 2.2};
        double scalingConstant = 50.0;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Main.printResultsTable(sizes, experimental, theoretical, scalingConstant);

        String output = outContent.toString();

        // Restore System.out
        System.setOut(System.out);

        assertTrue(output.contains("n"), "Header should contain column 'n'");
        assertTrue(output.contains("Experimental"), "Header should contain 'Experimental'");
        assertTrue(output.contains("10"), "Output should contain size 10");
        assertTrue(output.contains("100"), "Output should contain size 100");
    }
}


