package com.fox2code.foxloader.client;


import com.mojang.minecraft.level.tile.phys.AxisAlignedBB;
import com.mojang.minecraft.render.Frustrum;

/**
 * Hook {@link net.minecraft.src.client.renderer.EntityRenderer#renderWorld(float, long)}
 */
public class FrustrumHelper {
    public static final Frustrum frustrum = new Frustrum();

    public static boolean isBoundingBoxInFrustum(AxisAlignedBB box) {
        return frustrum.func_342_a(box);
    }

    public static boolean isBoundingBoxInFrustumFully(AxisAlignedBB box) {
        return frustrum.func_342_a(box);
    }

    public static class Hooks {
        public static void update(Frustrum frustrum, double x, double y, double z) {
            ClippingHelperImpl.getInstance();
            frustrum.func_343_a(x, y, z);
        }
    }
}
