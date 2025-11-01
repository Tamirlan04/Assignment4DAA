```markdown
#  Assignment 4 — Graph Analysis and Scheduling  
**Course:** Design and Analysis of Algorithms (DAA)  
**Institution:** Astana IT University  
**Authors:** Kyzylov Tamirlan  
**Language:** Java 17 (Maven project)  

---

##  Overview
This project implements advanced graph algorithms for analyzing task dependencies and optimizing execution schedules.  
The implementation follows the official requirements of *Assignment 4* and includes:

- **Strongly Connected Components (SCC)** — Tarjan’s DFS algorithm  
- **Condensation Graph (DAG)** construction  
- **Topological Sort** (Kahn’s Algorithm)  
- **Shortest and Longest Paths in DAG** — critical path analysis  
- **Metrics Module** for step counting and timing  
- **Dataset Generator** producing 9 graph inputs for experiments  

---

 Project Structure
```

assignment4/
├─ data/                     # Input datasets (tasks_1.json … tasks_9.json)
├─ src/
│   ├─ graph/
│   │   ├─ scc/              # Tarjan SCC algorithm
│   │   ├─ topo/             # Topological Sort and Condensation Graph
│   │   ├─ dagsp/            # DAG Shortest/Longest Path (DAGPaths.java)
│   │   └─ utils/            # Dataset Generator
│   ├─ metrics/              # Metrics and Instrumentation
│   └─ main/                 # Entry Point (Main.java)
├─ pom.xml                   # Maven configuration (Gson dependency)
├─ .gitignore
└─ README.md

````

---

##  Algorithm Modules

###  SCC Finder (Tarjan DFS)
- Discovers all strongly connected components.  
- Returns component groups and mapping from node → component ID.  

###  Condensation Graph + Topological Sort
- Builds a DAG from SCCs.  
- Sorts components using Kahn’s algorithm (topological order).  

### DAG Shortest & Longest Path (DAGPaths)
- Computes shortest distances for weighted DAGs.  
- Computes critical (longest) paths for project scheduling.  
- Uses topological ordering for linear-time processing.  

###  Metrics Instrumentation
- `Metrics.java` — interface for operations and timing.  
- `SimpleMetrics.java` — counts DFS calls, relaxations, push/pop operations, execution time.  

###  Dataset Generation
- `DatasetGenerator.java` creates 9 JSON files of various sizes:  
  - 3 × Small (6–10 vertices)  
  - 3 × Medium (10–20 vertices)  
  - 3 × Large (20–50 vertices)  
- Each contains directed and partially cyclic subgraphs.

---

##  Sample Input (JSON)
```json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    {"u": 1, "v": 2, "w": 2},
    {"u": 2, "v": 3, "w": 4},
    {"u": 3, "v": 1, "w": 1},
    {"u": 4, "v": 5, "w": 2},
    {"u": 5, "v": 6, "w": 5},
    {"u": 6, "v": 7, "w": 1}
  ],
  "source": 4,
  "weight_model": "edge"
}
````

---

##  Example Output

```
Strongly Connected Components:
[0] [1, 2, 3]
[1] [4]
[2] [5]
[3] [6]
[4] [7]

Topological Order of Condensation:
[1, 2, 3, 4, 0]

Shortest distances from source 4:
4 → 0  
5 → 2  
6 → 7  
7 → 8  

Longest (critical) distances from source 4:
4 → 0  
5 → 2  
6 → 7  
7 → 8  
```

---

##  Performance Metrics (Sample)

| Algorithm  | Nodes | Edges | DFS Calls | Relaxations | Time (ms) |
| ---------- | ----- | ----- | --------- | ----------- | --------- |
| Tarjan SCC | 50    | 120   | 73        | —           | 1.23      |
| Topo Sort  | 50    | 120   | —         | 0           | 0.42      |
| DAG Paths  | 50    | 120   | —         | 67          | 0.31      |

---

##  Build & Run

###  Requirements

* Java 17 or higher
* Maven 3.9+
* Gson Library

###  Build and Run

```bash
mvn compile
mvn exec:java -Dexec.mainClass="graph.scc.Main"
```

or, if run manually:

```bash
javac -cp gson.jar src/**/*.java
java -cp gson.jar;. graph.scc.Main
```

---

##  GitHub Structure / Hygiene (5 %)

* Clean Maven structure
* `.gitignore` excludes IDE/build files
* Informative commit messages:

  * `Add SCC and Topological modules`
  * `Implement DAG Paths and Metrics`
  * `Generate datasets`
  * `Finalize README`

---

##  Learning Outcomes

* Implemented Tarjan’s SCC and Topological Sorting from scratch
* Understood DAG properties and critical path analysis
* Practiced JSON input/output handling with Gson
* Measured algorithmic complexity empirically

---

##  Team Roles

| Member      | Responsibility                           |
| ----------- | ---------------------------------------- |
| Tamirlan A. | SCC and Topological Sort                 |
| Emir P.     | DAG Shortest / Longest Path and Metrics  |
| Bayazit N.  | Dataset Generation and Report Formatting |

---

##  References

* Tarjan, R.E. (1972). *Depth-First Search and Linear Graph Algorithms.*
* Cormen et al. (2009). *Introduction to Algorithms.*
* Astana IT University — CS-2413 Design and Analysis of Algorithms Syllabus

---

>  *All graph datasets and results can be found in the `data/` folder.
> For reproducibility, run `DatasetGenerator` to regenerate all inputs.*

```
