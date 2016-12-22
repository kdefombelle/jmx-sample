/**
 * $Id: //dev/kdefombelle/jmx-sample/manager/src/main/java/com/murex/jmx/client/ManagerMain.java#2 $
 * $Change: 2412746 $
 *
 * Copyright 2009 Murex, S.A. All Rights Reserved.
 *
 * This software is the proprietary information of Murex, S.A.
 * Use is subject to license terms.
 */
package fr.kdefombelle.jmx.client;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.jmx.JmxConstantsDemo;
import fr.kdefombelle.jmx.JmxUrlBuilder;
import fr.kdefombelle.jmx.ObjectNameFactory;
import fr.kdefombelle.jmx.provider.EngineMXBean;
import fr.kdefombelle.jmx.server.AbstractJmxMain;




//TODO:add namespace annotation spring mbean
//todo: add option jconsole JMX
public class ManagerMain extends AbstractJmxMain{

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerMain.class);
    private static MBeanServerConnection mbsc;
    private static String RMI_REGISTRY_PORT="1617";
    
    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static void main(String[] argv) {
        JMXServiceURL url = new JmxUrlBuilder().setRmiRegistryPort(RMI_REGISTRY_PORT).buildUrl();
		LOGGER.debug("JMX URL created {}", url);
        RiskEngineListener listener = new RiskEngineListener();
        
        JMXConnector jmxConnectorStub;
        List<ObjectName> listenedRiskEngineMBeans = new ArrayList<ObjectName>();
        try {
            HashMap<String, Object> env = new HashMap<String, Object>();
            String[] creds = { "kdefombelle", "mrpasswd" };
            env.put(JMXConnector.CREDENTIALS, creds);

            //runtime uses a static JMX service URL to perform a JNDI lookup of the JMX connector stub in the RMI registry
            jmxConnectorStub = JMXConnectorFactory.connect(url, env);
            LOGGER.debug("Connected...");

            mbsc = jmxConnectorStub.getMBeanServerConnection();

            String[] domains = mbsc.getDomains();
            for (String domain : domains) {
                LOGGER.debug("domain: {}",domain);
            }

            //------------------------------
            //Provider bean
            //------------------------------
            ObjectNameFactory objectNameFactory = new ObjectNameFactory(JmxConstantsDemo.DOMAIN);
            //get the MXBean from the MBean server based on its object name
            ObjectName riskEngineObjectName = objectNameFactory.createObjectName(JmxConstantsDemo.KEY_ENGINE, "E1");
            EngineMXBean riskEngineMBeanProxy = JMX.newMXBeanProxy(mbsc, riskEngineObjectName, EngineMXBean.class, true);
            final NotificationEmitter emitter = proxyToEmitter(riskEngineMBeanProxy);
            emitter.addNotificationListener(listener, null, null);
            listenedRiskEngineMBeans.add(riskEngineObjectName);
            LOGGER.debug("{}:{}",riskEngineMBeanProxy.getId(),riskEngineMBeanProxy.getState());

            waitForEnterPressed();
            riskEngineMBeanProxy.stop();

            waitForEnterPressed();

            for (ObjectName listenedRiskEngineObjectName : listenedRiskEngineMBeans) {
                mbsc.removeNotificationListener(listenedRiskEngineObjectName, listener, null, null);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (OperationsException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static NotificationEmitter proxyToEmitter(Object proxy) {
        MBeanServerInvocationHandler ih = (MBeanServerInvocationHandler) Proxy.getInvocationHandler(proxy);
        MBeanServerConnection mbsc = ih.getMBeanServerConnection();
        ObjectName name = ih.getObjectName();
        return (NotificationEmitter) JMX.newMXBeanProxy(mbsc, name, Cloneable.class, true);
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Inner Classes 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static class RiskEngineListener implements NotificationListener {
        public void handleNotification(Notification notification, Object handback) {
            LOGGER.debug("Received notification\n>>>>>> {}", notification.getSource());
        }
    }
}
