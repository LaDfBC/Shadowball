package com.jaerapps.enums;

public enum CoreCommand {
    HELP("help"),
    PREFIX("prefix"),
    GHOSTBALL("ghostball"),
    GUESS("guess"),
    RESOLVE("resolve"),
    POINTS("points"),
    SESSION_START("start-session"),
    SESSION_STOP("stop-session");


    String commandName;

    CoreCommand(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Loads an instance of this enum using the commandName String by exact match only.
     *
     * @param incomingCommand - The command sent by the user, telling the bot what to do.
     * @return The command found to match
     * @throws IllegalArgumentException if no match can be found.
     */
    public static CoreCommand init(String incomingCommand) {
        for (CoreCommand currentCommand : CoreCommand.values()) {
            if (incomingCommand.equals(currentCommand.commandName)) {
                return currentCommand;
            }
        }

        throw new IllegalArgumentException("Unrecognized command: " + incomingCommand);
    }
}
