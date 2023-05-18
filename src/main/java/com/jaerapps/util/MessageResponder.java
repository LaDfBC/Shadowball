package com.jaerapps.util;

import com.google.common.collect.Lists;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.List;

public class MessageResponder {
    private final List<Object> queuedMessages;
    private final MessageChannel channel;

    private MessageResponder(MessageChannel channel) {
        queuedMessages = Lists.newArrayList();
        this.channel = channel;
    }

    public List<Object> getMessages() {
        return queuedMessages;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public static MessageResponder create(MessageChannel channel) {
        return new MessageResponder(channel);
    }


    public MessageResponder addMessage(CharSequence message) {
        this.queuedMessages.add(message);
        return this;
    }

    public MessageResponder addMessage(MessageEmbed message) {
        this.queuedMessages.add(message);
        return this;
    }

    public MessageResponder addMessage(Message message) {
        this.queuedMessages.add(message);
        return this;
    }

    public void sendResponseMessages() {
        for (Object currentMessage : queuedMessages) {
            if (currentMessage instanceof MessageEmbed) {
                channel
                        .sendMessageEmbeds((MessageEmbed) currentMessage)
                        .queue();
            } else {
                channel.sendMessage("The dev messed up and tried to send a message that wasn't embedded properly.  Tell them to fix that.")
                        .queue();
            }
        }
    }

    public void addAllMessages(List<Object> messageList) {
        queuedMessages.addAll(messageList);
    }
}