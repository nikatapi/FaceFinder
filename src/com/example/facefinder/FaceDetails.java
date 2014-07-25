package com.example.facefinder;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class FaceDetails extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Intent i = getIntent();
    	
    	// Get the position
    	int position = i.getExtras().getInt("position");

    	// Get String arrays FilePathStrings
    	String[] filepath = i.getStringArrayExtra("filepath");

    	// Get String arrays FileNameStrings
    	String[] filename = i.getStringArrayExtra("filename");

    	
        FaceView faceView = new FaceView(this, filepath, filename, position);
        setContentView(faceView);
    }
}
