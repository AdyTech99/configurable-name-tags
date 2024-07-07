package io.github.adytech99.configurablenametags.mixin;

import io.github.adytech99.configurablenametags.config.ConfigUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Shadow protected abstract void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta);


    @Shadow @Final protected EntityRenderDispatcher dispatcher;

    @Inject(method = "hasLabel", at = @At("TAIL"), cancellable = true)
    public void render(T entity, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(entity.shouldRenderName() || entity.hasCustomName() && entity == this.dispatcher.targetedEntity && ConfigUtils.doesTheConfigWantToRenderTheNameTag(entity));
    }
}
