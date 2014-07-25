package com.example.facefinder;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FaceScan extends AsyncTask< Void, Void, Void> {

	

	@Override
	protected Void doInBackground(Void... params) {
		
		
		final int NUM_FACES = 10; // max is 64
		final boolean DEBUG = true;

		FaceDetector arrayFaces;
		FaceDetector.Face getAllFaces[] = new FaceDetector.Face[NUM_FACES];
		FaceDetector.Face getFace = null;
		Bitmap sourceImage;
		
		File file;
		File[] listFile;
		
		int picWidth;
		int picHeight;
		int foundface;
		
		String path;
		String output;
		
	
		// Check for SD Card
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					return null;
		}
		else {
			// Locate the image folder in your SD Card
			file = new File(Environment.getExternalStorageDirectory()+ File.separator + "DCIM" + File.separator + "Camera");
			path = Environment.getExternalStorageDirectory()+ File.separator + "DCIM" + File.separator + "Camera" + File.separator;
			
		}
		
		if (file.isDirectory()) {
			listFile = file.listFiles();
			// Create a String array for FilePathStrings
			String[] FilePathStrings = new String[listFile.length];
			// Create a String array for FileNameStrings
			String[] FileNameStrings = new String[listFile.length];
			
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inPreferredConfig = Bitmap.Config.RGB_565;
			bfo.inSampleSize = 2;

			for (int i = 0; i < listFile.length; i++) {
				// Get the path of the image file
				FilePathStrings[i] = listFile[i].getAbsolutePath();
				// Get the name image file
				FileNameStrings[i] = listFile[i].getName();
				output = Environment.getExternalStorageDirectory()+ File.separator + "FaceImages"+ File.separator;
				
				sourceImage = BitmapFactory.decodeFile( FilePathStrings[i], bfo);
				picWidth = sourceImage.getWidth();
				picHeight = sourceImage.getHeight();
				
				arrayFaces = new FaceDetector( picWidth, picHeight, NUM_FACES );
				foundface = arrayFaces.findFaces(sourceImage, getAllFaces);
				
				
				
				if(foundface!=0){
					//move the file to the FaceImages folder
					Log.v("Entered File OP","will try to move file");
					moveFile(path, FileNameStrings[i], output);
				}
					
			}
		}
		return null;
	}
	
	
	private void moveFile(String inputPath, String inputFile, String outputPath) {

	    InputStream in = null;
	    OutputStream out = null;
	    try {

	        //create output directory if it doesn't exist
	        File dir = new File (outputPath); 
	        if (!dir.exists())
	        {
	            dir.mkdirs();
	        }


	        in = new FileInputStream(inputPath + inputFile);        
	        out = new FileOutputStream(outputPath + inputFile);

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;

	        // write the output file
	        out.flush();
	        out.close();
	        out = null;

	        // delete the original file
	        new File(inputPath + inputFile).delete();  


	    } 

	    catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	    }
	    catch (Exception e) {
	        Log.e("tag", e.getMessage());
	    }

	}

}
