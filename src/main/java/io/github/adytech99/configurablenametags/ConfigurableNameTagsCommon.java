package io.github.adytech99.configurablenametags;

import io.github.adytech99.configurablenametags.config.ModConfig;
import net.minecraft.client.MinecraftClient;

public final class ConfigurableNameTagsCommon {
    public static final String MOD_ID = "configurablenametags";
    public static final MinecraftClient client = MinecraftClient.getInstance();

    public static void init() {

    }

    public static void renderingToggleButtonPressed(){
        ModConfig.HANDLER.instance().renderTags = !ModConfig.HANDLER.instance().renderTags;
        ModConfig.HANDLER.save();
    }
}
