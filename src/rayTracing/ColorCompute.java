package rayTracing;

import dataTypes.Vec3D;
import dataTypes.Vec2D;
import dataTypes.Intersection;
import dataTypes.Light;
import dataTypes.Grid;
import dataTypes.Scene;
import dataTypes.Surface;
import java.util.LinkedList;

import java.util.Random;

public class ColorCompute {
	
	public static Vec3D getColor(Intersection intersection, Vec3D cameraRay, Scene scene) {	
		Vec3D color = getSimpleColor(intersection, cameraRay, scene);
		color = calcShapeTransparency();
		return color.add(calcShapeReflection());
		
		return new Vec3D(0,0,0,"RGB");
	}
	
	public static Vec3D getSimpleColor(Intersection intersection, Vec3D cameraRay, Scene scene) {
		if(intersection == null) {
			return scene.getBackground();
		}
		Vec3D color = new Vec3D(0,0,0,"RGB");
		Vec3D point = intersection.getPoint();
		Surface shape = intersection.getShape();
		LinkedList<Light> lights = scene.lights;
		LinkedList<Surface> shapes = scene.shapes;
		Vec3D background = scene.getBackground();
		int shadowN = scene.getShadowN();
		
		for(Light light : lights) {
			double i = getLightIntensity(intersection, scene, light);
			Vec3D diffuseColor = getDiffuseColor(intersection, scene, i);		
			Vec3D specularColor = getSpecularColor(intersection, cameraRay, scene, i);
			color = color.add(diffuseColor);
			color = color.add(specularColor);
		}
		
		return color;
	}
	
	public static double getLightIntensity(Intersection intersection, Scene scene, Light light) {
		Vec3D point = intersection.getPoint();
		Surface shape = intersection.getShape();
		LinkedList<Surface> shapes = scene.shapes;
		
		Vec3D currentLightRay = Vec3D.createDistVec(light.getPosition(), point);
		currentLightRay = currentLightRay.normalized();
		Vec3D[] uv = light.findPerpendicularPlane(currentLightRay);
		int n = scene.getShadowN();
		Grid grid = new Grid(light, n, uv);
		int numOfHits = calNumberOfHits(grid, point, shape, shapes);
		double percentage = (numOfHits / (n * n));
		double lightIntensity = (1 - light.ShadowIntensity()) + light.ShadowIntensity() * percentage;
		return lightIntensity;
		
	}
	
	public static int calNumberOfHits(Grid grid, Vec3D point, Surface shape, LinkedList<Surface> shapes) {
		Random rand = new Random();
		double rand_dub1;
		double rand_dub2;
		Vec3D currentCellRay;
		Vec3D currentRaySourcePosition;
		int numOfHits = 0;
		int n = grid.getNumberOfCells();
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				Vec3D currentCellLeftPoint = grid.getbottomLeftPoint().add(grid.du().multiply(i).add(grid.dv().multiply(j)));
				rand_dub1 = rand.nextDouble();
				rand_dub2 = rand.nextDouble();
				Vec3D temp = new Vec3D(rand_dub1, rand_dub2, 0.0, "XYZ");
				currentRaySourcePosition = currentCellLeftPoint.add(temp);
				currentCellRay = Vec3D.createDistVec(currentRaySourcePosition, point);
				currentCellRay = currentCellRay.normalized();
				
				
			}
		}
		
	}

}
