package de.thro.vv.studienarbeit.server;

import de.thro.vv.studienarbeit.MeasurementService;
import de.thro.vv.studienarbeit.automat.Automat;
import de.thro.vv.studienarbeit.model.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiServer {

    private static final int PORT = Integer.parseInt(System.getenv().getOrDefault("PORT", "1024"));
    private static final Logger LOGGER = Logger.getLogger(MultiServer.class.getName());
    private final ExecutorService executorService;

    private ServerSocket serverSocket = null;

    private List<Socket> allConnected;


    public MultiServer(){
        allConnected = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(1);
    }


    /**
     * A Method to start the MultiServer. by calling it, you also start the Measurement-Service.
     */
    private void startRoutine(){
        MeasurementService measurementService = MeasurementService.getMeasurementService();

        try(ServerSocket server = new ServerSocket(PORT)) {
            measurementService.start();
            serverSocket = server;
            LOGGER.log(Level.INFO, "Server startet");

            while (!executorService.isShutdown() || !Thread.currentThread().isInterrupted()) {
                Socket client = server.accept();
                allConnected.add(client);
                LOGGER.info("Neuer Client verbindet sich mit server!");
                new Client().handle(client, this);
            }

        }
        catch(SocketException se){
            LOGGER.log(Level.INFO, "Socket Exception in Multiserver!");

        }
        catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Server was Interrupted!", ex);
        }

        finally {
            sendAllConnectedError();
            stop();
        }
    }

    /**
     * If the ExecutorService runs, shut it down
     */
    public void stop(){
        if(!executorService.isShutdown()) executorService.shutdown();
    }

    /**
     * Starts the server if it is not already started
     */
    public void start(){
        executorService.execute(this::startRoutine);
    }

    /**
     * Checks if server is running
     * @return Boolean if server is running
     */
    public boolean isStarted(){
        return !executorService.isShutdown();
    }

    /**
     * Interrupts the server and exits.
     */
    public void interrupt(){
        sendAllConnectedError();
        try {
            if (serverSocket != null) serverSocket.close();
        }
        catch(IOException e){
            LOGGER.warning("Fehler beim closen des Multi-Servers!");
        }
    }

    /**
     * Removes a Socket from the allConnected list. Usually called, if client quit the connection.
     * @param socket Socket to be removed
     */
    public void remFromConnected(Socket socket){
        allConnected.remove(socket);
    }

    /**
     * Sends an 'ERROR' message to all sockets that are currently connected to this server.
     */
    private void sendAllConnectedError() {
        LOGGER.log(Level.INFO, "Sending an error message to all the Clients that are currently connected th this server.");
        allConnected
                .forEach(socket -> {
                    try (ForwarderReceiver toClient = new ForwarderReceiver(socket)) {
                        toClient.forward(new Message(Automat.Symbol.ERROR, "{}"));
                    } catch (IOException ex) {
                        LOGGER.info("Wasn't able to send error message");
                    }
                });
    }
}
