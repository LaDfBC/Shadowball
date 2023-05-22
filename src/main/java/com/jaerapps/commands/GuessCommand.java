package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.pojo.GuessPojo;
import com.jaerapps.pojo.PlayPojo;
import com.jaerapps.service.GuessService;
import com.jaerapps.service.PlayService;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.annotation.Nonnull;

public class GuessCommand implements ICommand {
    private final GuessService guessService;
    private final PlayService playService;

    @Inject
    public GuessCommand(@Nonnull final GuessService guessService,
                        @Nonnull final PlayService playService
    ) {
        this.guessService = guessService;
        this.playService = playService;
    }
    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        int guessNumber;
        OptionMapping guessOption = event.getOption("guess");
        if (guessOption == null) {
            return ResponseMessageBuilder.buildErrorResponse("No guess number given!");
        } else {
            guessNumber = guessOption.getAsInt();
        }

        if (guessNumber > 1000 || guessNumber < 1) {
            return ResponseMessageBuilder.buildWarningResponse("That number is not between 1 and 1000.  Dirty cheater.  Shame.  On you AND your cow.");
        }

        if (!playService.hasActivePlay()) {
            return ResponseMessageBuilder.buildWarningResponse("Hey, there's no active play!  Start one up first with /shadowball");
        } else {
            @SuppressWarnings("OptionalGetWithoutIsPresent") // We checked it above
            PlayPojo play = playService.getActivePlay().get();

            guessService.upsert(
                    GuessPojo
                            .builder()
                            .withMemberId(event.getUser().getId())
                            .withMemberName(event.getUser().getName())
                            .withGuessedNumber(guessNumber)
                            .withPlayId(play.getPlayId())
                            .build()
            );
            return ResponseMessageBuilder.buildStandardResponse("OK", "Recorded your guess of " + guessNumber);
        }
    }
}
