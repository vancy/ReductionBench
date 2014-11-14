package test.oldv;

import pi.reductions.Reducible;
import pi.reductions.ReducibleNowait;

public class TestOriThread extends Thread{
	
	public Reducible<Integer> reducible;
	public int id;
	
	public TestOriThread(Reducible<Integer> reducible, int id) {
		this.reducible = reducible;
		this.id = id;
	}
	
	@Override
	public void run() {
		reducible.set(id);
	}
}