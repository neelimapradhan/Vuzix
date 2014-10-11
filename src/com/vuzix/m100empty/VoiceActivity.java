package com.vuzix.m100empty;




import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuzix.speech.VoiceControl;

public class VoiceActivity extends Activity implements SurfaceHolder.Callback {
	
	// create object of custom class extended from VoiceControl
	VoiceClass voice;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;
	 String mCurrentPhotoPath;
		ImageView mImageView;
		Button makePhoto, savePhoto;
		TextView fileText;
		
		//a variable to store a reference to the Image View at the main.xml file  
	    private ImageView iv_image;  
	    //a variable to store a reference to the Surface View at the main.xml file  
	    private SurfaceView sv;  
	  
	    //a bitmap to display the captured image  
	    private Bitmap bmp;  
	  
	    //Camera variables  
	    //a surface holder  
	    private SurfaceHolder sHolder;  
	    //a variable to control the camera  
	    private Camera mCamera;  
	    //the camera parameters  
	    private Parameters parameters;  
	  
	// The VoiceControl object that enables/disables voice within an 
	// application and provides speech recognition.
	VoiceControl vc;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice);
		
		//automatic click maybe
		
	    /*super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);*/  
  
        //get the Image View at the main.xml file  
        iv_image = (ImageView) findViewById(R.id.image_view);  
  
        //get the Surface View at the main.xml file  
        sv = (SurfaceView) findViewById(R.id.surface_view);  
  
        //Get a surface  
        sHolder = sv.getHolder();  
  
        //add the callback interface methods defined below as the Surface View callbacks  
        sHolder.addCallback(this);  
  
        //tells Android that this surface will have its data constantly replaced  
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
		
		//voice = new VoiceClass(this);
		//voice.addGrammar(Constants.GRAMMAR_BASIC);
		
		// Create the VoiceControl object and pass it the context.
		vc = new VoiceControl(this)
		{
			@Override
			protected void onRecognition(String word) 
			{
				// Set the TextView to contain whatever word the recognition 
				// service picks up. It is important that the View is cast to
				// a TextView via parentheses before setText is called.
				((TextView)findViewById(R.id.voice_text)).setText(word);
				if (word.equals("halt")){
					/*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    // Ensure that there's a camera activity to handle the intent
				    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				        // Create the File where the photo should go
				        File photoFile = null;
				        try {
				        	
				            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				            String imageFileName = "JPEG_" + timeStamp + "_";
				            File storageDir = Environment.getExternalStoragePublicDirectory(
				                    Environment.DIRECTORY_PICTURES);
				            File image = File.createTempFile(
				                imageFileName,  // prefix 
				                ".jpg",         // suffix 
				                storageDir      // directory
				            );

				            // Save a file: path for use with ACTION_VIEW intents
				            mCurrentPhotoPath = "file:" + image.getAbsolutePath();

				            photoFile = image;
				        } catch (IOException ex) {
				            // Error occurred while creating the File

				        }
				        // Continue only if the File was successfully created
				        if (photoFile != null) {
				            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				                    Uri.fromFile(photoFile));
				            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
				        }
				    }*/
					snap();
					
					Log.d("voiceact","I heard halt");
				}
			}
		};
		// Basic grammar is included by default.
	
}
	
	public void onResume(){
		super.onResume();
		
		// Turn the VoiceControl on when the application resumes.
		//voice.on();
		vc.on();
	}
	public void onPause(){
		super.onPause();
		
		// Turn the VoiceControl off when the application pauses.
		//voice.off();
		vc.off();
	}
	public void onDestroy(){
		super.onDestroy();
		
		// Destroy the VoiceControl object when the application is done so that
		// it can be properly torn down.
		//voice.destroy();
		vc.destroy();
	}
	
	//help
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)  
    {  
         //get camera parameters  
         parameters = mCamera.getParameters();  
  
         //set camera parameters  
         mCamera.setParameters(parameters);  
         mCamera.startPreview();  
  
         //sets what code should be executed after the picture is taken  
         //snap();  
    }  
	
	private void snap()
	{
		Camera.PictureCallback mCall = new Camera.PictureCallback()  
        {  
            @Override  
            public void onPictureTaken(byte[] data, Camera camera)  
            {  
                //decode the data obtained by the camera into a Bitmap  
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);  
                //set the iv_image  
                iv_image.setImageBitmap(bmp);  
            }  
        };  
 
        mCamera.takePicture(null, null, mCall); 
	}
  
    @Override  
    public void surfaceCreated(SurfaceHolder holder)  
    {  
        // The Surface has been created, acquire the camera and tell it where  
        // to draw the preview.  
        mCamera = Camera.open();  
        try {  
           mCamera.setPreviewDisplay(holder);  
  
        } catch (IOException exception) {  
            mCamera.release();  
            mCamera = null;  
        }  
    }  
  
    @Override  
    public void surfaceDestroyed(SurfaceHolder holder)  
    {  
        //stop the preview  
        mCamera.stopPreview();  
        //release the camera  
        mCamera.release();  
        //unbind the camera from this object  
        mCamera = null;  
    }  
}  


/* Basic Grammar Words. 
 * 
 *  move left/right/up/down 			next 		previous 	forward 	halt 			select <#> 	
	go back/home/left/right/up/down 	cancel 		stop 		exit 		<show> menu 	volume up/down 
 	scroll left/right/up/down 			call 		dial 		hang up 	answer 			ignore 			
	call back 							contacts 	favorites 	pair 		unpair 			sleep 			
	set clock/time 						cut 		copy 		paste 		delete 			voice on/off 	
	complete							launch <#>	mute		confirm		end				redial			
	shut down							<#>			show help
 * 
*/

/* com.vuzix.speech.Constants.GRAMMAR_MEDIA
 * 
 *	play						stop				pause		fast forward			rewind			
	scan forward/back			step forward/back	record		next track/song/video	slow
	previous track/song/video	audio				subtitle	seek forward/back
*/

/* com.vuzix.speech.Constants.GRAMMAR_CAMERA
 * 
 *	focus	zoom in/out		take picture/video		start recording		stop recording
 */

/* com.vuzix.speech.Constants.NAVIGATION
 * 
 *	left				right				up		down	north		south			east
	west				turn left/right		drive	walk	map			satellite		street
	points of interest	address				city	state	zip code	post code		street view

 */

/* com.vuzix.speech.Constants.GRAMMAR_MEDICAL
 * 
 *	allergy		anatomy			arm			back		body			bone
	chest		chemist			disease		doctor		finger			foot
	hand		insurance		leg			medicine	muscle			nurse
	patient		pharmacist		pharmacy	physician	prescription	sick
	sickness	sprain			surgeon		surgery		toe
 */
