package dataTypes;

public class Material {
	public int idx;
	public Vec3D diffuse;
	public Vec3D specular;
	public Vec3D reflection;
	public double phong;
	public double Transparency;
	public String name;
	
	public Material(int i, Vec3D diff, Vec3D spec, Vec3D reflect, double phong_coeff, double trans) {
		idx = i;
		diffuse = diff;
		specular = spec;
		reflection = reflect;
		phong = phong_coeff;
		Transparency = trans;
		name = "Material(" + i + ")";
	}
}
