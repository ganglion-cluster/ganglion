package ix.cloud.ganglion;

import java.io.ObjectInputStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

public class Incoming {

	Map<Byte, List<Handler>> handlers = new HashMap<>();
	DatagramSocket datagramSocket;
	ServerSocket serverSocket;

	static Incoming instance;

	public static Incoming getInstance() {
		if (instance==null) instance = new Incoming();
		return instance;
	}

	public Incoming() {
		try {
			datagramSocket = new DatagramSocket(5700);
			serverSocket = new ServerSocket(5701);
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	public void register(Handler handler) {
		if (!handlers.containsKey(handler.channel())) handlers.put(handler.channel(), new ArrayList<>());
		handlers.get(handler.channel()).add(handler);
	}

	public Runnable pulses() {
		return new Runnable() {

			@Override
			public void run() {

				while (true) {

					try {
						byte[] data = new byte[512];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						datagramSocket.receive(packet);																																												Signal signal = (Signal) SerializationUtils.deserialize(packet.getData());			

						for (Handler handler : handlers.get(signal.getChannel())) {				
							handler.accept(signal);				
						}

					} catch (Exception e) { e.printStackTrace(); }
				}

			}

		};
	}

	public Runnable storms() {
		return new Runnable() {

			@Override
			public void run() {
				while (true) {

					try {

						Socket socket = serverSocket.accept();
						Signal signal = (Signal) new ObjectInputStream(socket.getInputStream()).readObject();						
						socket.close();

						for (Handler handler : handlers.get(signal.getChannel())) {				
							handler.accept(signal);				
						}
						
					} catch (Exception e) { e.printStackTrace(); }
				}
			}

		};
	}

}
