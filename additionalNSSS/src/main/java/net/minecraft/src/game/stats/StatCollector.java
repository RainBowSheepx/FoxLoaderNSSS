package net.minecraft.src.game.stats;

import net.minecraft.src.client.gui.StringTranslate;

public class StatCollector {
    public static StringTranslate localizedName = StringTranslate.getInstance();

    public StatCollector() {
    }

    public static String translateToLocal(String var0) {
        return localizedName.translateKey(var0);
    }

    public static String translateToLocalFormatted(String var0, Object... var1) {
        return localizedName.translateKeyFormat(var0, var1);
    }
}
