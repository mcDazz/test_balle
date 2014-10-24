package com.example.testballe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class SimulationView extends View implements SensorEventListener {
	 
    private SensorManager mySensorManager;
    private Sensor myAccelerometer;
    private Display myDisplay;  
     
    private Bitmap myBitmap;
    private static final int TAILLE_BALLE = 32;
     
    private float origineX;
    private float origineY;
    private float boundHorizontal;
    private float boundVertical;
     
    public SimulationView(Context context) {
        super(context);
         
        Bitmap balle = BitmapFactory.decodeResource(getResources(), R.drawable.earth);
        myBitmap = Bitmap.createScaledBitmap(balle, TAILLE_BALLE, TAILLE_BALLE, true);
        
        Options opts = new Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        
        WindowManager myWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        myDisplay = myWindowManager.getDefaultDisplay();
         
        mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        origineX = w * 0.5f;
        origineY = h * 0.5f;
         
        boundHorizontal = (w - TAILLE_BALLE) * 0.5f;
        boundVertical = (h - TAILLE_BALLE) * 0.5f;
    }

    private float mySensorX;
    private float mySensorY;
    private float mySensorZ;
    private long mySensorTimeStamp;
     
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
         
        switch (myDisplay.getRotation()) {
        case Surface.ROTATION_0:
            mySensorX = event.values[0];
            mySensorY = event.values[1];
            break;
        case Surface.ROTATION_90:
            mySensorX = -event.values[1];
            mySensorY = event.values[0];
            break;
        case Surface.ROTATION_180:
            mySensorX = -event.values[0];
            mySensorY = -event.values[1];
            break;
        case Surface.ROTATION_270:
            mySensorX = event.values[1];
            mySensorY = -event.values[0];
            break;
        }
        mySensorZ = event.values[2];
        mySensorTimeStamp = event.timestamp;
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	public void startSimulation() {
	    mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_UI);
	}
	 
	public void stopSimulation() {
	    mySensorManager.unregisterListener(this);
	}
	
	private Particule maBalle = new Particule();
	 
	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    
	 
	    maBalle.majPosition(mySensorX, mySensorY, mySensorZ, mySensorTimeStamp);
	    maBalle.collisionCote(boundHorizontal, boundVertical);
	 
	    canvas.drawBitmap(myBitmap,
	                        (origineX - TAILLE_BALLE/2) + maBalle.positionX,
	                        (origineY - TAILLE_BALLE/2) - maBalle.positionY, null);
	     
	    invalidate();
	}
}
                
