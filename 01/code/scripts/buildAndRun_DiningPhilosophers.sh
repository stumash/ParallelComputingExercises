#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"
cd "${THIS_DIR}"

./build_DiningPhilosophers.sh && ./run_DiningPhilosophers.sh
