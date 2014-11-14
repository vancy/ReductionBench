package test.newv;

import pi.reductions.ReducibleNowait;

public class TestThread extends Thread{
	
	public ReducibleNowait<Integer> reducible;
	public int id;
	
	public TestThread(ReducibleNowait<Integer> reducible, int id) {
		this.reducible = reducible;
		this.id = id;
	}
	@Override
	public void run() {
		
		for(int i=0; i<id; i++) {
			reducible.put(i);
		}
		System.out.println("reduction .....");
//		reducible.reduce(new SimReduction());
		System.out.println("reduction done...");
	}
}
