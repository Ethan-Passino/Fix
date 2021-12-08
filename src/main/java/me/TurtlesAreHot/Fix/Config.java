package me.TurtlesAreHot.Fix;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static FileConfiguration config;

    public static void reloadConfig() { config = JavaPlugin.getPlugin(Main.class).getConfig(); }

    public static long getCelestialCooldown() { return config.getLong("celestial-cooldown"); }

    public static long getGodCooldown() { return config.getLong("god-cooldown"); }

    public static long getTitanCooldown() { return config.getLong("titan-cooldown"); }

    public static long getLegendCooldown() { return config.getLong("legend-cooldown"); }

    public static long getHeroCooldown() { return config.getLong("hero-cooldown"); }

    public static long getEliteCooldown() { return config.getLong("elite-cooldown"); }



}
