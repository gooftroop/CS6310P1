package lib;

public abstract class Simulation implements ISimulation {
	
	public static final int MAX_ITERATIONS = 10000;
	public static final double MAX_DEVIATION = 0.0001;
	public static final double MAX_TEMP = 100.0;
	public static final double MIN_TEMP = 0.0;
	
	protected IResultsHandler rh;
	
	protected int height = 0, width = 0, currIterations = 0;
	protected double maxDeviation = 0.0;
	
	public <R extends IResultsHandler> Simulation(R rh) {
		
		this.injectHandler(rh);
	}
	
	public Simulation() {
		/* empty constructor */
	}
	
	@Override 
	public <R extends IResultsHandler> void injectHandler(R rh) {
		
		if (rh == null) throw new IllegalArgumentException("Expecting ResultsHandler; got 'null' instead");
		
		this.rh = rh;
	}
	
	@Override
	public void update(double temp, int x, int y) {
		rh.display(temp, x, y);
	}	
	
	protected abstract void initializePlate();
}
