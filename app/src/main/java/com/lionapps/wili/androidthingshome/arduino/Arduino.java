package com.lionapps.wili.androidthingshome.arduino;

public class Arduino {
    private static final String uartDeviceName = "UART6";
    private static final int baudRate = 115200;
    private static final int dataBits = 8;
    private static final int stopBits = 1;

    private Arduino() {
    }

    public static String getUartDeviceName() {
        return uartDeviceName;
    }

    public static int getBaudRate() {
        return baudRate;
    }

    public static int getDataBits() {
        return dataBits;
    }

    public static int getStopBits() {
        return stopBits;
    }
}
