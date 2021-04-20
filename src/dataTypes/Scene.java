package dataTypes;

import java.util.LinkedList;



public class Scene {
	String name;
	
	// objects & lights
	public LinkedList<Material> materials = new LinkedList<Material>();
	public LinkedList<Surface> shapes = new LinkedList<Surface>();
	public LinkedList<Light> lights = new LinkedList<Light>();
	
	// screen parameters
	protected Vec up = null;     // Y axis
	protected Vec right= null; 	 // X axis 
	protected Vec towards= null; // Z axis
	protected double screen_dist=0,pixelSize=0, width=0, hight=0;
	protected Vec dRight=null,dUp=null;
	protected int xPixels=0, yPixels=0;
	protected Vec screenCenter=null, bottomLeftCorner=null ,bottomLeftPixel=null;
	
	//General Settings
	protected Vec background = null;
	protected int shadowN = 0;
	protected int recursion = 0;
	
	
	//camera eye parameters
	protected Vec camPosition=null;
	protected double focus=0;
	protected boolean fisheye=false;
	
	
	public Scene() {
	}
	
	public void updateCamera(Vec position, Vec lookat, Vec up_vec, double dist,
			double screen_width, double k, boolean fish) {
		
		towards = lookat.normalized();
		up = up_vec.multiply(-1).normalized();
		right = lookat.crossProduct(up).normalized();
		camPosition = position;
		fisheye = fish;
		focus = k;
		width = screen_width;
		screen_dist = dist;		
	}
	
	public void updateSettings(Vec bkgrnd, int n, int rec) {
		recursion = 0;
		shadowN=n;
		background = bkgrnd;
		
	}
	
	public void setUp(int xp, int yp, String filename) {
		name = filename;
		xPixels = xp;
		yPixels = yp;
		pixelSize = width/xp;
		hight = pixelSize*yPixels;
		dRight = right.multiply(pixelSize);
		dUp = up.multiply(pixelSize);
		screenCenter = towards.multiply(screen_dist);
		bottomLeftCorner = screenCenter.subtract(right.multiply(width).add(up.multiply(hight)).multiply(0.5));
		bottomLeftPixel = bottomLeftCorner.add(dRight.multiply(0.5).add(dUp.multiply(0.5)));
	
	}

	public Vec findPixleCenter(int stepsRight, int stepsUp) {
		Vec delta = dRight.multiply(stepsRight).add(dUp.multiply(stepsUp));
		return bottomLeftPixel.add(delta);
	}
	
	public Vec cameraPos() {
		return camPosition;
	}
	
	public Intersection firstIntersectionOfRay(Vec origin, Vec direction, Surface ignoreMe) {
		Intersection inter1 = null;
		for (Surface shape : shapes) {
			if ((ignoreMe != null) && (ignoreMe == shape)) {
				continue;
			}
				Intersection inter2 = shape.intersect(direction, origin);
				inter1 = Intersection.getFirst(inter1, inter2);
		}	
		return inter1;
	}
	

}
