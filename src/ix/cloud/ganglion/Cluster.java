package ix.cloud.ganglion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class Cluster implements Runnable, Handler {

	static Logger log = Logger.getLogger(Cluster.class.getName());
	static Cluster instance;
	
	public static Cluster getInstance() {
		
		if (instance == null) {
			instance = new Cluster(Variable.getBinds());
		}
		
		return instance;
		
	}
	
	Outgoing outgoing = Outgoing.getInstance();	
	Map<Cell, Long> cells = Collections.synchronizedMap(new LinkedHashMap<>());
	
	static long SELF_EXISTENCE = 0L;
	static long CONFIRM_EXISTENCE = 1L;
	static long FORWARD_EXISTENCE = 2L;
	
	static long DELAY = 100L;
	static long RETENTION = DELAY * 10;
	
	public Cluster(List<Cell> cells) {
		for (Cell cell : cells) {
			this.cells.put(cell, new Date().getTime());
		}		
	}
	
	@Override
	public void run() {
		
		List<Cell> unresponsive = new ArrayList<>();
				
		while (true) {
			
			long limit = new Date().getTime() - RETENTION;
			
			log.debug("yield...");
			
			// yield for existence
			for (Cell cell : cells.keySet()) {
				outgoing.impulse(new Signal(Host.getInstance(), cell, new Date().getTime(), channel(), SELF_EXISTENCE));
				if (cells.get(cell) < limit) unresponsive.add(cell);
			}
			
			// clean up unresponsive
			for (Cell cell : unresponsive) {
				if (Variable.getBinds().contains(cell)) continue;
				log.info("remove : " + cell);
				cells.remove(cell);
			}
			
			unresponsive.clear();
			
			Motion.pause(DELAY);
			
		}
		
	}

	@Override
	public void accept(Signal signal) {
		
		if (signal.getOperation() == SELF_EXISTENCE) {
			
			if (cells.containsKey(signal.getOrigin())) log.debug("add renew " + signal.getOrigin());
			else log.info("add " + signal.getOrigin());
				
			cells.put(signal.getOrigin(), new Date().getTime());
			outgoing.impulse(new Signal(Host.getInstance(), signal.getOrigin(), new Date().getTime(), channel(), CONFIRM_EXISTENCE));
			
			for (Cell cell : cells.keySet()) {
				if (cell.equals(signal.getOrigin())) continue;
				outgoing.impulse(signal.setOperation(FORWARD_EXISTENCE).setDestination(cell));
			}
		
			return;
			
		}
		
		if (signal.getOperation() == CONFIRM_EXISTENCE) {
			
			log.debug("confirm " + signal.getOrigin());
			cells.put(signal.getOrigin(), new Date().getTime());
			return;
		}
		
		if (signal.getOperation() == FORWARD_EXISTENCE && !cells.containsKey(signal.getOrigin())) {
			log.debug("test lead " + signal.getOrigin());
			outgoing.impulse(new Signal(Host.getInstance(), signal.getOrigin(), new Date().getTime(), channel(), CONFIRM_EXISTENCE));
			return;
		}
		
		
		
	}

	@Override
	public byte channel() {
		return (byte) 0;		
	}
	
	
	public Set<Cell> getCells() {
		return cells.keySet();
	}
	
}
