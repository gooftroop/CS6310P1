package util;

import java.util.Observable;

import lib.IResultsHandler;
import lib.UpdatePacket;

public class GallhpHandler extends Observable implements IResultsHandler {
	
	public GallhpHandler () {
		super();
	}

	@Override
	public void display(double temp, int x, int y) {
		setChanged();
		notifyObservers(new UpdatePacket(temp, x ,y));
	}

	@Override
	public void report() {
		return;
	}

	@Override
	public void start() {
		return;
	}

	@Override
	public void stop() {
		return;
	}

	@Override
	public int getNumIterations() {
		return 0;
	}

	@Override
	public long getStartTime() {
		return 0;
	}

	@Override
	public long getEndTime() {
		return 0;
	}

	@Override
	public long getUsedMemory() {
		return 0;
	}

	@Override
	public void setNumIterations(int numIterations) {
		return;
	}
}
