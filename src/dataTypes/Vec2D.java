package dataTypes;

public class Vec2D {
	private double v1;
	private double v2;
	
	/*
	 * Constructor
	 */
	public Vec2D(double va, double vb) {
		v1 = va;
		v2 = vb;	
	}
	
	protected Vec2D(int k) {
		if (Math.abs(k) >2) {
			System.out.println("Unit Vector with wrong dim " + k);
			System.exit(-3);
		}
		
		if (Math.abs(k)==1) {
			v1 = k;
			v2 = 0;
		}else if (Math.abs(k)==2) {
			v1 = 0;
			v2 = k;
		}else if (Math.abs(k)==3) {
			v1 = 0;
			v2 = 0;
		}
	}
	
		
	/*
	 * Vector Arithmetics
	 */
	
	
	public double dotProduct(Vec2D other) {

		return (v1*other.v1 + v2*other.v2);
	}
	
	public Vec2D multiply(Vec2D other) {
		return new Vec2D(v1*other.v1, v2*other.v2);
	}
	
	public Vec2D multiply(double scalar) {
		return new Vec2D(v1*scalar, v2*scalar);
	}
	
	public Vec2D multiply(int scalar) {
		return new Vec2D(v1*scalar, v2*scalar);
	}
	
	public Vec2D add(Vec2D other) {
		return new Vec2D(v1+other.v1, v2+other.v2);
	}
	
	public Vec2D subtract(Vec2D other) {
		return new Vec2D(v1-other.v1, v2-other.v2);
	}
	
	public double getNorm() {
		return Math.sqrt(this.dotProduct(this));
	}
	
	public Vec2D normalized() {
		return this.multiply(1/this.getNorm());
	}
	
	public double squared() {
		return (v1*v1)+(v2*v2);
	}
	
	
	public String str() {
		String values = "(" + v1 + "," + v2 + ")";
		return new String(values);
	}
	
	public double getV1() {
		return v1;
	}
	
	public double getV2() {
		return v2;
	}
	
	public static Vec2D createDistVec(Vec2D source, Vec2D dist) {
		return dist.subtract(source);
	}
	public static Vec2D perfromSteps(Vec3D startPoint ,int stepsRight, int stepsUp) {
		return null;
	}

}
