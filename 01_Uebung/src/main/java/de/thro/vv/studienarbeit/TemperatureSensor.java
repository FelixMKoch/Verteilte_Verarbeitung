package de.thro.vv.studienarbeit;

import de.thro.vv.studienarbeit.automat.Automat;
import de.thro.vv.studienarbeit.model.Measurement;
import de.thro.vv.studienarbeit.model.Message;
import de.thro.vv.studienarbeit.server.ForwarderReceiver;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class TemperatureSensor implements Closeable {
    private static final Logger LOGGER = Logger.getLogger("");
    private ForwarderReceiver server;


    public TemperatureSensor(ForwarderReceiver toServer) {
        this.server = toServer;
    }

    @Override
    public void close() throws IOException {
        server.close();
    }



    private Message callServer(Message message) {
        server.forward(message);
        return server.receive();
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "1024"));
        try (Socket toServer = new Socket(InetAddress.getLocalHost(), port);
             TemperatureSensor sensor = new TemperatureSensor(
                     new ForwarderReceiver(toServer))) {

            Message sensorHello = new Message(Automat.Symbol.SENSORHELLO, "{\"mac\":\"00-80-41-ae-fd-7e\"}");
            Message response = sensor.callServer(sensorHello);

            Message ack = new Message(Automat.Symbol.ACKNOWLEDGE, "{}");
            response = sensor.callServer(ack);

            Random rand = new Random();

            IntStream.range(0, 10).forEach((i) -> {
                Measurement measure = new Measurement( rand.nextInt(30), Measurement.Unit.KELVIN, Measurement.Type.TEMPERATURE, LocalDateTime.now());
                Message message = new Message(Automat.Symbol.MEASUREMENT, measure.toString());
                sensor.callServer(message);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Message message = new Message(Automat.Symbol.TERMINATESTATION, "{}");
            sensor.callServer(message);
        } catch (Exception x) {
            LOGGER.log(Level.SEVERE, x.getMessage(), x);
        }
    }
}
