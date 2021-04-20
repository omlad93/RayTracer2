package dataTypes;

public abstract class Surface {
	public Material material;
	protected String name;
	protected int idx; 
	
	public Material getMaterial() {
		return this.material;
	}
	
	public abstract Vec getNormalVec(Vec point);
	
	public abstract Intersection intersect(Vec ray, Vec origin);
	
	public Vec specularColor() {
		return material.specular;
	}
	
	public Vec diffuseColor() {
		return material.diffuse;
	}
	
	public Vec reflectionColor() {
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