package games.visen.repeatcommand.core;

import games.visen.repeatcommand.data.Storage;
import org.bukkit.OfflinePlayer;

import java.util.LinkedList;

public class RepeatCommandPlayer {


    private final OfflinePlayer player;

    private final LinkedList<RepeatCommand> repeatCommands = new LinkedList<>();

    public RepeatCommandPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public void addCommand(String command) {
        repeatCommands.add(new RepeatCommand(command, this));
    }

    public void removeCommand(int id) {
        if(id < repeatCommands.size()) {
            repeatCommands.get(id).deactivate();
            repeatCommands.remove(id);
        }
    }

    public void startCommand(int id, int tick) {
        if(id < repeatCommands.size()) {
            repeatCommands.get(id).activate(tick);
        }
    }

    public void stopCommand(int id) {
        if(id < repeatCommands.size()) {
            repeatCommands.get(id).deactivate();
        }
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public LinkedList<RepeatCommand> getRepeatCommands() {
        return repeatCommands;
    }

    public static RepeatCommandPlayer getRepeatCommandPlayer(OfflinePlayer player) {

        for(RepeatCommandPlayer repeatCommandPlayer : Storage.repeatCommandPlayers) {
            if(repeatCommandPlayer.getPlayer().equals(player)) {
                return repeatCommandPlayer;
            }
        }
        RepeatCommandPlayer repeatCommandPlayer = new RepeatCommandPlayer(player);
        Storage.repeatCommandPlayers.add(repeatCommandPlayer);
        return repeatCommandPlayer;
    }



}
