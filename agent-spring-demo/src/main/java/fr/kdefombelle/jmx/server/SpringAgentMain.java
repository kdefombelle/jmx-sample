package fr.kdefombelle.jmx.server;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringAgentMain extends AbstractJmxMain{

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------
	
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
        		new String[] { "fr/kdefombelle/jmx/application-context.xml" });
        waitForEnterPressed();
    }

}
