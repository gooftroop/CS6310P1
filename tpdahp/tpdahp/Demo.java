package tpdahp;

import java.util.Hashtable;

import lib.DemoHandler;
import lib.ISimulation;

public class Demo {
	
	private static final Hashtable<String, String> options = new Hashtable<String, String>();
	
	public static void main(String...args) {
		Demo.configureOpts();
		Demo.parseArgs(args);
		
		int width 		= Integer.parseInt(options.get("-d"));
		int height 		= Integer.parseInt(options.get("-d"));
		
		double left 	= Double.parseDouble(options.get("-l"));
		double right 	= Double.parseDouble(options.get("-r"));
		double top 		= Double.parseDouble(options.get("-t"));
		double bottom 	= Double.parseDouble(options.get("-b"));
		
		ISimulation simulation = new Tpdahp(new DemoHandler(width, height), width, height, left, right,top, bottom);
		
		System.out.format("Starting %d x %d plate simulation...\n", width, height);
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
		
		String curr = "", currOpt = "";
		
		if (args.length == 0) throw new IllegalArgumentException("No arguments provided to Demo");
		
		for (int i = 0; i < args.length; i++) {
			
			curr = args[i];
	        switch (curr.charAt(0)) {
	        
	        	case '-':
		            if (!"".equals(currOpt)) throw new IllegalArgumentException("No value for argument '" + currOpt + "'");
		            if (args.length == (i + 1))  throw new IllegalArgumentException("Expected value after argument '" + curr + "'");
		            if (!options.containsKey(curr)) throw new IllegalArgumentException("Invalid argument '" + curr + "'");
		            currOpt = curr;
		            break;
		        default:
		        	if ("".equals(currOpt)) throw new IllegalArgumentException("Expected argument for value '" + curr + "'");
		            options.put(currOpt, curr);
		            currOpt = "";
		            break;
	        }
		}	
	}
}