package fr.kdefombelle.jmx.beans;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

import fr.kdefombelle.jmx.JmxConstants;
import fr.kdefombelle.jmx.JmxConstantsDemo;
import fr.kdefombelle.jmx.ObjectNameFactory;


@ManagedResource(objectName = "Demo:type=engine,name=E1")
public class Engine implements NotificationPublisherAware {

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

    private NotificationPublisher notificationPublisher;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Engine(String id) {
        this.id = id;
        this.state = "INIT";
        this.objectNameFactory = new ObjectNameFactory(JmxConstantsDemo.DOMAIN);
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static Engine createEngine(String id) {
        return new Engine(id);
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void start() {
        logger.debug("{}:start",id);
        String oldState = state;
        state = "STARTED";
        sendNotification(oldState);
    }

    public void stop() {
        logger.debug("{}:stop",id);
        String oldState = state;
        state = "STOPPED";
        sendNotification(oldState);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    private void sendNotification(String oldState) {
        Notification n = new AttributeChangeNotification(objectNameFactory.createObjectName(JmxConstantsDemo.KEY_ENGINE, id), sequenceNumber, System.currentTimeMillis(), "State of provider " + id + " has changed", "State",
                "String", oldState, this.state);
        notificationPublisher.sendNotification(n);
    }

}
