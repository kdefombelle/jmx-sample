package fr.kdefombelle.jmx.provider;

public class EngineFactory {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Engine createEngine(final String id) {
        return new Engine(id);
    }

}
