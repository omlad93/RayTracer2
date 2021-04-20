package dataTypes;

public class Vec3D {
	private double v1;
	private double v2;
	private double v3;
	private String type;
	
	/*
	 * Constructor
	 * t might be and RGB or XYZ 
	 */
	public Vec3D(double va, double vb, double vc, String t) {
		v1 = va;
		v2 = vb;
		v3 = vc;
		type = t;	
	}
	
	protected Vec3D(int k, String t) {
		if (Math.abs(k) >3) {
			System.out.println("Unit Vector with wrong dim " + k);
			System.exit(-3);
		}
		
		if (Math.abs(k)==1) {
			v1 = k;
			v2 = 0;
			v3 = 0;
			type = t;
		}else if (Math.abs(k)==2) {
			v1 = 0;
			v2 = k;
			v3 = 0;
			type = t;
		}else if (Math.abs(k)==3) {
			v1 = 0;
			v2 = 0;
			v3 = k;
			type = t;
		}
	}
	
	
	
	/*
	 * Vector Arithmetics
	 */
	
	public Vec3D crossProduct(Vec3D other) {
		if (type != other.type) {
			System.out.println("CrossProduct between " + type + " and " + other.type);
			System.exit(-3);
		}
		double t1 = v2*other.v3 - v3*other.v2;
		double t2 = v1*other.v3 - v3*other.v1;
		double t3 = v1*other.v2 - v2*other.v1;
		return new Vec3D(t1,t2,t3,type);
		
	}
	
	
	public double dotProduct(Vec3D other) {
		if (type != other.type) {
			System.out.println("DotProduct between " + type + " and " + other.type);
			System.exit(-3);
		}
		return (v1*other.v1 + v2*other.v2 +v3*other.v3);
	}
	
	public Vec3D multiply(Vec3D other) {
		if (type != other.type) {
			System.out.println("Multiply between " + type + " and " + other.type);
			System.exit(-3);
		}
		return new Vec3D(v1*other.v1, v2*other.v2,v3*other.v3,type);
	}
	
	public Vec3D multiply(double scalar) {
		return new Vec3D(v1*scalar, v2*scalar,v3*scalar,type);
	}
	
	public Vec3D multiply(int scalar) {
		return new Vec3D(v1*scalar, v2*scalar,v3*scalar,type);
	}
	
	public Vec3D add(Vec3D other) {
		if (type != other.type) {
			System.out.println("Addition between " + type + " and " + other.type);
			System.exit(-3);
		}
		return new Vec3D(v1+other.v1, v2+other.v2,v3+other.v3,type);
	}
	
	public Vec3D subtract(Vec3D other) {
		if (type != other.type) {
			System.out.println("Addition between " + type + " and " + other.type);
			System.exit(-3);
		}
		return new Vec3D(v1-other.v1, v2-other.v2,v3-other.v3,type);
	}
	
	public double getNorm() {
		return Math.sqrt(this.dotProduct(this));
	}
	
	public Vec3D normalized() {
		return this.multiply(1/this.getNorm());
	}
	
	public double squared() {
		return (v1*v1)+(v2*v2)+(v3*v3);
	}
	
	
	public String getType() {
		return type;
	}
	
	public String str() {
		String prefix = type;
		String values = "(" + v1 + v2 + v3 + ")";
		return new String(prefix + values);
	}
	
	public static Vec3D createDistVec(Vec3D source, Vec3D dist) {
		return dist.subtract(source);
	}
	public static Vec3D perfromSteps(Vec3D startPoint ,int stepsRight, int stepsUp) {
		return null;
	}

}
