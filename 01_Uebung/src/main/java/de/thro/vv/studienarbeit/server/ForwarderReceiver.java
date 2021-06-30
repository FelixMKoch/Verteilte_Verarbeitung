package de.thro.vv.studienarbeit.server;

import de.thro.vv.studienarbeit.model.Message;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A helper class for receiving and sending data to a certain socket.
 * Also implements the Closable interface!
 */
public class ForwarderReceiver implements Closeable {

    private static final Logger LOGGER = Logger.getLogger("");
    private final PrintStream writeToServer;
    private final Scanner readFromServer;

    /**
     * The constructor takes in a socket and opens two streams: One input and one output stream.
     * @param peer The Socket this class should send its data afterwards.
     * @throws IOException If the In-/Outputstream cannot be made.
     */
    public ForwarderReceiver(Socket peer) throws IOException{
        try {
            this.writeToServer = new PrintStream(peer.getOutputStream());
            this.readFromServer = new Scanner(peer.getInputStream());
        } catch (IOException x) {
            LOGGER.log(Level.SEVERE, x.getMessage(), x);
            throw new IOException();
        }
    }

    /**
     * Closes both of the streams that were made while constructing this object.
     */
    @Override
    public void close(){
        this.readFromServer.close();
        this.writeToServer.close();
    }

    /**
     * sends a 'Message'-object to the other socket.
     * @param message a Message object with type and payload
     */
    public void forward(Message message) {
        LOGGER.info("Message is being sent");
        writeToServer.println(message.toString());
        writeToServer.flush();
    }

    /**
     * Gives back a 'Message'-object that was received from the client.
     * @return The message-object
     */
    public Message receive(){
        //Wait until the client sends something
        while(!readFromServer.hasNextLine());

        Message response = new Message(readFromServer.nextLine());
        LOGGER.info("Received:" + response.toString());
        return response;
    }
}
