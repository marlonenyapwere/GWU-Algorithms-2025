package edu.gwu.algorithms;

import edu.gwu.algorithms.algorithms.ConvexHullStableBenchmark;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ConvexHullStableBenchmark.
 *
 * These tests verify correctness of:
 *  - Random point generation
 *  - Convex Hull construction (basic geometric correctness)
 *  - Cross product calculations
 *  - End-to-end benchmark execution (no exceptions)
 *
 * Author: @Marlone Nyapwere
 * Date: October 2025
 */
public class ConvexHullStableBenchmarkTest {

    /** Test random point generation produces correct dimensions and range. */
    @Test
    void testGenerateRandomPoints() {
        Random rand = new Random(42);
        int[][] points = ConvexHullStableBenchmark.generateRandomPoints(100, rand);

        assertEquals(100, points.length, "Should generate 100 points");
        for (int[] p : points) {
            assertEquals(2, p.length, "Each point must have 2 coordinates");
            assertTrue(p[0] >= -1_000_000 && p[0] <= 1_000_000, "X out of range");
            assertTrue(p[1] >= -1_000_000 && p[1] <= 1_000_000, "Y out of range");
        }
    }

    /** Test convex hull for a simple square (expected: 4 points). */
    @Test
    void testConvexHullSquare() {
        int[][] square = {
                {0, 0}, {0, 1}, {1, 0}, {1, 1}
        };
        List<int[]> hull = ConvexHullStableBenchmark.convexHull(square);

        // The hull of a unit square should contain 4 distinct corners
        assertEquals(4, hull.size(), "Convex hull of square must have 4 vertices");

        // Ensure all corners are included
        Set<String> points = new HashSet<>();
        for (int[] p : hull) points.add(p[0] + "," + p[1]);
        assertTrue(points.contains("0,0"));
        assertTrue(points.contains("0,1"));
        assertTrue(points.contains("1,0"));
        assertTrue(points.contains("1,1"));
    }

    /** Test convex hull for collinear points (expected: 2 endpoints). */
    @Test
    void testConvexHullCollinearPoints() {
        int[][] collinear = {
                {0, 0}, {1, 1}, {2, 2}, {3, 3}, {4, 4}
        };
        List<int[]> hull = ConvexHullStableBenchmark.convexHull(collinear);

        // Only endpoints remain in convex hull
        assertEquals(2, hull.size(), "Convex hull of collinear points should have 2 points");
        assertTrue(hull.stream().anyMatch(p -> p[0] == 0 && p[1] == 0));
        assertTrue(hull.stream().anyMatch(p -> p[0] == 4 && p[1] == 4));
    }

    /** Test cross product direction correctness. */
    @Test
    void testCrossProduct() throws Exception {
        int[] a = {0, 0};
        int[] b = {1, 0};
        int[] c = {1, 1};
        var crossMethod = ConvexHullStableBenchmark.class
                .getDeclaredMethod("cross", int[].class, int[].class, int[].class);
        crossMethod.setAccessible(true);

        long result = (long) crossMethod.invoke(null, a, b, c);
        assertTrue(result > 0, "Cross product should be positive for counterclockwise turn");
    }

    /** Smoke test to ensure the benchmark main method runs without throwing exceptions. */
    @Test
    void testBenchmarkExecution() {
        assertDoesNotThrow(() -> ConvexHullStableBenchmark.main(new String[]{}),
                "Benchmark should run without exceptions");
    }

    /** Test theoretical value formula for correctness (n log2 n). */
    @Test
    void testTheoreticalComputation() {
        int n = 1024;
        double theoretical = n * (Math.log(n) / Math.log(2));
        assertEquals(1024 * 10, theoretical, 1e-5, "For n=1024, log₂ n = 10");
    }
}

