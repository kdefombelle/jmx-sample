package fr.kdefombelle.jmx.beans;

public class EngineFactory {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Engine createEngine(String id) {
        return new Engine(id);
    }

}
