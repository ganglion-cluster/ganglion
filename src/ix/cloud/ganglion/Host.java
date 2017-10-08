package ix.cloud.ganglion;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {
	
	static private Cell instance = null;
	
	public static Cell getInstance() {
		
		if (instance == null) {
			try {
				instance = new Cell(InetAddress.getLocalHost().getAddress());
			} catch (UnknownHostException e) {
				throw new UndeclaredThrowableException(e);
			}
		}
		
		return instance;
	}

	public static void setInstance(Cell instance) {
		Host.instance = instance;
	}
	
}
