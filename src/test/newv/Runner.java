package test.newv;

import test.SimReduction;
import pi.reductions.ReducibleNowait;

public class Runner {
	
	public static void main(String []s) {
		//singleTest();
		groupTest();
	}
	
	private static void groupTest()  {
		
		long startTime = 0;
		long endTime = 0;
		
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0,100);
		
		TestThread[] thGroup = new TestThread[10];
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i] = new TestThread(reducible, 10);
		}
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i].start();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startTime = System.nanoTime();
		int result = reducible.reduce(new SimReduction());
		endTime = System.nanoTime();
		
		System.out.println("new version reduction finished(Execution Time:"+ (endTime-startTime) +")");
		System.out.println("Result:"+result);
	}
	
	private static void singleTest() {
		
		long startTime = 0;
		long endTime = 0;
		
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0,100);
		
		TestThread th = new TestThread(reducible, 10);
		th.start();
		
		startTime = System.nanoTime();
		int result = reducible.reduce(new SimReduction());
		endTime = System.nanoTime();
		
		System.out.println("new version reduction finished(Execution Time:"+ (endTime-startTime) +")");
		System.out.println("Result:"+result);
		
	}

}
