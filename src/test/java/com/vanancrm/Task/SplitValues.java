package com.vanancrm.Task;

public class SplitValues {
public static void main(String args[]) {
	int batch = 3600/100;
	for (int i = 0; i <= 3600; i++) {
		if ( i%(3600/100) == 0) {
			System.out.println("i==="+i);
		}	
		//System.out.println("i="+i);
	}
}
}
