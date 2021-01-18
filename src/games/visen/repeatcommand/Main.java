package games.visen.repeatcommand;

import games.visen.repeatcommand.commands.RepeatCommandCommand;
import games.visen.repeatcommand.core.RepeatCommand;
import games.visen.repeatcommand.data.Storage;
import games.visen.repeatcommand.data.YAML;
import games.visen.repeatcommand.utils.Config;
import org.bukkit.Bukkit;
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

    @Override
    public void onDisable() {
        int id = 0;
        for(String key : YAML.database.getKeys(false)){
            YAML.database.set(key,null);
        }
        for( RepeatCommand repeatCommand : Storage.repeatCommands) {
            repeatCommand.save(YAML.database.createSection("command-" + id));
            repeatCommand.deactivate();
            id++;
        }
        YAML.database.save();
    }

    public static Main getInstance() {
        return instance;
    }
}
