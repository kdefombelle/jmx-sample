package fr.kdefombelle.jmx.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AbstractJmxMain {
	
    protected static void waitForEnterPressed() throws IOException {
        System.out.println("Press  to continue...");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}
