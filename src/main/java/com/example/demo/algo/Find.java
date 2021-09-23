package com.example.demo.algo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Find {
	static final List<String> names1 = Arrays.asList("John", "Paul", "George", "Ringo");
	static final Set<String> names2 = new HashSet<>(names1);
	
	public static boolean findNameInList(String name) {
		return names1.contains(name);
	}
	
	public static boolean findNameInSet(String name) {
		return names2.contains(name);
	}

}
