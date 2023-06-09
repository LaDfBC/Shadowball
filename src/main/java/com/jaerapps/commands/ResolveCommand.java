package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.pojo.PlayPojo;
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
import java.util.Random;
import java.util.UUID;

public class ResolveCommand implements ICommand {
    private final PlayService playService;
    private final PointsService pointsService;

    @Inject
    public ResolveCommand(
            @Nonnull final PlayService playService,
            @Nonnull final PointsService pointsService
    ) {
        this.playService = playService;
        this.pointsService = pointsService;
    }

    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        int pitchNumber;
        OptionMapping guessOption = event.getOption("pitch");
        if (guessOption == null) {
            return ResponseMessageBuilder.buildErrorResponse("No pitch number given!  I need a value or I can't calculate points!");
        } else {
            pitchNumber = guessOption.getAsInt();
        }

        if (!playService.hasActivePlay()) {
            return ResponseMessageBuilder.buildWarningResponse("You confused me.  There's no active play so I have nothing to close!");
        }
        Optional<UUID> resolvedPlayId = playService.resolvePlay(pitchNumber);
        if (resolvedPlayId.isEmpty()) {
            return ResponseMessageBuilder.buildErrorResponse(
                    "There was no play returned from the database - this is probably a programming bug unless you know what's wrong.  " +
                            "Contact the developer please!");
        } else {
            List<PointsPojo> pointsPerPlayer = pointsService.fetchSortedGuessesByPlay(resolvedPlayId.get());
            if (pointsPerPlayer.size() < 2) {
                return ResponseMessageBuilder.buildSketchyActionResponse(
                        "Closed the play successfully",
                        "Not enough people participated to give best and worst awards.  Stop being lazy."
                );
            } else {
                String playerHeader = "PLAYER";
                int maxPlayerWidth = getPlayerNameWithMostCharacters(playerHeader, pointsPerPlayer);

                StringBuilder eventualResponse =
                        new StringBuilder("```Closed this play! Here are the results:\n\n").append(playerHeader);

                int spaceCount = 0;
                while(playerHeader.length() + spaceCount < maxPlayerWidth) {
                    eventualResponse.append(" ");
                    spaceCount++;
                }

                eventualResponse.append("| DIFFERENCE  | BASE POINTS | (BONUS)\n\n");

                for (PointsPojo currentPlayerPoints : pointsPerPlayer) {
                    eventualResponse.append(currentPlayerPoints.getMemberName());

                    spaceCount = 0;
                    while(currentPlayerPoints.getMemberName().length() + spaceCount < maxPlayerWidth) {
                        eventualResponse.append(" ");
                        spaceCount++;
                    }

                    eventualResponse.append("|");
                    if(currentPlayerPoints.getDifference() < 10) {
                        eventualResponse.append("       ").append(currentPlayerPoints.getDifference()).append("     ");
                    } else if (currentPlayerPoints.getDifference() < 100) {
                        eventualResponse.append("      ").append(currentPlayerPoints.getDifference()).append("     ");
                    } else {
                        eventualResponse.append("     ").append(currentPlayerPoints.getDifference()).append("     ");
                    }

                    eventualResponse.append("|      ").append(currentPlayerPoints.getPoints()).append("      |    ").append(currentPlayerPoints.getBonusPoints()).append("\n");
                }

                return ResponseMessageBuilder.buildMultiFieldStandardResponse(
                        List.of(
                                new ResponseMessageBuilder.MessageEmbedField("Play Closed, Results:", eventualResponse.append("```").toString()),
                                new ResponseMessageBuilder.MessageEmbedField(
                                        "Best Guess", "Congrats to " + pointsPerPlayer.get(0).getMemberName() + " for being the closest"),
                                new ResponseMessageBuilder.MessageEmbedField(
                                        "Angel Hernandez Award",
                                            getLoserMessage(pointsPerPlayer.get(pointsPerPlayer.size() - 1).getMemberName()))
                        )
                );
            }
        }
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

    private String getLoserMessage(String memberName) {
        List<String> loserMessages = List.of(
            "And tell " + memberName + " they suck.",
            "Don't forget to shame " + memberName + " for their horrible guess.",
            memberName + " was about as useful as a poopy-flavored lollipop",
            "Holy shit, " + memberName + " is just the fucking worst at this game",
            "I should have awarded " +  memberName + " negative points for that awful nonsense swing.",
            "I give up. " + memberName + " should just quit immediately",
            "Wow, " + memberName + " did you think of that guess all by yourself?  Yeah? It shows."
        );

        return loserMessages.get(new Random().nextInt(loserMessages.size()));
    }



//        play = play_dao.resolve_play(pitch_number, server_id)
//        guess_dao.set_differences(pitch_number, play['play_id'])
//        guesses = points_service.fetch_sorted_guesses_by_play(guess_dao, play['play_id'])
//
//        response_message = "Closed this play! Here are the results:\n"
//        response_message += "PLAYER --- DIFFERENCE --- POINTS GAINED\n"
//        for guess in guesses:
//        response_message += guess[1] + " --- " + str(guess[2]) + " --- " + str(guess[3]) + "\n"
//
//        if len(guesses) < 2:
//        response_message += "Not enough people participated to give best and worst awards.  Stop being lazy."
//
//            else:
//        response_message += "\nCongrats to <@" + str(guesses[0][0]) + "> for being the closest! \n"
//        response_message += __get_loser_message__(str(guesses[-1][0]))
//
//        await message.channel.send(response_message)
}
