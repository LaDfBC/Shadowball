package com.jaerapps.pojo;

import com.google.common.base.Objects;

public class FullGuessContextPojo {
    private final GamePojo game;
    private final GuessPojo guess;
    private final PlayPojo play;

    private FullGuessContextPojo(
            GamePojo game,
            PlayPojo play,
            GuessPojo guess
    ) {
        this.game = game;
        this.guess = guess;
        this.play = play;
    }

    public GamePojo getGame() {
        return game;
    }

    public GuessPojo getGuess() {
        return guess;
    }

    public PlayPojo getPlay() {
        return play;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GamePojo game;
        private GuessPojo guess;
        private PlayPojo play;

        public Builder() {}

        public Builder withGame(GamePojo game) {
            this.game = game;
            return this;
        }

        public Builder withGuess(GuessPojo guess) {
            this.guess = guess;
            return this;
        }

        public Builder withPlay(PlayPojo play) {
            this.play = play;
            return this;
        }

        public FullGuessContextPojo build() {
            return new FullGuessContextPojo(game, play, guess);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullGuessContextPojo that = (FullGuessContextPojo) o;
        return Objects.equal(game, that.game) && Objects.equal(guess, that.guess) && Objects.equal(play, that.play);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(game, guess, play);
    }
}
