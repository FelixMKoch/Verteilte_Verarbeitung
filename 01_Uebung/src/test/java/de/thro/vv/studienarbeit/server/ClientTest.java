package de.thro.vv.studienarbeit.server;

import de.thro.vv.studienarbeit.MeasurementService;
import de.thro.vv.studienarbeit.automat.Automat;
import de.thro.vv.studienarbeit.model.Message;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientTest {

    @Test
    void handle() {
        MultiServer multiServer = new MultiServer();
        multiServer.start();
        Client client = new Client();
        Socket socket = new Socket();


        client.handle(socket, multiServer);
        ForwarderReceiver forwarderReceiver;
        try {
            forwarderReceiver = new ForwarderReceiver(socket);
        }
        catch(IOException ioException){
            return;
        }

        forwarderReceiver.forward(new Message(Automat.Symbol.SENSORHELLO, "SensorHello"));
        Assertions.assertEquals(forwarderReceiver.receive(), new Message(Automat.Symbol.STATIONHELLO, "{}"));

        forwarderReceiver.forward(new Message(Automat.Symbol.ACKNOWLEDGE, "StationHello"));
        Assertions.assertEquals(forwarderReceiver.receive(), new Message(Automat.Symbol.STATIONREADY, "{}"));

        forwarderReceiver.forward(new Message(Automat.Symbol.MEASUREMENT, "Measurement"));
        Assertions.assertEquals(forwarderReceiver.receive(), new Message(Automat.Symbol.STATIONREADY, "{}"));

        forwarderReceiver.forward(new Message(Automat.Symbol.TERMINATESTATION, "Terminate"));
        Assertions.assertFalse(multiServer.isStarted());
    }

}