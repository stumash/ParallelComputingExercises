#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}/../.."

./scripts/build_MatrixMultiplication.sh
if [ "$?" -ne "0" ]; then
    echo "build failed"
    exit 1
fi

csv_file="thread_count.csv"
echo "numThreads,millis" > "${csv_file}"

for numThreads in {1..64}; do
    for i in {1..4}; do
        millis="$(./scripts/run_MatrixMultiplication.sh -s 2000 -p true -n "${numThreads}")"
        echo "${numThreads},${millis}" >> "${csv_file}"
    done
done

if [ "$?" -ne "0" ]; then
    echo "benchmark failed"
    exit 1
fi

mv "${csv_file}" "${THIS_DIR}"
