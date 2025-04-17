package com.fox2code.foxloader.client.network;


import com.mojang.minecraft.entity.EntityPlayer;
import com.mojang.minecraft.entity.item.ItemStack;

public interface ImplNetworkPlayerControllerExt {
    default boolean notifyRegisteredItemUsedImpl(EntityPlayer player, ItemStack itemstack, int x, int y, int z) {
        return false;
    }

    default void notifyRegisteredSetPrimary(int x, int y, int z) {}
    default void notifyRegisteredSetSecondary(int x, int y, int z) {}
}
