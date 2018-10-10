#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}/.."

if [ ! -d "build" ]
then
    mkdir "build"
fi

javac ca/mcgill/ecse420/a1/matrixMultiplication/MatrixMultiplication.java -d build
