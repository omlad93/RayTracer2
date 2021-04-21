package dataTypes;

public class Sphere extends Surface{
	
	protected Vec3D center;
	protected double radius;
	
	
	public Sphere(Vec3D position, double r, Material mat, int idx) {
		center = position;
		radius = r;
		material = mat;
		name = "Sphere(" + idx + ")";
		this.idx = idx;
	}
	
	@Override
	public Vec3D getNormalVec(Vec3D point) {
		Vec3D r = Vec3D.createDistVec(center, point);
		return r.normalized();
	}
	
	@Override
	public Intersection intersect( Vec3D origin, Vec3D ray) {
		double t1,t2;
		Intersection inter = null;
		Vec3D point;
		Vec3D helper = origin.subtract(center);
		ray = ray.normalized();
		
		double a = ray.dotProduct(ray); //
		double b = 2*ray.dotProduct(helper);
		double c = helper.dotProduct(helper) - Math.pow(radius,2); 
		double d = Math.pow(b, 2) - 4*a*c;
		
		if (d<0) {
			
		}
		else if (d==0) {
			t1 = -(b / (2*a));
			point = origin.add(ray.multiply(t1));
			inter = new Intersection(point,this,t1);
	
		}
		else {
			double sq = Math.sqrt(d);
			t1 = (sq-b)/(2*a);
			t2 = -(sq+b)/(2*a);
			double[] t = (t1<t2) ? new double[]{t1,t2} : new double[]{t2,t1};
			
			if (t[0] > 0) {
				t1 =t[0];
			}
			else if (t2 > 0 ) {
				t1= t[1];
			}
			point = origin.add(ray.multiply(t1));
			inter = new Intersection(point,this,t1);
				
		}
		return inter;
	}
}
