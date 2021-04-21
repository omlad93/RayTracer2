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
import java.math.*;

public class ColorCompute {
	
	public static Vec3D getColor(Intersection intersection, Vec3D cameraRay, Scene scene) {
		int recurtion = scene.getRecusion();
		Vec3D color = getSimpleColor(intersection, cameraRay, scene);
		Vec3D trans = calcShapeTransparency(intersection, cameraRay, scene, color);
		Vec3D reflect= calcShapeReflection(intersection, cameraRay, scene, recurtion);
		return trans.add(reflect).clip();
	}
	
	public static Vec3D getSimpleColor(Intersection intersection, Vec3D cameraRay, Scene scene) {
		if(intersection == null) {
			return scene.getBackground();
		}
		Vec3D color = Vec3D.black;
		Vec3D point = intersection.getPoint(); 
		LinkedList<Light> lights = scene.lights;
		
		for(Light light : lights) {
			Vec3D currentLightRay = Vec3D.createDistVec(point, light.getPosition());
			currentLightRay = currentLightRay.normalized();
			double i = getLightIntensity(intersection, scene, light, currentLightRay);
			Vec3D diffuseColor = getDiffuseColor(intersection, scene, i, light, currentLightRay);		
			Vec3D specularColor = getSpecularColor(intersection, scene, i, light, currentLightRay);
			color = color.add(diffuseColor);
			color = color.add(specularColor);
		}
		return color.clip();
	}
	
	public static Vec3D calcShapeReflection(Intersection intersection, Vec3D ray, Scene scene, int recurtion) {
		if((intersection == null) || (recurtion == 0)){
			return Vec3D.black;			
		}
		Vec3D point = intersection.getPoint();
		Surface shape = intersection.getShape();
		Vec3D normal = intersection.getShape().getNormalVec(point);
		Vec3D reflectionRay = ray.subtract(normal.multiply(2 * ray.dotProduct(normal)));		
		Intersection newIntersection = scene.firstIntersectionOfRay(point, reflectionRay, shape);
		Vec3D shapeColor = getSimpleColor(newIntersection, reflectionRay, scene);
		shapeColor = calcShapeTransparency(newIntersection, reflectionRay, scene, shapeColor);
		Vec3D reflectionColor =  calcShapeReflection(newIntersection, reflectionRay, scene, recurtion - 1);
		return shape.reflectionColor().multiply(reflectionColor.add(shapeColor));	
	}
	
	public static Vec3D calcShapeTransparency(Intersection intersection, Vec3D cameraRay, Scene scene, Vec3D color) {
		if(intersection == null) {
			return color;
		}
		Vec3D finalColor;
		Surface shape = intersection.getShape();
		if(shape.material.Transparency == 0.0) {
			return color;
		}
		Vec3D point = intersection.getPoint();
		double transparency = shape.material.Transparency;
		
		Intersection newIntersection = scene.firstIntersectionOfRay(point, cameraRay, shape);
		finalColor = color.multiply(1 - transparency).add(getSimpleColor(newIntersection, cameraRay, scene).multiply(transparency));
		while(newIntersection != null) {
			shape = newIntersection.getShape();
			point = newIntersection.getPoint();
			if(shape.material.Transparency == 0.0) {
				return finalColor;
			}
			else {
				transparency = shape.material.Transparency;
				newIntersection = scene.firstIntersectionOfRay(point, cameraRay, shape);
				finalColor = finalColor.multiply(1 - transparency).add(getSimpleColor(newIntersection, cameraRay, scene).multiply(transparency));
			}
		}
		return finalColor.clip();
	}
	
 	public static Vec3D calcR(Vec3D vec, Vec3D normal) {
		double dotProduct = 2 * vec.dotProduct(normal);
		Vec3D r = normal.multiply(dotProduct);
		return r.subtract(vec);
	}
	
	public static Vec3D getSpecularColor(Intersection intersection, Scene scene, double lightIntensity, Light light, Vec3D currentLightRay) {
		if(intersection == null) {
			return scene.getBackground();
		}
		double n = intersection.getShape().phong();
		Vec3D point = intersection.getPoint();
		Vec3D normal = intersection.getShape().getNormalVec(point);
		Vec3D lightReflection = calcR(currentLightRay, normal);
		double dotProduct = lightReflection.dotProduct(currentLightRay);
		dotProduct = (double) Math.pow(Math.max(dotProduct, 0.0), n) * lightIntensity * light.specularIntensity(); 
		Vec3D intensity = intersection.getShape().specularColor().multiply(light.getColor()).multiply(dotProduct);
		return intensity.clip();	
	}
	
	public static Vec3D getDiffuseColor(Intersection intersection, Scene scene, double lightIntensity, Light light, Vec3D currentLightRay) {
		if(intersection == null) {
			return scene.getBackground();
		}
		Vec3D point = intersection.getPoint();
		Surface shape = intersection.getShape();
		Vec3D normal = intersection.getShape().getNormalVec(point);
		//Vec3D intensity = light.getColor().multiply(lightIntensity);
		double dotProduct = currentLightRay.dotProduct(normal);
		dotProduct = Math.max((double)dotProduct, 0.0) * lightIntensity;
		return shape.diffuseColor().multiply(light.getColor()).multiply(dotProduct).clip();	
	}
	
	public static double getLightIntensity(Intersection intersection, Scene scene, Light light, Vec3D currentLightRay) {
		Vec3D point = intersection.getPoint();
		Surface shape = intersection.getShape();
		LinkedList<Surface> shapes = scene.shapes;

		Vec3D[] uv = light.findPerpendicularPlane(currentLightRay);
		int n = scene.getShadowN();
		Grid grid = new Grid(light, n, uv);
		if ((point == null) || (shape == null) || (grid == null)) {
			System.out.println("aha!");
		}
		int numOfHits = calNumberOfHits(grid, point, shape, shapes, scene);
		double percentage = (double) (numOfHits / (n * n));
		double lightIntensity = (1 - light.ShadowIntensity()) + light.ShadowIntensity() * percentage;
		return lightIntensity;
		
	}
	
	public static int calNumberOfHits(Grid grid, Vec3D point, Surface shape, LinkedList<Surface> shapes, Scene scene) {
		Random rand = new Random();
		Vec3D currentCellRay;
		int numOfHits = 0;
		int n = grid.getNumberOfCells();
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				Vec3D currentCellLeftPoint = grid.getbottomLeftPoint().add(grid.du().multiply(i).add(grid.dv().multiply(j)));
				Vec3D uAddition = grid.du().multiply(rand.nextDouble()*grid.getCellSize());
				Vec3D vAddition = grid.dv().multiply(rand.nextDouble()*grid.getCellSize());
				Vec3D currentRaySourcePosition = currentCellLeftPoint.add(uAddition).add(vAddition);
				currentCellRay = Vec3D.createDistVec(currentRaySourcePosition, point).normalized();
				
				Intersection intersection = scene.firstIntersectionOfRay(currentRaySourcePosition, currentCellRay, null);		
				if(intersection != null && intersection.getShape() == shape) {
					numOfHits++;
				}
			}
		}
		return numOfHits;
		
	}
	

}
