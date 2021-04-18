package dataTypes;

public class Scene {
	String name;
	Vec up;
	Vec right;
	Vec towards;
	
	
	// screen parameters
	double screen_dist,pixelSize, width,hight;
	double dx,dy;
	int xPixels, yPixels;
	Vec screenCenter,bottomLeftCormer,bottomLeftPixel;
	
	
	//fish eye parameters
	double focus;
	boolean fisheyel;
}
