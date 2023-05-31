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
        for (PointsPojo currentMember : sortedMemberPoints) {
            pointsOutput.append(currentMember.getMemberName()).append("\t\t | \t\t").append(currentMember.getPoints()).append("\n");
        }

        return ResponseMessageBuilder.buildStandardResponse(
                "Leaderboard:",
                pointsOutput.toString()
        );
    }
}
