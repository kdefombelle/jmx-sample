
package fr.kdefombelle.jmx;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class JmxUrl {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static final String RMI_REGISTRY_PORT = "1617";

    private static String HOST = null;

    static {
        try {
            HOST = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * The JMX Service URL, It could be:<br/>
     *
     * <ul>
     * <li>JMX_RMI_SERVER_PORT fixed: <code>
     * service:jmx:rmi://<HOST>:<JMX_RMI_SERVER_PORT>/jndi/rmi://<HOST>:<RMI_REGISTRY_PORT>/jmxrmi</code></li>
     * <li>usually it is dynamically allocated by the JVM: <code>
     * service:jmx:rmi://<HOST>:/jndi/rmi://<HOST>:<RMI_REGISTRY_PORT>/jmxrmi</code></li>
     * <li>HOST for the JMX_RMI_SERVER_PORT could be deduced from the HOST of the RmiRegistry: <code>
     * service:jmx:rmi:///jndi/rmi://<HOST>:<RMI_REGISTRY_PORT>/jmxrmi</code></li>
     * </ul>
     */
    public static final String JMX_URL = "service:jmx:rmi:///jndi/rmi://" + HOST + ":" + RMI_REGISTRY_PORT + "/jmxrmi";
}
