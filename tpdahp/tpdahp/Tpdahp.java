package tpdahp;

import lib.IResultsHandler;
import lib.Simulation;

public final class Tpdahp extends Simulation {
	
	private double oldMatrix[][];
	private double newMatrix[][];
	
	private double leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Tpdahp(IResultsHandler rh) {
		super(rh);
	}
	
	@Override
	public void setup(int height, int width, float leftTemp, float rightTemp, float topTemp, float bottomTemp) {
		
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
		
		this.leftTemp 	= (double) leftTemp;
		this.rightTemp 	= (double) rightTemp;
		this.topTemp 	= (double) topTemp;
		this.bottomTemp = (double) bottomTemp;
		
		this.initializePlate();
	}
	
	@Override
	protected void initializePlate() {
		
		oldMatrix = new double[this.width][this.height];
		newMatrix = new double[this.width][this.height];
		
		initializeMatrix(oldMatrix);
		initializeMatrix(newMatrix);
	}
	
	@Override
	public void run() {
		
		// TODO add update call
		
		float maxDeviation = 0.0f;
		
		do {
			
			maxDeviation = 0.0f;
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int i = 1 ; i < this.width - 1 ; i ++ ) {
				for ( int j = 1; j < this.height - 1 ; j++ ) {
				
					newMatrix[i][j] = ( ( oldMatrix[i-1][j] + oldMatrix[i+1][j] + oldMatrix[i][j-1] + oldMatrix[i][j+1] ) / 4.0f );
					
					if ( newMatrix[i][j] - oldMatrix[i][j] > maxDeviation ) {
						maxDeviation = (float) (newMatrix[i][j] - oldMatrix[i][j]);
					}
				}
			}

			// overwrite oldMatrix with newMatrix
			swapMatrixes();
			
		} while (maxDeviation >= 0.01 && numIterations++ <= MAX_ITERATIONS);
	}
	
	private void initializeMatrix(double matrix[][]) {
		
		for (int i = 0; i < this.width; i++) {
			for ( int j = 0; j < this.height; j++) {
				
				// the corners of the matrix are are not used in the simulation, and so do not need special cases
				if (i == 0)
					matrix[i][j] = this.topTemp; 	// top row
				else if (i == (this.width - 1))
					matrix[i][j] = this.bottomTemp; // bottom row
				else if ( j == 0 )
					matrix[i][j] = this.leftTemp; 	// left column 
				else if (j == (this.height - 1))
					matrix[i][j] = this.rightTemp; 	// right column 
				else
					matrix[i][j] = 0.0f; 			// not an edge, the plate itself
			}
		}
	}
	
	private void swapMatrixes() {
		
		for ( int i = 1 ; i < this.width - 1 ; i++ ) {
			for ( int j = 1 ; j < this.height - 1 ; j++ ) {
				oldMatrix[i][j] = newMatrix[i][j];
			}
		}
	}
}
