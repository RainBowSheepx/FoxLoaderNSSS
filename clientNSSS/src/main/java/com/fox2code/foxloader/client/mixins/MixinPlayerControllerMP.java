package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.ModLoader;
import com.fox2code.foxloader.network.NetworkPlayer;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityPlayer;
import com.mojang.minecraft.entity.item.ItemStack;
import com.mojang.minecraft.level.World;
import com.mojang.minecraft.player.controller.PlayerController;
import com.mojang.minecraft.player.controller.PlayerControllerMP;
import com.mojang.minecraft.render.Vec3D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP extends PlayerController {
    public MixinPlayerControllerMP(Minecraft minecraft) {
        super(minecraft);
    }

    @Inject(method = "sendUseItem", at = @At("HEAD"), cancellable = true)
    public void onSendUseItem(EntityPlayer player, World world,
                              ItemStack itemstack, CallbackInfoReturnable<Boolean> cir) {
        if (ModLoader.Internal.notifyPlayerUseItem((NetworkPlayer) player,
                ClientMod.toRegisteredItemStack(itemstack))) {
            cir.setReturnValue(Boolean.FALSE);
        }
    }

    @Inject(method = "sendPlaceBlock", at = @At("HEAD"), cancellable = true)
    // No Vec3D
    public void onSendPlaceBlock(EntityPlayer player, World world, ItemStack itemstack,
                                 int x, int y, int z, int facing, CallbackInfoReturnable<Boolean> cir) {
        if (ModLoader.Internal.notifyPlayerUseItemOnBlock((NetworkPlayer) player,
                ClientMod.toRegisteredItemStack(itemstack), x, y, z, facing,
                (float) 1,(float) 1,(float) 1)) {
            cir.setReturnValue(Boolean.FALSE);
        }
    }

}
