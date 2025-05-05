package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.loader.ModContainer;
import com.fox2code.foxloader.network.NetworkConnection;
import com.fox2code.foxloader.network.NetworkPlayer;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityClientPlayerMP;
import com.mojang.minecraft.entity.EntityPlayerSP;
import com.mojang.minecraft.level.World;
import com.mojang.minecraft.networknew.NetClientHandler;
import net.minecraft.src.client.packets.Packet250PluginMessage;
import com.mojang.minecraft.util.Session;
import net.minecraft.src.client.gui.StringTranslate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityClientPlayerMP.class)
public class MixinEntityClientPlayerMP extends EntityPlayerSP implements NetworkPlayer {
    @Shadow public NetClientHandler sendQueue;

    public MixinEntityClientPlayerMP(Minecraft var1, World var2, Session var3, int var4) {
        super(var1, var2, var3);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    public void onNewMixinEntityClientPlayerMP(
            Minecraft var1, World var2, Session var3, NetClientHandler var4, CallbackInfo ci) {

    }

    @Override
    public NetworkConnection getNetworkConnection() {
        return (NetworkConnection) this.sendQueue;
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.CLIENT_ONLY;
    }

    @Override
    public void sendNetworkData(ModContainer modContainer, byte[] data) {
        sendQueue.addToSendQueue(new Packet250PluginMessage(modContainer.id, data));
    }

    @Override
    public void displayChatMessage(String chatMessage) {
        StringTranslate st = StringTranslate.getInstance();
        if (chatMessage.indexOf('\n') == -1) {
            Minecraft.getMinecraft().ingameGUI.addChatMessage(st.translateKey(chatMessage));
        } else {
            String[] splits = chatMessage.split("\\n");
            for (String split : splits) {
                Minecraft.getMinecraft().ingameGUI.addChatMessage(st.translateKey(split));
            }
        }
    }

    @Override
    public boolean hasFoxLoader() {
        return sendQueue != null && ((NetworkConnection) sendQueue).hasFoxLoader();
    }

    @Override
    public String getPlayerName() {
        return this.playerName;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public boolean isConnected() {
        return sendQueue != null && Minecraft.getMinecraft().mcWorld.multiplayerWorld &&
                ((NetworkConnection) sendQueue).isConnected();
    }

    @Override
    public void sendPlayerThroughPortalRegistered() {
        throw new RuntimeException("No authority over player");
    }
}
