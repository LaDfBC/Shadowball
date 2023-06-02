package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.enums.CoreCommand;
import com.jaerapps.pojo.PointsPojo;
import com.jaerapps.service.PlayService;
import com.jaerapps.service.PointsService;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PointsCommand implements ICommand {
    private final PointsService pointsService;

    @Inject
    public PointsCommand(@Nonnull final PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        Integer seasonNumber = null;
        Integer sessionNumber = null;

        OptionMapping seasonOption = event.getOption("season");
        if (seasonOption != null) {
            seasonNumber = seasonOption.getAsInt();
        }

        OptionMapping sessionOption = event.getOption("session");
        if (sessionOption != null) {
            sessionNumber = sessionOption.getAsInt();
        }

        List<PointsPojo> sortedMemberPoints = pointsService.fetchSortedPointsLeaders(seasonNumber, sessionNumber);
        StringBuilder pointsOutput = new StringBuilder();

        String header = "NAME";
        pointsOutput.append(header);
        int maxNameLength = getPlayerNameWithMostCharacters(header,sortedMemberPoints);
        int spaceCount = 0;
        while(header.length() + spaceCount < maxNameLength) {
            pointsOutput.append(" ");
            spaceCount++;
        }

        pointsOutput.append("|  POINTS\n\n");

        for (PointsPojo currentMember : sortedMemberPoints) {
            pointsOutput.append(currentMember.getMemberName());

            spaceCount = 0;
            while(currentMember.getMemberName().length() + spaceCount < maxNameLength) {
                pointsOutput.append(" ");
                spaceCount++;
            }
            pointsOutput.append("| ").append(currentMember.getPoints()).append("\n");
        }

        String title;
        if (seasonNumber == null && sessionNumber == null) {
            title = "Here's the overall leaderboard:";
        } else if (sessionNumber == null) {
            title = "Here's the leaderboard for season " + seasonNumber + ":";
        } else {
            title = "Here's the leaderboard for season " + seasonNumber + " and session " + sessionNumber + ":";
        }


        return ResponseMessageBuilder.buildStandardResponse(title, "```\n\n" + pointsOutput.toString() + "```");
    }

    private int getPlayerNameWithMostCharacters(String header, List<PointsPojo> pointsPerPlayer) {
        int max = header.length();
        for (PointsPojo currentPlayer : pointsPerPlayer) {
            if (currentPlayer.getMemberName().length() > max) {
                max = currentPlayer.getMemberName().length();
            }
        }

        return max + 1; // We're adding 1 to the end of this so that we know which integer value to match - and all values get at least 1
    }
}
