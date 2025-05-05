package com.fox2code.foxloader.client.mixins;

import com.fox2code.foxloader.registry.GameRegistry;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.fox2code.foxloader.registry.RegisteredItemStack;

import com.mojang.minecraft.entity.item.Item;
import com.mojang.minecraft.entity.item.ItemStack;
import com.mojang.minecraft.level.tile.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class MixinItem implements RegisteredItem {
    @Shadow @Final public int shiftedIndex;
    @Shadow protected int maxStackSize;
    @Unique float worldItemScale = 1.0F;

    @Override
    public RegisteredBlock asRegisteredBlock() {
        int id = GameRegistry.convertItemIdToBlockId(this.shiftedIndex);
        return id == -1 ? null : (RegisteredBlock) Block.allBlocks[id];
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public RegisteredItemStack newRegisteredItemStack() {
        return (RegisteredItemStack) (Object) new ItemStack((Item) (Object) this);
    }

    @Override
    public int getRegisteredItemId() {
        return this.shiftedIndex;
    }

    @Override
    public int getRegisteredItemMaxStackSize() {
        return this.maxStackSize;
    }

    @Override
    public float getWorldItemScale() {
        return this.worldItemScale;
    }

    @Override
    public void setWorldItemScale(float worldItemScale) {
        this.worldItemScale = worldItemScale;
    }
}
