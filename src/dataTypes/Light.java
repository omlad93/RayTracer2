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
	
	public Vec3D getPosition() {
		return this.position;
	}
	
	public Vec3D getColor() {
		return color;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Vec3D[] findPerpendicularPlane(Vec3D otherLightRay) {
		double d = this.position.dotProduct(otherLightRay);
		double x = otherLightRay.getV1();
		double y = otherLightRay.getV2();
		double z = otherLightRay.getV3();
		Vec3D u = new Vec3D(1.0, 0.0 , (double)((-x + d)/ z), "XYZ");
		Vec3D v = new Vec3D(0.0, 1.0 , (double)((-y + d)/ z), "XYZ");
		return new Vec3D[] {u.normalized(), v.normalized()};
		
	}

}
