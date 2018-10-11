#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}"

./build_MatrixMultiplication.sh
if [ "${?}" != "0" ]; then
    echo "build failed"
    exit 1
fi

./run_MatrixMultiplication.sh "${@}"
