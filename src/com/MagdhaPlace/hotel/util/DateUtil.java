package com.MagdhaPlace.hotel.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static int nightsRented(LocalDate startDate, LocalDate returnDate){
        int nights = (int) ChronoUnit.DAYS.between(startDate, returnDate);
        return nights;
    }
}
