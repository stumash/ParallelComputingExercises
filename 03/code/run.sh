#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "${0}")")"
cd "${THIS_DIR}"

helpString=$(cat <<HEREDOC
usage: run.sh matrixmult [options]
HEREDOC
)

if [ -z "${1}" ]; then
    echo "${helpString}"
    exit 1
fi
if [[ "${1}" =~ ^-*h(elp)?$ ]]; then
    echo "${helpString}"
    exit 0
fi

classpath="${THIS_DIR}/target/lib/*:."
cd "target/classes"

if [ "${1}" == "matrixmult" ]; then
    java \
        -classpath "${classpath}" \
        "ca.mcgill.ecse420.a3.MatrixMultiplication.MultiplierBenchmark" \
        "${@}"
    exit 0
else
    echo "${helpString}"
fi
