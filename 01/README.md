# What's in this folder

```
.
├── code
│   ├── ca
│   │   └── mcgill
│   │       └── ecse420
│   │           └── a1
│   │               ├── diningPhilosophers
│   │               │   ├── noPossibleStarvation
│   │               │   │   ├── Chopstick.java
│   │               │   │   ├── DiningPhilosophersWithNoDeadlock.java
│   │               │   │   └── Philosopher.java
│   │               │   └── possibleStarvation
│   │               │       ├── Chopstick.java
│   │               │       ├── DiningPhilosophers.java
│   │               │       └── Philosopher.java
│   │               └── matrixMultiplication
│   │                   ├── MatrixMultiplication.java
│   │                   ├── PartialMatrixMultiplication.java
│   │                   └── Utils.java
│   └── scripts
│       ├── diningPhilosophers
│       │   ├── noPossibleStarvation
│       │   │   ├── buildAndRun_DiningPhilosophers.sh
│       │   │   ├── build_DiningPhilosophers.sh
│       │   │   └── run_DiningPhilosophers.sh
│       │   └── possibleStarvation
│       │       ├── buildAndRun_DiningPhilosophers.sh
│       │       ├── build_DiningPhilosophers.sh
│       │       └── run_DiningPhilosophers.sh
│       └── matrixMultiplication
│           ├── parallel_benchmarks
│           │   ├── matrix_size
│           │   │   ├── matrix_size_plot.py
│           │   │   └── matrix_size.sh
│           │   └── thread_count
│           │       ├── thread_count_plot.py
│           │       └── thread_count.sh
│           ├── buildAndRun_MatrixMultiplication.sh
│           ├── build_MatrixMultiplication.sh
│           └── run_MatrixMultiplication.sh
├── report
│   ├── images
│   │   ├── matrix_size_plot.png
│   │   └── thread_count_plot.png
│   ├── report.pdf
│   └── report.tex
├── hw1.pdf
├── hw1_submission_instructions.pdf
└── README.md
```

# The report

You can find the written answers to the homework in `report/report.pdf`.

# The code

All the code for this assignment is in the `code` directory.

## How to build and run the code

The `scripts` directory contains scripts for building and running the various code files.
The names are self explanatory, but the easiest to use scripts are those that start with
`buildAndRun_`.

```bash
./code/scripts/diningPhilosphers/noPossibleStarvation/buildAndRun_DiningPhilosophers.sh

./code/scripts/diningPhilosphers/possibleStarvation/buildAndRun_DiningPhilosophers.sh

./code/scripts/matrixMultiplication/buildAndRun_MatrixMultiplication.sh
```

The `scripts/matrixMultiplication` folder also contains a sub-folder `parallel_benchmarks` with two sub-folders,
`thread_count` and `matrix_size`. These two subfolders contain scripts that answer questions
1.4 and 1.5 of assignment, wherein we are asked to record the running time of the parallel
matrix multiplication as the number of threads and the size of the matrix size are varied
independently.

In each of these `matrix_size` and `thread_count` folders, there is a script that
records the running times to a `.csv` file, and a `.py` script that produces a graph `.png`
file based on the `.csv`.
