package fr.kdefombelle.jmx.provider;

import java.io.IOException;

import javax.management.NotificationEmitter;
import javax.management.OperationsException;


public interface EngineMXBean extends NotificationEmitter {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    String getId() throws OperationsException, IOException;

    void start() throws OperationsException, IOException;

    void stop() throws OperationsException, IOException;

    String getState();

    EngineDetails getDetails();

}
