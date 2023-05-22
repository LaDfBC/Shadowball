/*
 * This file is generated by jOOQ.
 */
package com.jaerapps.generated.jooq.public_.tables.records;


import com.jaerapps.generated.jooq.public_.tables.Play;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlayRecord extends UpdatableRecordImpl<PlayRecord> implements Record4<UUID, Integer, OffsetDateTime, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.play.play_id</code>.
     */
    public void setPlayId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.play.play_id</code>.
     */
    public UUID getPlayId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.play.pitch_value</code>.
     */
    public void setPitchValue(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.play.pitch_value</code>.
     */
    public Integer getPitchValue() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.play.creation_date</code>.
     */
    public void setCreationDate(OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.play.creation_date</code>.
     */
    public OffsetDateTime getCreationDate() {
        return (OffsetDateTime) get(2);
    }

    /**
     * Setter for <code>public.play.game_id</code>.
     */
    public void setGameId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.play.game_id</code>.
     */
    public UUID getGameId() {
        return (UUID) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, Integer, OffsetDateTime, UUID> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, Integer, OffsetDateTime, UUID> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Play.PLAY.PLAY_ID;
    }

    @Override
    public Field<Integer> field2() {
        return Play.PLAY.PITCH_VALUE;
    }

    @Override
    public Field<OffsetDateTime> field3() {
        return Play.PLAY.CREATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return Play.PLAY.GAME_ID;
    }

    @Override
    public UUID component1() {
        return getPlayId();
    }

    @Override
    public Integer component2() {
        return getPitchValue();
    }

    @Override
    public OffsetDateTime component3() {
        return getCreationDate();
    }

    @Override
    public UUID component4() {
        return getGameId();
    }

    @Override
    public UUID value1() {
        return getPlayId();
    }

    @Override
    public Integer value2() {
        return getPitchValue();
    }

    @Override
    public OffsetDateTime value3() {
        return getCreationDate();
    }

    @Override
    public UUID value4() {
        return getGameId();
    }

    @Override
    public PlayRecord value1(UUID value) {
        setPlayId(value);
        return this;
    }

    @Override
    public PlayRecord value2(Integer value) {
        setPitchValue(value);
        return this;
    }

    @Override
    public PlayRecord value3(OffsetDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public PlayRecord value4(UUID value) {
        setGameId(value);
        return this;
    }

    @Override
    public PlayRecord values(UUID value1, Integer value2, OffsetDateTime value3, UUID value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlayRecord
     */
    public PlayRecord() {
        super(Play.PLAY);
    }

    /**
     * Create a detached, initialised PlayRecord
     */
    public PlayRecord(UUID playId, Integer pitchValue, OffsetDateTime creationDate, UUID gameId) {
        super(Play.PLAY);

        setPlayId(playId);
        setPitchValue(pitchValue);
        setCreationDate(creationDate);
        setGameId(gameId);
    }
}
