package com.example.facefinder;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class MainGallery extends Activity {
	// Declare variables
		private String[] FilePathStrings;
		private String[] FileNameStrings;
		private File[] listFile;
		GridView grid;
		GridViewAdapter adapter;
		File file;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_face_gallery);

			// Check for SD Card
			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Toast.makeText(this, "Error! No External Storage Found!", Toast.LENGTH_LONG)
						.show();
			} 
			else {
				// Locate the image folder in your SD Card
				file = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "DCIM" + File.separator + "Camera");
			}

			if (file.isDirectory()) {
				listFile = file.listFiles();
				// Create a String array for FilePathStrings
				FilePathStrings = new String[listFile.length];
				// Create a String array for FileNameStrings
				FileNameStrings = new String[listFile.length];

				for (int i = 0; i < listFile.length; i++) {
					// Get the path of the image file
					FilePathStrings[i] = listFile[i].getAbsolutePath();
					// Get the name image file
					FileNameStrings[i] = listFile[i].getName();
				}
			}

			// Locate the GridView in gridview_main.xml
			grid = (GridView) findViewById(R.id.face_gridview);
			// Pass String arrays to LazyAdapter Class
			adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
			// Set the LazyAdapter to the GridView
			grid.setAdapter(adapter);
		
			// Capture gridview item click
			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent i = new Intent(MainGallery.this, ViewImage.class);
					// Pass String arrays FilePathStrings
					i.putExtra("filepath", FilePathStrings);
					// Pass String arrays FileNameStrings
					i.putExtra("filename", FileNameStrings);
					// Pass click position
					i.putExtra("position", position);
					
					startActivity(i);
				}

			});
		
		
		}
}
		
