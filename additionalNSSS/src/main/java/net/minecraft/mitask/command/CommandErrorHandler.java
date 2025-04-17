package net.minecraft.mitask.command;


import com.mojang.minecraft.entity.EntityPlayerSP;
import net.minecraft.src.game.stats.StatCollector;

public class CommandErrorHandler {
    public static String opOnly = "command.op_only";
    public static String findHelp = "command.find_help";
    public static String invalidItem = "§c" + StatCollector.translateToLocal("command.invalid_item");
    public static String invalidTime = "§c" + StatCollector.translateToLocal("command.invalid_time");
    public static String invalidTimeMethod = "command.invalid_time_method";
    public static String invalidStackSize = "command.invalid_stack_size";
    public static String invalidPlayer = "§c" + StatCollector.translateToLocal("command.invalid_player");
    public static String invalidCommand = "command.invalid_command";
    public static String invalidTpCoords = "command.tp.error";
    public static String invalidGamemode = "command.gamemode.error";

    public CommandErrorHandler() {
    }

    public static void commandUsageMessage(String message, EntityPlayerSP commandExecutor) {
        String syntax = "§a" + StatCollector.translateToLocal("command.syntax") + ": ";
        commandExecutor.worldObj.playSoundAtEntity(commandExecutor, "random.wood click", 0.8F, 1.0F);
        commandExecutor.sendChatMessage(syntax + message);
    }
}
