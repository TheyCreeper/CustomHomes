package com.theycreeper.customhomes;

import com.mojang.brigadier.CommandDispatcher;
import com.theycreeper.customhomes.commands.HomeCommand;
import com.theycreeper.customhomes.commands.HomesCommand;
import com.theycreeper.customhomes.commands.RemoveHomeCommand;
import com.theycreeper.customhomes.commands.SetHomeCommand;
import com.theycreeper.customhomes.utils.Log;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.CommandSourceStack;

public final class CustomHomes {
    public static final String MOD_ID = "customhomes";

    public void init() {
        Log.info("Initializing Easy Homes Mod");

        // MidnightConfig.init(MOD_ID, EasyHomesConfig.class);
    }

    public static Screen createConfigurationScreen(Screen parent) {
        return MidnightConfig.getScreen(parent, MOD_ID);
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        HomeCommand.register(dispatcher);
        SetHomeCommand.register(dispatcher);
        RemoveHomeCommand.register(dispatcher);
        HomesCommand.register(dispatcher);
        // Register additional commands here
    }
}
