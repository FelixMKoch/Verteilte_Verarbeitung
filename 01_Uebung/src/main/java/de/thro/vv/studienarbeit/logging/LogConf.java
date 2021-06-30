package de.thro.vv.studienarbeit.logging;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.*;

public class LogConf {

    private static final Logger LOGGER = Logger.getLogger(LogConf.class.getName());


    private LogConf(){

    }

    public static boolean startLogger(){
        try {
            configureLogger();
        }
        catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Logger Name konnte nicht gesetzt werden!");
            return false;
        }

        //Logger gibt am Anfang Informationen aus
        loggerStartInformation();
        return true;
    }


    private static void configureLogger(){
        Handler handler = null;
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
        try {
            handler = new FileHandler( Paths.get(System.getenv().getOrDefault("LOG_FILE", "logger.log")).toString());
            handler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        var logger = Logger.getLogger("");
        String logLevel = System.getenv().getOrDefault("LOG_LEVEL", "INFO");
        logger.setLevel("INFO".equals(logLevel) ? Level.INFO : Level.SEVERE);   //It is given that only these two options exist.
        logger.addHandler(handler);
    }


    private static void loggerStartInformation(){
        LOGGER.info("Programm startet");
        LOGGER.info("Java: " + System.getenv("JAVA_HOME"));
        LOGGER.info("Username: " + System.getenv("USERNAME"));
        LOGGER.info("Operating System: " + System.getenv("OS"));
        LOGGER.info("PORT: " + System.getenv("PORT"));
        LOGGER.info("Log-File: " + System.getenv("LOG_FILE"));
        LOGGER.info("Json-File: " + System.getenv("JSON_FILE"));
        LOGGER.info("Log-Level: " + System.getenv("LOG_LEVEL"));
    }
}
