package test.newv;

import test.SimReduction;
import pi.reductions.ReducibleNowait;

public class Runner {
	
	public static void main(String []s) {
		singleTest();
	}
	
	private static void groupTest() {
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0,100);
		
		TestThread[] thGroup = new TestThread[10];
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i] = new TestThread(reducible, 10);
		}
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i].start();
		}
		
		while (!reducible.reduceFinished()); 
		System.out.println("Result:"+reducible.reduce(new SimReduction()));
	}
	
	private static void singleTest() {
		
		long startTime = 0;
		long endTime = 0;
		
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0,100);
		
		TestThread th = new TestThread(reducible, 10);
		th.start();
		
		startTime = System.nanoTime();
		reducible.reduce(new SimReduction());
		endTime = System.nanoTime();
		
		System.out.println("ori reduction finished(Execution Time:"+ (endTime-startTime) +")");
		System.out.println("Result:"+reducible.reduce(new SimReduction()));
		
	}

}
