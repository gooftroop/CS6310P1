package lib;

public interface ISimulation {
	
	public abstract void run();
	
	public void setup(int height, int width, float leftTemp, float rightTemp, float topTemp, float bottomTemp);
}
