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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class ButtonPressActivity extends Activity {

	// declare, to see these look in activity_main.xml, rename/delete as needed
	TextView buttonPressLabel, keyPressLabel, buttonNameLabel, keyIDLabel;
		
		
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button_press);
		
		// these will not be updated in this iteration, hooking them up for user convenience
		buttonPressLabel = (TextView) this.findViewById(R.id.buttonPressLabel);
		keyPressLabel = (TextView) this.findViewById(R.id.keyPressLabel);
		
		// will vary as user presses buttons
		buttonNameLabel = (TextView) this.findViewById(R.id.buttonNameLabel);
		keyIDLabel = (TextView) this.findViewById(R.id.keyIDLabel);
				
				
				
	}
	

	/* BUTTON PRESS SAMPLE */
	// button is the name of the physical button on the m100
	// key is the corresponding android ID
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		boolean correctKey = false; // stays false if unexpected button pressed
		
		// log used keycode
		Log.w("M100Dev",""+keyCode);
		// show used keycode
		keyIDLabel.setText(keyCode+"");
		
		switch(keyCode){
			case KeyEvent.KEYCODE_ENTER: // SELECT button pressed, 66
				correctKey = true; // expected button pressed	
				
				// I am using a string literal here, this is (kind of) ok as long as you don't use the same string more than once
				buttonNameLabel.setText("Select Button");				
			break;	
					
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				correctKey = true;

				// I am using a generated string ID for the sake of example, found in values/strings.xml
				buttonNameLabel.setText(this.getString(R.string.next_pressed));
			break;
			
			case KeyEvent.KEYCODE_DPAD_LEFT:
				correctKey = true;

				// I am using a generated string ID for the sake of example, found in values/strings.xml
				buttonNameLabel.setText(this.getString(R.string.prev_pressed));
			break;
			
			// Held button presses, previous button held takes user back to home screen
			case KeyEvent.FLAG_KEEP_TOUCH_MODE :
				correctKey = true;
				buttonNameLabel.setText("Select Button Held");
			break;
			
			case KeyEvent.KEYCODE_MENU :
				correctKey = true;
				buttonNameLabel.setText("Next Button Held");
			break;
			
			/* Default, surprising button press
			default:
				correctKey = false; // unexpected button pressed
				buttonNameLabel.setText("Unknown Button Pressed");				
			break;*/
		}
		
		return correctKey; 
	}
	
	// Relevant links:
	/*
	 * Catching Keypress: http://stackoverflow.com/questions/8137325/android-how-to-add-listener-to-hardware-menu-button
	 * Key Codes: http://developer.android.com/reference/android/view/KeyEvent.html
	 * 
	 */
	
	
	/* END BUTTON PRESS SAMPLE */ 
}
