/*
 * Copyright (c) 2014, Vuzix Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

*  Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
    
*  Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
    
*  Neither the name of Vuzix Corporation nor the names of
   its contributors may be used to endorse or promote products derived
   from this software without specific prior written permission.
    
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package com.vuzix.m100empty;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// code from
//http://developer.android.com/training/camera/photobasics.html.


/* other useful camera options:
 * http://developer.android.com/training/camera/cameradirect.html
 * http://developer.android.com/guide/topics/media/camera.html
 */
public class CameraActivity extends Activity {
	
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	// set up the activity
	ImageView mImageView;
	Button makePhoto, savePhoto;
	TextView fileText;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);
	    Log.w("M100Dev","onCreate");
	    
	    // force to landscape mode 
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    
	    // set up the activity
	    mImageView = (ImageView)findViewById(R.id.mImageView);
	    mImageView.setVisibility(View.GONE);
	    makePhoto = (Button)findViewById(R.id.photo_butt);
	    savePhoto = (Button)findViewById(R.id.save_butt);
	    savePhoto.setVisibility(View.GONE);
	    fileText = (TextView)findViewById(R.id.file_text);
	    fileText.setVisibility(View.GONE);
	  
	  }
	
		// on click method
	  public void takePhoto(View view) {
		 // method starts an available camera app and sends an intent
	  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	    	// this intent will store the taken image
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }

	  }
	  
	 
	  
	  // grab taken image
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	          Bundle extras = data.getExtras();
	          Bitmap imageBitmap = (Bitmap) extras.get("data");
	          
	          mImageView.setVisibility(View.VISIBLE);
	          
	          //set the image file to show
	          mImageView.setImageBitmap(imageBitmap);
	          
	          makePhoto.setVisibility(View.GONE);
	          savePhoto.setVisibility(View.VISIBLE);
	          fileText.setVisibility(View.VISIBLE);
	      }
	  }
	  
	  String mCurrentPhotoPath;
	  // save the file
	  private File createImageFile() throws IOException {
	      // Create an image file name
	      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	      String imageFileName = "JPEG_" + timeStamp + "_";
	      File storageDir = Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES);
	      File image = File.createTempFile(
	          imageFileName,  /* prefix */
	          ".jpg",         /* suffix */
	          storageDir      /* directory */
	      );

	      // Save a file: path for use with ACTION_VIEW intents
	      mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	      fileText.append(" " + mCurrentPhotoPath);
	    
	      return image;
	  }
	  
	  // on click method
	  public void savePhoto(View view){
		  try {
			createImageFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  
		
	  }
	  
	

}
