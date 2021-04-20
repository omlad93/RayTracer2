package dataTypes;

import dataTypes.Light;

public class Grid {
	protected Vec3D axisU;
	protected Vec3D axisV;
	protected int numOfCells;
	protected Vec3D center;
	protected double width;
	protected double cellSize;
	protected Vec3D du;
	protected Vec3D dv;
	protected Vec3D bottomLeftPoint;
	
	public Grid(Light light, int n, Vec3D[] uv) {
		this.axisU = uv[0];
		this.axisV = uv[1];
		this.numOfCells = n;
		this.center = light.getPosition();
		this.width = light.getRadius();
		this.cellSize = (double)(this.width/this.numOfCells);
		this.du = this.axisU.multiply(cellSize);
		this.dv = this.axisV.multiply(cellSize);
		Vec3D temp1 = this.axisU.multiply(width);
		Vec3D temp2 = this.axisV.multiply(width);
		this.bottomLeftPoint = 	this.center.subtract((temp1.add(temp2)).multiply(0.5));
	}
	
	public int getNumberOfCells() {
		return this.numOfCells;
	}
	
	public double getCellSize() {
		return this.cellSize;
	}
	
	public Vec3D getbottomLeftPoint() {
		return this.bottomLeftPoint;
	}
	
	public Vec3D du() {
		return this.du;
	}
	
	public Vec3D dv() {
		return this.dv;
	}
	
	

}
