package pi.reductions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class ReducibleNowait<E> {
	
	private class Node {
		private E value;
		private AtomicReference<Node> next = new AtomicReference<Node>();
		private long threadID;
		Node(E ref) {
			this.value = ref;
			this.next.set(null);
			this.threadID = Thread.currentThread().getId();
		}	
		
		long getThreadID() {
			return this.threadID;
		}
	}
	
	private int expectSize = 0;
	private AtomicInteger actualSize = new AtomicInteger(0);
	private AtomicInteger numOfReductionOperated = new AtomicInteger(0);
	
	private AtomicReference<Node> head = new AtomicReference<Node>();
	private AtomicReference<Node> tail = new AtomicReference<Node>();
	
	private volatile boolean alreadyReduced = false;
	private volatile boolean elementPuttingFinished = false;
	
	private E initialValue = null;
	private E reducedValue = null;
	
	public ReducibleNowait(E initialValue) {
		this.initialValue = initialValue;
		
		this.head.set(new Node(null));
		this.tail.set(this.head.get());
	}
	
	public ReducibleNowait(E initialValue, int elementSize) {
		this.initialValue = initialValue;
		this.expectSize = elementSize;
		
		this.head.set(new Node(null));
		this.tail.set(this.head.get());
		
	}
	
	
	//Equal to enq
	public void put(E value) {
		if (this.expectSize!=0 && this.expectSize == this.actualSize.get()) {
			throw new RuntimeException("Cannot put value " + value + " to Reducible container, because container is full");
		}
		
		if (this.expectSize==0 && this.elementPuttingFinished) {
			throw new RuntimeException("Cannot put value " + value + " to Reducible container, because container stops accepting element");
		}
		
		enq(value);
		//increase actualSize
		this.actualSize.incrementAndGet();
	}
	
	private boolean enq(E value) {
		Node node = new Node(value);
		while (true) {
			Node last = this.tail.get();
			Node next = last.next.get();
			if (last == tail.get()) {
				if (next == null) {
					if (last.next.compareAndSet(next, node)) {
						this.tail.compareAndSet(last, node);
						return false;
					}
				}
			}
			else {
				tail.compareAndSet(last, next);
			}
		}
	}
	
	private E deq() throws EmptyQueueException {
		while(true) {
			Node first = head.get();
			Node last = tail.get();
			Node next = first.next.get();
			if (first == head.get()) {
				if (first == last) {
					if (next == null) {
						//throw new EmptyQueueException();
						return null;
					}
					tail.compareAndSet(last, next);
				}
				else {
					E value = next.value;
					if (head.compareAndSet(first, next)) {
						return value;
					}
				}
			}
		}
	}
	
	public void finishPuttingElement() {
		this.elementPuttingFinished = true;
	}
	
	public boolean reduceFinished() {
		return this.alreadyReduced;
	}
	
	public E reduce(Reduction<E> reduction) {
		
		//If the entire reduction is finished, just reture the final value;
		if (this.alreadyReduced) {
			return this.reducedValue;
		}
		
		E myFirstValue = null;
		E mySecondValue = null;

		//Else, get my first value
		while (this.alreadyReduced == false) {
			myFirstValue = this.deq();
			if (myFirstValue == null) {
				//if I cannot get my firstValue, restart the while loop
				continue;
			}
			while ((mySecondValue = this.deq()) != null) {
				myFirstValue = reduction.reduce(myFirstValue, mySecondValue);
				
				if (this.actualSize.get()-1 == this.numOfReductionOperated.incrementAndGet()) {
					//System.out.println("|reduction times:"+this.numOfReductionOperated.get()+" | actual size:"+this.actualSize.get());
					this.reducedValue = myFirstValue;
					this.alreadyReduced = true;
					return this.reducedValue;
				}
			}
			if (mySecondValue == null) {
				//If I cannot get my secondValue, put back my first value to the queue;
				this.enq(myFirstValue);
				//restart the while loop again
				continue;
			}
		}
		return this.reducedValue;
	}
}
