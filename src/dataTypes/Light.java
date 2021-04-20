package dataTypes;

public class Light {
	
	protected Vec3D position;
	protected Vec3D color;
	protected double specularIntensity;
	protected double shadowIntensity;
	protected double radius;
	
	
	public Light(Vec3D pos, Vec3D col, double spec, double shadow, double r) {
		position = pos;
		color = col;
		specularIntensity = spec;
		shadowIntensity = shadow;
		radius = r;
		
	}
	
	public double ShadowIntensity() {
		return shadowIntensity;
	}
	
	public double specularIntensity() {
		return specularIntensity;
	}
	
	public Vec3D getNormalizedLightRay(Vec3D point) {
		return Vec3D.createDistVec(position, point).normalized();
	}

}
