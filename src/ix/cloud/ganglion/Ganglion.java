package ix.cloud.ganglion;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Ganglion {

	static Logger log = Logger.getLogger(Ganglion.class.getName());
	
	static Ganglion instance;
	
	public static Ganglion getInstance() {
		if (instance==null) instance = new Ganglion();
		return instance;		
	}
	
	Outgoing outgoing = Outgoing.getInstance();
	Incoming incoming = Incoming.getInstance();
	Cluster cluster = Cluster.getInstance();
	
	public Ganglion() {	
			
		new Thread(incoming.pulses()).start();
		new Thread(incoming.storms()).start();
		new Thread(cluster).start();		
		
		incoming.register(cluster);
		
	}
	
	Map<Byte, Queue> queues = new HashMap<>();
	
	public Queue getQueue(byte channel) {
		if (!queues.containsKey(channel)) {
			queues.put(channel, new Queue<>(channel));
			incoming.register(queues.get(channel));
			new Thread(queues.get(channel)).run();
		}
		return queues.get(channel);
	}
	
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		log.info(InetAddress.getByAddress(Host.getInstance().getLocation()));
		new Ganglion();
	}
	
}
