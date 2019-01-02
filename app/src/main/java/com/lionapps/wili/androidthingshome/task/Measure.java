package com.lionapps.wili.androidthingshome.task;

import com.lionapps.wili.androidthingshome.arduino.ArduinoDriver;
import com.lionapps.wili.androidthingshome.repository.Repository;

import java.util.Timer;
import java.util.TimerTask;

public class Measure {
    private Repository repository;
    private ArduinoDriver arduinoDriver;
    private Timer timer;

    public Measure(Repository repository) {
        this.repository = repository;
        arduinoDriver = new ArduinoDriver();
    }

    public void startMeasuring(long periodTime){
        arduinoDriver.destroy();
        arduinoDriver.startup();
        if (timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repository.insertMeasurement(arduinoDriver.getMeasurement());
            }
        },0,periodTime);
    }

    public void stopMeasuring(){
        timer.purge();
        timer.cancel();
        arduinoDriver.destroy();
    }
}
