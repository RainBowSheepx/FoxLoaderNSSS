package com.fox2code.foxloader.client.mixins;


import com.mojang.minecraft.networknew.NetHandler;
import com.mojang.minecraft.networknew.packet.Packet;
import net.minecraft.src.client.packets.Packet250PluginMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NetHandler.class)
public abstract class MixinNetHandler {


    @Shadow public abstract void registerPacket(Packet packet);

    @Unique
    public void onHandlePluginMessage(Packet250PluginMessage pluginMessage) {
        this.registerPacket(pluginMessage);
    }

}
