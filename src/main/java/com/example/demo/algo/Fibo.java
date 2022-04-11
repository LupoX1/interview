package com.example.demo.algo;

public class Func {
	
	static int f(int n) {
		if(n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		int a = 0;
		int b = 1;
		
		for(int i=0; i<n; i++) {
			int c = a + b;
			a = b;
			b = c;
		}
		
		return a;
	}
	
	static int g(int n) {
		if(n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if(n == 0) {
			return 0;
		}
		
		if(n == 1) {
			return 1;
		}
		
		return g(n-1) + g(n-2);
	}
}
