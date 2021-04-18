package dataTypes;

public class Intersection {
	private Vec point;
	private double t;
	private Surface shape;
	
	public Intersection(Vec intersectionPoint, Surface hitSurface, double length) {
		point = intersectionPoint;
		t = length;
		shape = hitSurface;
	}
	
	public boolean isValid() {
		return t>0;
	}
	
	public Vec getPoint() {
		if(isValid()) {
			return point;
		}else {
			return null;
		}
	}
	
	private double getT() {
		return t;
	}
	
	public Surface getShape() {
		return shape;
	}
	
	public static Intersection getFirst(Intersection a, Intersection b) {
		if (a == null) {
			if (b.isValid()) {
				return b;
			}else {
			return null;
			}
		}
		if (b == null) {
			if (a.isValid()) {
				return a;
			}else {
			return null;
			}
		}
		if ((a.getT() < b.getT()) && a.isValid() ) {
			return a;			
		}else if (b.isValid()) {
			return b;
		}else {
			return null;
		}
	}
}
