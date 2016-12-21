package fr.kdefombelle.jmx.server;

import java.util.Collections;

import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXPrincipal;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleJmxAuthenticator implements JMXAuthenticator {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(SimpleJmxAuthenticator.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Subject authenticate(Object credentials) {
        boolean authenticated = false;
        // Verify that credentials is of type String[].
        if (!(credentials instanceof String[])) {
            // Special case for null so we get a more informative message
            if (credentials == null) {
                throw new SecurityException("Credentials required");
            }
            throw new SecurityException("Credentials should be String[]");
        }

        // Verify that the array contains three elements (username/password/realm).
        final String[] castedCredentials = (String[]) credentials;
        if (castedCredentials.length != 2) {
            throw new SecurityException("Credentials should have 2 elements");
        }

        String username = castedCredentials[0];
        String password = castedCredentials[1];

        // Perform authentication (here always OK)
        authenticated = true;
        logger.debug("Credentials are {}:{}", username, password);
        if (authenticated) {
            return new Subject(true, Collections.singleton(new JMXPrincipal(username)), Collections.EMPTY_SET, Collections.EMPTY_SET);
        }
        throw new SecurityException("Invalid credentials");
    }
}
