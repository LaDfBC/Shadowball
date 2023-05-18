package com.jaerapps.datastore;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jaerapps.datastore.rowobjects.GameDatabaseRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jaerapps.guice.BasicModule.DATABASE_URL;


public class GameDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameDAO.class);

    private final String databaseUrl;

    @Inject
    public GameDAO(@Named(DATABASE_URL) String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
}
