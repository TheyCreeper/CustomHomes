package com.theycreeper.customhomes.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.theycreeper.customhomes.data.HomeData;
import com.theycreeper.customhomes.data.ModAttachments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class RenameHome {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("delhome")
                        .then(Commands.argument("homeName", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    String homeName = StringArgumentType.getString(context, "homeName");

                                    // get player data
                                    HomeData homeData = player.getData(ModAttachments.PLAYER_HOMES);

                                    // Check if home exists
                                    if (!homeData.hasHome(homeName)) {
                                        context.getSource().sendFailure(
                                                Component.literal("Home '" + homeName + "' not found!")
                                        );
                                        return 0;
                                    }

                                    // remove home
                                    homeData.removeHome(homeName);

                                    context.getSource().sendSuccess(
                                            () -> Component.literal("Removed home: " + homeName),
                                            true
                                    );

                                    return 1;
                                })
                        )
        );
    }
}
