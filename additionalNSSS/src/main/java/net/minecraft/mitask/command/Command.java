package net.minecraft.mitask.command;


import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityPlayerSP;
import net.minecraft.fox2code.ChatColors;
import net.minecraft.mitask.PlayerCommandHandler;
import net.minecraft.silveros.RindUtil;

public abstract class Command implements ChatColors {
    public static boolean isCommandsLoaded = false;
    public static PlayerCommandHandler pch = new PlayerCommandHandler((Minecraft)null);
    /** @deprecated */
    @Deprecated
    public Minecraft mc;
    private String name;
    private boolean opOnly;
    private boolean isHidden;
    private String[] aliases;

    public Command(String name, boolean opOnly, boolean isHidden, String[] aliases) {
        this.name = name;
        this.opOnly = opOnly;
        this.isHidden = isHidden;
        this.aliases = aliases;
    }

    public String getName() {
        return this.name;
    }

    public boolean isOpOnly() {
        return this.opOnly;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public void onExecute(String[] args, EntityPlayerSP commandExecutor) {
    }

    protected int tryParse(String input, int value) {
        return RindUtil.tryParseInt(input, value);
    }

    public abstract void printHelpInformation(EntityPlayerSP var1);

    public abstract String commandSyntax();
}
