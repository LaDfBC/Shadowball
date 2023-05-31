package com.jaerapps.commands;

import com.jaerapps.ResponseMessageBuilder;
import com.jaerapps.pojo.GamePojo;
import com.jaerapps.service.GameService;
import com.jaerapps.service.PlayService;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class GhostballCommand implements ICommand {
    private final PlayService playService;
    private final GameService gameService;

    @Inject
    public GhostballCommand(@Nonnull final PlayService playService, @Nonnull final GameService gameService) {
        this.playService = playService;
        this.gameService = gameService;
    }

    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
        if (playService.hasActivePlay()) {
            return ResponseMessageBuilder.buildWarningResponse("There's already an active play.  Could you close that one first, please?");
        }

        UUID gameId = UUID.randomUUID();
        if (gameService.hasActiveGame()) {
            Optional<GamePojo> activeGame = gameService.getActiveGame();
            if(activeGame.isPresent()) {
                gameId = activeGame.get().getGameId(); // Use existing game if one is active
            }
        } else {
           return ResponseMessageBuilder.buildErrorResponse(
                    "There is no active Game for me to put this play in!  Start one up with !set-season or !set-session");
        }

        playService.insert(gameId);
        event.getChannel().asTextChannel().sendMessage("<@&376584128264536065> pitch is in!  Use /guess to tell me what you think").queue();
        //TODO:
        return ResponseMessageBuilder.buildStandardResponse("New Play",
                "Ping set - ready for guesses");



//        if play_dao.is_active_play(server_id):
//        await message.channel.send("There's already an active play.  Could you close that one first, please?")
//        else:
//        generated_play_id = uuid.uuid4()
//        if game_dao.is_game_active():
//        current_game_id = game_dao.get_active()['game_id']
//            else:
//        current_game_id = None
//
//        play_object = {PLAY_ID: generated_play_id, CREATION_DATE: datetime.datetime.now(), SERVER_ID: server_id, GAME_ID: current_game_id}
//        play_dao.insert(play_object)
//
//        await message.channel.send("<@&1064364867412312225>, pitch is in!  Send me your guesses with a !guess command.")
    }
}
