package com.jaerapps.enums;

public enum CoreCommand {
    HELP("help"),
    PREFIX("prefix"),
    GHOSTBALL("shadowball"),
    GUESS("guess"),
    RESOLVE("resolve"),
    POINTS("points"),
    SET_SEASON("set-season"),
    GET_GAME_INFO("get-game"),
    SET_SESSION("set-session"),
//    ADD_SCORING_LEVEL("add-scoring-level"),
//    REMOVE_SCORING_LEVEL("remove-scoring-level"),
    EXTRACT_DATA("extract-data");

    private final String commandName;

    CoreCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
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
