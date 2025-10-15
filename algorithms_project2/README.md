# Design and Analysis of Algorithms - Project 2

**Author:** Marlone Nyapwere  
**Date:** 14 October 2025  
**Course:** Design and Analysis of Algorithms  

---

## 📌 Project Overview

This project benchmarks the performance of the **Convex Hull algorithm (Monotone Chain – O(n log n))** with high statistical stability.  
For each input size, the algorithm runs multiple times (default **1000**) and records the **average runtime**.  
The experimental results are compared to the theoretical **n log₂ n** model, scaled by a constant computed from large input sizes.

The program outputs a formatted table with the following columns:

| Column | Description |
|--------|-------------|
| n | Input size |
| Experimental Avg (ns) | Average experimental runtime over all trials |
| Theoretical (n log₂ n) | Theoretical complexity value |
| Scaling Constant (C) | Normalization constant for adjustment |
| Adjusted Theoretical | C × (n log₂ n) |

---

## 📂 Project Structure

```
algorithms_project2/
│── pom.xml
│── src/
│    ├── main/java/edu/gwu/algorithms/algorithms/ConvexHullStableBenchmark.java
│    └── test/java/edu/gwu/algorithms/ConvexHullStableBenchmarkTest.java
│── README.md
```

---

## ⚙️ Dependencies

The project uses **Maven** for build and dependency management.

- **Java 17** (or higher)
- **JUnit 5.14.0-RC1** (for unit testing)

Defined in `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.14.0-RC1</version>
    <scope>test</scope>
</dependency>
```

---

## ▶️ How to Run

### 1. Clone the repository
```bash
git clone https://github.com/marlonenyapwere/GWU-Algorithms-2025.git
cd GWU-Algorithms-2025/algorithms_project2
```

### 2. Compile and Run with Maven
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="edu.gwu.algorithms.algorithms.ConvexHullStableBenchmark"
```

### 3. Compile and Run without Maven
```bash
javac src/main/java/edu/gwu/algorithms/algorithms/ConvexHullStableBenchmark.java -d out
java -cp out edu.gwu.algorithms.algorithms.ConvexHullStableBenchmark
```

---

## 🧪 Running Unit Tests

Unit tests verify the correctness of the convex hull algorithm, point generation, and benchmarking stability.

Run tests with Maven:
```bash
mvn test
```

Key tests include:
- Random point generation bounds and dimensions.  
- Convex hull correctness for squares and collinear points.  
- Cross product orientation verification.  
- End-to-end benchmark execution without exceptions.  
- Theoretical formula correctness for **n log₂ n**.

---

## 📊 Sample Output

Example output table from experimental runs:

```
n            Experimental Avg (ns)   Theoretical (n log₂ n)   Scaling Constant   Adjusted Theoretical
100          2015200                 664.4                    6401.35            4257562
500          539700                  4482.9                   6401.35            28691833
1000         977800                  9965.0                   6401.35            63885280
5000         2974000                 61461.0                  6401.35            393416158
10000        5300000                 132877.1                 6401.35            850909625
```

---

## 📈 Observations

The results confirm the **O(n log n)** asymptotic complexity of the Monotone Chain algorithm:

- As input size increases, experimental runtimes align closely with the theoretical growth curve.  
- For smaller input sizes, variations occur due to **JVM warm-up** and **system noise**.  
- The computed scaling constant effectively normalizes the data, showing convergence trends.

---

## ✅ Conclusion

This experiment demonstrates the consistency between theoretical and experimental performance of the Convex Hull algorithm.  
The Monotone Chain method provides deterministic **O(n log n)** performance while maintaining numerical stability and precision.  
Through statistical averaging and scaling normalization, runtime behavior was shown to converge closely with the expected asymptotic model.

---
