package com.lionapps.wili.androidthingshome.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lionapps.wili.androidthingshome.R;
import com.lionapps.wili.androidthingshome.repository.Measurement;

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
    @BindView(R.id.temperature_graph_view)
    GraphView mTempGraphView;
    @BindView(R.id.humidity_graph_view)
    GraphView mHumGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupGraphs();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.startMeasuring();

        viewModel.getAllMeasurements().observe(this, new Observer<List<Measurement>>() {
            @Override
            public void onChanged(List<Measurement> measurements) {
                Measurement measurement = measurements.get(measurements.size() - 1);
                mTemperature.setText(String.valueOf(measurement.getTemperature()) + "°C");
                mHumidity.setText(String.valueOf(measurement.getHumidity()) + "%");
                setDataToGraphs(measurements);
            }
        });
    }

    private void setDataToGraphs(List<Measurement> measurementList) {
        mTempGraphView.removeAllSeries();
        mHumGraphView.removeAllSeries();
        LineGraphSeries<DataPoint> tempSeries = new LineGraphSeries<DataPoint>();
        tempSeries.setColor(Color.YELLOW);
        tempSeries.setAnimated(false);
        LineGraphSeries<DataPoint> humSeries = new LineGraphSeries<DataPoint>();
        humSeries.setColor(Color.BLUE);
        humSeries.setAnimated(false);
        for (int i = 0; i < measurementList.size(); i++) {
            tempSeries.appendData(new DataPoint(i, measurementList.get(i).getTemperature()), true, measurementList.size());
            humSeries.appendData(new DataPoint(i, measurementList.get(i).getHumidity()), true, measurementList.size());
        }
        mTempGraphView.addSeries(tempSeries);
        mHumGraphView.addSeries(humSeries);
    }
    private void setupGraphs(){
        mTempGraphView.setTitle("Temperature °C");
        mTempGraphView.setBackgroundColor(Color.BLACK);
        mTempGraphView.setTitleColor(Color.WHITE);
        mTempGraphView.getGridLabelRenderer().setGridColor(Color.WHITE);
        mTempGraphView.getLegendRenderer().setTextColor(Color.WHITE);

        mHumGraphView.setTitle("Humidity %");
        mHumGraphView.setBackgroundColor(Color.BLACK);
        mHumGraphView.setTitleColor(Color.WHITE);
        mHumGraphView.getGridLabelRenderer().setGridColor(Color.WHITE);
        mHumGraphView.getLegendRenderer().setTextColor(Color.WHITE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.stopMeasuring();
    }
}
