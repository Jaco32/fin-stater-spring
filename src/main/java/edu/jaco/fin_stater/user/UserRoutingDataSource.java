package edu.jaco.fin_stater.user;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class UserRoutingDataSource extends AbstractRoutingDataSource {

    private String lookupKey = "ANIELKA1";

    @Override
    protected Object determineCurrentLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }
}
