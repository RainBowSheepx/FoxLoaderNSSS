package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.client.network.ImplNetworkPlayerControllerExt;
import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.ModLoader;
import com.fox2code.foxloader.network.NetworkPlayer;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityPlayer;
import com.mojang.minecraft.entity.item.ItemStack;
import com.mojang.minecraft.level.World;
import com.mojang.minecraft.player.controller.PlayerController;
import com.mojang.minecraft.player.controller.PlayerControllerSP;
import com.mojang.minecraft.render.Vec3D;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerControllerSP.class)
public class MixinPlayerControllerSP extends PlayerController implements ImplNetworkPlayerControllerExt {
    public MixinPlayerControllerSP(Minecraft minecraft) {
        super(minecraft);
    }

    @Override
    public boolean sendUseItem(EntityPlayer player, World var2, ItemStack var3) {
        if (ModLoader.Internal.notifyPlayerUseItem((NetworkPlayer) player,
                ClientMod.toRegisteredItemStack(var3))) {
            return false;
        }
        return super.sendUseItem(player, var2, var3);
    }

    @Override
    public boolean sendPlaceBlock(EntityPlayer player, World world, ItemStack itemstack, int x, int y, int z, int facing) {
        if (ModLoader.Internal.notifyPlayerUseItemOnBlock((NetworkPlayer) player,
                ClientMod.toRegisteredItemStack(itemstack), x, y, z, facing,
                (float) 1,(float) 1,(float) 1)) {
            return false;
        }
        if (this.notifyRegisteredItemUsedImpl(player, itemstack, x, y, z)) {
            return false;
        }
        return super.sendPlaceBlock(player, world, itemstack, x, y, z, facing);
    }
}
