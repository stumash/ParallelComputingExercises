#!/usr/bin/env python3

from pathlib import Path
from matplotlib import pyplot as plt
import pandas as pd

# get path to this script
this_path = Path(__file__).parents[0]

# path to csv file in same folder as this script
csv_path = this_path / "thread_count.csv"

# read the csv file and compute mean time (ms) per numThreads
df = pd.read_csv(str(csv_path))
df = (df.groupby(['numThreads'], as_index=False)).mean()

# plot the graph
plt.plot(df['numThreads'], df['millis'])
plt.xlabel('numThreads')
plt.ylabel('millis')
plt.title('time in ms vs number of threads used')

# path to .png of thread count benchmark
png_path = this_path / 'thread_count_plot.png'

plt.savefig(str(png_path))
