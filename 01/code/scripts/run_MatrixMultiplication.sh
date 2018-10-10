#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}/.."

if [ ! -f "build/ca/mcgill/ecse420/a1/matrixMultiplication/MatrixMultiplication.class" ]
then
    echo "not found: build/ca/mcgill/ecse420/a1/matrixMultiplication/MatrixMultiplication.class"
    exit 1
fi

cd "build"
java ca.mcgill.ecse420.a1.matrixMultiplication.MatrixMultiplication "${@}"
