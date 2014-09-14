package lib;

import java.util.Hashtable;

public abstract class SimulationRunner<T extends ISimulation> {
	
	private static final Hashtable<String, String> options = new Hashtable<String, String>();

	protected static void configureOpts() {
		options.put("-d", "");
		options.put("-l", "");
		options.put("-r", "");
		options.put("-t", "");
		options.put("-b", "");
	}
	
	protected static void parseArgs(String... args) {
		
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
