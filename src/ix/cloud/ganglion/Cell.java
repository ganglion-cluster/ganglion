package ix.cloud.ganglion;

import java.io.Serializable;
import java.util.Arrays;

public class Cell implements Serializable {

	private static final long serialVersionUID = -3828662217698440213L;

	byte[] location;

	public Cell(byte... location) {
		super();
		this.location = location;
	}

	public byte[] getLocation() {
		return location;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(location);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (!Arrays.equals(location, other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Arrays.toString(location);
	}
	
	
	
}
