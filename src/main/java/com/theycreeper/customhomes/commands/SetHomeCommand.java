package com.theycreeper.customhomes.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.theycreeper.customhomes.data.HomeData;
import com.theycreeper.customhomes.data.ModAttachments;
import com.theycreeper.customhomes.utils.HomeUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("sethome")
                        .then(Commands.argument("homeName", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    String homeName = StringArgumentType.getString(context, "homeName");

                                    // Validate home name
                                    if (HomeUtils.isInvalidName(homeName)) {
                                        context.getSource().sendFailure(
                                            Component.literal("Invalid home name! Use only letters, numbers, hyphens, and underscores.")
                                        );
                                        return 0;
                                    }

                                    // Get or create home data
                                    HomeData homeData = player.getData(ModAttachments.PLAYER_HOMES);

                                    // Create new home
                                    HomeData.Home home = new HomeData.Home(
                                        player.getX(),
                                        player.getY(),
                                        player.getZ(),
                                        player.level().dimension().toString()
                                    );

                                    // Add home to player data
                                    homeData.addHome(homeName, home);

                                    context.getSource().sendSuccess(
                                            () -> Component.literal("Home '" + homeName + "' set at current location!"),
                                            true
                                    );

                                    return 1;
                                })
                        )
        );
    }
}
