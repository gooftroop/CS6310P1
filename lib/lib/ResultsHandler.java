package lib;


public abstract class ResultsHandler implements IResultsHandler {
	
	public static final int NEWLINE_LENGTH = 1;
	
	protected ISimulation sim;
	
	// Number of total iterations made
	private int numIterations = 0;
	
	// Times since epoch used for profiling
	private long startTime, endTime;
	
	public ResultsHandler() {
		this.start();
	}
	
	public void stop() {
		this.endTime = System.nanoTime();
	}
	
	@Override
	public void start() {
		this.startTime = System.nanoTime();
	}

	@Override
	public int getNumIterations() {
		return new Integer(this.numIterations);
	}
	
	@Override
	public long getStartTime() {
		return new Long(this.startTime);
	}
	
	@Override
	public long getEndTime() {
		return new Long(this.endTime);
	}

	@Override
	public void setNumIterations(int numIterations) {
		
		if (numIterations < 0 || numIterations > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid numIterations range");
		
		this.numIterations = numIterations;
	}
	
	@Override
	public abstract void display(float temp, int x, int y);
	
	@Override
	public abstract void report();
}