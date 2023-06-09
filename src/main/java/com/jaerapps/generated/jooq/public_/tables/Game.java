/*
 * This file is generated by jOOQ.
 */
package com.jaerapps.generated.jooq.public_.tables;


import com.jaerapps.generated.jooq.public_.Keys;
import com.jaerapps.generated.jooq.public_.Public;
import com.jaerapps.generated.jooq.public_.tables.records.GameRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Game extends TableImpl<GameRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.game</code>
     */
    public static final Game GAME = new Game();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GameRecord> getRecordType() {
        return GameRecord.class;
    }

    /**
     * The column <code>public.game.game_id</code>.
     */
    public final TableField<GameRecord, UUID> GAME_ID = createField(DSL.name("game_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.game.session_number</code>.
     */
    public final TableField<GameRecord, Integer> SESSION_NUMBER = createField(DSL.name("session_number"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.game.season_number</code>.
     */
    public final TableField<GameRecord, Integer> SEASON_NUMBER = createField(DSL.name("season_number"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.game.currently_active</code>.
     */
    public final TableField<GameRecord, Boolean> CURRENTLY_ACTIVE = createField(DSL.name("currently_active"), SQLDataType.BOOLEAN.nullable(false), this, "");

    private Game(Name alias, Table<GameRecord> aliased) {
        this(alias, aliased, null);
    }

    private Game(Name alias, Table<GameRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.game</code> table reference
     */
    public Game(String alias) {
        this(DSL.name(alias), GAME);
    }

    /**
     * Create an aliased <code>public.game</code> table reference
     */
    public Game(Name alias) {
        this(alias, GAME);
    }

    /**
     * Create a <code>public.game</code> table reference
     */
    public Game() {
        this(DSL.name("game"), null);
    }

    public <O extends Record> Game(Table<O> child, ForeignKey<O, GameRecord> key) {
        super(child, key, GAME);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<GameRecord> getPrimaryKey() {
        return Keys.GAME_PKEY;
    }

    @Override
    public List<UniqueKey<GameRecord>> getKeys() {
        return Arrays.<UniqueKey<GameRecord>>asList(Keys.GAME_PKEY);
    }

    @Override
    public Game as(String alias) {
        return new Game(DSL.name(alias), this);
    }

    @Override
    public Game as(Name alias) {
        return new Game(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Game rename(String name) {
        return new Game(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Game rename(Name name) {
        return new Game(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, Integer, Integer, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
