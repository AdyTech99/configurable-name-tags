package io.github.adytech99.configurablenametags.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class ModConfig{
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("configurable_name_tags.json");

    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(Identifier.of("configurable_name_tags", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_PATH)
                    .build())
            .build();



    @SerialEntry
    @AutoGen(category = "enable_rendering", group = "common")
    @Boolean
    public boolean renderTags = true;

    @SerialEntry
    @AutoGen(category = "enable_rendering", group = "common")
    @IntSlider(min = -1, max = 64, step = 1)
    public int tagDistance = -1;

    @SerialEntry
    @AutoGen(category = "enable_rendering", group = "players")
    @Boolean(formatter = Boolean.Formatter.CUSTOM)
    public boolean whenToRenderTagsForPlayers = true;

    @SerialEntry
    @AutoGen(category = "enable_rendering", group = "entities")
    @Boolean(formatter = Boolean.Formatter.CUSTOM)
    public boolean whenToRenderTagsForEntities = false;

    @SerialEntry
    @AutoGen(category = "enable_rendering", group = "self")
    @Boolean(formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean whenToRenderTagsForSelf = false;


    private static Screen createScreen(@Nullable Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }

    public static Screen createConfigScreen(Screen parent) {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return createScreen(parent);
        }
        return null;
    }
}
