package com.theycreeper.customhomes.data;

import com.theycreeper.customhomes.CustomHomes;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CustomHomes.MOD_ID);

    public static final Supplier<AttachmentType<HomeData>> PLAYER_HOMES = ATTACHMENT_TYPES.register(
            "player_homes",
            () -> AttachmentType.serializable(() -> new HomeData()).build()
    );
}
