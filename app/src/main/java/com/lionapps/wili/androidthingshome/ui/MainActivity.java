package com.lionapps.wili.androidthingshome.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lionapps.wili.androidthingshome.R;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    private MainViewModel viewModel;

    private static final String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.temperatureTextView)
    TextView mTemperature;
    @BindView(R.id.humidityTextView)
    TextView mHumidity;
    @BindView(R.id.getDataButton)
    Button getDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = new MainViewModel();





        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTemperature.setText(String.format("%s°C", viewModel.getTemperature()));
                mHumidity.setText(String.format("%s%%", viewModel.getHumidity()));
            }
        });

    }
}
