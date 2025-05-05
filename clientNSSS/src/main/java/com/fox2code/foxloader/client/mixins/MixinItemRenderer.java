package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.ClientModLoader;
import com.fox2code.foxloader.loader.ModLoaderOptions;

import com.mojang.minecraft.entity.EntityLiving;
import com.mojang.minecraft.entity.item.ItemRenderer;
import com.mojang.minecraft.entity.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Inject(method = "func_897_a", at = @At(value = "INVOKE", target =
            "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V", ordinal = 0, shift = At.Shift.AFTER))
    private void onItemPreRenderBlock(ItemStack itemstack, CallbackInfo ci) {
        ClientModLoader.Internal.glScaleItem(itemstack);
    }

    @Inject(method = "func_897_a", at = @At(value = "INVOKE", target =
            "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onItemPreRender(ItemStack itemstack, CallbackInfo ci) {
        ClientModLoader.Internal.glScaleItemOverXYTranslate(itemstack, -0.0625F, 0.125f);
    }

    // TODO: Check ItemRenderer
/*    @Inject(method = "func_897_a", at = @At(value = "INVOKE", target =
            "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V", ordinal = 0, shift = At.Shift.AFTER))
    private void onItemPreRenderCoordBlock(ItemStack itemstack, CallbackInfo ci) {
        ClientModLoader.Internal.glScaleItemNoZFighting(itemstack);
    }

    @Inject(method = "func_897_a", at = @At(value = "INVOKE", target =
            "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0, shift = At.Shift.AFTER))
    private void onItemPreRenderCoord(ItemStack itemstack, CallbackInfo ci) {
        ClientModLoader.Internal.glScaleItemOverZTranslate(itemstack, 0.3125F);
    }*/
}
