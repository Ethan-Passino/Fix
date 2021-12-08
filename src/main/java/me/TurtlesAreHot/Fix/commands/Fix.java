package me.TurtlesAreHot.Fix.commands;

import me.TurtlesAreHot.Fix.Config;
import me.TurtlesAreHot.Fix.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Fix implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info("Only players can run this command.");
            return false;
        }
        Player p = (Player) sender;
        ItemStack held = p.getInventory().getItem(p.getInventory().getHeldItemSlot());
        if(held == null || held.getType() == Material.AIR) {
            // This is the case the player is not holding anything in their hand
            p.sendMessage(ChatColor.RED + "You must be holding something in your hand to use this command.");
            return false;
        }
        long cooldownTime = 0L;
        if(p.hasPermission("fix.celestial")) {
            cooldownTime = Config.getCelestialCooldown();
        } else if(p.hasPermission("fix.god")) {
            cooldownTime = Config.getGodCooldown();
        } else if(p.hasPermission("fix.titan")) {
            cooldownTime = Config.getTitanCooldown();
        } else if(p.hasPermission("fix.legend")) {
            cooldownTime = Config.getLegendCooldown();
        } else if(p.hasPermission("fix.hero")) {
            cooldownTime = Config.getHeroCooldown();
        } else if(p.hasPermission("fix.elite")) {
            cooldownTime = Config.getEliteCooldown();
        }
        if(cooldownTime == 0L) {
            // This is the case the player does not have permission for this command.
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }

        // Check the cooldown to make sure they don't have a cooldown
        long cooldown = (long) Cooldown.checkCooldown(p)/1000;
        // cooldown is the number of seconds that has passed since the cooldown started.
        if(cooldown < cooldownTime && cooldown != -1) {
            // This is the case the player is still on cooldown.
            p.sendMessage(ChatColor.RED + "You are still on cooldown. Please check back in later.");
            return false;
        }
        Cooldown.removeCooldown(p);
        Damageable meta = (Damageable) held.getItemMeta();
        if(meta == null) {
            p.sendMessage(ChatColor.RED + "The item you are holding does not have a durability.");
            return false;
        }
        if(!(meta.hasDamage())) {
            p.sendMessage(ChatColor.RED + "The item you are trying to repair does not need repaired.");
            return false;
        }
        int currentHealth = meta.getDamage();
        meta.setDamage(0);
        held.setItemMeta((ItemMeta) meta);
        p.sendMessage(ChatColor.GREEN + "Repaired your item!");
        Cooldown.addCooldown(p, System.currentTimeMillis());
        return false;
    }
}
