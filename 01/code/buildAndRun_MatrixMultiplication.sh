#!/usr/bin/env bash

if [ ! -d "build" ]
then
    mkdir "build"
fi

javac ca/mcgill/ecse420/a1/matrixMultiplication/MatrixMultiplication.java -d build

cd build

java ca.mcgill.ecse420.a1.matrixMultiplication.MatrixMultiplication "$@"
