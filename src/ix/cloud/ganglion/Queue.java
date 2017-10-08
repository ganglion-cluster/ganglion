package ix.cloud.ganglion;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class Queue<T> implements Runnable, Handler{

	static Logger log = Logger.getLogger(Queue.class.getName());
	
	byte channel;
	
	static long OFFERING = 0L;
	static long REQUEST = 1L;
	static long DATA = 2L;
	
	Cluster cluster = Cluster.getInstance();
	
	List<T> incoming = Collections.synchronizedList(new LinkedList<>());
	List<T> outgoing = Collections.synchronizedList(new LinkedList<>());
	
	public Queue(byte channel) {
		super();
		this.channel = channel;
	}

	Thread waitIncoming = null;
	Thread waitOutgoing = null;
	
	public T take() {
		
		waitIncoming = Thread.currentThread();
		
		while (true) {
			
			if (incoming.size() == 0) {				
				Motion.pause(100);
				continue;
			}
			
			waitIncoming = null;
			log.info("execute queue");
			return incoming.get(0);
			
		}
		
	}

	public void put(T data) {
		log.info("add queue");
		outgoing.add(data);
		waitOutgoing.interrupt();
	}
	
	@Override
	public void accept(Signal signal) {
		
		if (signal.getOperation() == OFFERING) {
			log.info("get offer " + signal);
			if (waitIncoming != null) 
			Outgoing.getInstance().impulse(new Signal(Host.getInstance(), signal.getOrigin(), new Date().getTime(), channel, REQUEST));
		}
		
		if (signal.getOperation() == REQUEST) {
			if (outgoing.size() == 0) return;
			log.info("get request " + signal);
			T data = outgoing.get(0);
			Outgoing.getInstance().storm(new Signal(Host.getInstance(), signal.getOrigin(), new Date().getTime(), channel, DATA).setContent(data));
			outgoing.remove(data);
		}
		
		if (signal.getOperation() == DATA) {
			log.info("get data " + signal);
			incoming.add((T) signal.getContent());
			if (waitIncoming != null) waitIncoming.interrupt();
		}

	}

	@Override
	public byte channel() {
		return channel;
	}

	@Override
	public void run() {
	
		waitOutgoing = Thread.currentThread();
		
		while (true) {
			
			if (outgoing.size() > 0) {
				for (Cell cell : Cluster.getInstance().getCells()) {
					Outgoing.getInstance().impulse(new Signal(Host.getInstance(), cell, new Date().getTime(), channel, OFFERING));
				}
			}
			
			Motion.pause(100);
			
		}
		
	}
	
}
