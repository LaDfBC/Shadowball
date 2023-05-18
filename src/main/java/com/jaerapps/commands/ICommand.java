package com.jaerapps.commands;

import com.jaerapps.util.MessageContext;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public interface ICommand {
    public MessageEmbed runCommand(MessageContext context);
}
