package com.lionapps.wili.androidthingshome.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.lionapps.wili.androidthingshome.R;
import com.lionapps.wili.androidthingshome.data.database.Measurement;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    private static final String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.temperatureTextView)
    TextView mTemperature;
    @BindView(R.id.humidityTextView)
    TextView mHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.startMeasuring();

        viewModel.getAllMeasurements().observe(this, new Observer<List<Measurement>>() {
            @Override
            public void onChanged(List<Measurement> measurements) {
                Measurement measurement = measurements.get(measurements.size() - 1);
                mTemperature.setText(String.valueOf(measurement.getTemperature()) + "°C");
                mHumidity.setText(String.valueOf(measurement.getHumidity()) + "%");
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.stopMeasuring();
    }
}
