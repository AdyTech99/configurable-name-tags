package io.github.adytech99.configurablenametags.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.adytech99.configurablenametags.config.ModConfig;


public class ModMenuAPIImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfig::createConfigScreen;
    }
}
