package com.jaerapps.datastore;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jaerapps.generated.jooq.public_.tables.records.PlayRecord;
import com.jaerapps.pojo.DatabaseRowToPojoTranslator;
import com.jaerapps.pojo.PlayPojo;
import org.jooq.CloseableDSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static com.jaerapps.generated.jooq.public_.Tables.PLAY;
import static com.jaerapps.guice.BasicModule.DATABASE_URL;

public class PlayDAO {
    private final String databaseUrl;

    @Inject
    public PlayDAO(@Named(DATABASE_URL) String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public UUID insert(@Nonnull PlayPojo playToInsert) throws DataAccessException {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            return ctx
                    .insertInto(PLAY)
                    .columns(PLAY.PLAY_ID, PLAY.GAME_ID, PLAY.CREATION_DATE, PLAY.PITCH_VALUE)
                    .values(
                            playToInsert.getPlayId(),
                            playToInsert.getGameId(),
                            OffsetDateTime.ofInstant(playToInsert.getCreationDate().toInstant(), ZoneId.of("UTC")),
                            playToInsert.getPitchValue()
                    )
                    .returningResult(PLAY.PLAY_ID)
                    .fetchOne()
                    .get(PLAY.PLAY_ID);
        }
    }

    public Optional<PlayPojo> getActivePlay() {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            PlayRecord record = ctx
                    .selectFrom(PLAY)
                    .where(PLAY.PITCH_VALUE.isNull())
                    .fetchOne();
            return DatabaseRowToPojoTranslator.playFromRecord(record);
        }
    }

    public Optional<UUID> resolvePlay(Integer pitchNumber) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            return Optional.of(ctx
                    .update(PLAY)
                    .set(PLAY.PITCH_VALUE, pitchNumber)
                    .where(PLAY.PITCH_VALUE.isNull())
                    .returningResult(PLAY.PLAY_ID)
                    .fetchOne()
                    .get(PLAY.PLAY_ID));
        }
    }
}
