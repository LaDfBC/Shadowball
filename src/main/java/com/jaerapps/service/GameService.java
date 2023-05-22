package com.jaerapps.service;

import com.google.inject.Inject;
import com.jaerapps.datastore.GameDAO;
import com.jaerapps.pojo.GamePojo;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class GameService {
    private final GameDAO gameDAO;
    @Inject
    public GameService(@Nonnull final GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void insert(GamePojo gameToInsert) {
        gameDAO.setAllGamesInactive();
        gameDAO.insert(gameToInsert);
    }

    public boolean hasActiveGame() {
        return gameDAO.getActiveGame().isPresent();
    }

    public Optional<GamePojo> getActiveGame() {
        return gameDAO.getActiveGame();
    }

    public Optional<GamePojo> fetchGameBySeasonAndSession(Integer seasonNumber, Integer sessionNumber) {
        return gameDAO.fetchGameBySeasonAndSession(seasonNumber, sessionNumber);
    }

    public void setActive(UUID gameId) {
        gameDAO.setAllGamesInactive();
        gameDAO.setGameActiveById(gameId);
    }
}
