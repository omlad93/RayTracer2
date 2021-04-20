package dataTypes;

public class Intersection {
	private Vec3D point;
	private double t;
	private Surface shape;
	
	public Intersection(Vec3D intersectionPoint, Surface hitSurface, double length) {
		point = intersectionPoint;
		t = length;
		shape = hitSurface;
	}
	
	public boolean isValid() {
		return t>0;
	}
	
	public Vec3D getPoint() {
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
		if ((a == null) && (b == null)) {
			return null;
		}
		else if (a == null) {
			if (b.isValid()) {
				return b;
			}else {
			return null;
			}
		}
		else if (b == null) {
			if (a.isValid()) {
				return a;
			}else {
			return null;
			}
		}
		
		else if (!a.isValid() && !b.isValid())
			return null;
		else if (a.isValid() &&  !b.isValid())
			return a;
		else if (b.isValid() && !a.isValid())
			return b;
		//both valid
		else if (a.getT() < b.getT())
			return a;
		return b;
		
	}

}
