package edu.kit.microservice_management.infrastructure.utils.logging;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Logger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void printMessage(String message) {
        String messageToPrint = getCurrentFormattedTime() + "  INFO : " + message;
        System.out.println(messageToPrint);
    }

    public static void printError(String message) {
        String messageToPrint = getCurrentFormattedTime() + "  ERROR: " + message;
        System.err.println(messageToPrint);
    }
    private static String getCurrentFormattedTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTime = sdf.format(timestamp);
        return formattedTime;
    }
}
