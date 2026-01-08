package com.naveen.easycar.utility;

import com.naveen.easycar.exception.InvalidDateException;

import java.time.LocalDate;

public class DateUtils {
    public static void validateDates(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now())) {
            throw new InvalidDateException("Booking cannot start in the past");
        }
        if (!end.isAfter(start)) {
            throw new InvalidDateException("End date must be after start date");
        }
    }
}
