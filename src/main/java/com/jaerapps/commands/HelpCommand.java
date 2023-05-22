package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

public class HelpCommand implements ICommand {
    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        return ResponseMessageBuilder.buildMultiFieldStandardResponse(
                List.of(
                    new ResponseMessageBuilder.MessageEmbedField(
                            "Overview",
                            "This bot runs entirely on slash commands.  " +
                            "Simply type a forward slash (/) in Discord to see options and parameters for each command listed here.\n" +
                                    "Ping LaDfBC#1246 for help, suggestions, or to complain."
                    ),
                    new ResponseMessageBuilder.MessageEmbedField(
                            "guess <REQUIRED: guess>",
                    "This will add your guess to the currently active play. " +
                            "I will give you a thumbs up if everything worked!"
                    ),
                    new ResponseMessageBuilder.MessageEmbedField(
                            "shadowball",
                            "Starts a new play.  You'll need to use /set-season before running this command so I understand game context"
                    ),
                    new ResponseMessageBuilder.MessageEmbedField(
                            "help",
                            "You literally just asked for this.  If you ask again, I guess I'll repeat myself."
                    ),
                    new ResponseMessageBuilder.MessageEmbedField(
                            "resolve <REQUIRED: pitch>",
                            "Closes the play and prints out points gained for each person who participated"
                    ),
                    new ResponseMessageBuilder.MessageEmbedField(
                            "set-season <REQUIRED: season> <OPTIONAL: session>",
                            "Sets the season and (if you want) session for this game. This is how I figure out how to" +
                                    " award points on a seasonal or game basis."
                    ),
                    new ResponseMessageBuilder.MessageEmbedField(
                            "set-session <REQUIRED: session>",
                            "Sets the session for this game. This is how I figure out how to" +
                                    " award points on a seasonal or game basis."
                    )
                )
        );
    }
}
