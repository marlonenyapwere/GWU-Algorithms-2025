package edu.gwu.algorithms.algorithms;

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
    public static void main(String[] args) {

    }
}
