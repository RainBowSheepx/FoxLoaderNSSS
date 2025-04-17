package com.fox2code.foxloader.client.mixins;

import net.minecraft.src.game.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(EntityList.class)
public interface AccessorEntityList {
	@Invoker
	static void invokeAddMapping(Class<?> entityClass, String entityTypeName, int entityTypeID) {
		throw new IllegalStateException();
	}
}
