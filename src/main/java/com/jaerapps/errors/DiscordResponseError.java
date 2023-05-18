package com.jaerapps.errors;


/**
 * End layer error built to deliver an error response to Discord.
 */
public class DiscordResponseError extends Error {
    private String message;
    private Throwable cause;

    public DiscordResponseError(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public DiscordResponseError(String message) {
        this.message = message;
        this.cause = null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
