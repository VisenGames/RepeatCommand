package games.visen.repeatcommand;

import games.visen.repeatcommand.commands.RepeatCommandCommand;
import games.visen.repeatcommand.core.RepeatCommand;
import games.visen.repeatcommand.data.YAML;
import games.visen.repeatcommand.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        loadCommands();
        loadData();
        loadRepeatCommands();
    }

    public void loadCommands() {
        new RepeatCommandCommand(this);
    }

    public void loadData() {
        YAML.database = new Config("database.yml");
    }

    public void loadRepeatCommands() {
        for(String key : YAML.database.getKeys(false)) {
            RepeatCommand.load(YAML.database.getConfigurationSection(key));
        }
    }

    public static Main getInstance() {
        return instance;
    }
}
