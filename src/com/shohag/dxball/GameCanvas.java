package com.shohag.dxball;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;



@SuppressLint("Draw")
public class GameCanvas extends View {
	public static boolean gameOver;
    public static boolean newLife;
    public static int life, canvasHeight, canvasWidth;
    float barWidth = 200;
    float X = 0, Y = 300;
    int s = 0;
    float barLeft, barRight, barTop, barBottom;
    boolean leftPos, rightPos, start = true;
    float clicked;
    int level = 1, row = 1, speed = 5;
    Paint paint;
    Ball ball;
    Bar bar;
    int Color1, Color2;
    MediaPlayer mp;
  
	
	ArrayList<Object> bricks = new ArrayList<Object>();
	

    public GameCanvas(Context context) {
        super(context);
        paint = new Paint();
        life = 3;
        gameOver = false;
        newLife = false;
       
    }

    
    
    //protected void onStart() {
       // super.onStart();
       // sensormanager.registerListener(this, sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
  //  }
     
 //   protected void onStop() {
     //   sensormanager.unregisterListener(this);
       // super.onStop();
  //  }
 
    
   public  class gameOver extends Activity{
	   
	   
	   
   }



	@Override
    protected void onDraw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        if(start == true){
        	
        	//brick color
            start = false;
            row = 5; 
            Color1 = Color.rgb(64, 0, 128); 
            
            //brick object
            for(int i = 5; i >= row; i--){
            	int color;
            	  for(int j = 5; j > i; j--){
            		 canvas.drawText("Life: "+life, 0, 0, paint);
  	                }
            		  
            	  
	            for(int k = 1; k < (i*2) ; k++){
	                if(X >= canvas.getWidth()) {
	                    X = 0;
	                    Y += 30;
	                }
	                
	                color = Color1; 
	                bricks.add(new Object(X, Y, X+(canvas.getWidth()/5)-2, Y+28,color));
	                X += canvas.getWidth() / 5;
	            }
            }

            
            // ball
            ball = new Ball( canvas.getWidth()/2, canvas.getHeight()-100, Color.GRAY, 22);
            ball.setDx(speed);
            ball.setDy(-speed);
            
            
            // bar
            barLeft = getWidth() / 2 - (barWidth / 2);
            barTop = getHeight() - 20;
            barRight = getWidth() / 2 + (barWidth / 2);
            barBottom = getHeight();
            bar = new Bar(barLeft, barTop, barRight, barBottom, Color.GREEN);
        }

        if(bricks.size() <= 0){
        	gameOver = true;
        	newLife = false;
        	start = false;
        	
        }
        
        if(newLife && !start) {
            newLife = false;
          
            if(life == 2 || life == 3){
            	ball = new Ball(canvas.getWidth()/2,canvas.getHeight()-50, Color.rgb(106, 38, 165), 20);
            }
            
            else if(life == 1){
            	ball = new Ball(canvas.getWidth()/2,canvas.getHeight()-50, Color.rgb(173, 10, 37), 20);
            }
            
            else if(life < 0){
            	
            	((DXBALL_GAME_Activity)getContext()).finish();
            }
            
            ball.setDx(speed);
            ball.setDy(-speed);
        }
        
       if(gameOver){
        	
        	//Intent i = new Intent(getContext(),gameOver.class);
	        // startService(i);
    	 
           ((DXBALL_GAME_Activity)getContext()).finish();
           this.highScore();
           
        }
        
        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        canvas.drawText("Score: "+s, 350, 30, paint);

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        canvas.drawText("Life: "+life, 250, 30, paint);
		
		canvas.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), ball.getPaint());
		
        canvas.drawRect(bar.getLeft(), bar.getTop(), bar.getRight(), bar.getBottom(), bar.getPaint());

        for(int i=0; i < bricks.size(); i++){
            canvas.drawRect(bricks.get(i).getLeft(),bricks.get(i).getTop(),bricks.get(i).getRight(),bricks.get(i).getBottom(),bricks.get(i).getPaint());
        }
        
        this.BrickCollision(canvas);
        this.BarCollision(canvas);
        this.BoundaryCollision(canvas);

        ball.move();
        bar.moveBar(barLeft,barRight);
        this.run();
    }
    

	public void BoundaryCollision(Canvas canvas) {
        if((ball.getY() - ball.getRadius()) >= canvas.getHeight()){
            life -= 1;
            newLife = true;
        }

        if(life == 0){
        	gameOver = true;
        	start = false;
        }
        else{
	        if((ball.getX() + ball.getRadius()) >= canvas.getWidth() || (ball.getX() - ball.getRadius()) <= 0){
	        	ball.setDx(-ball.getDx());
	        }
	        
	        if( (ball.getY() - ball.getRadius()) <= 0){
	        	ball.setDy(-ball.getDy());
	        }
        }
    }
    
    public void BarCollision(Canvas canvas){
        if(((ball.getY() + ball.getRadius()) >= bar.getTop()) && ((ball.getY()+ball.getRadius()) <= bar.getBottom()) && ((ball.getX()) >= bar.getLeft()) && ((ball.getX()) <= bar.getRight())) {
        	ball.setDy(-(ball.getDy()));
        }

    }
    
    public void BrickCollision(Canvas canvas){
    	// mp = MediaPlayer.create(this,R.raw.Breaking);
        for(int i=0; i < bricks.size(); i++) {
            if (((ball.getY() - ball.getRadius()) <= bricks.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= bricks.get(i).getTop()) && ((ball.getX()) >= bricks.get(i).getLeft()) && ((ball.getX()) <= bricks.get(i).getRight())) {
            	s += 1;
                bricks.remove(i);
               // mp.start();
            	ball.setDy(-(ball.getDy()));
            }
        }

    }
    
//score storing 
    
    public void highScore()
    {
    	String filename="score";  
        String data=String.valueOf(s);  
        FileOutputStream fos;  
        try {  
         fos = openFileOutput(filename, Context.MODE_PRIVATE);  
         //default mode is PRIVATE, can be APPEND etc.  
         fos.write(data.getBytes());  
         fos.close();  
          
       //  Toast.makeText(canvas,filename + " saved",  
                 //Toast.LENGTH_LONG).show();  
           
          
        } catch (FileNotFoundException e) {e.printStackTrace();}  
        catch (IOException e) {e.printStackTrace();
        
        }  
        
        //String filename=editTextFileName.getText().toString();  
        StringBuffer stringBuffer = new StringBuffer();    
        try {  
            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader  
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));  
            String inputString;  
            //Reading data line by line and storing it into the stringbuffer                
            while ((inputString = inputReader.readLine()) != null) {  
                stringBuffer.append(inputString + "\n");  
            }  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //Displaying data on the toast  
        Toast.makeText((Context) getApplicationWindowToken(),stringBuffer.toString(),  
                Toast.LENGTH_LONG).show();  
          
    }  
    private InputStream openFileInput(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
	
   

    private FileOutputStream openFileOutput(String filename, int modePrivate) {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressLint("Click")
	@Override
    public boolean onTouchEvent(MotionEvent e) {	
    	clicked=e.getX();
    	if(clicked>200){
    		barLeft=clicked-195;		
    		barRight = barLeft + barWidth;
    		bar.moveBar(barLeft,barRight);	
    	}
    	else {
    		barLeft=clicked-5;		
    		barRight = barLeft + barWidth;
    		bar.moveBar(barLeft,barRight);
		}
    		
    	return true;
    }

    public void run(){
        invalidate();
        setBackgroundColor(0xFFB441B4);
    }



	//public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	//}



	//public void onSensorChanged(SensorEvent arg0) {
	// TODO Auto-generated method stub
		
	//}



	
}
