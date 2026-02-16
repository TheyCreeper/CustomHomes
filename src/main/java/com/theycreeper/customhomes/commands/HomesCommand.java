package com.theycreeper.customhomes.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.theycreeper.customhomes.data.HomeData;
import com.theycreeper.customhomes.data.ModAttachments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class HomesCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("homes")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();

                            // get player data
                            HomeData homeData = player.getData(ModAttachments.PLAYER_HOMES);
                            context.getSource().sendSuccess(
                                    () -> Component.literal("Homes list: " + homeData.getHomeList()),
                                    true
                            );
                            return 1;
                        })
        );
    }
}
