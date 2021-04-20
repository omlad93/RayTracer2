package dataTypes;

public class Sphere extends Surface{
	
	protected Vec center;
	protected double radius;
	
	
	public Sphere(Vec position, double r, Material mat, int idx) {
		center = position;
		radius = r;
		material = mat;
		name = "Sphere(" + idx + ")";
		this.idx = idx;
	}
	
	@Override
	public Vec getNormalVec(Vec point) {
		Vec r = Vec.createDistVec(point, center);
		return r.normalized();
	}
	
	@Override
	public Intersection intersect(Vec ray, Vec origin) {
		double t1,t2;
		Intersection inter;
		Vec point;
		Vec helper = origin.subtract(center);
		double a = ray.squared();
		double b = 2*ray.dotProduct(helper);
		double c = helper.squared() - Math.pow(radius,2); 
		double d = Math.pow(b, 2)-4*a*c;
		
		if (d<0) {
			return null;
		} else if (d==0) {
			t1 = -b /(2*a);
			point = origin.add(ray.multiply(t1));
			inter = new Intersection(point,this,t1);
			return inter;
		}else {
			t1 = (Math.sqrt(d)-b)/(2*a);
			t2 = -(Math.sqrt(d)+b)/(2*a);
			if ((t1<t2) && t1>0) {
				point = origin.add(ray.multiply(t1));
				inter = new Intersection(point,this,t1);
				return inter;
			}else if (t2>0) {
				point = origin.add(ray.multiply(t1));
				inter = new Intersection(point,this,t2);
				return inter;
			}else {
				return null;
			}	
		}
	}



	
	


	

}
