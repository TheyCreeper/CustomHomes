package com.theycreeper.customhomes;

import com.mojang.brigadier.CommandDispatcher;
import com.theycreeper.customhomes.data.ModAttachments;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(CustomHomes.MOD_ID)
public final class CustomHomesClient {

    public CustomHomesClient(IEventBus modEventBus, ModContainer modContainer) {
        // Register attachments
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);

        initConfigurationScreen(modContainer);
    }

    private static void initConfigurationScreen(ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (c, parent) -> CustomHomes.createConfigurationScreen(parent)
        );
    }

    @EventBusSubscriber(modid = CustomHomes.MOD_ID)
    public static class CommonEventBusSubscriber {
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            CustomHomes.registerCommands(dispatcher);
        }
    }
}
