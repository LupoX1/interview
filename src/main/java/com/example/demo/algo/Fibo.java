package com.example.demo.algo;

public class Fibo {
	
	/**
	 * 
	 * @param n position in the fibonacci sequence
	 * @return the n-th number in the fibonacci sequence
	 */
	static int fibo_lin(int n) {
		if(n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		int a = 0;
		int b = 1;
		
		for(int i=0; i< n; i++) {
			int c = a + b;
			a = b;
			b = c;
		}
		
		return a;
	}
	
	/**
	 * 
	 * @param n position in the fibonacci sequence
	 * @return the n-th number in the fibonacci sequence
	 */
	static int fibo_rec(int n) {
		if(n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if(n == 0) {
			return 0;
		}
		
		if(n == 1) {
			return 1;
		}
		
		return fibo_rec(n-1) + fibo_rec(n-2);
	}
}
