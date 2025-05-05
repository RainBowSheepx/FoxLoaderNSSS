package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.client.ResourceReloadingHelper;

import com.mojang.minecraft.render.RenderEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEngine.class)
public class MixinRenderEngine {
    @Inject(method = "func_1065_b", at = @At("HEAD"))
    public void refreshTextureMapsHook(CallbackInfo ci) {
        ResourceReloadingHelper.Internal.markResourceReloadStart();
    }
}
