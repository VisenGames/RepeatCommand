package games.visen.repeatcommand.core;

import games.visen.repeatcommand.Main;
import games.visen.repeatcommand.data.Storage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.UUID;

public class RepeatCommand {

    private final String command;

    private boolean active = false;

    private BukkitTask mainTask;

    private final RepeatCommandPlayer player;

    private int ticks = 0;

    public RepeatCommand(String command, RepeatCommandPlayer player) {
        this.command = command;
        this.player = player;
        Storage.repeatCommands.add(this);
    }

    public void activate(int ticks) {
        if(!active) {
            active = true;
            this.ticks = ticks;
            mainTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command), ticks, ticks);
        }
    }

    public void deactivate() {
        if(active) {
            active = false;
            mainTask.cancel();
        }
    }

    public boolean isActive() {
        return active;
    }

    public String getCommand() {
        return command;
    }

    public void save(ConfigurationSection configSection) {
        configSection.set("player-uuid", this.player.getPlayer().getUniqueId().toString());
        configSection.set("command", this.command);
        configSection.set("active", this.active);
        configSection.set("ticks", ticks);
    }

    public static void load(ConfigurationSection configSection) {
        try {
            RepeatCommandPlayer repeatCommandPlayer = RepeatCommandPlayer.getRepeatCommandPlayer(Bukkit.getOfflinePlayer(UUID.fromString(Objects.requireNonNull(configSection.getString("player-uuid")))));
            String command = configSection.getString("command");
            boolean active = configSection.getBoolean("active");
            int ticks = configSection.getInt("ticks");
            RepeatCommand repeatCommand = new RepeatCommand(command, repeatCommandPlayer);
            if(active) {
                repeatCommand.activate(ticks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
