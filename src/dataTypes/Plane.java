package dataTypes;

public class Plane extends Surface {
	
	protected double offset;
	protected Vec3D normal;
	
	public Plane(Vec3D n, double D, Material mat, int idx) {
		normal = n;
		offset = D;
		name = "Plane(" + idx + ")";
		material = mat;
		this.idx = idx;
	}


	@Override
	public Vec3D getNormalVec(Vec3D point) {
		return normal.normalized();
	}

	@Override
	public Intersection intersect(Vec3D origin, Vec3D ray) {
		ray = ray.normalized();
		double dotProduct = ray.dotProduct(normal);
		Intersection inter = null;
		double t=0;
		Vec3D po=null;
		
		if (dotProduct == 0) {
			if (origin.dotProduct(normal) + offset == 0) {
				inter = new Intersection(origin, this, 0);
			}
		}else {
			po=normal.multiply(offset);
			t = (normal.dotProduct(po.subtract(origin)))/dotProduct;
			Vec3D p = origin.add(ray.multiply(t));
			inter = new Intersection(p, this, t);
		}
		
		return inter;
	}
	
	}
	

