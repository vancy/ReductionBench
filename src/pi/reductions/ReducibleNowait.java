package pi.reductions;
import java.util.concurrent.atomic.AtomicReference;
import pi.util.ThreadID;

public class ReducibleNowait<E> {
	
	
	private class Node {
		private E value;
		private AtomicReference<Node> next;
		private long threadID;
		Node(E ref) {
			this.value = ref;
			this.next = null;
			this.threadID = Thread.currentThread().getId();
		}
		Node get() {
			return this.next.get();
		}
	}
	
	private AtomicReference<Node> head;
	private AtomicReference<Node> tail;
	private boolean alreadyReduced = false;
	
	private E initialValue = null;
	
	private E reducedValue = null;
	
	public ReducibleNowait(E initialValue) {
		this.initialValue = initialValue;
	}
	
	public void set(E value) {
		//Equal to enq
		Node node = new Node(value);
		while (true) {
			Node last = this.tail.get();
			Node next = last.next.get();
			if (last == tail.get()) {
				if (next == null) {
					if (last.next.compareAndSet(next, node)) {
						this.tail.compareAndSet(last, node);
						return;
					}
				}
			}
			else {
				tail.compareAndSet(last, next);
			}
		}
	}
	
	public E deq() throws Exception {
		while(true) {
			Node first = head.get();
			Node last = tail.get();
			Node next = first.next.get();
			if (first == head.get()) {
				if (first == last) {
					if (next == null) {
						throw new Exception("Empty Exception");
					}
					tail.compareAndSet(last, next);
					}
			}
			else {
				E value = next.value;
				if (head.compareAndSet(first, next)) {
					return value;
				}
			}
		}
	}
	
	public E reduce(Reduction<E> reduction) {
		return null;
		
	}
}
