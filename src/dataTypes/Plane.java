package dataTypes;

public class Plane extends Surface {
	
	protected double offset;
	protected Vec normal;
	
	public Plane(Vec n, double D, Material mat, int idx) {
		normal = n;
		offset = D;
		name = "Plane(" + idx + ")";
		material = mat;
		this.idx = idx;
	}


	@Override
	public Vec getNormalVec(Vec point) {
		return normal;
	}

	@Override
	public Intersection intersect(Vec ray, Vec origin) {
		double dotProduct = ray.dotProduct(normal);
		Intersection inter = null;
		double t;
		Vec po;
		
		if (dotProduct == 0) {
			if (origin.dotProduct(normal) + offset == 0) {
				inter = new Intersection(origin, this, 0);
			}
		}else {
			po=normal.multiply(offset);
			t = normal.dotProduct(po.subtract(origin))/dotProduct;
			if (t>0) {
				po = origin.add(ray.multiply(t));
				inter = new Intersection(po, this,t);
			}
		}
		
		return inter;
	}
	
	}
	

