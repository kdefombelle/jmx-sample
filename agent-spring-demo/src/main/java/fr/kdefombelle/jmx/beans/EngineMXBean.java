
package fr.kdefombelle.jmx.beans;

import java.io.IOException;

import javax.management.OperationsException;


public interface EngineMXBean {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    String getId() throws OperationsException, IOException;

    void start() throws OperationsException, IOException;

    void stop() throws OperationsException, IOException;

    String getState();

}
