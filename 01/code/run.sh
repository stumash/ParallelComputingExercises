#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}"

if [ -z "${1}" ] || \
   [ "${1}" == "--help" ] || \
   [ "${1}" == "-h" ] || \
   [ "${1}" != "m" ] && \
   [ "${1}" != "dd" ] && \
   [ "${1}" != "ds" ] && \
   [ "${1}" != "d" ]
then
    echo "usage: run <name> <args...>"
    echo ""
    echo "examples:"
    echo "  ./run.sh m -s 1000 -p true -n 8 # matrix mult, matrixSize=1000 parallel=true numThreads=8"
    echo "  ./run.sh dd                     # dining philosophers, can deadlock and starve"
    echo "  ./run.sh ds                     # dining philosophers, cannot deadlock but can starve"
    echo "  ./run.sh d                      # dining philosophers, cannot deadlock or starvation"
    exit 1
fi

if [ "${1}" == "m" ]; then
    path="ca/mcgill/ecse420/a1/matrixMultiplication/MatrixMultiplication"
elif [ "${1}" == "dd" ]; then
    path="ca/mcgill/ecse420/a1/diningPhilosophers/canDeadlock/DiningPhilosophers"
elif [ "${1}" == "ds" ]; then
    path="ca/mcgill/ecse420/a1/diningPhilosophers/cannotDeadlock/DiningPhilosophers"
elif [ "${1}" == "d" ]; then
    path="ca/mcgill/ecse420/a1/diningPhilosophers/cannotStarve/DiningPhilosophers"
fi

if [ ! -f "build/${path}.class" ]
then
    echo "not found: build/${path}.class"
    echo "please run 'make' first"
    exit 1
fi

shift
cd "build"
java "${path}" "${@}"
