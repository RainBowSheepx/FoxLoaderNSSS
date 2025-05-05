package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.loader.ClientModLoader;
import com.fox2code.foxloader.registry.GameRegistry;

import com.mojang.minecraft.entity.EntityItem;
import com.mojang.minecraft.entity.item.ItemStack;
import com.mojang.minecraft.entity.render.RenderItem;
import com.mojang.minecraft.render.Render;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;


import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
abstract class MixinRenderItem extends Render {
	@Inject(method = "initialize", at = @At(value = "INVOKE", target =
			"Lorg/lwjgl/opengl/GL11;glPushMatrix()V", ordinal = 1, shift = At.Shift.AFTER))
	private void onItemRenderBlock(
			EntityItem entityItem, double x, double y, double z, float yaw, float deltaTicks, CallbackInfo ci) {
		ClientModLoader.Internal.glScaleItem(entityItem.item);
	}

	@Inject(method = "initialize", at = @At(value = "INVOKE", target =
			"Lorg/lwjgl/opengl/GL11;glEnable(I)V", ordinal = 0, shift = At.Shift.AFTER))
	private void onItemPreRender(
			EntityItem entityitem, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
		ClientModLoader.Internal.glScaleItem(entityitem.item);
	}

/*	@Redirect(method = "renderItemEntity", at = @At(value = "CONSTANT", args = {"classValue=net/minecraft/src/game/item/ItemRecord"}))
	private boolean accountForModBlocks(Object item, Class<ItemRecord> type) {
		return !GameRegistry.isLoaderReservedBlockItemId(((Item) item).itemID);
	}*/
}