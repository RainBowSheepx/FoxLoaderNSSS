package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.client.gui.GuiModList;
import com.fox2code.foxloader.client.gui.GuiUpdateButton;
import com.fox2code.foxloader.loader.ModLoader;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.network.SidedMetadataAPI;


import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.GuiButton;
import com.mojang.minecraft.gui.GuiIngameMenu;
import com.mojang.minecraft.gui.GuiScreen;
import net.minecraft.src.client.gui.GuiLinkConfirm;
import net.minecraft.src.client.gui.StringTranslate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {
    @Inject(method = "initGui", at = @At(value = "RETURN"))
    public void onInitGui(CallbackInfo ci) {
        this.controlList.add(new GuiUpdateButton(500, this.width - 62, 2, 60, 20, "Mods"));
        Map<String, String> metadata = SidedMetadataAPI.getActiveMetadata();
        if (metadata.containsKey(SidedMetadataAPI.KEY_SERVER_BUTTON_NAME) &&
                metadata.containsKey(SidedMetadataAPI.KEY_SERVER_BUTTON_LINK)) {
            StringTranslate st = StringTranslate.getInstance();
            GuiButton guiButton;
            this.controlList.add(guiButton = new GuiButton(501, this.width / 2 - 100, this.height / 4 + 56,
                    st.translateKey(metadata.get(SidedMetadataAPI.KEY_SERVER_BUTTON_NAME))));
            guiButton.enabled2 = true;
            guiButton.displayString = st.translateKey("warning.server-controlled-button");
        }
    }

    @Inject(method = "actionPerformed", at = @At(value = "HEAD"), cancellable = true)
    public void onActionPerformed(GuiButton button, CallbackInfo ci) {
        if (button.id == 1) {
            NetworkPlayer networkPlayer = (NetworkPlayer) Minecraft.getMinecraft().thePlayer;
            if (networkPlayer != null && networkPlayer.getConnectionType() ==
                    NetworkPlayer.ConnectionType.SINGLE_PLAYER) {
                ModLoader.Internal.notifyNetworkPlayerDisconnected(networkPlayer, null);
            }
        }

        if (button.id == 500) {
            this.mc.setCurrentScreen(new GuiModList(this));
            ci.cancel();
        }

        if (button.id == 501) {
            Map<String, String> metadata = SidedMetadataAPI.getActiveMetadata();
            if (metadata.containsKey(SidedMetadataAPI.KEY_SERVER_BUTTON_NAME) &&
                    metadata.containsKey(SidedMetadataAPI.KEY_SERVER_BUTTON_LINK)) {
                this.mc.setCurrentScreen(new GuiLinkConfirm(this,
                        metadata.get(SidedMetadataAPI.KEY_SERVER_BUTTON_LINK)));
            } else button.enabled = false;

        }
    }
}
