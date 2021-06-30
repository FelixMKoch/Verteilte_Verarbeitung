package de.thro.vv.studienarbeit;

import de.thro.vv.studienarbeit.logging.LogConf;
import de.thro.vv.studienarbeit.server.MultiServer;

public class Main {

    public static void main(String[] args){
        LogConf.startLogger();
        new MultiServer().start();
    }
}
