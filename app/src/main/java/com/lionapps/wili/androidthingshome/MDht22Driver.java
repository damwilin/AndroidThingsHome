package com.lionapps.wili.androidthingshome;

import android.util.Log;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.UartDevice;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MDht22Driver implements BaseSensor, AutoCloseable {
    private static final String TAG = "Dht22Driver";
    private static final int CHUNK_SIZE = 10;
    private ByteBuffer mMessageBuffer = ByteBuffer.allocate(10);
    private final Arduino arduino;
    private UartDevice mDevice;
    private boolean receiving;
    private PeripheralManager mPeripheralManagerService;

    public MDht22Driver(Arduino arduino) {
        this.arduino = arduino;
    }

    public void startup() {
        mPeripheralManagerService = PeripheralManager.getInstance();

        try {
            this.mDevice = mPeripheralManagerService.openUartDevice(this.arduino.getUartDeviceName());
            this.mDevice.setDataSize(this.arduino.getDataBits());
            this.mDevice.setParity(0);
            this.mDevice.setStopBits(this.arduino.getStopBits());
            this.mDevice.setBaudrate(this.arduino.getBaudRate());
        } catch (IOException var5) {
            try {
                this.close();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            throw new IllegalStateException("Sensor can't start", var5);
        }
    }

    public String getTemperature() {
        String mode = "T";
        String response = "";
        byte[] buffer = new byte[10];

        try {
            response = this.fillBuffer(buffer, mode);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return response;
    }

    public String getHumidity() {
        String mode = "H";
        String response = "";
        byte[] buffer = new byte[10];

        try {
            response = this.fillBuffer(buffer, mode);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return response;
    }

    private String fillBuffer(byte[] buffer, String mode) throws IOException {
        this.mDevice.write(mode.getBytes(), mode.length());

        try {
            Thread.sleep(500L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

        this.mDevice.read(buffer, buffer.length);
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
        if (this.mDevice != null) {
            try {
                this.mDevice.close();
            } finally {
                this.mDevice = null;
            }
        }

    }

    public void shutdown() {
        if (this.mDevice != null) {
            try {
                this.mDevice.close();
                this.mDevice = null;
            } catch (IOException var2) {
                Log.w("Dht22Driver", "Unable to close UART device", var2);
            }
        }

    }
}

