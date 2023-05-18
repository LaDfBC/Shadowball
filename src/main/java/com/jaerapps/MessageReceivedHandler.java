package com.jaerapps;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.jaerapps.errors.DiscordResponseError;
import com.jaerapps.util.MessageContext;
import com.jaerapps.util.MessageResponder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceivedHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(MessageReceivedHandler.class);

//    private final ServiceConductor serviceConductor;



    @Inject
    public MessageReceivedHandler(
//                                  PreferenceService playerPreferenceService,
                                  ) {
//        this.serviceConductor = serviceConductor;
//        this.playerPreferenceService = playerPreferenceService;
    }

    public MessageResponder handleMessage(MessageReceivedEvent event) {
        MessageResponder responder = null;

        if (!shouldHandleTopLevelMessage(event)) {
            return null;
        }

//        try {

            // Ensures the message we got is parseable and should be something we handle.
            MessageContext messageContext;
            try {
                messageContext = new MessageContext(event);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                LOGGER.trace("This message was not for a bot!");
                return null;
            } catch (Exception e) {
                LOGGER.warn("I caught something weird: ", e);
                return null;
            }

            responder = MessageResponder.create(messageContext.getChannel());

//            if (serviceConductor.verifyPrefix(messageContext)) {
//                if (serviceConductor.isDefaultPrefix(messageContext.getPrefix())) {
            switch (messageContext.getCommand()) {
                case HELP:

//                            if (messageContext.getCommandArguments().size() == 0) {
//                                responder.addMessage(HelpMessages.buildFromRequest(messageContext.getCommandArguments()).build());
//                            } else if (messageContext.getCommandArguments().size() == 1) {
//                                try {
//                                    GameType gameType = GameType.init(messageContext.getCommandArguments().get(0));
//                                    if (gameType.equals(GameType.DODGEBALL)) {
//                                        responder.addMessage(ResponseMessageBuilder.buildDodgeballHelpResponse());
//                                        return responder;
//                                    }
//                                } catch (IllegalArgumentException ise) {
//                                    responder.addMessage(ResponseMessageBuilder.buildErrorResponse(ise.getMessage()));
//                                    return responder;
//                                }
//                            } else {
//                                responder.addMessage(ResponseMessageBuilder.buildErrorResponse("Need either 0 or 1 argument to help messages!"));
//                            }
////                            break;
//                    }
//                }
//            }
//        } catch (DiscordResponseError dre) {
//                LOGGER.error("Encountered an error that bubbled all the way up!  Handling it!");
//                event
//                        .getChannel()
//                        .sendMessageEmbeds(ResponseMessageBuilder.buildErrorResponse(dre))
//                        .queue();
//            } catch (Exception e) {
//                LOGGER.error("Unexpected Error! Original message follows - please file a bug report, thanks!", e);
//                event
//                        .getChannel()
//                        .sendMessageEmbeds(ResponseMessageBuilder.buildErrorResponse(new DiscordResponseError("Original Message: ", e)))
//                        .queue();
//            }
//    }

            }
//        }
        return null;
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
}
