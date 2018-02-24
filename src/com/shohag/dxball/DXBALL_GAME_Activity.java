package com.shohag.dxball;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class DXBALL_GAME_Activity extends Activity {

	 /** Called when the activity is first created. */
	
	Button button;
	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//setContentView(R.layout.start);
			 //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			
		     
			//setContentView(R.layout.start);
	       
	      
	      // button = (Button)findViewById(R.id.button1);
	      // btn2 = (Button)findViewById(R.id.button2);
	       //btn3 = (Button)findViewById(R.id.button3);
	       //btn = (Button)findViewById(R.id.button4);
	     // button.setOnClickListener(new View.OnClickListener() {
	      //    public void onClick(View view) {
	        	//  startActivity(i);  
	         //     
	         //  }
			
	    //   });
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			View Ball = new GameCanvas(this);
		    setContentView(Ball);
			
			
			
			
		
	        //setContentView(new GameCanvas(this));

}
}


