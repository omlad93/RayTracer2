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
		
		for (int k=-3 ; k<=6 ; k++) {
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

	@Override
	public Intersection intersect(Vec3D origin, Vec3D ray) {
		Intersection inter=null;
		for (Plane plane : planes) {
			inter = Intersection.getFirst(inter, plane.intersect(origin, ray));	
		}
		return inter;
	}
	
}
