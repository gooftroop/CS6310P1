package lib;

public class UpdatePacket {
	
	public final double temp;
	public final int x, y;
	
	public UpdatePacket(double temp, int x, int y) {
		
		if (temp < Double.MIN_VALUE || temp > Double.MAX_VALUE)
			throw new IllegalArgumentException("Invalid temp");
		
		if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid x location");
		
		if (y < Integer.MIN_VALUE || y > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid y location");
		
		this.temp = temp;
		this.x = x;
		this.y = y;
	}

}
