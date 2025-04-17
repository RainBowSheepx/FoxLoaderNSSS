package net.minecraft.mitask;

import java.util.ArrayList;
import java.util.Iterator;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityPlayerSP;
import net.minecraft.mitask.command.Command;
import net.minecraft.mitask.command.CommandErrorHandler;

public class PlayerCommandHandler {
    public static ArrayList<Command> commands = new ArrayList();
    Minecraft mc;
    public static PlayerCommandHandler instance;

    public PlayerCommandHandler(Minecraft mc) {
        if (!Command.isCommandsLoaded) {
            this.mc = mc;
            instance = this;
            this.registerCommands(mc);
            Command.isCommandsLoaded = true;
            System.out.println("Loaded " + commands.size() + " commands");
        }
    }

    public void addCommand(Command command) {
        if (!command.isHidden()) {
            commands.add(command);
        }

    }

    private void registerCommands(Minecraft mc) {
       // TODO: Add vanilla commands
    }

    public void handleSlashCommand(String message, EntityPlayerSP commandExecutor) {
        Iterator var3 = commands.iterator();

        Command command;
        String[] commandMessage;
        label35:
        do {
            do {
                if (!var3.hasNext()) {
                    commandExecutor.sendChatMessage(CommandErrorHandler.invalidCommand);
                    return;
                }

                command = (Command)var3.next();
                commandMessage = message.split(" ");
                if (command.getAliases() != null) {
                    String[] var6 = command.getAliases();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        String alias = var6[var8];
                        if (commandMessage[0].equalsIgnoreCase("/" + alias)) {
                            this.handleCommand(command, commandExecutor, commandMessage);
                            return;
                        }
                    }
                    continue label35;
                }
            } while(!commandMessage[0].equalsIgnoreCase("/" + command.getName()));

            this.handleCommand(command, commandExecutor, commandMessage);
            return;
        } while(!commandMessage[0].equalsIgnoreCase("/" + command.getName()));

        this.handleCommand(command, commandExecutor, commandMessage);
    }

    private void handleCommand(Command command, EntityPlayerSP commandExecutor, String[] commandMessage) {
        command.onExecute(commandMessage, commandExecutor);
    }

    public void reloadCommands() {
        commands.clear();
        this.registerCommands(this.mc);
        Iterator var1 = commands.iterator();

        while(var1.hasNext()) {
            Command command = (Command)var1.next();
            System.out.println("Reloaded command: " + command.getName());
        }

    }
}