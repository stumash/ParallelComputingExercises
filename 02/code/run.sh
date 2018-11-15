#!/usr/bin/env bash

helpString=<<EOF
usage: run.sh [-h|--help]

Builds the code if it isn't already built, then runs the only
file that has a main method, FilterTest.
EOF

if [ -n "${1}" ]; then
    echo "$helpString"
    if [[ "${1}" != "-h" && "${1}" != "--help" ]]; then
        exit 1
    else
        exit 0
    fi
fi

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}"

BUILD_DIR="${THIS_DIR}/target/classes"

if [ ! -d "${BUILD_DIR}" ]; then
    mvn clean compile
fi
cd "${BUILD_DIR}"

java ca.mcgill.ecse420.a2.FilterTest
