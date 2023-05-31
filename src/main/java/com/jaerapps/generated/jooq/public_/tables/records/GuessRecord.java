/*
 * This file is generated by jOOQ.
 */
package com.jaerapps.generated.jooq.public_.tables.records;


import com.jaerapps.generated.jooq.public_.tables.Guess;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GuessRecord extends UpdatableRecordImpl<GuessRecord> implements Record5<String, UUID, Integer, Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.guess.member_id</code>.
     */
    public void setMemberId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.guess.member_id</code>.
     */
    public String getMemberId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.guess.play_id</code>.
     */
    public void setPlayId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.guess.play_id</code>.
     */
    public UUID getPlayId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.guess.guessed_number</code>.
     */
    public void setGuessedNumber(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.guess.guessed_number</code>.
     */
    public Integer getGuessedNumber() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.guess.difference</code>.
     */
    public void setDifference(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.guess.difference</code>.
     */
    public Integer getDifference() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.guess.member_name</code>.
     */
    public void setMemberName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.guess.member_name</code>.
     */
    public String getMemberName() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<String, UUID, Integer, Integer, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<String, UUID, Integer, Integer, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Guess.GUESS.MEMBER_ID;
    }

    @Override
    public Field<UUID> field2() {
        return Guess.GUESS.PLAY_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Guess.GUESS.GUESSED_NUMBER;
    }

    @Override
    public Field<Integer> field4() {
        return Guess.GUESS.DIFFERENCE;
    }

    @Override
    public Field<String> field5() {
        return Guess.GUESS.MEMBER_NAME;
    }

    @Override
    public String component1() {
        return getMemberId();
    }

    @Override
    public UUID component2() {
        return getPlayId();
    }

    @Override
    public Integer component3() {
        return getGuessedNumber();
    }

    @Override
    public Integer component4() {
        return getDifference();
    }

    @Override
    public String component5() {
        return getMemberName();
    }

    @Override
    public String value1() {
        return getMemberId();
    }

    @Override
    public UUID value2() {
        return getPlayId();
    }

    @Override
    public Integer value3() {
        return getGuessedNumber();
    }

    @Override
    public Integer value4() {
        return getDifference();
    }

    @Override
    public String value5() {
        return getMemberName();
    }

    @Override
    public GuessRecord value1(String value) {
        setMemberId(value);
        return this;
    }

    @Override
    public GuessRecord value2(UUID value) {
        setPlayId(value);
        return this;
    }

    @Override
    public GuessRecord value3(Integer value) {
        setGuessedNumber(value);
        return this;
    }

    @Override
    public GuessRecord value4(Integer value) {
        setDifference(value);
        return this;
    }

    @Override
    public GuessRecord value5(String value) {
        setMemberName(value);
        return this;
    }

    @Override
    public GuessRecord values(String value1, UUID value2, Integer value3, Integer value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GuessRecord
     */
    public GuessRecord() {
        super(Guess.GUESS);
    }

    /**
     * Create a detached, initialised GuessRecord
     */
    public GuessRecord(String memberId, UUID playId, Integer guessedNumber, Integer difference, String memberName) {
        super(Guess.GUESS);

        setMemberId(memberId);
        setPlayId(playId);
        setGuessedNumber(guessedNumber);
        setDifference(difference);
        setMemberName(memberName);
    }
}
