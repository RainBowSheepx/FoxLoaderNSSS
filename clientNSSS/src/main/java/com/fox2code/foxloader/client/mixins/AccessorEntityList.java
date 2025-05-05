package com.fox2code.foxloader.client.mixins;


import com.mojang.minecraft.entity.EntityList;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(EntityList.class)
public interface AccessorEntityList {
	@Invoker(value = "func_1080_a")
	static void invokeAddMapping(Class<?> entityClass, String entityTypeName, int entityTypeID) {
		throw new IllegalStateException();
	}
}
