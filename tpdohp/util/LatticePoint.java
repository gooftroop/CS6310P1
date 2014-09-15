package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class LatticePoint implements Cell<LatticePoint> {
	
	public static final double AVG = 4.0;
	
	public final int x, y;
	
	private boolean visited;
	private double currTemp, newTemp;
	
	private final boolean isEdge;
	
	private LatticePoint top = null, bottom = null, left = null, right = null;
	
	public LatticePoint(double temp, boolean isEdge, int x, int y) {
		
		if (temp > Double.MAX_VALUE) throw new IllegalArgumentException("Invalid temp provided");
		if (x > Integer.MAX_VALUE || x < Integer.MIN_VALUE) throw new IllegalArgumentException("Invalid 'x' provided");
		if (y > Integer.MAX_VALUE || y < Integer.MIN_VALUE) throw new IllegalArgumentException("Invalid 'y' provided");
		
		this.x = x;
		this.y = y;
		
		this.setTemp(temp);
		this.visited = false;
		this.isEdge = isEdge;
	}
	
	public LatticePoint(LatticePoint top, LatticePoint bottom, LatticePoint left, LatticePoint right, double temp, boolean isEdge, int x, int y) {
		
		this(temp, isEdge, x, y);
		
		this.setTop(top);
		this.setBottom(bottom);
		this.setLeft(left);
		this.setRight(right);
	}
	
	@Override
	public void setTop(LatticePoint top) {
		
		if (top == null) return;
		this.top = top;
	}
	
	@Override
	public LatticePoint getTop() {
		return this.top;
	}
	
	@Override
	public void setBottom(LatticePoint bottom) {
		
		if (bottom == null) return;
		this.bottom = bottom;
	}
	
	@Override
	public LatticePoint getBottom() {
		return this.bottom;
	}
	
	@Override
	public void setRight(LatticePoint right) {
		
		if (right == null) return;
		this.right = right;
	}
	
	@Override
	public LatticePoint getRight() {
		return this.right;
	}
	
	@Override
	public void setLeft(LatticePoint left) {
		
		if (left == null) return;
		this.left = left;
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
	public double calculateTemp() {
		this.newTemp = (this.top.getTemp() + this.bottom.getTemp() + this.right.getTemp() + this.left.getTemp()) / AVG;
		return this.newTemp - this.currTemp;
	}
	
	@Override
	public void swapTemp() {
		this.currTemp = this.newTemp;
		this.newTemp = 0;
	}
	
	@Override
	public void visited(boolean visited) {
		this.visited = visited;
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