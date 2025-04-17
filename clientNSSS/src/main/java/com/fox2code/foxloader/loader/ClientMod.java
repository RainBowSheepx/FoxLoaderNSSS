package com.fox2code.foxloader.loader;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.RegisteredItemStack;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.Entity;
import com.mojang.minecraft.entity.item.ItemStack;
import com.mojang.minecraft.render.Render;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

public interface ClientMod extends Mod.SidedMod {
    /**
     * Override to implement {@link Render renderers} for your {@link Entity entities}.
     * @param renderers the list of renderers to modify
     */
    @ApiStatus.OverrideOnly
    default void registerRenderers(Map<Class<? extends Entity>, Render> renderers) {
    }

    static Minecraft getGameInstance() {
        return Minecraft.getMinecraft();
    }

    static NetworkPlayer getLocalNetworkPlayer() {
        return (NetworkPlayer) getGameInstance().thePlayer;
    }

    @SuppressWarnings("DataFlowIssue")
    static ItemStack toItemStack(RegisteredItemStack registeredItemStack) {
        return (ItemStack) (Object) registeredItemStack;
    }

    static RegisteredItemStack toRegisteredItemStack(ItemStack registeredItemStack) {
        return (RegisteredItemStack) (Object) registeredItemStack;
    }
}
