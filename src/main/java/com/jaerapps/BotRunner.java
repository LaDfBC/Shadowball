package com.jaerapps;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jaerapps.guice.BasicModule;
import com.jaerapps.util.MessageResponder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
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
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        try {
            MessageResponder responder = messageReceivedHandler.handleMessage(event);
            if (responder != null) {
                responder.sendResponseMessages();
            }
        } catch (Exception e) {
            LOGGER.error("Something awful has happened!  Check the logs!");

            event
                    .getMessage()
                    .getChannel()
                    .sendMessageEmbeds(ResponseMessageBuilder.buildErrorResponse("Critical unhandled error: " + e.getMessage()))
                    .queue();
        }
    }

    public static void main(String[] args) throws LoginException {
        if (args.length < 2) {
            System.out.println("You have to provide a token as first argument and config file as the second!");
            System.exit(1);
        }

        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new BotRunner(args[1]))
                .setActivity(Activity.competing("c!help will tell you everything I can do!"))
                .build();

        LOGGER.debug("Successfully started bot!");
    }

}
