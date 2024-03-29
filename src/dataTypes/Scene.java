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
	protected double screen_dist=0, width=0, hight=0;
	protected double pixelW,pixelH;
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
		right = (up_vec.crossProduct(towards)).normalized();
		up = towards.crossProduct(right).normalized().multiply(-1);

		camPosition = position;
		fisheye = fish;
		focus = k;
		width = screen_width;
		screen_dist = dist;		
	}
	
	public void updateSettings(Vec3D bkgrnd, int n, int rec) {
		recursion = rec;
		shadowN=n;
		background = bkgrnd;
		
	}
	
	public void setUp(int xp, int yp, String filename) {
		name = filename;
		xPixels = xp;
		yPixels = yp;
		pixelW = width/xp;
		pixelH = pixelW * xp/yp;
		hight = pixelH*yPixels;
		dRight = right.multiply(pixelW);
		dUp = up.multiply(pixelH);
		screenCenter = camPosition.add(towards.multiply(screen_dist));
		bottomLeftCorner = screenCenter.subtract(right.multiply(width).add(up.multiply(hight)).multiply(0.5));
		bottomLeftPixel = bottomLeftCorner.add(dRight.multiply(0.5).add(dUp.multiply(0.5)));

	}

//	public Vec3D findPixleCenter(int stepsRight, int stepsUp) {
//		Vec3D deltaR = dRight.multiply(stepsRight);
//		Vec3D deltaU = dUp.multiply(stepsUp);
//		Vec3D pixel = bottomLeftPixel.add(deltaR.add(deltaU));
//		if (fisheye) {
//			pixel = getFishPix(pixel);
//		}
//		return pixel;
//	}
	
	public Vec3D findPixleCenter(int row, int col) {
		double dr = pixelW*(col - 0.5) - hight/2;
		double du = pixelH*(row - 0.5) - width/2;
		Vec3D deltaR = right.multiply(dr);
		Vec3D deltaU = up.multiply(du);
		Vec3D pixel = screenCenter.add(deltaR.add(deltaU));
		
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
				Intersection inter2 = shape.intersect(origin, direction.normalized());
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
		double factor = focus/screen_dist;
		if ((focus < 0) && (focus >= -1)) {
			theta = (1/focus)*Math.asin(factor*Xif);
		}
		else if (focus == 0) {
			theta = (Xif/screen_dist);
		}
		else{
			theta =  (1/focus)*Math.atan(factor*Xif);
		}
		
		if (theta < 0) {
			theta += 2*Math.PI;
		}
		return theta;
	}
	
	/* get distance of pixel on the screen plane from screen center */
	public double getXip(double theta) {
		return screen_dist * Math.tan(theta);
	}
	
	public boolean isFish() {
		return fisheye;
	}

	public Vec3D getFishPix(Vec3D pix) {
		
		double Xif = pix.subtract(screenCenter).getNorm(); // pixel dist from screen
		double theta = calculateTheta(Xif);				   // effective ray angle 
		if (theta > Math.PI/2) {
			return null;	// ray will break and go caput
		}	
		double Xip = getXip(theta);
		Vec3D newPix = screenCenter.add(Vec3D.createDistVec(screenCenter, pix).normalized().multiply(Xip));

		return newPix;

	}

}
