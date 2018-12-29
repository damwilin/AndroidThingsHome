package com.lionapps.wili.androidthingshome.ui;

import com.lionapps.wili.androidthingshome.arduino.ArduinoDriver;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private ArduinoDriver arduinoDriver;

    public MainViewModel() {
        setupArduinoDriver();
    }

    private void setupArduinoDriver(){
        arduinoDriver = new ArduinoDriver();
        arduinoDriver.startup();
    }

    public String getHumidity(){
        return arduinoDriver.getHumidity();
    }

    public String getTemperature(){
        return arduinoDriver.getTemperature();
    }
}
