package dataTypes;




/*
 * a 3d vector which can be RGB / XYZ
 */
public class Vec3D {
	private double v1;
	private double v2;
	private double v3;
	private String type;
	
	
	private static double zero = (double)0;
	private static double max = (double)1;
	public static Vec3D black = new Vec3D(zero,zero,zero, "RGB");
	public static Vec3D white = new Vec3D(max,max,max,"RGB");

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
		type = t;
		
		int sign = (k>0)? 1 : -1;
		if (Math.abs(k)==1) {
			
			v1 = sign;
			v2 = 0;
			v3 = 0;

		}else if (Math.abs(k)==2) {
			v1 = 0;
			v2 = sign;
			v3 = 0;

		}else if (Math.abs(k)==3) {
			v1 = 0;
			v2 = 0;
			v3 = sign;

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
		double t2 = v3*other.v1 - v1*other.v3;
		double t3 = v1*other.v2 - v2*other.v1;
		Vec3D cross= new Vec3D(t1,t2,t3,type);
		return cross;
		
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
		double norm = Math.sqrt(this.dotProduct(this));
		return norm;
	}
	
	public Vec3D normalized() {
		double factor = 1/getNorm();
		if (factor == Double.POSITIVE_INFINITY){
			return this;
		}
		Vec3D normalized = multiply(factor);
		return normalized;
	}
	
	

	public double squared() {
		return (v1*v1)+(v2*v2)+(v3*v3);
	}
	
	public String getType() {
		return type;
	}
	
	public String str() {
		String prefix = type;
		String values = "(" + v1 + "," + v2+ "," + v3 + ")";
		return new String(prefix + values);
	}
	
	public double getV1() {
		return v1;
	}
	
	public double getV2() {
		return v2;
	}
	
	public double getV3() {
		return v3;
	}
	
	public boolean isBlack() {
		return v1 < 0.05 && v2 < 0.05 && v3 < 0.05;		
	}
	
	public boolean iswhite() {
		return v1 >= 0.9 && v2 >= 0.9 && v3 >= 0.9;		
	}
	
	public boolean outOfRange() {
		boolean t1 = v1<0 || v1 >1;
		boolean t2 = v2<0 || v2 >1;
		boolean t3 = v3<0 || v3 >1;
		return t1 || t2 || t3;		
	}
	
	public Vec3D clip() {
		double t1 = Math.max(Math.min(1,v1),0);
		double t2 = Math.max(Math.min(1,v2),0);
		double t3 = Math.max(Math.min(1,v3),0);
		return new Vec3D(t1,t2,t3,type);		
	}
	
	public static Vec3D createDistVec(Vec3D source, Vec3D dest) {
		return dest.subtract(source);
	}
	

	
	
	
	
}
