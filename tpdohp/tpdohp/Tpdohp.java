package tpdohp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lib.IResultsHandler;
import lib.Simulation;
import util.Cell;

public final class Tpdohp extends Simulation {
	
	private LatticePoint root = null;
	
	private double leftTemp = 0, rightTemp = 0, topTemp = 0, bottomTemp = 0;
	
	private int height = 0, width = 0;
	
	public Tpdohp(IResultsHandler rh) {
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
	}

	@Override
	public void initializePlate() {
		
		LatticePoint latticeRoot = new LatticePoint(0, true);
		LatticePoint curr = latticeRoot, left = null, top = null;
		LatticePoint anchor = curr;
		
		double temp = 0;
		boolean edge = true;
		String debug = "";
		
		for (int h = 0; h < this.height; h++) {
			for (int w = 0; w < this.width; w++) {
				
				edge = (w == 0 || h == 0 || w == this.width - 1 || h == this.height - 1) ? true : false;
				
				if (h == 0) temp = this.topTemp;
				else if (w == 0) temp = this.leftTemp;
				else if (h == this.height - 1) temp = this.bottomTemp;
				else if (w == this.width - 1) temp = this.rightTemp;
				else temp = 0;
				
				curr.setRight(new LatticePoint(null, null, null, null, temp, edge));
				curr.setTop(top);
				curr.setLeft(left);
				
				debug = String.format("%5.2f ", curr.currTemp);
				System.out.print(debug);
				
				left = curr;
				if (top != null) top = top.right;
			}
			top = anchor;
			anchor = anchor.bottom;
			System.out.println();
		}
		
		this.root = latticeRoot.getBottom().getRight();
	}
	
	@Override
	public void run() {
		
		Queue<LatticePoint> bfs = new LinkedList<LatticePoint>();
		
		// TODO add update call
		
		float maxDeviation = 0;
		float deviation = 0;
		
		do {
			
			bfs.add(this.root);
			this.root.visited = true;
			
			if ((deviation = this.root.calculateTemp()) > maxDeviation) maxDeviation = deviation;
			
			while(!bfs.isEmpty()) {
				
				LatticePoint point = bfs.remove();
				LatticePoint child = null;
				Iterator<LatticePoint> itr = point.getChildren(false);
				while(itr.hasNext()) {
					child = itr.next();
					child.visited = true;
					
					if ((deviation = child.calculateTemp()) > maxDeviation) maxDeviation = deviation;
					
					bfs.add(child);
				}
				
				point.swapTemp();
			}
		
			this.root.swapTemp();
			clearNodes();
		
		} while (maxDeviation >= 0.01 && numIterations++ <= MAX_ITERATIONS);
	}
	
	private void clearNodes() {
		
		Queue<LatticePoint> bfs = new LinkedList<LatticePoint>();
		
		bfs.add(this.root);
		this.root.visited = false;
		while(!bfs.isEmpty()) {
			LatticePoint point = bfs.remove();
			LatticePoint child = null;
			
			Iterator<LatticePoint> itr = point.getChildren(true);
			while(itr.hasNext()) {
				child = itr.next();
				child.visited = false;
				bfs.add(child);
			}
		}
	}
	
	private final class LatticePoint implements Cell<LatticePoint> {
		
		public static final double AVG = 4.0;
		
		private LatticePoint top = null;
		private LatticePoint bottom = null;
		private LatticePoint left = null;
		private LatticePoint right = null;
		private boolean visited, isEdge;
		private double currTemp, newTemp;
		
		public LatticePoint(double temp, boolean isEdge) {
			
			this.setTemp(temp);
			this.visited = false;
			this.isEdge = isEdge;
		}
		
		public LatticePoint(LatticePoint top, LatticePoint bottom, LatticePoint left, LatticePoint right, double temp, boolean isEdge) {
			
			this(temp, isEdge);
			
			this.setTop(top);
			this.setBottom(bottom);
			this.setLeft(left);
			this.setRight(right);
		}
		
		@Override
		public void setTop(LatticePoint top) {
			
			if (top == null) return;
			
			this.top = top;
			top.bottom = this;
		}
		
		@Override
		public LatticePoint getTop() {
			return this.top;
		}
		
		@Override
		public void setBottom(LatticePoint bottom) {
			
			if (bottom == null) return;
			
			this.bottom = bottom;
			bottom.top = this;
		}
		
		@Override
		public LatticePoint getBottom() {
			return this.bottom;
		}
		
		@Override
		public void setRight(LatticePoint right) {
			
			if (right == null) return;
			
			this.right = right;
			right.left = this;
		}
		
		@Override
		public LatticePoint getRight() {
			return this.right;
		}
		
		@Override
		public void setLeft(LatticePoint left) {
			
			if (left == null) return;
			
			this.left = left;
			left.setRight(this);
		}
		
		@Override
		public LatticePoint getLeft() {
			return this.left;
		}
		
		@Override
		public double getTemp() {
			return new Double(this.currTemp);
		}
		
		@Override
		public void setTemp(double temp) {
			this.currTemp = temp;
		}
		
		@Override
		public float calculateTemp() {
			this.newTemp = (this.top.currTemp + this.bottom.currTemp + this.right.currTemp + this.left.currTemp) / AVG;
			return (float)this.newTemp - (float)this.currTemp;
		}
		
		@Override
		public void swapTemp() {
			this.currTemp = this.newTemp;
		}
		
		@Override
		public Iterator<LatticePoint> getChildren(boolean unvisited) {
			List<LatticePoint> ret = new ArrayList<LatticePoint>();
			
			if (!this.top.isEdge 	&& this.top.visited == unvisited) 		ret.add(this.top);
			if (!this.bottom.isEdge && this.bottom.visited == unvisited) 	ret.add(this.bottom);
			if (!this.left.isEdge 	&& this.left.visited == unvisited) 		ret.add(this.left);
			if (!this.right.isEdge 	&& this.right.visited == unvisited) 	ret.add(this.right);
			
			return ret.iterator();
		}
	}
}
