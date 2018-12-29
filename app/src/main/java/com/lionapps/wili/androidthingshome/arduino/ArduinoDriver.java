package com.lionapps.wili.androidthingshome.arduino;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.UartDevice;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ArduinoDriver implements AutoCloseable {
    private static final String TAG = ArduinoDriver.class.getSimpleName();
    private ByteBuffer mMessageBuffer = ByteBuffer.allocate(10);
    private UartDevice mUartDevice;
    private boolean receiving;

    public ArduinoDriver() {
    }

    public void startup() {
        try {
            this.mUartDevice = PeripheralManager.getInstance().openUartDevice(Arduino.getUartDeviceName());
            this.mUartDevice.setDataSize(Arduino.getDataBits());
            this.mUartDevice.setParity(0);
            this.mUartDevice.setStopBits(Arduino.getStopBits());
            this.mUartDevice.setBaudrate(Arduino.getBaudRate());
        } catch (IOException e) {
            try {
                this.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            throw new IllegalStateException("Arduino startup error", e);
        }
    }

    public String getTemperature() {
        String mode = "T";
        String response = "";
        byte[] buffer = new byte[10];

        try {
            response = this.fillBuffer(buffer, mode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getHumidity() {
        String mode = "H";
        String response = "";
        byte[] buffer = new byte[10];

        try {
            response = this.fillBuffer(buffer, mode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String fillBuffer(byte[] buffer, String mode) throws IOException {
        this.mUartDevice.write(mode.getBytes(), mode.length());

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.mUartDevice.read(buffer, buffer.length);
        this.processBuffer(buffer, buffer.length);
        return (new String(this.mMessageBuffer.array(), "UTF-8")).replaceAll("\u0000", "");
    }

    private void processBuffer(byte[] buffer, int length) {
        for(int i = 0; i < length; ++i) {
            if (36 == buffer[i]) {
                this.receiving = true;
            } else if (35 == buffer[i]) {
                this.receiving = false;
                this.mMessageBuffer.clear();
            } else if (this.receiving && buffer[i] != 0) {
                this.mMessageBuffer.put(buffer[i]);
            }
        }

    }

    public void close() throws Exception {
        if (this.mUartDevice != null) {
            try {
                this.mUartDevice.close();
            } finally {
                this.mUartDevice = null;
            }
        }

    }

    public void destroy() {
        if (this.mUartDevice != null) {
            try {
                this.mUartDevice.close();
                this.mUartDevice = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

