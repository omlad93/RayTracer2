package dataTypes;
import java.util.LinkedList;

public class Box extends Surface {
	protected LinkedList<Plane> planes = new LinkedList<Plane>();
	protected Vec3D center;
	protected double edgeLen;
	
	
	public Box(Vec3D position, double len,Material mat, int idx) {
		center = position;
		edgeLen = len;
		material = mat;
		name = "Box(" + idx + ")";
		this.idx = idx;
		
		int j;
		Vec3D unitVec, p;
		double d;
		
		for (int k=-3 ; k<=3 ; k++) {
			if (k != 0) {
				unitVec = new Vec3D(k,"XYZ"); //normal
				p = center.add(unitVec.multiply(len/2)); //point on plane
				d = p.dotProduct(unitVec); // d = ax+by+cz
				j = (k>0) ? Math.abs(k)+3 : Math.abs(k) ;
				planes.add(new Plane(unitVec,d,mat,j));
			}
		}
		

	}
	
	@Override
	public Vec3D getNormalVec(Vec3D point) {
		for (Plane plane : planes) {
			if (plane.normal.dotProduct(point) == plane.offset) {
				return plane.normal;
			}
		}
		return null;
	}
	
	public boolean inBox(Vec3D point) {
		Vec3D dist = point.subtract(center);
		if (Math.abs(dist.getV1()) > edgeLen/2) {
			return false;
		}
		else if (Math.abs(dist.getV2()) > edgeLen/2) {
			return false;
		}
		else if (Math.abs(dist.getV3()) > edgeLen/2) {
			return false;
		}
		return true;
		
		
	}
	
	@Override
	public Intersection intersect(Vec3D origin, Vec3D ray) {
		Intersection inter=null, real=null;
		for (Plane plane : planes) {
			inter = Intersection.getFirst(inter, plane.intersect(origin, ray));
			if (inBox(inter.getPoint())) {
				 real = inter;
			}
		}
		return real;
	}
	
}
