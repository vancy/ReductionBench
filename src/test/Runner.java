package test;

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
		System.out.println("Starting single test");
		
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0,10);
		
		TestThread th = new TestThread(reducible, 10);
		th.start();
		System.out.println("thread started");
		while (!reducible.reduceFinished()); 
		
		System.out.println("reduction finished");
		System.out.println("Result:"+reducible.reduce(new SimReduction()));
		
	}

}
