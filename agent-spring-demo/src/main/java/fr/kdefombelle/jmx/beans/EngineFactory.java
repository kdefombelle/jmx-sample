package fr.kdefombelle.jmx.beans;

public class EngineFactory {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Engine createEngine(final String id) {
        return new Engine(id);
    }

}
