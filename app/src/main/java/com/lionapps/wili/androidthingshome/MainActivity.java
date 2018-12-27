package com.lionapps.wili.androidthingshome;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.things.pio.PeripheralManager;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String UART_DEVICE_NAME = "UART6";
    private static final int BAUD_RATE = 115200;
    private static final int DATA_BITS = 8;
    private static final int STOP_BITS = 1;
    private MDht22Driver dht22Driver;
    private TextView mTemperature;
    private TextView mHumidity;
    private Button getDataButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager pioManager = PeripheralManager.getInstance();
        Log.d(TAG, "Available GPIO: " + pioManager.getGpioList());

        mTemperature = findViewById(R.id.temperatureTextView);
        mHumidity = findViewById(R.id.humidityTextView);
        getDataButton = findViewById(R.id.getDataButton);

        Arduino mArduino = new Arduino.ArduinoBuilder()
                .uartDeviceName(UART_DEVICE_NAME)
                .baudRate(BAUD_RATE)
                .dataBits(DATA_BITS)
                .stopBits(STOP_BITS)
                .build();

        dht22Driver = new MDht22Driver(mArduino);
        dht22Driver.startup();

        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTemperature.setText(String.format("%s°C", dht22Driver.getTemperature()));
                mHumidity.setText(String.format("%s%%", dht22Driver.getHumidity()));
            }
        });


    }

    @Override
    protected void onDestroy() {
        dht22Driver.shutdown();
        super.onDestroy();
    }
}
