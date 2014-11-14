package test;

import java.util.Random;

import pi.reductions.Reduction;


public class SimReduction implements Reduction<Integer>{

	@Override
	public Integer reduce(Integer first, Integer second) {
		Integer result = first+second;
		simTimeElaspe();
		return result;
	}
	
	private void simTimeElaspe() {
		Random r = new Random();
		int lo = r.nextInt(50000);
		for (int i=0; i<lo; i++) {
			//loop
		}
	}

}
