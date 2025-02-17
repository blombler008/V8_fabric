package com.tattyhost.fabric.v8.mixin.client;

import com.tattyhost.fabric.v8.items.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "updateRenderState", at = @At("TAIL"))
    private void updateRenderState(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        if (player.isUsingItem()) {
            ItemStack itemStack = player.getStackInHand(player.getActiveHand());
            if (itemStack.isOf(ModItems.CIGARETTE_ITEM)) {

                ItemModelManager itemModelManager = MinecraftClient.getInstance().getItemModelManager();
                itemModelManager.updateForLivingEntity(state.spyglassState, itemStack, ModelTransformationMode.HEAD, false, player);

                Vector3f translation = state.spyglassState.getTransformation().translation;
                Vector3f rotation = state.spyglassState.getTransformation().rotation;
                Vector3f scale = state.spyglassState.getTransformation().scale;

//                translation.set(0.0F, 0.0F, 0.0F);
//                rotation.set(0.0F, 0.0F, 0.0F);
//                scale.set(1.0F, 1.0F, 0.3F);

            }
        }
    }
}