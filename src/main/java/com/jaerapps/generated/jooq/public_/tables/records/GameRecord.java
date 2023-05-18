/*
 * This file is generated by jOOQ.
 */
package com.jaerapps.generated.jooq.public_.tables.records;


import com.jaerapps.generated.jooq.public_.tables.Game;

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
public class GameRecord extends UpdatableRecordImpl<GameRecord> implements Record4<UUID, Integer, Integer, Boolean> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.game.game_id</code>.
     */
    public void setGameId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.game.game_id</code>.
     */
    public UUID getGameId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.game.session_number</code>.
     */
    public void setSessionNumber(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.game.session_number</code>.
     */
    public Integer getSessionNumber() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.game.season_number</code>.
     */
    public void setSeasonNumber(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.game.season_number</code>.
     */
    public Integer getSeasonNumber() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.game.currently_active</code>.
     */
    public void setCurrentlyActive(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.game.currently_active</code>.
     */
    public Boolean getCurrentlyActive() {
        return (Boolean) get(3);
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
    public Row4<UUID, Integer, Integer, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, Integer, Integer, Boolean> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Game.GAME.GAME_ID;
    }

    @Override
    public Field<Integer> field2() {
        return Game.GAME.SESSION_NUMBER;
    }

    @Override
    public Field<Integer> field3() {
        return Game.GAME.SEASON_NUMBER;
    }

    @Override
    public Field<Boolean> field4() {
        return Game.GAME.CURRENTLY_ACTIVE;
    }

    @Override
    public UUID component1() {
        return getGameId();
    }

    @Override
    public Integer component2() {
        return getSessionNumber();
    }

    @Override
    public Integer component3() {
        return getSeasonNumber();
    }

    @Override
    public Boolean component4() {
        return getCurrentlyActive();
    }

    @Override
    public UUID value1() {
        return getGameId();
    }

    @Override
    public Integer value2() {
        return getSessionNumber();
    }

    @Override
    public Integer value3() {
        return getSeasonNumber();
    }

    @Override
    public Boolean value4() {
        return getCurrentlyActive();
    }

    @Override
    public GameRecord value1(UUID value) {
        setGameId(value);
        return this;
    }

    @Override
    public GameRecord value2(Integer value) {
        setSessionNumber(value);
        return this;
    }

    @Override
    public GameRecord value3(Integer value) {
        setSeasonNumber(value);
        return this;
    }

    @Override
    public GameRecord value4(Boolean value) {
        setCurrentlyActive(value);
        return this;
    }

    @Override
    public GameRecord values(UUID value1, Integer value2, Integer value3, Boolean value4) {
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
     * Create a detached GameRecord
     */
    public GameRecord() {
        super(Game.GAME);
    }

    /**
     * Create a detached, initialised GameRecord
     */
    public GameRecord(UUID gameId, Integer sessionNumber, Integer seasonNumber, Boolean currentlyActive) {
        super(Game.GAME);

        setGameId(gameId);
        setSessionNumber(sessionNumber);
        setSeasonNumber(seasonNumber);
        setCurrentlyActive(currentlyActive);
    }
}
