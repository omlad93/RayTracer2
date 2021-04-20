package dataTypes;

public class Light {
	
	protected Vec position;
	protected Vec color;
	protected double specularIntensity;
	protected double shadowIntensity;
	protected double radius;
	
	
	public Light(Vec pos, Vec col, double spec, double shadow, double r) {
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
	
	public Vec getNormalizedLightRay(Vec point) {
		return Vec.createDistVec(position, point).normalized();
	}

}
