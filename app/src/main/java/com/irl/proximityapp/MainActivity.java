package com.irl.proximityapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/** A proximity app that informs the user of magnetic fields and if objects are close the phone.*/
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor magnetFieldSensor;

    private TextView proximityAlert;
    private TextView metalDetector;

    /** View call. Sets activity and variables. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        magnetFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        proximityAlert = findViewById(R.id.proximityAlert);
        metalDetector = findViewById(R.id.metalDetector);

    }

    /** Registers this as a sensor listener. */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /** Unregisters this as a sensor listener. */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     *  Checks what sensor is sending data and updates the ui
     * with respective value. Computes the magnitude of the
     * magnetic field axles or tells the user if they are close
     * or far from the proximity sensor.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY ) {
            if (event.values[0] >= 5)
                proximityAlert.setText("FAR!");
            else if (event.values[0] < 5)
                proximityAlert.setText("CLOSE!");
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double magnitude = Math.sqrt((x * x) + (y * y) + (z * z));
            metalDetector.setText("METALDETECTOR" + "\n" + magnitude);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
