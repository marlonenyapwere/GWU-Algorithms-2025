package edu.gwu.algorithms.algorithms;

import java.util.Random;

/**
 * Main.java
 *
 * This program benchmarks the performance of a custom nested loop algorithm
 * using arrays of increasing size. It compares the experimental runtime
 * against a theoretical model based on the square of the logarithm (base 2) of n.
 *
 * The results are printed in a formatted table showing:
 * - Input size (n)
 * - Experimental time in nanoseconds
 * - Theoretical time ((log₂ n)²)
 * - Scaling constant
 * - Adjusted theoretical time
 *
 * The experiment involves multiplying elements from two randomly generated arrays
 * using nested loops with logarithmic growth patterns.
 *
 * Author: @Marlone Nyapwere
 * Created: September 26, 2025
 * Name: Design and Analysis of Algorithms - Project 1
 *
 * Usage:
 * Compile: javac Main.java
 * Run: java Main
 */
public class Main {

    public static void main(String[] args) {
        int[] testSizes = {10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

        long[] experimentalTimes = new long[testSizes.length];
        double[] theoreticalTimes = new double[testSizes.length];

        Random random = new Random();

        // Run experiment
        for (int i = 0; i < testSizes.length; i++) {
            int n = testSizes[i];
            int[] arrayA = generateRandomArray(n, random);
            int[] arrayB = generateRandomArray(n, random);

            long startTime = System.nanoTime();
            long resultSum = performExperiment(arrayA, arrayB, n);
            long endTime = System.nanoTime();

            long elapsedTime = endTime - startTime;
            experimentalTimes[i] = elapsedTime;
            theoreticalTimes[i] = Math.pow(Math.log(n) / Math.log(2), 2); // (log₂ n)²
        }

        // Compute scaling constant using only "large n" values (≥ 100000)
        double scalingConstant = 0;
        int count = 0;
        for (int i = 0; i < testSizes.length; i++) {
            if (testSizes[i] >= 100000) {  // ignore small n
                scalingConstant += experimentalTimes[i] / theoreticalTimes[i];
                count++;
            }
        }
        scalingConstant /= count; // average

        // Print results
        printResultsTable(testSizes, experimentalTimes, theoreticalTimes, scalingConstant);
    }

    /**
     * Generates an array of random integers between 0 and (RANDOM_BOUND - 1).
     *
     * @param size The desired length of the array.
     * @param random The Random object to use for generation.
     * @return An array of random integers.
     */
    static int[] generateRandomArray(int size, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }

    /**
     * Performs the experiment by computing a sum based on nested loops with logarithmic growth.
     * The complexity is dictated by the factors INNER_LOOP_FACTOR (sqrt(2)) and OUTER_LOOP_FACTOR (sqrt(3)).
     *
     * @param a The first input array.
     * @param b The second input array.
     * @param n The logical size of the input arrays (also array.length).
     * @return The accumulated sum of products from the experiment.
     */
    static long performExperiment(int[] a, int[] b, int n) {
        long sum = 0;
        int j = 5;
        while (j < n / 2) {
            int k = 5;
            while (k < n) {
                sum += (long) a[j] * b[k];
                k = (int) (k * Math.sqrt(2));
            }
            j = (int) (j * Math.sqrt(3));
        }
        return sum;
    }


    /**
     * Prints a formatted table of experimental and theoretical results.
     *
     * @param sizes The array of input sizes (n).
     * @param experimental The array of experimental runtimes in nanoseconds.
     * @param theoretical The array of raw theoretical values ((log₂ n)²).
     * @param scalingConstant The scaling factor determined by the first data point.
     */
    static void printResultsTable(int[] sizes, long[] experimental, double[] theoretical, double scalingConstant) {
        System.out.printf("%-12s %-18s %-25s %-20s %-25s%n",
                "n", "Experimental (ns)", "Theoretical ((log₂ n)²)", "Scaling Constant", "Adjusted Theoretical");

        for (int i = 0; i < sizes.length; i++) {
            long n = sizes[i];
            long expTime = experimental[i];
            double theoTime = theoretical[i];
            double adjustedTime = scalingConstant * theoTime;

            System.out.printf("%-12d %-18d %-25.1f %-20.2f %-25.0f%n",
                    n, expTime, theoTime, scalingConstant, adjustedTime);
        }
    }
}
