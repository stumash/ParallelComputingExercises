#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}"

./build_MatrixMultiplication.sh && ./run_MatrixMultiplication.sh "${@}"
