package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredTileEntity;
import com.fox2code.foxloader.registry.RegisteredWorld;
import com.mojang.minecraft.entity.tile.TileEntity;
import com.mojang.minecraft.level.World;
import com.mojang.minecraft.level.tile.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity implements RegisteredTileEntity {
    @Shadow public World world;
    @Shadow public int x;
    @Shadow public int y;
    @Shadow public int z;

    @Shadow public abstract Block func_478_g();

    @Override
    public RegisteredWorld getCurrentRegisteredWorld() {
        return (RegisteredWorld) this.world;
    }

    @Override
    public int getRegisteredX() {
        return this.x;
    }

    @Override
    public int getRegisteredY() {
        return this.y;
    }

    @Override
    public int getRegisteredZ() {
        return this.z;
    }

    @Override
    public RegisteredBlock getRegisteredBlock() {
        return (RegisteredBlock) this.func_478_g();
    }
}
