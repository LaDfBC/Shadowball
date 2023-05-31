package com.jaerapps;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jaerapps.guice.BasicModule;
import com.jaerapps.util.MessageResponder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class BotRunner extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotRunner.class);

    private final MessageReceivedHandler messageReceivedHandler;

    public BotRunner(final String configFilePath) {
        // Load Configs
        Configuration.setInstanceFromFile(configFilePath);

        // Initialize Guice
        Injector injector = Guice.createInjector(new BasicModule());
        messageReceivedHandler = injector.getInstance(MessageReceivedHandler.class);

        // Flyway migration - Runs if configuration exists and is set to true.  Skips otherwise.
        if (Configuration.getProperty(Configuration.UPDATE_DB_ON_STARTUP).isPresent() &&
                Configuration.getProperty(Configuration.UPDATE_DB_ON_STARTUP).get().equalsIgnoreCase("true")) {
            DatabaseHelper.runFlyway();

            // Create the JOOQ files needed to interact with the database
            if (Configuration.getProperty(Configuration.GENERATE_JOOQ).isPresent() &&
                    Configuration.getProperty(Configuration.GENERATE_JOOQ).get().equalsIgnoreCase("true")) {
                DatabaseHelper.runJooq();
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        try {
            MessageResponder responder = messageReceivedHandler.handleMessage(event);
            if (responder != null) {
                responder.sendResponseMessages(event);
            }
        } catch (Exception e) {
            LOGGER.error("Something awful has happened!  Check the logs!");
            e.printStackTrace();
            event
                    .getHook()
                    .sendMessageEmbeds(
                            ResponseMessageBuilder.buildErrorResponse("Critical unhandled error: " + e.getMessage()))
                    .queue();
        }
    }

    @VisibleForTesting
    protected boolean shouldHandleTopLevelMessage(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().isBot()) { // Short-circuit if a human didn't send the message
            return false;
        }

        if (!message.isFromGuild()) { // Check if from a server (instead of DM)
            return false;
        }

        if (!event.getMessage().getContentRaw().startsWith("!")) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws LoginException {
        if (args.length < 2) {
            System.out.println("You have to provide a token as first argument and config file as the second!");
            System.exit(1);
        }

        // args[0] should be the token
        // We only need 4 intents for this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDA discordContext = JDABuilder.createLight(args[0],
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new BotRunner(args[1]))
                .setActivity(Activity.competing("/help will tell you everything I can do!"))
                .build();

        if (Boolean.parseBoolean(Configuration.getPropertyOrThrow("slash.commands.update"))) {discordContext
                    .updateCommands()
                    .addCommands(
                            Commands.slash(
                                    "help",
                                    "Displays the help message for this bot."),
                            Commands.slash(
                                    "shadowball",
                                    "Starts up a new play that people can respond to."),
                            Commands.slash(
                                            "set-season",
                                            "Sets the season and, optionally, session, for the current game and set of plays")
                                    .addOption(OptionType.INTEGER, "season", "Season number for this game", true)
                                    .addOption(OptionType.INTEGER, "session", "Session number for this game", false),
                            Commands.slash(
                                    "set-session",
                                    "Sets the session for the current game.  A game with a valid season must be active!")
                                    .addOption(OptionType.INTEGER, "session", "Session number for this game", true),
                            Commands.slash(
                                    "guess",
                                    "Guess a number on the current play.")
                                    .addOption(OptionType.INTEGER, "guess", "What number would you swing?", true),
                            Commands.slash(
                                    "resolve",
                                    "Close the current play with the actual pitch value")
                                    .addOption(OptionType.INTEGER, "pitch", "Actual pitch number", true),
                            Commands.slash(
                                    "points",
                                    "Fetches the best users by total points - overall - or by a season - or by a selected game")
                                    .addOption(OptionType.INTEGER, "season", "Season of the game to show points for", false)
                                    .addOption(OptionType.INTEGER, "session", "Session of the game to show points for", false),
//                            Commands.slash(
//                                    "add-scoring-level",
//                                    "Adds a new line for maximum guess difference to points scored level")
//                                    .addOption(OptionType.INTEGER, "difference", "Maximum difference between guess and pitch, inclusive", true)
//                                    .addOption(OptionType.INTEGER, "points", "Points scored by a player with this difference.", true),
//                            Commands.slash(
//                                    "remove-scoring-level",
//                                    "Deletes a maximum guess difference to points scored level")
//                                    .addOption(OptionType.INTEGER, "difference", "Maximum difference between guess and pitch.  Must match an existing one!", true),
                            Commands.slash(
                                    "extract-data",
                                    "Extracts all guesses, plays, and games I've ever run on this server")
                                    .addOption(OptionType.STRING, "filename", "Name of the file you want to be sent back to you.")
                    )
                    .queue();
        }

        LOGGER.debug("Successfully started bot!");
    }

}
