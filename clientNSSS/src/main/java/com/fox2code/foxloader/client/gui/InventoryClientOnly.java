package com.fox2code.foxloader.client.gui;


import com.mojang.minecraft.entity.item.IInventory;

/**
 * Mark a {@link IInventory} as client side only, and to avoid any network inconsistencies
 */
public interface InventoryClientOnly extends IInventory {
}
