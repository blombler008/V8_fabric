package com.tattyhost.fabric.v8.mixin.client;

import com.tattyhost.fabric.v8.items.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (item.isOf(ModItems.CIGARETTE_ITEM) && player.isUsingItem()) {
            boolean bl = hand == Hand.MAIN_HAND;
            matrices.push();
//            matrices.translate(0.0F, -0.4F, -0.4F);
//            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
            ((HeldItemRenderer) (Object) this).renderItem(player, item, ModelTransformationMode.HEAD, bl, matrices, vertexConsumers, light);
            matrices.pop();
            ci.cancel();
        }
    }
}