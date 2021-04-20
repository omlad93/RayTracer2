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
		int j;
		Vec3D unitVec3D, p;
		double d;
		
		for (int k=-3 ; k<=6 ; k++) {
			if (k != 0) {
				unitVec3D = new Vec3D(k,"XYZ"); //normal
				p = center.add(unitVec3D.multiply(len/2)); //point on plane
				d = p.dotProduct(unitVec3D); // d = ax+by+cz
				j = (k>0) ? Math.abs(k)+3 : Math.abs(k) ;
				planes.add(new Plane(unitVec3D,d,mat,j));
			}
		}
		

	}
	
	@Override
	public Vec3D getNormalVec3D(Vec3D point) {
		for (Plane plane : planes) {
			if (plane.normal.dotProduct(point) == plane.offset) {
				return plane.normal;
			}
		}
		return null;
	}

	@Override
	public Intersection intersect(Vec3D ray, Vec3D origin) {
		Intersection inter=null;
		for (Plane plane : planes) {
			inter = Intersection.getFirst(inter, plane.intersect(ray, origin));	
		}
		return inter;
	}
	
}
