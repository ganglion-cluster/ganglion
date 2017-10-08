package ix.cloud.ganglion;

public interface Handler {

	public void accept(Signal signal);
	public byte channel();
	
}
