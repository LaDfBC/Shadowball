package com.jaerapps.commands;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class LoadDataCommand implements ICommand{
    @Override
    public MessageEmbed runCommand(SlashCommandInteractionEvent event) {
//        Map<String, UUID> playSequenceToId = Maps.newHashMap();
//        AtomicReference<Integer> inserted = new AtomicReference<>(0);
//        File inputFile = new File("filename");
//        Guild sunnydaleGuild = event.getJDA().getGuildById(idhere...);
//        sunnydaleGuild.loadMembers().onSuccess(members -> {
//            List<Integer> linesSuccessful = Lists.newArrayList();
//            Integer lineNumber = -1;
//            try (BufferedReader br
//                         = new BufferedReader(new FileReader(inputFile))) {
//                String line;
//                while((line = br.readLine()) != null) {
//                    String[] lineParts = line.split(",");
//                    int columnNumber = 0;
//                    lineNumber++;
//                    try {
//                        if (lineParts[0].equals("")) {
//                            continue;
//                        }
//                        int playNumber = Integer.parseInt(lineParts[columnNumber++]);
//                        int sessionNumber = Integer.parseInt(lineParts[columnNumber++]);
//                        String pitcherName = lineParts[columnNumber++];
//                        int pitchSequence = Integer.parseInt(lineParts[columnNumber++]);
//                        int pitchNumber = Integer.parseInt(lineParts[columnNumber++]);
//                        String memberId = lineParts[columnNumber++];
//                        int swingNumber = Integer.parseInt(lineParts[columnNumber++]);
//                        int difference = Integer.parseInt(lineParts[columnNumber++]);
//
//                        UUID gameId = gameService.fetchOrInsert(GamePojo
//                                .builder()
//                                        .withSessionNumber(sessionNumber)
//                                        .withSeasonNumber(1)
//                                        .isCurrentlyActive(false)
//                                        .build());
//
//
//                        if (!playSequenceToId.containsKey(gameId.toString() + "_" + pitchSequence)) {
//                            UUID playId = playService.insert(PlayPojo
//                                    .builder()
//                                    .withPlayId(UUID.randomUUID())
//                                    .withGameId(gameId)
//                                    .withPitchValue(pitchNumber)
//                                    .withCreationDate(new Date())
//                                    .build());
//                            playSequenceToId.put(gameId.toString() + "_" + pitchSequence, playId);
//                        }
//
//                        Optional<Member> possibleMember = members.stream().filter(member -> member.getId().equals(memberId)).findFirst();
//                        if (possibleMember.isPresent()) {
//                            System.out.println("INSERTED FIRST: " + (inserted.getAndSet(inserted.get() + 1) + 1));
//                            guessService.upsert(GuessPojo
//                                    .builder()
//                                    .withGuessedNumber(swingNumber)
//                                    .withDifference(difference)
//                                    .withMemberId(memberId)
//                                    .withMemberName(possibleMember.get().getUser().getName())
//                                    .withPlayId(playSequenceToId.get(gameId + "_" + pitchSequence))
//                                    .build());
//                            linesSuccessful.add(lineNumber);
//                        } else {
//                            System.out.println("INSERTED SECOND: " + (inserted.getAndSet(inserted.get() + 1) + 1));
//                            guessService.upsert(GuessPojo
//                                    .builder()
//                                    .withGuessedNumber(swingNumber)
//                                    .withDifference(difference)
//                                    .withMemberId(memberId)
//                                    .withMemberName("Unknown")
//                                    .withPlayId(playSequenceToId.get(gameId + "_" + pitchSequence))
//                                    .build());
//                            linesSuccessful.add(lineNumber);
//                        }
//
//
//                    } catch (NumberFormatException nfe) {
//                        nfe.printStackTrace();
//                        // no op - Intentional
//                        System.out.println("Probably ok - we know some of the rows aren't integers");
//                    }
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(linesSuccessful);
//        });
//
//        return ResponseMessageBuilder.buildStandardResponse("OK", "OK");
        return null;
    }
}
