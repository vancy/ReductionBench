package test.others;

import pi.reductions.ReducibleNowait;

public class QueueTest {

	public static void main(String[] args) {
		ReducibleNowait<Integer> reducible = new ReducibleNowait<Integer>(0,10);
		
		reducible.put(1);
		//System.out.println(reducible.deq());
	}

}
