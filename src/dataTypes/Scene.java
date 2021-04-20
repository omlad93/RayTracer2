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
	
	public void updateCamera(Vec3D position, Vec3D lookat, Vec3D up_vec, double dist,
			double screen_width, double k, boolean fish) {
		
		towards = lookat.normalized();
		up = up_vec.normalized();
		right = (towards.crossProduct(up)).normalized();
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
		screenCenter = camPosition.add(towards.multiply(screen_dist));
		bottomLeftCorner = screenCenter.subtract(right.multiply(width).add(up.multiply(hight)).multiply(0.5));
		bottomLeftPixel = bottomLeftCorner.add(dRight.multiply(0.5).add(dUp.multiply(0.5)));

	}

	public Vec3D findPixleCenter(int stepsRight, int stepsUp) {
		Vec3D deltaR = dRight.multiply(stepsRight);
		Vec3D deltaU = dUp.multiply(stepsUp);
		Vec3D pixel = bottomLeftPixel.add(deltaR.add(deltaU));
		if (fisheye) {
			pixel = getFishPix(pixel);
		}
		return pixel;
	}
	
	public Vec3D cameraPos() {
		return camPosition;
	}
	
	public int getShadowN() {
		return shadowN;
	}
	
	public int getRecusion() {
		return recursion;
	}
	
	public Vec3D getBackground() {
		return background;
	}
	
	public Intersection firstIntersectionOfRay(Vec3D origin, Vec3D direction, Surface ignoreMe) {
		Intersection first = null;
		for (Surface shape : shapes) {
			if ((ignoreMe != null) && (ignoreMe == shape)) {
				continue;
			}
				Intersection inter2 = shape.intersect(origin, direction);
				first = Intersection.getFirst(first, inter2);
		}	
		return first;
	}
	
	/* get phi - angle with Right Axis */
	public double getPhi(Vec3D pix) {
		Vec3D delta = pix.subtract(screenCenter);
		double phi = Math.atan(delta.getV2()/delta.getV1());
		return phi;
	}
	
	/* get theta - angle with Towards axis */
	public double calculateTheta(double Xif) {
		double theta;
		if ((focus < 0) && (focus >= -1)) {
			theta = (1/focus)*Math.asin(focus*Xif/screen_dist);
		}
		else if (focus==0) {
			theta = Xif/screen_dist;
		}
		else{
			theta =  (1/focus)*Math.atan(focus*Xif/screen_dist);
		}
		if (theta <0) {
			theta += 2*Math.PI;
		}
		return theta;
	}
	
	/* get distance of pixel on the screen plane from screen center */
	public double getRadiusOnScreen(double theta) {
		return screen_dist * Math.cos(theta);
	}
	

	public Vec3D getFishPix(Vec3D pix) {
		
		double Xif = pix.subtract(screenCenter).getNorm();
		double theta = calculateTheta(Xif);
		if (theta > Math.PI/2) {
			return null;
		}
		
		double Xip = getRadiusOnScreen(theta);
//		double phi = getPhi(pix);
		boolean rightOverFlow, upOverFlow;
//		Vec3D rightStep = right.multiply(Xip*Math.cos(phi));
//		Vec3D step = up.multiply(Xip*Math.sin(phi)).add(rightStep);
		
		Vec3D newPix = pix.subtract(screenCenter).normalized().multiply(Xip);
//		rightOverFlow = right.dotProduct(step) > (width/2);
//		upOverFlow = up.dotProduct(step) > (hight/2);
//		if (upOverFlow || rightOverFlow) {
//			return null;
//		}

		return newPix;

	}

}
