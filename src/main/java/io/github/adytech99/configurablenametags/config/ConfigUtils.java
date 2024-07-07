package io.github.adytech99.configurablenametags.config;

import io.github.adytech99.configurablenametags.ConfigurableNameTagsCommon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class ConfigUtils {
    public static boolean doesTheConfigWantToRenderTheNameTag(Entity entity) {
        if ((entity instanceof PlayerEntity) && !ModConfig.HANDLER.instance().whenToRenderTagsForPlayers && ConfigurableNameTagsCommon.client.targetedEntity != entity) {
            return false;
        }

        if (!(entity instanceof PlayerEntity) && !ModConfig.HANDLER.instance().whenToRenderTagsForEntities && ConfigurableNameTagsCommon.client.targetedEntity != entity) {
            return false;
        }

        if (entity == ConfigurableNameTagsCommon.client.player && !ModConfig.HANDLER.instance().whenToRenderTagsForSelf) {
            return false;
        }

        return (ModConfig.HANDLER.instance().renderTags && isWithinTagDistance(entity));
    }

    public static boolean isWithinTagDistance(Entity entity){
        if(ModConfig.HANDLER.instance().tagDistance == -1) return true;
        if(ConfigurableNameTagsCommon.client.player != null)
            return ConfigurableNameTagsCommon.client.player.distanceTo(entity) <= ModConfig.HANDLER.instance().tagDistance;
        return false;
    }
}
