package tpfahp;

import lib.ResultsHandler;

public class TpfahpHandler extends ResultsHandler {
	
	private String resultMatrix;
	private final int width;
	
	public TpfahpHandler(int width, int height) {
		super();
		
		if (width < 0 || width > Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid width");
		if (height < 0 || height > Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid height");
		
		String populateRow = new String(new char[width]).replace("\0", "000.00 ").concat("\n");
		resultMatrix = new String(new char[height]).replace("\0", populateRow);
		
		this.width = width;
	}

	@Override
	public void display(float temp, int x, int y) {
		
		int index = (y * (width * 7 + NEWLINE_LENGTH)) + (x * 7);
		resultMatrix = resultMatrix.substring(0, index) + String.format("%06.2f ", temp) + resultMatrix.substring((index + 7 ));
	}

	@Override
	public void report() {
		
		System.out.println("Simulation Complete.");
		System.out.println("Total number of iterations made: " + this.getNumIterations());
		
		double total = ((double)(this.getEndTime() - this.getStartTime())) / 1000000000;
		System.out.format("Time taken to complete: %f seconds\n", total);
		System.out.println();
		
		System.out.println(resultMatrix);
	}
}
