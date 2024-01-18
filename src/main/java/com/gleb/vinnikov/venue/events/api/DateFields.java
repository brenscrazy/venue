package com.gleb.vinnikov.venue.events.api;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class DateFields {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateFields(Timestamp timestamp) {
        this(timestamp.toLocalDateTime());
    }

    private DateFields(LocalDateTime localDateTime) {
        this.year = localDateTime.getYear();
        this.month = localDateTime.getMonthValue();
        this.day = localDateTime.getDayOfMonth();
        this.hour = localDateTime.getHour();
        this.minute = localDateTime.getMinute();
    }

    public Timestamp toTimestamp() {
        return Timestamp.valueOf(LocalDateTime.of(
                year, month, day, hour, minute
        ));
    }

}
