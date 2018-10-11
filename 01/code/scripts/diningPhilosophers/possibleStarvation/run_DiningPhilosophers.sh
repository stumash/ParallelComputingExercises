#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}/../../.."

path="ca/mcgill/ecse420/a1/diningPhilosophers/possibleStarvation/DiningPhilosophers"

if [ ! -f "build/${path}.class" ]
then
    echo "not found: build/${path}.class"
    exit 1
fi

cd "build"
java "${path}" "${@}"
