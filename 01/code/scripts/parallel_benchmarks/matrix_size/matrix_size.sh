#!/usr/bin/env bash

if [ -z "${1}" ]; then
    echo "ERROR: expected one argument: number of threads for matrix multiplication"
    exit 1
fi

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}/../../.."

./scripts/build_MatrixMultiplication.sh
if [ "$?" -ne "0" ]; then
    echo "build failed"
    exit 1
fi

csv_file="${THIS_DIR}/matrix_size.csv"
echo "matrixSize,millis" > "${csv_file}"

for matrixSize in {100,200,500,1000,2000,4000}; do
    for i in {1..4}; do
        millis="$(./scripts/run_MatrixMultiplication.sh -s "${matrixSize}" -p true -n "${1}")"
        echo "${matrixSize},${millis}" >> "${csv_file}"
    done
done

if [ "$?" -ne "0" ]; then
    echo "benchmark failed"
    exit 1
fi
