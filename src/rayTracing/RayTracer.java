package rayTracing;


import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataTypes.Scene;
import dataTypes.Vec3D;
import dataTypes.Intersection;


/**
 *  Main class for ray tracing exercise.
 */
public class RayTracer {

	public int imageWidth;
	public int imageHeight;
	public Scene scene = new Scene();


	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size as input.
	 * @throws RayTracerException 
	 */
	public static void main(String[] args) throws RayTracerException {

		try {

			RayTracer tracer = new RayTracer();

                        // Default values:
			tracer.imageWidth = 500;
			tracer.imageHeight = 500;

			if (args.length < 2)
				throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

			String sceneFileName = args[0];
			String outputFileName = args[1];

			if (args.length > 3)
			{
				tracer.imageWidth = Integer.parseInt(args[2]);
				tracer.imageHeight = Integer.parseInt(args[3]);
			}


			// Parse scene file:
			tracer.parseScene(sceneFileName);

			tracer.scene.setUp(tracer.imageWidth, tracer.imageWidth, sceneFileName);

			
			// Render scene:
			tracer.renderScene(outputFileName);
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
//		} catch (RayTracerException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		}
	}

	/**
	 * Parses the scene file and creates the scene. Change this function so it generates the required objects.
	 */
	public void parseScene(String sceneFileName) throws IOException, RayTracerException
	{
		FileReader fr = new FileReader(sceneFileName);

		BufferedReader r = new BufferedReader(fr);
		String line = null;
		int lineNum = 0;
		int materialIdx = 1, surfaceIdx=0;


		
		System.out.println("Started parsing scene file " + sceneFileName);
		System.out.println("PNG size is: " + imageHeight + "x" + imageWidth);



		while ((line = r.readLine()) != null)
		{
			line = line.trim();
			++lineNum;

			if (line.isEmpty() || (line.charAt(0) == '#'))
			{  // This line in the scene file is a comment
				continue;
			}
			else
			{
				String code = line.substring(0, 3).toLowerCase();
				String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

				if (code.equals("cam")){
                                        
					auxiliary.camParse(params,scene);
//					System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
				}
				else if (code.equals("set")){
                                       
					auxiliary.setParse(params,scene);
//					ystem.out.println(String.format("Parsed general settings (line %d)", lineNum));
				}
				else if (code.equals("mtl"))
				{
                    scene.materials.add(auxiliary.parseMaterial(params, materialIdx));
                    materialIdx ++;
//					System.out.println(String.format("Parsed material (line %d)", lineNum));
				}
				else if (code.equals("sph"))
				{
					scene.shapes.add(auxiliary.parseSphere(params, surfaceIdx, scene.materials));
					surfaceIdx++;
//					System.out.println(String.format("Parsed sphere (line %d)", lineNum));
				}
				else if (code.equals("pln"))
				{
					scene.shapes.add(auxiliary.parsePlane(params, surfaceIdx, scene.materials));
					surfaceIdx++;
//					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				}
				else if (code.equals("box"))
				{
					scene.shapes.add(auxiliary.parseBox(params, surfaceIdx, scene.materials));
					surfaceIdx++;
//					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				}
				else if (code.equals("lgt"))
				{
					scene.lights.add(auxiliary.parseLight(params));
//					System.out.println(String.format("Parsed light (line %d)", lineNum));
				}
				else
				{
					System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
				}
			}
		}

		r.close();
		System.out.println("Finished parsing scene file " + sceneFileName);

	}

	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName)
	{
		long startTime = System.currentTimeMillis();
		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];


                // Put your ray tracing code here!
                //
                // Write pixel color values in RGB format to rgbData:
                // Pixel [x, y] red component is in rgbData[(y * this.imageWidth + x) * 3]
                //            green component is in rgbData[(y * this.imageWidth + x) * 3 + 1]
                //             blue component is in rgbData[(y * this.imageWidth + x) * 3 + 2]
                //
                // Each of the red, green and blue components should be a byte, i.e. 0-255
		
		for (int i=0; i<imageHeight; i++) { //rows
			for (int j=0; j< imageWidth; j++) { //columns
				int colorIdx = 3*(i*imageWidth + j);
				Vec3D color;
				Vec3D pixel = scene.findPixleCenter(i, j);
				if (pixel == null) {
					color = Vec3D.black;
					auxiliary.storeColor(rgbData, color, colorIdx);
					continue;
				}
				Vec3D cameraRay = Vec3D.createDistVec(scene.cameraPos(), pixel).normalized();
				Intersection hit = scene.firstIntersectionOfRay(pixel, cameraRay, null);
				
				//get color
				if (hit == null)
					// color = (scene.isFish())? Vec3D.white : scene.getBackground();
					color = scene.getBackground();
				else
					color = ColorCompute.getColor(hit, cameraRay, scene);
			  auxiliary.storeColor(rgbData, color, colorIdx);
			}
		}
		

		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

		// This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}

	//////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName)
	{
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
	    int height = buffer.length / width / 3;
	    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
	    ColorModel cm = new ComponentColorModel(cs, false, false,
	            Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    SampleModel sm = cm.createCompatibleSampleModel(width, height);
	    DataBufferByte db = new DataBufferByte(buffer, width * height);
	    WritableRaster raster = Raster.createWritableRaster(sm, db, null);
	    BufferedImage result = new BufferedImage(cm, raster, false, null);

	    return result;
	}

	public static class RayTracerException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RayTracerException(String msg) {  super(msg); }
	}


}
