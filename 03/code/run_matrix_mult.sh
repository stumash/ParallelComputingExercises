#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "${0}")")"
cd "${THIS_DIR}"

# run matrix multiplication benchmark
classpath="${THIS_DIR}/target/lib/*:."
cd "target/classes"

if [ -z "${1}" ]; then
    java \
        -classpath "${classpath}" \
        "ca.mcgill.ecse420.a3.MatrixMultiplication.MultiplierBenchmark" \
        "--help"
    exit 0
fi

java \
    -classpath "${classpath}" \
    "ca.mcgill.ecse420.a3.MatrixMultiplication.MultiplierBenchmark" \
    "${@}"
