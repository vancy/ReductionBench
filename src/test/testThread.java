package test;

import pi.reductions.ReducibleNowait;

public class testThread extends Thread{
	
	public ReducibleNowait<Integer> reducible;
	public int id;
	
	public testThread(ReducibleNowait<Integer> reducible, int id) {
		this.reducible = reducible;
		this.id = id;
	}
	@Override
	public void run() {
		
		for(int i=0; i<id; i++) {
			reducible.put(i);
		}
		
		reducible.reduce(new SimReduction());
		
	}
}
