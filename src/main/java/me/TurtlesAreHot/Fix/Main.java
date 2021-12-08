package me.TurtlesAreHot.Fix;

import me.TurtlesAreHot.Fix.commands.Fix;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static File dataFolder;

    @Override
    public void onEnable() {
        dataFolder = getDataFolder();
        createCooldownFolder();
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        // cooldowns in seconds
        config.addDefault("celestial-cooldown", 86400); //1 days
        config.addDefault("god-cooldown", 172800); //2 days
        config.addDefault("titan-cooldown", 259200); //3 days
        config.addDefault("legend-cooldown", 302400); //3.5 days
        config.addDefault("hero-cooldown", 345600); // 4 days
        config.addDefault("elite-cooldown", 432000); // 5 days
        config.options().copyDefaults(true);
        this.saveConfig();
        Config.reloadConfig();
        getCommand("fix").setExecutor(new Fix());
    }

    @Override
    public void onDisable() {

    }

    public static File getFolder() { return dataFolder; }

    private void createCooldownFolder() {
        File cooldownFolder = new File(getFolder(), "/cooldown/");
        if(!(cooldownFolder.exists())) {
            cooldownFolder.mkdirs();
        }
    }
}
