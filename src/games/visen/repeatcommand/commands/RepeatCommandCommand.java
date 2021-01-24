package games.visen.repeatcommand.commands;

import games.visen.repeatcommand.Main;
import games.visen.repeatcommand.core.RepeatCommand;
import games.visen.repeatcommand.core.RepeatCommandPlayer;
import games.visen.repeatcommand.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;


public class RepeatCommandCommand implements CommandExecutor {
    private Main plugin;

    public RepeatCommandCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("repeatcommand").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("repeatcommand.use") && sender instanceof Player) {
            if(args.length > 0) {
                try {
                    switch (args[0]) {
                        case "add":
                            StringBuilder commandBuilder = new StringBuilder();
                            for (int i = 1; i < args.length; i++) {
                                commandBuilder.append(args[i]).append(" ");
                            }
                            String command = commandBuilder.toString();
                            RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).addCommand(command);
                            Utils.message(sender, "&aAdded command: " + command);
                            break;
                        case "remove":
                            RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).removeCommand(Integer.parseInt(args[1]));
                            Utils.message(sender, "&cRemoved command!");
                            break;
                        case "start":
                            RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).startCommand(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                            Utils.message(sender, "&aStarted command!");
                            break;
                        case "stop":
                            RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).stopCommand(Integer.parseInt(args[1]));
                            Utils.message(sender, "&cStopped command!");
                            break;
                        case "help":
                            sendHelpMessage(sender);
                            break;
                        case "list":
                            LinkedList<RepeatCommand> repeatCommands = RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).getRepeatCommands();
                            for (int i = 0; i < repeatCommands.size(); i++) {
                                String message;
                                if (repeatCommands.get(i).isActive()) {
                                    message = "&c[&a" + i + "&c] &e Command: |" + repeatCommands.get(i).getCommand() + "| &aActive";
                                } else {
                                    message = "&c[&a" + i + "&c] &e Command: |" + repeatCommands.get(i).getCommand() + "| &cNot Active";
                                }
                                Utils.message(sender, message);
                            }
                            break;
                    }
                } catch(Exception e) {
                    Utils.message(sender, "&4Invalid command usage!");
                    sendHelpMessage(sender);
                }
            } else {
                sendHelpMessage(sender);
            }
        } else {
            Utils.message(sender, "&cYou can not use this command!");
        }

        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        Utils.message(sender, "&cUsage: ");
        Utils.message(sender, "/repeatcommand add {%command%} - Add a command.");
        Utils.message(sender, "/repeatcommand remove {%command_id%} - Remove a command.");
        Utils.message(sender, "/repeatcommand start {%command_id%} {%ticks%} - Start a command with a tick delay.");
        Utils.message(sender, "/repeatcommand stop {%command_id%} - Stop a command.");
        Utils.message(sender, "/repeatcommand list - Shows list of added commands.");
        Utils.message(sender, "/repeatcommand help - Shows this!");
    }
}
