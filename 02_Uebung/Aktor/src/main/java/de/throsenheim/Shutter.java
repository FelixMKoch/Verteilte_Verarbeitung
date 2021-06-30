package de.throsenheim;

/**
 * An class that should represent the current state of the shutter.
 */
public abstract class Shutter {

    private static boolean status;

    public static boolean isStatus(){
        return status;
    }

    public static void setStatus(boolean newstatus){
        status = newstatus;
    }

}
