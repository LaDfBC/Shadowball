package com.jaerapps.service;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.jaerapps.datastore.GuessDAO;
import com.jaerapps.datastore.PlayDAO;
import com.jaerapps.pojo.PlayPojo;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class PlayService {
    private final PlayDAO playDAO;
    private final GuessDAO guessDAO;

    @Inject
    public PlayService(
            @Nonnull final PlayDAO playDAO,
            @Nonnull final GuessDAO guessDAO
    ) {
        this.playDAO = playDAO;
        this.guessDAO = guessDAO;
    }

    public boolean hasActivePlay() {
        return getActivePlay().isPresent();
    }

    public Optional<PlayPojo> getActivePlay() {
        return playDAO.getActivePlay();
    }

    public Optional<UUID> resolvePlay(@Nonnull Integer pitch_number) {
        Preconditions.checkNotNull(pitch_number, "Pitch Number cannot be null!");

        Optional<UUID> resolvedPlayId = playDAO.resolvePlay(pitch_number);
        resolvedPlayId.ifPresent(uuid -> guessDAO.setDifferences(pitch_number, uuid));

        return resolvedPlayId;
    }

    public UUID insert(UUID gameId) {
        return playDAO.insert(PlayPojo
                .builder()
                .withGameId(gameId)
                .withPlayId(UUID.randomUUID())
                .withCreationDate(new Date())
                .withPitchValue(null)
                .build()
        );
    }

    public UUID insert(PlayPojo fullPlay) {
        return playDAO.insert(fullPlay);
    }
}
