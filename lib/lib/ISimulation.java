package lib;

public interface ISimulation {
	
	public abstract void run();
	
	public void update(double temp, int x, int y);
	
	public void setup(int height, int width, double leftTemp, double rightTemp, double topTemp, double bottomTemp);
}
