package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.pojo.GamePojo;
import com.jaerapps.service.GameService;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class SetSessionCommand implements ICommand{
    private final GameService gameService;

    @Inject
    public SetSessionCommand(@Nonnull final GameService gameService) {
        this.gameService = gameService;
    }
    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        Integer sessionNumber;
        OptionMapping sessionOption = event.getOption("session");
        if (sessionOption == null) {
            return ResponseMessageBuilder.buildErrorResponse("No season number given!");
        } else {
            sessionNumber = sessionOption.getAsInt();
        }

        Optional<GamePojo> possibleActiveGame = gameService.getActiveGame();
        if (possibleActiveGame.isEmpty()) {
            return ResponseMessageBuilder.buildWarningResponse(
                    "There is no active game and I won't try to guess what season it is.  Set the season first with my command /set-season");
        } else {
            Integer seasonNumber = possibleActiveGame.get().getSeasonNumber();
            Optional<GamePojo> possibleExistingGame = gameService.fetchGameBySeasonAndSession(seasonNumber, sessionNumber);
            if (possibleExistingGame.isPresent()) {
                gameService.setActive(possibleExistingGame.get().getGameId());
                return ResponseMessageBuilder.buildSketchyActionResponse(
                        "I set the game for season " + seasonNumber + " and session " + sessionNumber + " as active.",
                        "That game already existed though!  " +
                                "You are repeating a season and session previously used, please verify you used the correct numbers!"
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
}
