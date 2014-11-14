package test.newv;

import test.SimReduction;
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
			reducible.put(1);
		}
		//if (id < 5) {
			reducible.reduce(new SimReduction());
		//}


	}
}
