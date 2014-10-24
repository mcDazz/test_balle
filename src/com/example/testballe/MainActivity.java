package com.example.testballe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	    private SimulationView mSimulationView;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	         
	        //PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
	        mSimulationView = new SimulationView(this);
	        setContentView(mSimulationView);
	    }
	 
	    @Override
	    protected void onResume() {
	        super.onResume();
	        mSimulationView.startSimulation();
	        
	    }
	 
	    @Override
	    protected void onPause() {
	        super.onPause();
	        mSimulationView.stopSimulation();
	       
	    }   
}
