package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.pojo.FullGuessContextPojo;
import com.jaerapps.service.ExtractionService;
import com.jaerapps.service.GameService;
import com.jaerapps.service.GuessService;
import com.jaerapps.service.PlayService;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExtractDataCommand implements ICommand{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractDataCommand.class);
    private final ExtractionService extractionService;


    private final GameService gameService;
    private final GuessService guessService;
    private final PlayService playService;

    @Inject
    public ExtractDataCommand(@Nonnull final ExtractionService extractionService,
                              @Nonnull final GameService gameService,
                              @Nonnull final GuessService guessService,
                              @Nonnull final PlayService playService) {
        this.extractionService = extractionService;
        this.gameService = gameService;
        this.guessService = guessService;
        this.playService = playService;
    }

    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        OptionMapping filenameOption = event.getOption("filename");
        if (filenameOption == null) {
            return ResponseMessageBuilder.buildErrorResponse("No file name given!");
        }

        List<FullGuessContextPojo> allGuesses = extractionService.fetchAllData();
        File csvFile = new File(filenameOption.getAsString());
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("GAME ID,SEASON NUMBER,SESSION NUMBER,PLAY ID,CREATION DATE,PITCH VALUE,MEMBER ID,MEMBER NAME,GUESSED NUMBER,DIFFERENCE\n");

            allGuesses.forEach(fullGuessContextPojo -> {
                try {
                    writer.write(buildGuessContextLine(fullGuessContextPojo));
                } catch (IOException ioe) {
                    LOGGER.error("Failed to write line during extraction");
                    LOGGER.error(ioe.getMessage());
                    ioe.printStackTrace();
                }
            });

            event.getChannel().sendFiles(FileUpload.fromData(csvFile)).queue();
            if (!csvFile.delete()) {
                LOGGER.warn("File" + csvFile.getName() + " not deleted!  This may lead to file system bloat and other issues!");
            }
            return ResponseMessageBuilder.buildStandardResponse(
                    "Extracted", "Discord does not permit attaching files to embedded messages or " +
                            "Slash Command responses so I sent the file in a separate message " +
                            "that should have been posted very recently or will be very shortly.");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Encountered an error during data extraction, please see stack above.");
            return ResponseMessageBuilder.buildErrorResponse(
                    "Encountered an error during file creation.  This is a bug, please contact the bot's owner.");
        }
    }

    private String buildGuessContextLine(FullGuessContextPojo fullGuessContextPojo) {
        return
                fullGuessContextPojo.getGame().getGameId() + "," +
                fullGuessContextPojo.getGame().getSeasonNumber() + "," +
                fullGuessContextPojo.getGame().getSessionNumber() + "," +
                fullGuessContextPojo.getPlay().getPlayId() + "," +
                fullGuessContextPojo.getPlay().getCreationDate().toString() + "," +
                fullGuessContextPojo.getPlay().getPitchValue() + "," +
                fullGuessContextPojo.getGuess().getMemberId() + "," +
                fullGuessContextPojo.getGuess().getMemberName() + "," +
                fullGuessContextPojo.getGuess().getGuessedNumber() + "," +
                fullGuessContextPojo.getGuess().getDifference() + "\n";
    }
}
