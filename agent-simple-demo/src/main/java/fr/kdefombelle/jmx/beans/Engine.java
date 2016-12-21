package fr.kdefombelle.jmx.beans;

import java.io.IOException;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.OperationsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.jmx.JmxConstantsDemo;
import fr.kdefombelle.jmx.ObjectNameFactory;


public class Engine extends NotificationBroadcasterSupport implements EngineMXBean {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private String id;
    private String state;
    private long sequenceNumber = 1;

    private ObjectNameFactory objectNameFactory;
    private EngineDetails engineDetails;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Engine(String id) {
        this.id = id;
        this.state = "INIT";
        this.objectNameFactory = new ObjectNameFactory(JmxConstantsDemo.DOMAIN);
        setEngineDetails(new EngineDetails("as" + id, System.currentTimeMillis()));
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static Engine createEngine(String id) {
        return new Engine(id);
    }

    public final void setEngineDetails(EngineDetails engineDetails) {
        this.engineDetails = engineDetails;
    }

    @Override
    public EngineDetails getDetails() {
        return engineDetails;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    @Override
    public void start() throws OperationsException, IOException {
        logger.debug("{}:start",id);
        String oldState = state;
        state = "STARTED";
        sendNotification(oldState);
    }

    @Override
    public void stop() throws OperationsException, IOException {
        logger.debug("{}:stop",id);
        String oldState = state;
        state = "STOPPED";
        sendNotification(oldState);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute (message) of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }

    @Override
    public String toString() {
        return id;
    }

    private void sendNotification(String oldState) {
        Notification n = new AttributeChangeNotification(objectNameFactory.createObjectName(JmxConstantsDemo.KEY_ENGINE, id), sequenceNumber, System.currentTimeMillis(), "State of provider " + id + " has changed", "State",
                "String", oldState, this.state);
        sendNotification(n);
    }

}
