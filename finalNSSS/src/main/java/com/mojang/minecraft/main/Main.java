package com.mojang.minecraft.main;

import com.fox2code.foxloader.launcher.ClientMain;
import com.mojang.minecraft.MinecraftApplet;

/**
 * Should never be loaded in memory, if it is loaded, it means something wrong happened
 * <p>
 * Use {@link ClientMain} instead to start the game.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new MinecraftApplet(); // We need to remap args for it to work
    }
}
