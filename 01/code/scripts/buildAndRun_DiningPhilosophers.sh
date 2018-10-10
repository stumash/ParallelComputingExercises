#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -f "$0")")"

./build_DiningPhilosophers.sh && ./run_DiningPhilosophers.sh
