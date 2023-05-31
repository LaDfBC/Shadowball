package com.jaerapps.service;

import com.google.inject.Inject;
import com.jaerapps.datastore.GameDAO;
import com.jaerapps.datastore.GuessDAO;
import com.jaerapps.pojo.FullGuessContextPojo;

import javax.annotation.Nonnull;
import java.util.List;

public class ExtractionService {
    private final GameDAO gameDAO;

    @Inject
    public ExtractionService(@Nonnull final GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public List<FullGuessContextPojo> fetchAllData() {
        return gameDAO.extractAllHistory();
    }


}
