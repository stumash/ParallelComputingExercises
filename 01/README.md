# What's in this folder

```
.
├── code
│   ├── ca
│   │   └── mcgill
│   │       └── ecse420
│   │           └── a1
│   │               ├── diningPhilosophers
│   │               │   ├── DiningPhilosophers.java
│   │               │   └── DiningPhilosophersWithNoDeadlock.java
│   │               └── matrixMultiplication
│   │                   ├── MatrixMultiplication.java
│   │                   ├── PartialMatrixMultiplication.java
│   │                   └── Utils.java
│   └── scripts
│       ├── parallel_benchmarks
│       │   ├── matrix_size
│       │   │   ├── matrix_size_plot.py
│       │   │   └── matrix_size.sh
│       │   └── thread_count
│       │       ├── thread_count_plot.py
│       │       └── thread_count.sh
│       ├── buildAndRun_DiningPhilosophers.sh
│       ├── buildAndRun_MatrixMultiplication.sh
│       ├── build_DiningPhilosophers.sh
│       ├── build_MatrixMultiplication.sh
│       ├── run_DiningPhilosophers.sh
│       └── run_MatrixMultiplication.sh
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

The `scripts` directory contains 6 scripts for running and building the matrix multiplication
solution and the dining philosophers solution.

Each of the six `bash` scripts are executable. They have self-explanatory names and can be ran
as follows:

```bash
./scripts/build_MatrixMultiplication.sh

./scripts/buildAndRun_DiningPhilosophers.sh

./scripts/buildAndRun_MatrixMultiplication.sh

./scripts/build_DiningPhilosophers.sh

./scripts/build_MatrixMultiplication.sh

./scripts/run_DiningPhilosophers.sh

./scripts/run_MatrixMultiplication.sh
```

The `scripts folder` also contains a sub-folder `parallel_benchmarks` with two sub-folders,
`thread_count` and `matrix_size`. These two subfolders contain scripts that answer questions
1.4 and 1.5 of assignment, wherein we are asked to record the running time of the parallel
matrix multiplication as the number of threads and the size of the matrix size are varied
independently.

In each of the `matrix_size` and `thread_coutn` folders, there is a `bash` script that
records the running time to a `.csv` file, and a `.py` script that produces a graph `.png`
file based on the `.csv`.
