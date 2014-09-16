package tpfahp;

import lib.IResultsHandler;
import lib.Simulation;

public final class Tpfahp extends Simulation {

	private float oldMatrix[][];
	private float newMatrix[][];
	
	private float leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Tpfahp(IResultsHandler rh) {
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
		
		this.leftTemp = leftTemp;
		this.rightTemp = rightTemp;
		this.topTemp = topTemp;
		this.bottomTemp = bottomTemp;
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
		
		do {
			
			maxDeviation = 0.0f;
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int i = 1 ; i < this.width - 1 ; i ++ ) {
				for ( int j = 1; j < this.height - 1 ; j++ ) {
				
					newMatrix[i][j] = ( ( oldMatrix[i-1][j] + oldMatrix[i+1][j] + oldMatrix[i][j-1] + oldMatrix[i][j+1] ) / 4.0f );
					
					if ( newMatrix[i][j] - oldMatrix[i][j] > maxDeviation ) {
						maxDeviation = newMatrix[i][j] - oldMatrix[i][j];
					}
					
					this.update(newMatrix[i][j], i, j);
				}
			}

			// overwrite oldMatrix with newMatrix
			swapMatrixes();
			
		} while (maxDeviation >= 0.01 && currIterations++ <= MAX_ITERATIONS);
		
		rh.stop();
		rh.setNumIterations(currIterations);
		rh.report();
	}
	
	private void initializeMatrix(float matrix[][]) {
		
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
