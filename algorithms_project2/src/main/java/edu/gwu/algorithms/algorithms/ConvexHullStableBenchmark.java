package edu.gwu.algorithms.algorithms;


import java.util.*;

/**
 * ConvexHullStableBenchmark.java
 *
 * This program benchmarks the Convex Hull algorithm (Monotone Chain – O(n log n))
 * with high statistical stability. For each input size, the algorithm runs
 * multiple times (default 1000) and the *average runtime* is recorded.
 *
 * The average experimental times are compared to the theoretical model n log₂ n,
 * scaled by a constant C computed from large n values.
 *
 * Output Table Columns:
 * - Input size (n)
 * - Experimental average time (ns)
 * - Theoretical (n log₂ n)
 * - Scaling Constant (C)
 * - Adjusted Theoretical = C × (n log₂ n)
 *
 * Author: @Marlone Nyapwere
 * Date: October 2025
 * Course: Design and Analysis of Algorithms – Project 2
 */
public class ConvexHullStableBenchmark {

    // Number of runs per input size
    private static final int TRIALS = 1000;

    public static void main(String[] args) {
        int[] testSizes = {100, 500, 1000, 5000, 10000, 20000, 50000};
        long[] experimentalAverages = new long[testSizes.length];
        double[] theoreticalValues = new double[testSizes.length];

        Random random = new Random();

        // Warm-up runs (JIT optimization)
        for (int i = 0; i < 5; i++) {
            int[][] warm = generateRandomPoints(1000, random);
            convexHull(warm);
        }

        // Main experiment
        for (int i = 0; i < testSizes.length; i++) {
            int n = testSizes[i];
            long totalTime = 0;

            for (int t = 0; t < TRIALS; t++) {
                int[][] points = generateRandomPoints(n, random);

                long start = System.nanoTime();
                List<int[]> hull = convexHull(points);
                long end = System.nanoTime();

                totalTime += (end - start);
            }

            long avgTime = totalTime / TRIALS;
            experimentalAverages[i] = avgTime;
            theoreticalValues[i] = n * (Math.log(n) / Math.log(2)); // n log₂ n
        }

        // Compute stable scaling constant using only large n (≥ 5000)
        double scalingConstant = 0;
        int count = 0;
        for (int i = 0; i < testSizes.length; i++) {
            if (testSizes[i] >= 5000) {
                scalingConstant += (double) experimentalAverages[i] / theoreticalValues[i];
                count++;
            }
        }
        scalingConstant /= count;

        // Print formatted results table
        printResultsTable(testSizes, experimentalAverages, theoreticalValues, scalingConstant);
    }

    /** Generates random 2D integer points within [-10^6, 10^6]. */
    static int[][] generateRandomPoints(int n, Random random) {
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = random.nextInt(2_000_001) - 1_000_000;
            points[i][1] = random.nextInt(2_000_001) - 1_000_000;
        }
        return points;
    }

    /** Convex Hull via Monotone Chain algorithm – O(n log n). */
    static List<int[]> convexHull(int[][] pts) {
        if (pts.length < 3) return Arrays.asList(pts);

        Arrays.sort(pts, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        List<int[]> lower = new ArrayList<>();
        List<int[]> upper = new ArrayList<>();

        for (int[] p : pts) {
            while (lower.size() >= 2 && cross(lower.get(lower.size()-2), lower.get(lower.size()-1), p) <= 0)
                lower.remove(lower.size()-1);
            lower.add(p);
        }

        for (int i = pts.length - 1; i >= 0; i--) {
            int[] p = pts[i];
            while (upper.size() >= 2 && cross(upper.get(upper.size()-2), upper.get(upper.size()-1), p) <= 0)
                upper.remove(upper.size()-1);
            upper.add(p);
        }

        // Merge lower and upper hulls
        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);
        lower.addAll(upper);
        return lower;
    }

    /** Cross product (b - a) × (c - a). */
    private static long cross(int[] a, int[] b, int[] c) {
        return (long)(b[0] - a[0]) * (c[1] - a[1])
                - (long)(b[1] - a[1]) * (c[0] - a[0]);
    }

    /** Prints the final formatted results table. */
    static void printResultsTable(int[] sizes, long[] experimental, double[] theoretical, double scalingConstant) {
        System.out.printf("%-12s %-20s %-25s %-20s %-25s%n",
                "n", "Experimental Avg (ns)", "Theoretical (n log₂ n)", "Scaling Constant", "Adjusted Theoretical");

        for (int i = 0; i < sizes.length; i++) {
            long n = sizes[i];
            long expTime = experimental[i];
            double theo = theoretical[i];
            double adjusted = scalingConstant * theo;

            System.out.printf("%-12d %-20d %-25.1f %-20.2f %-25.0f%n",
                    n, expTime, theo, scalingConstant, adjusted);
        }
    }
}
