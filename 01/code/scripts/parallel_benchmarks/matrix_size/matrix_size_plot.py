#!/usr/bin/env python3

from pathlib import Path
from matplotlib import pyplot as plt
import pandas as pd

# get path to this script
this_path = Path(__file__).parents[0]

# path to csv file in same folder as this script
csv_path = this_path / "matrix_size.csv"

# read the csv file and compute mean time (ms) per numThreads
df = pd.read_csv(str(csv_path))
df = (df.groupby(['matrixSize'], as_index=False)).mean()

# plot the graph
plt.plot(df['matrixSize'], df['millisPar'], "r", label='parallel (8 threads)')
plt.plot(df['matrixSize'], df['millisSeq'], "b", label='sequential')
plt.xlabel('matrixSize')
plt.ylabel('millis')
plt.title('time in ms vs size of matrix used')
plt.legend()

# path to .png of matrix size benchmark
png_path = this_path / 'matrix_size_plot.png'

plt.savefig(str(png_path))
