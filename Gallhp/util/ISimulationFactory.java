package util;

import lib.ISimulation;

public interface ISimulationFactory {
	
	public ISimulation produceSimulation(SimulationSelection simulation);

}
