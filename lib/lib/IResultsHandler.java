package lib;

public interface IResultsHandler {
	
	public void display(double temp, int x, int y);
	
	public void report();
	
	public void start();
	
	public void stop();
	
	public int getNumIterations();
	
	public long getStartTime();
	
	public long getEndTime();
	
	public long getUsedMemory();
	
	public void setNumIterations(int numIterations);

}
