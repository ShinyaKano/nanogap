import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class My_Plugin_Binary_Publication implements PlugIn {	//Binary conversion of image

	public void run(String arg) {
		
      	ImagePlus imp = IJ.getImage();
      	ImageProcessor ip = imp.getProcessor();
      	int width = ip.getWidth();
      	int height = ip.getHeight();
		int x=0;
		int y=0;
		int y_up=0;								//y of upper side of gap
		int y_low=0;							//y of lower side of gap
      	int Thr = 0;   							//Threshold brightness intensity
		int k=5;								//Find gap if k pixels are successively found.
		boolean sgn1=false;						//Sign for finding gap

	loop:	for (Thr = 0; Thr < 256; Thr++){
	         		
			for (x = 0; x < width; x++){
				int gap = 0 ; 					//Number of successive pixels lower than Thr
				int gap_temp=0; 					//Temporary maximum number of gap in one scan
				sgn1=false;
	
				for (y = 0; y < height; y++) {		
					int intensity = ip.get(x,y);
					if(intensity<Thr){
						gap++; 	
						if (gap>=k){
							sgn1=true;			//True if successive k pixels lower than Thr are found
							if(gap>gap_temp){
								y_up=(y-gap)+1;		
								y_low=y;			
								gap_temp=gap;				
							}
						}
					}else{
						gap=0; 
					}	
				}	
							
				if(!sgn1){
					break; 					//Increment Thr if no gap is found in one scan
				}

				if(sgn1 && x==width-1){				//Find good threshold brightness intensity
					IJ.log("Find the gap using k=" + k);				
					IJ.log("Threshold: " + Thr);
					break loop;
				}

			}
	  }

        // Binary conversion
        for (x = 0; x < width; x++) {
               for (y = 0 ; y < height; y++) {
                   int intensity = ip.get(x,y);
                   if (intensity < Thr) {
                      ip.set(x, y, 0);  
                   } else {
                      ip.set(x, y, 255); 
        		}
        	  }
        }
    	  imp.updateAndDraw();
	}
}
