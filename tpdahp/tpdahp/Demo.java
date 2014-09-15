package tpdahp;

import java.util.Hashtable;

import lib.ISimulation;
import tpfahp.Tpfahp;

public class Demo {
	
	private static final Hashtable<String, String> options = new Hashtable<String, String>();
	
	public static void main(String...args) {
		Demo.configureOpts();
		Demo.parseArgs(args);
		
		ISimulation simulation = new Tpfahp(new TpdahpHandler());
		
		int width = Integer.parseInt(options.get("-d"));
		int height = Integer.parseInt(options.get("-d"));
		float left = Float.parseFloat(options.get("-l"));
		float right = Float.parseFloat(options.get("-r"));
		float top = Float.parseFloat(options.get("-t"));
		float bottom = Float.parseFloat(options.get("-b"));
		
		simulation.setup(width, height, left, right,top, bottom);
		simulation.run();
	}

	private static void configureOpts() {
		options.put("-d", "");
		options.put("-l", "");
		options.put("-r", "");
		options.put("-t", "");
		options.put("-b", "");
	}
	
	private static void parseArgs(String... args) {
		
		String currOpt = "";
		
		for (int i = 0; i < args.length; i++) {
			
	        switch (args[i].charAt(0)) {
	        
		        case '-':
		            if (args[i].length() < 2) throw new IllegalArgumentException("Not a valid argument: " + args[i]);
		            if (args[i].charAt(1) == '-') throw new IllegalArgumentException("Not a valid argument: " + args[i]);
		            else {
		                if (args.length - 1 == (i + 1))  throw new IllegalArgumentException("Expected arg after: " + args[i]);
		                if (!options.contains(args[i])) throw new IllegalArgumentException("Invalid argument: " + args[i]);
		                currOpt = args[i];
		            }
		            break;
		        default:
		            options.put(currOpt, args[i]);
		            break;
	        }
		}	
	}
}

//private void print ( Float matrix[][] ) {
//
//String outputString = "";
//
//System.out.println("Iteration "+numIterations+":");
//
//for ( int i = 0 ; i < matrixDimension ; i++ ) {
//	
//	for ( int j = 0 ; j < matrixDimension ; j++ ) {
//		
//		// skip the edge rows
//		if ( i != 0 && i != ( matrixDimension - 1 ) && j != 0 && j != ( matrixDimension - 1 ) ) {
//			
//			outputString = String.format("%5.2f", matrix[i][j]);
//			System.out.print(outputString);					
//			System.out.print(" ");
//		}
//	}
//	
//	// new line for next row
//	System.out.println();
//}
//}