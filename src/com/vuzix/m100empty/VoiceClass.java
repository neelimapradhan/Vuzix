package com.vuzix.m100empty;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.vuzix.speech.VoiceControl;


// for super basic functionality in voice this will work as a stub class extending VoiceControl
public class VoiceClass extends VoiceControl {
	
	TextView tv;
	public VoiceClass(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	// this info needs to be sent to the activity
	@Override
	protected void onRecognition(String word) {
		Log.w("M100Dev","word:" + word);
	}

}
