import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class My_Plugin_Perimeter_Publication implements PlugIn {//Calculate area, perimeter, and average nanogap distance using a binary converted image

	public void run(String arg) {
		
		ImagePlus imp = IJ.getImage();
		ImageProcessor ip = imp.getProcessor();
		int width = ip.getWidth();
		int height = ip.getHeight();
		int x=0;
		int y=0;
		int peri = 0;
		int area = 0;
		double pixtonm=(100.0/54.0); 			//pixel to nm conversion ratio: ((double)nm/(double)pixels)

		IJ.run(imp, "Open", "");			//Open (dilate and erode)

		for (x=0;x<width;x++){				//Count area in pixels
			for (y=0;y<height;y++){
				int intensity = ip.get(x,y);
				if (intensity ==0){
					area=area+1;
				}
			}
		}
	
		IJ.run(imp, "Outline", "");

		for (x=0;x<width;x++){				//Count perimeter in pixels
			for (y=0;y<height;y++){
				int intensity = ip.get(x,y);
				if (intensity ==0){
					peri=peri+1;
					ip.set(x, y, 122);	//Change gray if pixels are counted
				}
			}
		}

		//Show results of calculation
		IJ.log("Area: " + area + " pixel.");
		IJ.log("Perimeter:" + peri + " pixel.");
		IJ.log("Average nanogap distance:" + (area/(peri/2.0)) + "pixels.");
		IJ.log("Pixel to nm conversion: " + pixtonm + " nm/pixel.");
		IJ.log("Average nanogap distance:" + (area/(peri/2.0)*pixtonm) + "nm.");

	}
}
