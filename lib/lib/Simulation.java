package lib;

public abstract class Simulation implements ISimulation {
	
	public static final int MAX_ITERATIONS = 10000;
	public static final float MAX_TEMP = 100;
	public static final float MIN_TEMP = 0;
	
	protected final IResultsHandler rh;
	
	protected int height = 0, width = 0, numIterations = 0;
	
	public <R extends IResultsHandler> Simulation(R rh) {
		
		if (rh == null) throw new IllegalArgumentException("Expecting ResultsHandler; got 'null' instead");
		
		this.rh = rh;
	}
	
	protected void update(float temp, int x, int y) {
		rh.display(temp, x, y);
	}	

	public abstract void setup(int height, int width, float leftTemp, float rightTemp, float topTemp, float bottomTemp);
	
	public abstract void run();
	
	protected abstract void initializePlate();
}
