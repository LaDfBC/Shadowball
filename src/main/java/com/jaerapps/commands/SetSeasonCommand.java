package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.pojo.GamePojo;
import com.jaerapps.service.GameService;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SetSeasonCommand implements ICommand {
    private final GameService gameService;

    @Inject
    public SetSeasonCommand(@Nonnull final GameService gameService) {
        this.gameService = gameService;
    }
    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        Integer seasonNumber;
        OptionMapping seasonOption = event.getOption("season");
        if (seasonOption == null) {
            return ResponseMessageBuilder.buildErrorResponse("No season number given!");
        } else {
            seasonNumber = seasonOption.getAsInt();
        }

        // Optional Parameter - allow nulls
        OptionMapping sessionNumberString = event.getOption("session");
        Integer sessionNumber = sessionNumberString != null ? sessionNumberString.getAsInt() : null;

        Optional<GamePojo> possibleExistingGame = gameService.fetchGameBySeasonAndSession(seasonNumber, sessionNumber);
        if (possibleExistingGame.isPresent()) {
            gameService.setActive(possibleExistingGame.get().getGameId());
            return ResponseMessageBuilder.buildSketchyActionResponse(
                    "I set the game for season " + seasonNumber + " and session " + sessionNumber + " as active.",
                    "That game already existed though!  " +
                            "You are repeating a season and session previously used, please verify you used the corrrect numbers!"
            );
        } else {
            gameService.insert(
                    GamePojo
                            .builder()
                            .withGameId(UUID.randomUUID())
                            .withSeasonNumber(seasonNumber)
                            .withSessionNumber(sessionNumber)
                            .isCurrentlyActive(true)
                            .build()
            );
            return ResponseMessageBuilder.buildStandardResponse(
                    "Successfully Set Season!",
                    "You are now recording plays for season " + seasonNumber + " and session " + sessionNumber
            );
        }
    }
}
