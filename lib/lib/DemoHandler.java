package lib;

import java.util.Arrays;

public class DemoHandler extends ResultsHandler {

	private String[][] resultMatrix;
	
	private final int WIDTH;
	private final int HEIGHT;
	
	public DemoHandler(int width, int height) {
		super();
		
		if (width < 0 || width > Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid width");
		if (height < 0 || height > Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid height");
		
		this.HEIGHT = height;
		this.WIDTH = width;
	
		resultMatrix = new String[WIDTH][HEIGHT];
		
		for (String[] row: resultMatrix)
		    Arrays.fill(row, "000.00");
	}

	@Override
	public void display(double temp, int x, int y) {
		resultMatrix[x][y] = String.format("%06.2f ", temp);
	}

	@Override
	public void report() {
		
		System.out.println("Simulation Complete.");
		System.out.println("Total number of iterations made: " + this.getNumIterations());
		System.out.println("Maximum runtime memory used: " + this.getUsedMemory() + " bytes");
		
		double total = ((double)(this.getEndTime() - this.getStartTime())) / 1000000000;
		System.out.format("Time taken to complete: %f seconds\n", total);
		System.out.println();
		
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				System.out.print(resultMatrix[x][y]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
}