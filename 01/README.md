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
│       ├── buildAndRun_DiningPhilosophers.sh
│       ├── buildAndRun_MatrixMultiplication.sh
│       ├── build_DiningPhilosophers.sh
│       ├── build_MatrixMultiplication.sh
│       ├── run_DiningPhilosophers.sh
│       └── run_MatrixMultiplication.sh
├── hw1.pdf
├── hw1_submission_instructions.pdf
└── report
    ├── report.pdf
    └── report.tex
```

# The report

You can find the written answers to the homework in `report/report.pdf`.

# The code

All the code for this assignment is in the `code` directory.

## How to build and run the code

The `scripts` directory contains 6 files for running and building the matrix multiplication
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
