#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}/../.."

if [ ! -d "build" ]
then
    mkdir "build"
fi

path="ca/mcgill/ecse420/a1/matrixMultiplication/"

rm build/${path}/* > /dev/null

javac \
    -d build \
    "${path}/MatrixMultiplication.java"
