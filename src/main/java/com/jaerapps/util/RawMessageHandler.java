package com.jaerapps.util;

import com.google.common.collect.Lists;
import com.jaerapps.enums.CoreCommand;

import java.util.List;

public class RawMessageHandler {
    final private List<String> arguments;
    final private List<String> argumentsWithCaseRetained;
    final private String botCommandPrefix;
    final private String command;

    /**
     * Standard Constructor.  Takes the whole message in its entirety and splits it up to understand what's being asked.
     *
     * @param incomingMessage - The whole message in its entirety
     */
    public RawMessageHandler(String incomingMessage) {
        String incomingMessageLower = incomingMessage.toLowerCase();

        this.botCommandPrefix = incomingMessageLower.split("!")[0];
        this.command = incomingMessageLower.split("!", 2)[1].split(" ")[0];
        this.arguments = Lists.newArrayList(incomingMessageLower.split("!", 2)[1].split(" "));
        this.argumentsWithCaseRetained = Lists.newArrayList(incomingMessage.split("!", 2)[1].split(" "));
        arguments.remove(0);
    }

    /**
     * Fetches the command (the first bit after the !) - so for example in a!b c, the command would be "b"
     *
     * @return The CoreCommand selected by the user
     * @throws IllegalArgumentException if the command requested is not supported
     */
    public CoreCommand getRequestedCommand() {
        return CoreCommand.init(command);
    }

    /**
     * Simply a getter for all of the arguments except the first in the command.
     * So for example the arguments in a!b c d would be the list of c and d.
     *
     * @return All arguments (space separated) in a list, except the first.
     */
    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Simply a getter for all of the arguments except the first in the command.
     * So for example the arguments in a!b c d would be the list of c and d.
     *
     * @return All arguments (space separated) in a list, except the first.
     */
    public List<String> getCaseSensitiveArguments() {
        return argumentsWithCaseRetained;
    }

    /**
     * Fetches the prefix (The stuff before the !)
     * @return Just that prefix amount
     */
    public String getPrefix() {
        return botCommandPrefix;
    }

}
