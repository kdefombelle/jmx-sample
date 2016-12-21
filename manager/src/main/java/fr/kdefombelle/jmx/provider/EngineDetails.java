package fr.kdefombelle.jmx.provider;

import java.beans.ConstructorProperties;


public class EngineDetails {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private String aliasName;
    private long creationTimestamp;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    @ConstructorProperties({ "aliasName", "creationTimestamp" })
    public EngineDetails(String aliasName, long creationTimestamp) {
        this.aliasName = aliasName;
        this.creationTimestamp = creationTimestamp;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public String getAliasName() {
        return aliasName;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

}
