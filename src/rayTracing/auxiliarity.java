package rayTracing;
import dataTypes.Vec3D;
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
		Vec3D position = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		Vec3D lookatPoint = new Vec3D(dbl(p[3]), dbl(p[4]),dbl(p[5]),"XYZ");
		Vec3D lookat = Vec3D.createDistVec(position, lookatPoint);
		Vec3D up = new Vec3D(dbl(p[6]), dbl(p[7]),dbl(p[8]),"XYZ");
		double distance = dbl(p[9]);
		double width = dbl(p[10]);
		boolean fish = p.length > 11 && bool(p[11]);
		double k = p.length == 13 ? dbl(p[12]) : 0.5; 	
		scene.updateCamera(position, lookat, up, distance, width, k, fish);	
	}
	
	public static void setParse(String[] p, Scene scene) {
		Vec3D background = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]), "RGB");
		int n = integer(p[3]);
		int rec = integer(p[4]);
		scene.updateSettings(background, n, rec);	
	}
	
	public static Material parseMaterial(String[] p, int idx) {
		Vec3D diffuse  = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]),"RGB");
		Vec3D specular = new Vec3D(dbl(p[3]), dbl(p[4]), dbl(p[5]),"RGB");
		Vec3D reflect  = new Vec3D(dbl(p[6]), dbl(p[7]), dbl(p[8]),"RGB");
		double phong = dbl(p[9]);
		double trnasparency = dbl(p[10]);
		return new Material(idx, diffuse, specular, reflect, phong, trnasparency);
		
		
	}
	
	public static Sphere parseSphere(String[] p, int Idx, LinkedList<Material> matList) {
		Vec3D center = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		double radius = dbl(p[3]);
		int mat_idx = integer(p[4])-1;
		Material mat = matList.get(mat_idx);
		return new Sphere(center, radius, mat, Idx);
	}

	public static Plane parsePlane(String[] p, int Idx, LinkedList<Material> matList) {
		Vec3D normal = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		double offset = dbl(p[3]);
		int mat_idx = integer(p[4])-1;
		Material mat = matList.get(mat_idx);
		return new Plane(normal, offset, mat, mat_idx);
	}
	
	public static Box parseBox(String[] p, int Idx, LinkedList<Material> matList) {
		Vec3D center = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		double edge = dbl(p[3]);
		int mat_idx = integer(p[4])-1;
		Material mat = matList.get(mat_idx);
		return new Box(center, edge, mat, mat_idx);
	}
	
	public static Light parseLight(String[] p) {
		Vec3D position = new Vec3D(dbl(p[0]), dbl(p[1]), dbl(p[2]), "XYZ");
		Vec3D color = new Vec3D(dbl(p[3]), dbl(p[4]),dbl(p[5]),"RGB");
		double specularIntens = dbl(p[6]);
		double shadowIntens = dbl(p[7]);
		double r = dbl(p[8]);

		return new Light(position, color, specularIntens, shadowIntens, r);
	}

	public static void storeColor(byte[] png2be, Vec3D rgb, int idx ) {
		
		png2be[idx]  = (byte) (255*rgb.getV1());
		png2be[idx+1]= (byte) (255*rgb.getV2());
		png2be[idx+2]= (byte) (255*rgb.getV3());
		
		
	}
	
}
