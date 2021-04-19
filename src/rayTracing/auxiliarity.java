package rayTracing;
import dataTypes.Vec;
import dataTypes.Material;
import dataTypes.Scene;
import dataTypes.Sphere;
import dataTypes.Box;
import dataTypes.Plane;
import dataTypes.Light;
import java.util.LinkedList;



class auxiliary {
	
	
	public static double dbl(String d) {
		return Double.parseDouble(d);
	}
	
	public static boolean bool(String b) {
		return Boolean.parseBoolean(b);
	}
	
	public static int integer(String n) {
		return Integer.parseInt(n);
	}
	
	public static void camParse(String[] p, Scene scene) {
		Vec position = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		Vec lookat = new Vec(dbl(p[3]), dbl(p[4]),dbl(p[5]),"XYZ");
		Vec up = new Vec(dbl(p[6]), dbl(p[7]),dbl(p[8]),"XYZ");
		double distance = dbl(p[9]);
		double width = dbl(p[10]);
		boolean fish = p.length > 11 && bool(p[11]);
		double k = p.length == 13 ? dbl(p[12]) : 0.5; 	
		scene.updateCamera(position, lookat, up, distance, width, k, fish);	
	}
	
	public static void setParse(String[] p, Scene scene) {
		Vec background = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]), "RGB");
		int n = integer(p[3]);
		int rec = integer(p[4]);
		scene.updateSettings(background, n, rec);	
	}
	
	public static Material parseMaterial(String[] p, int idx) {
		Vec diffuse  = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]),"RGB");
		Vec specular = new Vec(dbl(p[3]), dbl(p[4]), dbl(p[5]),"RGB");
		Vec reflect  = new Vec(dbl(p[6]), dbl(p[7]), dbl(p[8]),"RGB");
		double phong = dbl(p[9]);
		double trnasparency = dbl(p[10]);
		return new Material(idx, diffuse, specular, reflect, phong, trnasparency);
		
		
	}
	
	public static Sphere parseSphere(String[] p, int Idx, LinkedList<Material> matList) {
		Vec center = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		double radius = dbl(p[3]);
		int mat_idx = integer(p[4])-1;
		Material mat = matList.get(mat_idx);
		return new Sphere(center, radius, mat, Idx);
	}

	public static Plane parsePlane(String[] p, int Idx, LinkedList<Material> matList) {
		Vec normal = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		double offset = dbl(p[3]);
		int mat_idx = integer(p[4])-1;
		Material mat = matList.get(mat_idx);
		return new Plane(normal, offset, mat, mat_idx);
	}
	
	public static Box parseBox(String[] p, int Idx, LinkedList<Material> matList) {
		Vec center = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		double edge = dbl(p[3]);
		int mat_idx = integer(p[4])-1;
		Material mat = matList.get(mat_idx);
		return new Box(center, edge, mat, mat_idx);
	}
	
	public static Light parseLight(String[] p) {
		Vec position = new Vec(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		Vec color = new Vec(dbl(p[3]), dbl(p[4]),dbl(p[5]),"RGB");
		double specularIntens = dbl(p[6]);
		double shadowIntens = dbl(p[7]);
		double r = dbl(p[8]);

		return new Light(position, color, specularIntens, shadowIntens, r);
	}

	
	
	
	
}
