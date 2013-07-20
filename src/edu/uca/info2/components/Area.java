package edu.uca.info2.components;

public class Area {
	

	private long centerNodeId;
	
	private int radius;
	
	public Area(long centerNodeId) {

		setCenterNodeId(centerNodeId);
		setRadius(1);
	}

	public long getCenterNodeId() {
		return centerNodeId;
	}

	public void setCenterNodeId(long centerNodeId) {
		this.centerNodeId = centerNodeId;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	
	public String toString() {
		
		return " Area: center: " + getCenterNodeId() + " radius: " + getRadius(); 
	}
}
