package ix.cloud.ganglion;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Variable {

	private static List<Cell> binds = Collections.synchronizedList(new LinkedList<>());	
	public static int UDP_PORT = 5702;
	public static int TCP_PORT = 5701;
	
	public static void setBinds(Cell... binds) {
		Variable.binds.clear();
		Variable.binds.addAll(Arrays.asList(binds));
	}
	
	public static List<Cell> getBinds() {
		return binds;
	}
	
	
}
