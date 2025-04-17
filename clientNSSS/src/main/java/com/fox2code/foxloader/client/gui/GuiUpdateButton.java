package com.fox2code.foxloader.client.gui;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.updater.UpdateManager;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.GuiButton;
import com.mojang.minecraft.gui.GuiSmallButton;


public final class GuiUpdateButton extends GuiButton {
    private final String text, textColor;

    public GuiUpdateButton(int var1, int var2, int var3, String var4) {
        super(var1, var2, var3,150, 20, var4);
        this.text = var4;
        this.textColor = ChatColors.RAINBOW + var4 + ChatColors.RESET;
    }

    public GuiUpdateButton(int var1, int var2, int var3, int var4, int var5, String var6) {
        super(var1, var2, var3, var4, var5, var6);
        this.text = var6;
        this.textColor = ChatColors.RAINBOW + var6 + ChatColors.RESET;
    }

    @Override
    public void func_561_a(Minecraft _mc, int var2, int var3) {
        this.displayString = UpdateManager.getInstance().hasUpdates() ? this.textColor : this.text;
        super.func_561_a(_mc, var2, var3);
    }
}
