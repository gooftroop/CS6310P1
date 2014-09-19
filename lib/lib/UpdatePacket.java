package lib;

public class UpdatePacket {
	
	public final double temp;
	public final int x, y;
	
	public UpdatePacket(double temp, int x, int y) {
		
		this.temp = temp;
		this.x = x;
		this.y = y;
	}

}
