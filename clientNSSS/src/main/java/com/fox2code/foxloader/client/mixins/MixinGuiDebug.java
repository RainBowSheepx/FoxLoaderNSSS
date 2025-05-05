package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.launcher.BuildConfig;
import com.fox2code.foxloader.loader.ClientModLoader;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.GuiIngame;
import com.mojang.minecraft.render.FontRenderer;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngame.class)
public class MixinGuiDebug {
    @Shadow private Minecraft mc;

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE",
            target = "Lcom/mojang/minecraft/render/FontRenderer;drawStringWithShadow(Ljava/lang/String;III)V",
            ordinal = 0))
    private void drawVersionStringWith(FontRenderer instance, String s, int x, int y, int fontcolor) {
        instance.drawStringWithShadow( s + " (FoxLoader " + BuildConfig.FOXLOADER_VERSION + ")", x, y, fontcolor);
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE",
            target = "Lcom/mojang/minecraft/render/FontRenderer;drawStringWithShadow(Ljava/lang/String;III)V",
            ordinal = 8))
    private void drawServerStringWith(FontRenderer instance, String s, int x, int y, int fontcolor) {
        instance.drawStringWithShadow(s + ClientModLoader.Internal.getColoredServerNameDebugExt(), x, y, fontcolor);
    }

/*    @Redirect(method = "drawScreen", at = @At(value = "INVOKE",
            target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"))
    private boolean isKeyDown(int key) {
        switch (key) {
            default:
                // Fallback, but we should never reach here
                return Keyboard.isKeyDown(key);
            case Keyboard.KEY_Z:
                return ClientModLoader.showFrameTimes;
            case Keyboard.KEY_X:
                return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        }
    }*/
}
