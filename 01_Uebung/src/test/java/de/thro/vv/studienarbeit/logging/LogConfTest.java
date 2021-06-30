package de.thro.vv.studienarbeit.logging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogConfTest {

    @Test
    @DisplayName("Logger works properly")
    void startLogger() {
        Assertions.assertTrue(LogConf.startLogger());
        Assertions.assertDoesNotThrow(LogConf::startLogger);
    }
}