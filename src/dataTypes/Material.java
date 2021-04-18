package dataTypes;

public class Material {
	public int idx;
	public Vec diffuse;
	public Vec specular;
	public Vec reflection;
	public double phong;
	public double Transparency;
	public String name;
	
	public Material(int i, Vec diff, Vec spec, Vec reflect, double phong_coeff, double trans) {
		idx = i;
		diffuse = diff;
		specular = spec;
		reflection = reflect;
		phong = phong_coeff;
		Transparency = trans;
		name = "Material(" + i + ")";
	}
}
