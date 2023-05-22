package com.jaerapps.service;

import com.google.inject.Inject;
import com.jaerapps.datastore.GameDAO;
import com.jaerapps.datastore.GuessDAO;
import com.jaerapps.pojo.GuessPojo;

import javax.annotation.Nonnull;
import java.util.Optional;

public class GuessService {
    private final GuessDAO guessDAO;
    @Inject
    public GuessService(@Nonnull final GuessDAO guessDAO) {
        this.guessDAO = guessDAO;
    }

    public void upsert(GuessPojo guessPojo) {
        Optional<GuessPojo> possiblyExistingGuess = guessDAO.getByPlayAndMember(
                guessPojo.getPlayId(), guessPojo.getMemberId(), guessPojo.getMemberName());

        if (possiblyExistingGuess.isEmpty()) {
            guessDAO.insert(guessPojo);
        } else {
            guessDAO.update(guessPojo);
        }
    }
}
