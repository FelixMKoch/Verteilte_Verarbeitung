package de.thro.vv.studienarbeit.server;

import de.thro.vv.studienarbeit.MeasurementService;
import de.thro.vv.studienarbeit.automat.Automat;
import de.thro.vv.studienarbeit.model.Message;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final int MAX_THREADS = 2;

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private static final ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS, (r)->{
        Thread t = Executors.defaultThreadFactory().newThread(r);
        t.setDaemon(true);
        return t;
    });




    public Client(){

    }

    /**
     * This method keeps up the connection to the Client in another Thread.
     * It also goes through the stations and returns if the connection is not established properly.
     * @param client The client, all the Messages are received from or sent to
     * @param multiServer If the server should be terminated, this server is to be used
     */
    public void handle(Socket client, MultiServer multiServer){

        executorService.execute(()-> {
            boolean terminateFlag = false;
            try (client; ForwarderReceiver toClient = new ForwarderReceiver(client)){
                Automat automat = new Automat();
                Message message = toClient.receive();
                MeasurementService measurementService = MeasurementService.getMeasurementService();

                while(automat.getCurrentState() != Automat.State.ERROR
                        && automat.getCurrentState() != Automat.State.TERMINATED){

                    LOGGER.log(Level.INFO, message.getPayload());

                    //Tritt ein, falls der Client die Station herunterfahren will.
                    if(message.getType() == Automat.Symbol.TERMINATESTATION){
                        terminateFlag = true;
                        break;
                    }

                    //Falls messages empfangen werden sollen, sollen diese auch direkt in die Queue geworfen werden.
                    if(automat.getCurrentState() == Automat.State.WAITINGFORMEASUREMENT)
                        measurementService.addToQueue(message.getPayload());

                    Automat.Symbol returnSymbol = automat.nextState(message.getType());
                    Message returnMessage = new Message(returnSymbol, "{}");

                    toClient.forward(returnMessage);

                    message = toClient.receive();
                }


                //Soll der Thread und der Server terminieren, oder nur dieser Thread?
                if(terminateFlag) {
                    multiServer.interrupt();
                    executorService.shutdown();
                }

                multiServer.remFromConnected(client);

            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                multiServer.remFromConnected(client);
            }
        });
    }
}
