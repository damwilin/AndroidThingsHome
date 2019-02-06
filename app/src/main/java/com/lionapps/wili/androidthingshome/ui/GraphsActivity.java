package com.lionapps.wili.androidthingshome.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lionapps.wili.androidthingshome.R;
import com.lionapps.wili.androidthingshome.data.database.Measurement;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphsActivity extends AppCompatActivity {

    GraphsViewModel viewModel;

    @BindView(R.id.temperature_graph_view)
    GraphView mTempGraphView;
    @BindView(R.id.humidity_graph_view)
    GraphView mHumGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        ButterKnife.bind(this);
        setupGraphs();
        viewModel = ViewModelProviders.of(this).get(GraphsViewModel.class);

        viewModel.getAllMeasurements().observe(this, new Observer<List<Measurement>>() {
            @Override
            public void onChanged(List<Measurement> measurements) {
                Measurement measurement = measurements.get(measurements.size() - 1);
                setDataToGraphs(measurements);
            }
        });
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
}
