package ix.cloud.ganglion;

public class Motion {

	public static void pause(long delay) {
		try { Thread.sleep(delay); } catch (InterruptedException e) {}
	}
	
}
