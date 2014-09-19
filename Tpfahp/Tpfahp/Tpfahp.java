package Tpfahp;

import lib.IResultsHandler;
import lib.Simulation;

public final class Tpfahp extends Simulation {

	private float oldMatrix[][];
	private float newMatrix[][];
	
	private float leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Tpfahp() {
		/* Empty */
	}
	
	public Tpfahp(IResultsHandler rh, int height, int width, double topTemp, double bottomTemp, double leftTemp, double rightTemp) {
		super(rh);
		
		this.setup(height, width, topTemp, bottomTemp, leftTemp, rightTemp);
		this.initializePlate();
	}
	
	@Override
	public void setup(int width, int height, double topTemp, double bottomTemp, double leftTemp, double rightTemp) {
	
		if (height < 0 || height >= Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid height dimension");
		
		if (width < 0 || width >= Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid width dimension");
		
		if (leftTemp < MIN_TEMP || leftTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		if (rightTemp < MIN_TEMP || rightTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		if (topTemp < MIN_TEMP || topTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		if (bottomTemp < MIN_TEMP || bottomTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		this.height = height + 2;
		this.width = width + 2;
		
		this.leftTemp 	= (float) leftTemp;
		this.rightTemp 	= (float) rightTemp;
		this.topTemp 	= (float) topTemp;
		this.bottomTemp = (float) bottomTemp;
		
		this.initializePlate();
	}
	
	@Override
	protected void initializePlate() {
		
		oldMatrix = new float[this.width][this.height];
		newMatrix = new float[this.width][this.height];
		
		this.initializeMatrix(oldMatrix);
		this.initializeMatrix(newMatrix);
	}
	
	@Override
	public void run() {
		
		double deviation = 0;
		
		do {
			
			maxDeviation = 0.0d;
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int y = 1 ; y < this.height - 1 ; y ++ ) {
				for ( int x = 1; x < this.width - 1 ; x++ ) {
				
					newMatrix[x][y] = ((oldMatrix[x - 1][y] + oldMatrix[x + 1][y] + oldMatrix[x][y - 1] + oldMatrix[x][y + 1]) / 4.0f);
					
					if ((deviation = (newMatrix[x][y] - oldMatrix[x][y])) > maxDeviation )
						maxDeviation = deviation;
					
					this.update(newMatrix[x][y], (x - 1), (y - 1));
				}
			}
			
			swapMatrix(oldMatrix, newMatrix);
			
		} while (maxDeviation >= MAX_DEVIATION && currIterations++ <= MAX_ITERATIONS);
		
		rh.stop();
		rh.setNumIterations(currIterations);
		rh.report();
	}
	
	private void initializeMatrix(float matrix[][]) {
		
		for (int y = 0; y < this.height; y++) {
			for ( int x = 0; x < this.width; x++) {
				
				// the corners of the matrix are are not used in the simulation, and so do not need special cases
				if (y == 0)
					matrix[x][y] = this.topTemp; 	// top row
				else if (y == (this.height - 1))
					matrix[x][y] = this.bottomTemp; // bottom row
				else if ( x == 0 )
					matrix[x][y] = this.leftTemp; 	// left column 
				else if (x == (this.width - 1))
					matrix[x][y] = this.rightTemp; 	// right column 
				else
					matrix[x][y] = new Float(0.0); 			// not an edge, the plate itself
			}
		}
	}
	
	private void swapMatrix(float[][] dest, float[][] src) {
		
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				dest[x][y] = src[x][y];
			}
		}
	}
}
