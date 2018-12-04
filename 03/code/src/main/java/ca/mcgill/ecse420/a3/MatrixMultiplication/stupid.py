#!/usr/bin/env python3

import numpy as np

N = 10

M = (np.random.rand(N,N)*100).astype(int)
A = (np.random.rand(N,1)*100).astype(int)

C  = (np.zeros((N,1))).astype(int)

def main():
    global M,A,C,N

    mult(0,N) # achieved result
    print(C)

    C_ = M.dot(A) # correct result
    print(C_)

    equality = " " if np.array_equal(C,C_) else " not "
    print("algorithm is{}correct".format(equality))

def mult(idx, n):
    global C
    if n == 1:
        C[idx,0] = dotprod(idx, 0, N)
    else:
        mult(idx, n//2)
        new_idx = idx + (n//2)
        mult(new_idx, n-(new_idx-idx))

def dotprod(row, idx, n):
    if n == 1:
        return M[row,idx]*A[idx]
    else:
        new_idx = idx + (n//2)
        return dotprod(row, idx, n//2) + dotprod(row, new_idx, n-(new_idx-idx))


if __name__ == "__main__":
    main()
