package twfahp;

import java.util.Arrays;

import lib.IResultsHandler;
import lib.Simulation;

public final class Twfahp extends Simulation {
	
	private Float plate[][];
	
	private Float leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Twfahp(IResultsHandler rh) {
		super(rh);
	}
	
	@Override
	public void setup(int height, int width, double leftTemp, double rightTemp, double topTemp, double bottomTemp) {
		
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
		
		this.leftTemp 	= new Float(leftTemp);
		this.rightTemp 	= new Float(rightTemp);
		this.topTemp 	= new Float(topTemp);
		this.bottomTemp = new Float(bottomTemp);
		
		this.initializePlate();
	}
	
	@Override
	protected void initializePlate() {
		
		plate = new Float[this.width][this.height];
		
		initializeMatrix(plate);
	}
	
	@Override
	public void run() {
		
		int top = 0;
		float newTemp = 0;
		double deviation = 0;
		Float hold[] = new Float[this.width];
		Arrays.fill(hold, this.topTemp);
		
		do {
			
			maxDeviation = 0.0f;
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int i = 1 ; i < this.width - 1 ; i ++ ) {
				
				top = i - 1;
				
				// inside - if hold is valid, apply to -1 and replace with new
				
				for ( int j = 1; j < this.height - 1 ; j++ ) {
				
					newTemp = ((plate[i-1][j] + plate[i+1][j] + plate[i][j-1] + plate[i][j+1]) / 4.0f);
					
					if ((deviation = newTemp - plate[i][j]) > maxDeviation )
						maxDeviation = deviation;
					
					plate[top][j] = hold[j];
					hold[j] = newTemp; 
					
					this.update(newTemp, (i - 1), (j - 1));
				}
			}
			
		} while (maxDeviation >= 0.01 && currIterations++ <= MAX_ITERATIONS);
		
		rh.stop();
		rh.setNumIterations(currIterations);
		rh.report();
	}
	
	private void initializeMatrix(Float matrix[][]) {
		
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
}
