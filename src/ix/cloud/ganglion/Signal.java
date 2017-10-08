package ix.cloud.ganglion;

import java.io.Serializable;

public class Signal implements Serializable {

	private static final long serialVersionUID = -5631394061338323470L;

	Cell origin;
	Cell destination;
	
	long time;
	byte channel;
	
	long operation;

	Object content = null;
	
	public Signal(Cell origin, Cell destination, long time, byte channel, long operation) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.time = time;
		this.channel = channel;
		this.operation = operation;
	}

	public Cell getOrigin() {
		return origin;
	}

	public Signal setOrigin(Cell origin) {
		this.origin = origin;
		return this;
	}

	public Cell getDestination() {
		return destination;
	}

	public Signal setDestination(Cell destination) {
		this.destination = destination;
		return this;
	}

	public long getTime() {
		return time;
	}

	public Signal setTime(long time) {
		this.time = time;
		return this;
	}

	public byte getChannel() {
		return channel;
	}

	public Signal setChannel(byte channel) {
		this.channel = channel;
		return this;
	}

	public long getOperation() {
		return operation;
	}

	public Signal setOperation(long operation) {
		this.operation = operation;
		return this;
	}
	
	public Object getContent() {
		return content;
	}

	public Signal setContent(Object content) {
		this.content = content;
		return this;
	}

	@Override
	public String toString() {
		return "Signal [origin=" + origin + ", destination=" + destination + ", time=" + time + ", channel=" + channel + ", operation=" + operation + "]";
	}
	
	
	
}
