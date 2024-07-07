package io.github.adytech99.configurablenametags.fabric;

import io.github.adytech99.configurablenametags.ConfigurableNameTagsCommon;
import io.github.adytech99.configurablenametags.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import static io.github.adytech99.configurablenametags.ConfigurableNameTagsCommon.MOD_ID;

public final class ConfigurableNameTagsFabricClient implements ClientModInitializer {

    public static KeyBinding ENABLE_NAME_TAGS_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key." + MOD_ID + ".renderingEnabled",
            InputUtil.GLFW_KEY_N,
            "key.categories." + MOD_ID
    ));

    public static void onClientTick(MinecraftClient client){
        if(ENABLE_NAME_TAGS_KEYBINDING.wasPressed())
            ConfigurableNameTagsCommon.renderingToggleButtonPressed();
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(ConfigurableNameTagsFabricClient::onClientTick);
        ModConfig.HANDLER.load();
    }
}
