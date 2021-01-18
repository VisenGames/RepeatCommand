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
                if(args[0].equals("add")) {
                    StringBuilder commandBuilder = new StringBuilder();
                    for(int i = 1; i < args.length; i++) {
                        commandBuilder.append(args[i]);
                    }
                    String command = commandBuilder.toString();
                    RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).addCommand(command);
                } else if (args[0].equals("remove")) {
                    RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).removeCommand(Integer.parseInt(args[1]));
                } else if (args[0].equals("start")) {
                    RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).startCommand(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                } else if (args[0].equals("stop")) {
                    RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).stopCommand(Integer.parseInt(args[1]));
                }  else if (args[0].equals("list")) {
                    LinkedList<RepeatCommand> repeatCommands = RepeatCommandPlayer.getRepeatCommandPlayer((Player) sender).getRepeatCommands();
                    for(int i = 0; i < repeatCommands.size(); i++) {
                        String message;
                        if(repeatCommands.get(i).isActive()) {
                            message = "&c[&a" + i + "&c] &e Command: |" + repeatCommands.get(i).getCommand() + "| &aActive";
                        } else {
                            message = "&c[&a" + i + "&c] &e Command: |" + repeatCommands.get(i).getCommand() + "| &cNot Active";
                        }
                        Utils.message(sender, message);
                    }
                }
            }
        } else {
            Utils.message(sender, "&cYou can not use this command!");
        }

        return true;
    }
}
