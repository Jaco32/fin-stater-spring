package edu.jaco.fin_stater.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class UserRoutingDataSource extends AbstractRoutingDataSource {

    @Value("{DB_DEFAULT_SCHEMA}")
    private String lookupKey;

    @Override
    protected Object determineCurrentLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }
}
