package ix.cloud.ganglion;

import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;

public class Outgoing {

	static Logger log = Logger.getLogger(Outgoing.class.getName());
	static private Outgoing instance;
	
	public static Outgoing getInstance() {
		if (instance == null) instance = new Outgoing();
		return instance;
	}
	
	public Outgoing() {}
	
	public void impulse(Signal signal) {
		try {

			log.debug("impulse " + signal);
			DatagramSocket socket = new DatagramSocket();
			byte[] plain = SerializationUtils.serialize(signal.setContent(null));
			DatagramPacket sendPacket = new DatagramPacket(plain, plain.length, InetAddress.getByAddress(signal.getDestination().getLocation()), Variable.UDP_PORT);
			socket.send(sendPacket);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void storm(Signal signal) {
		try {

			log.debug("storm " + signal);
			
			Socket target = new Socket(InetAddress.getByAddress(signal.getDestination().getLocation()), Variable.TCP_PORT);			
			ObjectOutputStream oos = new ObjectOutputStream(target.getOutputStream());			
			oos.writeObject(signal);

			target.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
