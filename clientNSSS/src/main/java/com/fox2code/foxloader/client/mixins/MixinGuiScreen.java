package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.config.NoConfigObject;

import com.mojang.minecraft.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiScreen.class)
public class MixinGuiScreen implements NoConfigObject {
}
