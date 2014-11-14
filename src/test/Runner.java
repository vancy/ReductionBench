package test;

import pi.reductions.ReducibleNowait;

public class Runner {
	
	public static void main(String []s) {
		
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0);
		
		testThread[] thGroup = new testThread[10];
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i] = new testThread(reducible, i);
		}
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i].start();
		}
		
	}

}
