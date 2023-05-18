package com.jaerapps.util;

import com.jaerapps.enums.CoreCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class MessageContext {
    private final MessageReceivedEvent event;
    private final RawMessageHandler rawMessageHandler;

    public MessageContext(MessageReceivedEvent event) {
        this.event = event;
        this.rawMessageHandler = new RawMessageHandler(event.getMessage().getContentRaw());
    }

    public String getPrefix() {
        return rawMessageHandler.getPrefix();
    }

    public MessageChannel getChannel() {
        return event.getChannel();
    }

    public String getServerId() {
        return event.getGuild().getId();
    }

    public Guild getServer() {
        return event.getGuild();
    }

    public String getChannelId() {
        return getChannel().getId();
    }

    public User getAuthor() {
        return event.getAuthor();
    }

    public String getAuthorId() {
        return getAuthor().getId();
    }

    public List<String> getCommandArguments() {
        return rawMessageHandler.getArguments();
    }

    public List<String> getCaseSensitiveArguments() {
        return rawMessageHandler.getCaseSensitiveArguments();
    }

    public CoreCommand getCommand() {
        return rawMessageHandler.getRequestedCommand();
    }
}
