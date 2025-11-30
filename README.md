# 🧬 Genetic Algorithm TSP Solver (Java)

🎯 Project Overview
This project is a robust implementation of a Genetic Algorithm (GA) in Java designed to find near-optimal solutions for the classic Traveling Salesperson Problem (TSP). It demonstrates core competency in combinatorial optimization, algorithm design, and performance tuning.

🛠️ Key Technical Features
Combinatorial Optimization: Solves the non-deterministic polynomial-time (NP-hard) TSP by evolving populations of potential routes to minimize total distance.

Permutation Encoding: Utilizes an Order Representation to ensure all generated routes (chromosomes) represent valid tours, where each city is visited exactly once.

Advanced GA Operators: Implements specialized selection, crossover, and mutation methods tailored for permutation-based problems.

Data Parsing: Includes a dedicated parser (readCoordinates) to handle standard TSPLIB file formats (e.g., att48.txt).

Performance Metrics: Tracks and reports key statistics (Best, Worst, and Average Fitness) across generations to monitor convergence.

⚙️ Genetic Algorithm Components
The optimization relies on a set of custom-engineered genetic operators:

1. Fitness Function (Evaluation)
Function: distancecalc

Metric: Total distance of the tour.

Calculation: Uses Euclidean Distance between sequential city coordinates (minimization problem).

2. Selection Strategy
To maintain balance between exploitation and exploration, two selection methods were implemented:

Rank Selection (parent2): Selects parents based on their rank (position in the sorted list of fitness values), reducing the bias towards extremely fit individuals.

Roulette Wheel Selection (parent1): Selects parents based on their inverse fitness proportion (since lower distance is better), which introduces a degree of randomness.

3. Crossover Strategy
Function: child1 (Custom Cyclical Strategy)

Purpose: Combines genetic material from two parents while preserving the unique set of nodes (cities) in the resulting child, which is critical for permutation encoding.

4. Mutation Operators
Two custom mutation functions are used to introduce diversity and help the algorithm escape local optima:

Insert Mutation (mutation1): Selects a gene and inserts it at a random position within a defined sub-segment.

Random Slide Mutation (mutation2): Selects two positions and slides/swaps elements within the segment defined by these points.
