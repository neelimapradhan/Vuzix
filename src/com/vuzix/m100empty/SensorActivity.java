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

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;

//information on motion sensors: http://developer.android.com/guide/topics/sensors/sensors_motion.html

public class SensorActivity extends Activity implements SensorEventListener
{
	
	
	SensorManager mSensorManager;
	String sensorList;
	
	// sensors available
	Sensor lightSensor, proximitySensor, rotationVectorSensor, gravitySensor,
		accelerationSensor, orientationSensor, gyroscopeSensor;
	
	// textviews for activity
	TextView rotateX, gyroX, accelX, orientX, gravX,
		rotateY, gyroY, accelY, orientY, gravY,
		rotateZ, gyroZ, accelZ, orientZ, gravZ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor);
		
		sensorList = "";
		
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		// get list of sensors available
		List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		for(Sensor s : deviceSensors){
			if(!s.getName().contains("MPL")){
				sensorList+=" " + s.getName() + ",";
			}
		}
		//print created string
		Log.w("M100Dev","all sensors: " + sensorList);
		

		
		// There are warnings, but since we are on a lower android version they are unavoidable.

	    rotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	    gravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		accelerationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		orientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		gyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		
		
		// this is alot of text :D
		rotateX=(TextView)findViewById(R.id.rotate_x);rotateY=(TextView)findViewById(R.id.rotate_y);rotateZ=(TextView)findViewById(R.id.rotate_z);
		gravX=(TextView)findViewById(R.id.grav_x);gravY=(TextView)findViewById(R.id.grav_y);gravZ=(TextView)findViewById(R.id.grav_z);
		accelX=(TextView)findViewById(R.id.accel_x);accelY=(TextView)findViewById(R.id.accel_y);accelZ=(TextView)findViewById(R.id.accel_z);
		orientX=(TextView)findViewById(R.id.orient_x);orientY=(TextView)findViewById(R.id.orient_y);orientZ=(TextView)findViewById(R.id.orient_z);
		gyroX=(TextView)findViewById(R.id.gyro_x);gyroY=(TextView)findViewById(R.id.gyro_y);gyroZ=(TextView)findViewById(R.id.gyro_z);
	}

	 @Override
	  protected void onResume() {
	    // Register listeners for the sensor.
	    super.onResume();
	    mSensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
	  }
	 @Override
	  protected void onPause() {
	    // Be sure to unregister the sensors when the activity pauses so we don't kill several things
	    super.onPause();
	    mSensorManager.unregisterListener(this);
	  }


	 
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.w("M100Dev","ACCURACY CHANGED: " +sensor.getName());
	}
	
	
	// catch sensors' information
	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		
		// string format for readability
		DecimalFormat df = new DecimalFormat("##.##");	
		// store values
		String x = "X: " + df.format(event.values[0]);
		String y = "Y: " + df.format(event.values[1]);
		String z = "Z: " + df.format(event.values[2]);
		
		// print info
		switch(sensor.getType()){
			case Sensor.TYPE_ROTATION_VECTOR:
				rotateX.setText(x);
				rotateY.setText(y);
				rotateZ.setText(z);
			break;
			case Sensor.TYPE_GRAVITY:
				gravX.setText(x);
				gravY.setText(y);
				gravZ.setText(z);
				
			break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				accelX.setText(x);
				accelY.setText(y);
				accelZ.setText(z);
			break;
			case Sensor.TYPE_ORIENTATION:
				orientX.setText(x);
				orientY.setText(y);
				orientZ.setText(z);
				
			break;
			case Sensor.TYPE_GYROSCOPE:
				gyroX.setText(x);
				gyroY.setText(y);
				gyroZ.setText(z);
			break;
			default:
				Log.w("M100Dev","something interesting is happening with: "+sensor.getName());
			break;
		}
				 
		
	}
	
	
	
}
