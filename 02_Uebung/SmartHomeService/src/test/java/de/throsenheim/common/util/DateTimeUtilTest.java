package de.throsenheim.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void getLocalDateTime() {
        LocalDateTime time = DateTimeUtil.getLocalDateTIme();
        assertTrue(!time.isAfter(LocalDateTime.now()));
    }

    @Test
    void getLocalDate() {
        LocalDate date = DateTimeUtil.getLocalDate();
        assertTrue(date.isEqual(LocalDate.now()));
    }
}