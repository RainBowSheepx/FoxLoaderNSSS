package com.fox2code.foxloader.client.gui;


import com.mojang.minecraft.gui.GuiScreen;

public interface GuiConfigProvider {
    GuiScreen provideConfigScreen(GuiScreen parent);
}
