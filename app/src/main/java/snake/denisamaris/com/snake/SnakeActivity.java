package snake.denisamaris.com.snake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class SnakeActivity extends Activity implements SensorEventListener, ShakeListener.OnShakeListener {

    // Declare an instance of SnakeEngine
    SnakeEngine snakeEngine;
    private SensorManager mSensorManager;
    private Sensor mTempSensor;
    private ShakeListener shakeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the SnakeEngine class
        snakeEngine = new SnakeEngine(this, size);

        // Make snakeEngine the view of the Activity
        setContentView(snakeEngine);
        shakeListener = new ShakeListener(this);
        shakeListener.setOnShakeListener(this);
    }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        snakeEngine.resume();
        mSensorManager.registerListener(this, mTempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        snakeEngine.pause();
        mSensorManager.unregisterListener(this);
    }

    public static void navigate(Context context) {
        context.startActivity(new Intent(context, SnakeActivity.class));
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float ambient_temperature = event.values[0];
        this.snakeEngine.onNewTemperatureReceived(ambient_temperature);
    }


    @Override
    public void onShake() {
        MenuActivity.navigate(this);
    }
}

