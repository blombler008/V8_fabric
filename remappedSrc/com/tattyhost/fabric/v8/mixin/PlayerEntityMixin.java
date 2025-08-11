package com.tattyhost.fabric.v8.mixin;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.items.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
//    @Inject(method = "isUsingSpyglass", at = @At("HEAD"), cancellable = true)
//    private void isUsingSpyglass(CallbackInfoReturnable<Boolean> cir) {
//        PlayerEntity player = (PlayerEntity) (Object) this;
//        ItemStack activeItem = player.getActiveItem();
//        if (activeItem.isOf(Items.SPYGLASS) || activeItem.isOf(ModItems.CIGARETTE_ITEM)) {
//            cir.setReturnValue(true);
//        }
//    }
}