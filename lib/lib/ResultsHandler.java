package lib;

public abstract class ResultsHandler implements IResultsHandler {
	
	protected ISimulation sim;
	
	private int numIterations = 0;
	
	public abstract void display(float temp, int x, int y);

	public int getNumIterations() {
		return new Integer(numIterations);
	}

	public void setNumIterations(int numIterations) {
		
		if (numIterations < 0 || numIterations > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid numIterations range");
		
		this.numIterations = numIterations;
	}
}