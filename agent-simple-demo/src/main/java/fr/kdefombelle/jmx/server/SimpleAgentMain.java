package fr.kdefombelle.jmx.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import javax.management.remote.JMXConnectorServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.jmx.JmxConstantsDemo;
import fr.kdefombelle.jmx.JmxUrlBuilder;
import fr.kdefombelle.jmx.ObjectNameFactory;
import fr.kdefombelle.jmx.beans.Engine;
import fr.kdefombelle.jmx.beans.EngineMXBean;

public class SimpleAgentMain extends AbstractJmxMain{

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    /** The logger used by this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAgentMain.class);
    private static String RMI_REGISTRY_PORT="1617";
    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        //create JMX agent
        HashMap<String, Object> env = new HashMap<String, Object>();
        env.put(JMXConnectorServer.AUTHENTICATOR, new SimpleJmxAuthenticator());
        String jmxUrl = new JmxUrlBuilder().setRmiRegistryPort(RMI_REGISTRY_PORT).build();
        SimpleAgent agent = new SimpleAgent(jmxUrl, env, new ObjectNameFactory(JmxConstantsDemo.DOMAIN));

        //MBean to monitor
        EngineMXBean engine1 = new Engine("E1");
        EngineMXBean engine2 = new Engine("E2");

        try {
            //start RMI registry
            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(RMI_REGISTRY_PORT));
            LOGGER.debug("RMI Registry created: " + registry);

            //register MBean
            agent.registerMBean(engine1, JmxConstantsDemo.KEY_ENGINE, engine1.getId());
            agent.registerMBean(engine2, JmxConstantsDemo.KEY_ENGINE, engine2.getId());
            LOGGER.debug("Registring {}:{}", JmxConstantsDemo.KEY_ENGINE, engine1.getId());
            LOGGER.debug("Registring {}:{}", JmxConstantsDemo.KEY_ENGINE, engine2.getId());
            
            //start JMX connector
            LOGGER.info("JMX connector started");
            agent.startJmxConnector();
            
            waitForEnterPressed();

            //unregister MBean
            agent.unregisterMBean(JmxConstantsDemo.KEY_ENGINE, engine1.getId());
            agent.unregisterMBean(JmxConstantsDemo.KEY_ENGINE, engine2.getId());
            LOGGER.debug("Unregistring {}", engine1.getId());
            LOGGER.debug("Unregistring {}", engine2.getId());
            waitForEnterPressed();

        } catch (Exception e) {
            LOGGER.error("Error while processing", e);
            throw new RuntimeException(e);
        } finally {
            //stop JMX connector
            agent.stopJmxConnector();
            LOGGER.info("JMX connector stopped");
        }
    }

}
