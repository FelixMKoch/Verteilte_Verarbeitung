package de.throsenheim.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class DateTimeUtil {

    private DateTimeUtil(){

    }

    public static LocalDate getLocalDate(){
        return LocalDate.now();
    }

    public static LocalDateTime getLocalDateTIme(){
        return LocalDateTime.now();
    }
}
