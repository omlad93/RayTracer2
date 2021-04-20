package dataTypes;

public abstract class Surface {
	public Material material;
	protected String name;
	protected int idx; 
	
	public Material getMaterial() {
		return this.material;
	}
	
	public abstract Vec3D getNormalVec3D(Vec3D point);
	
	public abstract Intersection intersect(Vec3D ray, Vec3D origin);
	
	public Vec3D specularColor() {
		return material.specular;
	}
	
	public Vec3D diffuseColor() {
		return material.diffuse;
	}
	
	public Vec3D reflectionColor() {
		return material.reflection;
	}
	
	public double phong() {
		return material.phong;
	}
	
	public double Transparency() {
		return material.Transparency;
	}
	
	public int comapreTo(Surface otherSurface) {
		return Integer.compare(idx, otherSurface.idx);
	}
	
	
}