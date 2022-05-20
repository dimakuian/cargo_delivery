package com.epam.delivery.service;

import java.sql.Timestamp;

public final class StringToTimestampConverter {
    private StringToTimestampConverter() {
    }

    /**
     * @param date request param
     * @return Timestamp
     */
    public static Timestamp beginTimestampConverter(String date) {
        Timestamp timestamp = Timestamp.valueOf(date + " 00:00:00");
        return timestamp;
    }


    /**
     * @param date request param
     * @return Timestamp
     */
    public static Timestamp endTimestampConverter(String date) {
        Timestamp timestamp = Timestamp.valueOf(date + " 23:59:59");
        return timestamp;
    }
}
