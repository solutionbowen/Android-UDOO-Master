package org.udoo.androidadkdemo;

import me.palazzetti.adktoolkit.AdkManager;
import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.view.View;

public class UDOOBlinkLEDActivity extends Activity{
	
//	private static final String TAG = "UDOO_AndroidADK";

	private AdkManager mAdkManager;
	
	private ToggleButton buttonLED;
	
	private Button btUpdate;
	private Button btTest;
	
	//private TextView tvTemperature;
	private TextView tvFlame;
	private TextView tvFlameValue;
	//private TextView tvHumidityValue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//getWindow().setBackgroundDrawableResource(R.drawable.picture);
		
		mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));

		registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());
		
		buttonLED = (ToggleButton) findViewById(R.id.toggleButtonLED);
		//tvTemperature = (TextView) findViewById(R.id.tvTemperature);
		tvFlame = (TextView) findViewById(R.id.tvFlame);
		tvFlameValue = (TextView) findViewById(R.id.tvFlameValue);
		//tvHumidityValue = (TextView) findViewById(R.id.tvHumidityValue);
		btUpdate = (Button) findViewById(R.id.btUpdate);
		btTest = (Button) findViewById(R.id.btTest);
		
		btUpdate.setOnClickListener(btReadListener);
		btTest.setOnClickListener(btReadListener);
		

		
		Log.i("ADK manager", "available: " + mAdkManager.serialAvailable());
	}
	
	private Button.OnClickListener btReadListener = new Button.OnClickListener(){
		public void onClick(View v){
			switch (v.getId()){
				case R.id.btUpdate:
					mAdkManager.writeSerial("1");
			//		String temperature = mAdkManager.readSerial();
					String humidity = mAdkManager.readSerial();
					tvFlameValue.setText(humidity);
					//String temperature = mAdkManager.readSerial();
					//tvTemperatureValue.setText(temperature);
				case R.id.btTest:
					mAdkManager.writeSerial("0");
				//	String humidity = mAdkManager.readSerial();
//					tvHumidityValue.setText(humidity);
					//String humidity = mAdkManager.readSerial();
					//tvHumidityValue.setText(humidity);
			}
		}
	};
 
	@Override
	public void onResume() {
		super.onResume(); 
		mAdkManager.open();
	}
 
	@Override
	public void onPause() {
		super.onPause();
		mAdkManager.close();
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mAdkManager.getUsbReceiver());
    }
 
	// ToggleButton method - send message to the SAM3X with the AdkManager
	public void blinkLED(View v) {
		if (buttonLED.isChecked()) { 
			//writeSerial() allows you to write a single char or a String object.
			mAdkManager.writeSerial("1");
			//String temperature = mAdkManager.readSerial();
		} else {
			mAdkManager.writeSerial("0");
			//String humidity = mAdkManager.readSerial();
		}	
	}
 
}
