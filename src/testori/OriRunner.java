package testori;

import pi.reductions.Reducible;
import pi.reductions.ReducibleNowait;
import test.SimReduction;

public class OriRunner {
	
	public static void main(String []s) {
		Test();
	}
	
	private static void Test() {
		
		long startTime = 0;
		long endTime = 0;
		
		Reducible<Integer> reducible = new Reducible<Integer>(0);
		
		TestOriThread[] thGroup = new TestOriThread[10];
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i] = new TestOriThread(reducible, i);
		}
		
		for (int i=0; i<thGroup.length; i++) {
			thGroup[i].start();
		}
		
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startTime = System.nanoTime();
		reducible.reduce(new SimReduction());
		endTime = System.nanoTime();
		
		System.out.println("ori reduction finished(Execution Time:"+ (endTime-startTime) +")");
		System.out.println("Result:"+reducible.reduce(new SimReduction()));
		
	}

}
