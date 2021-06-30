package de.thro.vv.studienarbeit;

import de.thro.vv.studienarbeit.model.Measurement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeasurementService {

    private static final Logger LOGGER = Logger.getLogger(MeasurementService.class.getName());

    private static final BlockingQueue<String> QUEUE = new ArrayBlockingQueue<>(10);

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1, r->{
        Thread t = Executors.defaultThreadFactory().newThread(r);
        t.setDaemon(true);
        return t;
    });

    private static MeasurementService measurementService;

    // A private constructor so you cannot get an object of this class
    private MeasurementService(){

    }

    /*
    Should present a Singleton
     */
    public static MeasurementService getMeasurementService(){
        if(MeasurementService.measurementService == null)
            MeasurementService.measurementService = new MeasurementService();

        return measurementService;
    }


    /**
     * A Method to get a String and write it into a File with name and Path received by Environment variables.
     * @param json A Json-String
     */
    public boolean writeMeasurement(String json){
        Measurement measurement = Measurement.fromJson(json);
        if(measurement == null){
            LOGGER.log(Level.INFO, "Measurement aus JSON-String ergab null-Wert!");
            return false;
        }
        try (OutputStream outputStream = new FileOutputStream(Paths.get(System.getenv().getOrDefault("JSON_FILE", "json.log")).toString(), true)){
            outputStream.write((measurement.toString() + "\n").getBytes());
            outputStream.flush();
        }
        catch(IOException ex){
            LOGGER.log(Level.WARNING, "Measurement-File konnte nicht geschrieben werden.", ex);
        }
        return true;
    }

    /**
     * Adds a String to the BlockingQueue inside this Class. If the queue is already filled with Strings,
     * it waits until there is a free place.
     * @param s String which will be added to queue
     */
    public void addToQueue(String s){
        try {
            LOGGER.log(Level.INFO, s);
            if(s != null) QUEUE.put(s);
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get one item of the QUEUE
     * @return THe first String of the queue. If queue is empty, this function returns null instead of a String
     */
    public String getFromQueue() {
        if(QUEUE.isEmpty()) return null;

        try {
            return QUEUE.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, e.getMessage(), e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Starts the Measurement Service. This sets up a new Thread which constantly checks if the BlockingQueue is not empty and then calls the
     * writeMeasurement method.
     */
    public void start() {
        EXECUTOR_SERVICE.execute(()->{
            /*
            Falls der MultiServer shutdowned wurde, oder abgestürzt ist, wird dieser Thread noch so lange aufrecht gehalten,
            bis die Queue leer ist. Danach wird auch dieser Thread geshutdowned.
            */
            try {
                while (!Thread.currentThread().isInterrupted() || (!QUEUE.isEmpty())) {

                    writeMeasurement(QUEUE.take());

                }
            }
            catch (InterruptedException ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
                Thread.currentThread().interrupt();
                stop();
            }
        });
    }

    /**
     * Stops the Executor if it is started
     */
    public void stop(){
        if(!EXECUTOR_SERVICE.isShutdown()){
            EXECUTOR_SERVICE.shutdown();
        }
    }

    /**
     * Is the Service still running?
     * @return True if it's still running, false if not
     */
    public boolean isRunning(){
        return !EXECUTOR_SERVICE.isShutdown();
    }
}
