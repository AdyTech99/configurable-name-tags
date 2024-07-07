package io.github.adytech99.configurablenametags.mixin;

import io.github.adytech99.configurablenametags.ConfigurableNameTagsCommon;
import io.github.adytech99.configurablenametags.config.ConfigUtils;
import io.github.adytech99.configurablenametags.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

    @Shadow
    protected abstract boolean hasLabel(T livingEntity);

    @Shadow protected abstract boolean isVisible(T entity);

    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }


    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    public void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (ModConfig.HANDLER.instance().renderTags && livingEntity != ConfigurableNameTagsCommon.client.targetedEntity
                && (this.hasLabel(livingEntity)
                || (ModConfig.HANDLER.instance().whenToRenderTagsForEntities && !(livingEntity instanceof PlayerEntity) && isValidToRender(livingEntity))))
            super.renderLabelIfPresent(livingEntity, ((Entity) livingEntity).getDisplayName(), matrixStack, vertexConsumerProvider, i, 0);
    }


    @Inject(method = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    public void hasLabel(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (!ConfigUtils.doesTheConfigWantToRenderTheNameTag(livingEntity)) {
            cir.setReturnValue(false);
        }
        else cir.setReturnValue(isValidToRender(livingEntity));
    }

    /*@Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("TAIL"), cancellable = true)
    public void hasLabel(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();

        if (!ConfigUtils.doesTheConfigWantToRenderTheNameTag(livingEntity)) {
            cir.setReturnValue(false);
            return;
        }

        ClientPlayerEntity clientPlayerEntity = ConfigurableNameTagsCommon.client.player;
        boolean bl = !livingEntity.isInvisibleTo(clientPlayerEntity);

        if (livingEntity != clientPlayerEntity) {
            AbstractTeam abstractTeam = livingEntity.getScoreboardTeam();
            AbstractTeam abstractTeam2 = clientPlayerEntity.getScoreboardTeam();
            if (abstractTeam != null) {
                AbstractTeam.VisibilityRule visibilityRule = abstractTeam.getNameTagVisibilityRule();
                switch (visibilityRule) {
                    case ALWAYS ->
                            cir.setReturnValue(bl && ConfigUtils.doesTheConfigWantToRenderTheNameTag(livingEntity) && isValidToRender(livingEntity));
                    case NEVER -> cir.setReturnValue(false);
                    case HIDE_FOR_OTHER_TEAMS ->
                            cir.setReturnValue((abstractTeam2 == null ? bl : abstractTeam.isEqual(abstractTeam2) && (abstractTeam.shouldShowFriendlyInvisibles() || bl)) && ConfigUtils.doesTheConfigWantToRenderTheNameTag(livingEntity) && isValidToRender(livingEntity));
                    case HIDE_FOR_OWN_TEAM ->
                            cir.setReturnValue((abstractTeam2 == null ? bl : !abstractTeam.isEqual(abstractTeam2) && bl) && ConfigUtils.doesTheConfigWantToRenderTheNameTag(livingEntity) && isValidToRender(livingEntity));
                    default ->
                            cir.setReturnValue(ConfigUtils.doesTheConfigWantToRenderTheNameTag(livingEntity) && isValidToRender(livingEntity));
                }
                return;
            }
        }

        cir.setReturnValue(cir.getReturnValue() && isValidToRender(livingEntity));
        return;
    }*/

    @Unique
    public boolean isValidToRender(LivingEntity livingEntity){
        //return MinecraftClient.isHudEnabled() && livingEntity != ConfigurableNameTagsCommon.client.getCameraEntity() && !livingEntity.isInvisibleTo(ConfigurableNameTagsCommon.client.player) && !livingEntity.hasPassengers() && livingEntity.hasCustomName();
        return MinecraftClient.isHudEnabled() && !livingEntity.isInvisibleTo(ConfigurableNameTagsCommon.client.player) && !livingEntity.hasPassengers() && (livingEntity.hasCustomName() || livingEntity instanceof PlayerEntity);
    }


}
