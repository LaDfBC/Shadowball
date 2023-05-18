package com.jaerapps;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class ResponseMessageBuilder {
    public static MessageEmbed buildErrorResponse(String message) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Error Encountered");
        eb.setColor(Color.RED);
        eb.addField("Error: ", message != null ? message : "Caught an unhandled exception with no message.  " +
                "This should be considered a critical exception - please see the footer for how to contact this " +
                "bot's developer and let them know they messed up big time.  Thanks!", false);
        eb.setFooter("If this is reoccurring or consistent, please consider messaging this bot's author: LaDfBC#1246");
        return eb.build();
    }
}
