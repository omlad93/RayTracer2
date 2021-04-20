package dataTypes;

import java.util.LinkedList;



public class Scene {
	String name;
	
	// objects & lights
	public LinkedList<Material> materials = new LinkedList<Material>();
	public LinkedList<Surface> shapes = new LinkedList<Surface>();
	public LinkedList<Light> lights = new LinkedList<Light>();
	
	// screen parameters
	protected Vec3D up = null;     // Y axis
	protected Vec3D right= null; 	 // X axis 
	protected Vec3D towards= null; // Z axis
	protected double screen_dist=0,pixelSize=0, width=0, hight=0;
	protected Vec3D dRight=null,dUp=null;
	protected int xPixels=0, yPixels=0;
	protected Vec3D screenCenter=null, bottomLeftCorner=null ,bottomLeftPixel=null;
	
	//General Settings
	protected Vec3D background = null;
	protected int shadowN = 0;
	protected int recursion = 0;
	
	
	//camera eye parameters
	protected Vec3D camPosition=null;
	protected double focus=0;
	protected boolean fisheye=false;
	
	
	public Scene() {
	}
	
	public void updateCamera(Vec3D position, Vec3D lookat, Vec3D up_Vec3D, double dist,
			double screen_width, double k, boolean fish) {
		
		towards = lookat.normalized();
		up = up_Vec3D.multiply(-1).normalized();
		right = lookat.crossProduct(up).normalized();
		camPosition = position;
		fisheye = fish;
		focus = k;
		width = screen_width;
		screen_dist = dist;		
	}
	
	public void updateSettings(Vec3D bkgrnd, int n, int rec) {
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

	public Vec3D findPixleCenter(int stepsRight, int stepsUp) {
		Vec3D delta = dRight.multiply(stepsRight).add(dUp.multiply(stepsUp));
		return bottomLeftPixel.add(delta);
	}
	
	public Vec3D cameraPos() {
		return camPosition;
	}
	
	public Vec3D getBackground() {
		return background;
	}
	
	public int getShadowN() {
		return shadowN;
	}
	
	public int getRecurtion() {
		return recursion;
	}
	
	
	
	public Intersection firstIntersectionOfRay(Vec3D origin, Vec3D direction, Surface ignoreMe) {
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
