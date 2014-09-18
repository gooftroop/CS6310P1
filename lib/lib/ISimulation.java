package lib;

public interface ISimulation {
	
	public abstract void run();
	
	public void update(double temp, int x, int y);
	
	public void setup(int width, int height, double topTemp, double bottomTemp, double leftTemp, double rightTemp);
	
	public <R extends IResultsHandler> void injectHandler(R rh);
	
}
