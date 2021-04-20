package dataTypes;

public class Scene {
	String name;
	
	// screen parameters
	protected Vec up = null;
	protected Vec right= null;
	protected Vec towards= null;
	protected double screen_dist=0,pixelSize=0, width=0,hight=0;
	protected Vec dRight=null,dUp=null;
	protected int xPixels=0, yPixels=0;
	protected Vec screenCenter=null, bottomLeftCorner=null ,bottomLeftPixel=null;
	
	//General Settings
	protected Vec background =null;
	protected int shadowN =0;
	protected int recursion=0;
	
	
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
	
	public Intersection Intersection(Vec origin, Vec direction, Surface ignoreMe ) {
		
		return null;
	}
	

}
