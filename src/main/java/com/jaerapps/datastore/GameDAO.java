package com.jaerapps.datastore;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jaerapps.generated.jooq.public_.tables.records.GameRecord;
import com.jaerapps.pojo.DatabaseRowToPojoTranslator;
import com.jaerapps.pojo.FullGuessContextPojo;
import com.jaerapps.pojo.GamePojo;
import org.jooq.CloseableDSLContext;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jaerapps.generated.jooq.public_.Tables.*;
import static com.jaerapps.guice.BasicModule.DATABASE_URL;


public class GameDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameDAO.class);

    private final String databaseUrl;

    @Inject
    public GameDAO(@Named(DATABASE_URL) String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void insert(@Nonnull GamePojo gameToInsert) throws DataAccessException {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            ctx
                    .insertInto(GAME)
                    .columns(GAME.GAME_ID, GAME.SEASON_NUMBER, GAME.SESSION_NUMBER, GAME.CURRENTLY_ACTIVE)
                    .values(
                            gameToInsert.getGameId(),
                            gameToInsert.getSeasonNumber(),
                            gameToInsert.getSessionNumber(),
                            gameToInsert.getCurrentlyActive() != null && gameToInsert.getCurrentlyActive()
                    )
                    .execute();
        }
    }

    public Optional<GamePojo> getActiveGame() {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            GameRecord record = ctx
                    .selectFrom(GAME)
                    .where(GAME.CURRENTLY_ACTIVE.isTrue())
                    .fetchOne();
            return DatabaseRowToPojoTranslator.gameFromRecord(record);
        }
    }

    public Optional<GamePojo> fetchGameBySeasonAndSession(Integer seasonNumber, Integer sessionNumber) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            GameRecord record = ctx
                    .selectFrom(GAME)
                    .where(GAME.SEASON_NUMBER.eq(seasonNumber))
                    .and(GAME.SESSION_NUMBER.eq(sessionNumber))
                    .fetchOne();

            return DatabaseRowToPojoTranslator.gameFromRecord(record);
        }
    }

    public void setAllGamesInactive() {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            ctx
                    .update(GAME)
                    .set(GAME.CURRENTLY_ACTIVE, false)
                    .execute();
        }
    }

    public void setGameActiveById(UUID gameId) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            ctx
                    .update(GAME)
                    .set(GAME.CURRENTLY_ACTIVE, true)
                    .where(GAME.GAME_ID.eq(gameId))
                    .execute();
        }
    }

    public List<FullGuessContextPojo> extractAllHistory() {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            List<Record> allRecords = ctx
                    .select(GAME.asterisk(), PLAY.asterisk(), GUESS.asterisk())
                    .from(GAME)
                    .join(PLAY)
                    .on(GAME.GAME_ID.eq(PLAY.GAME_ID))
                    .join(GUESS)
                    .on(PLAY.PLAY_ID.eq(GUESS.PLAY_ID))
                    .fetch();

            return DatabaseRowToPojoTranslator.fullContextFromRecords(allRecords);
        }
    }
}
