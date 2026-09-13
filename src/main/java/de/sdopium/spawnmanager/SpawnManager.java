package de.sdopium.spawnmanager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnManager extends JavaPlugin {

    private Location spawn;

    @Override
    public void onEnable() {
        getLogger().info("SpawnManager wurde gestartet!");
        saveDefaultConfig();

        if (getConfig().contains("spawn")) {
            spawn = getConfig().getLocation("spawn");
        }

        getCommand("spawn").setExecutor(this);
        getCommand("setspawn").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("SpawnManager wurde beendet!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Dieser Befehl ist nur für Spieler.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("setspawn")) {
            spawn = player.getLocation().clone();
            getConfig().set("spawn", spawn);
            saveConfig();

            player.sendMessage(ChatColor.GREEN + "Server-Spawn wurde gesetzt!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("spawn")) {
            if (spawn == null) {
                player.sendMessage(ChatColor.RED + "Es wurde noch kein Spawn gesetzt.");
                return true;
            }

            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Du wurdest zum Spawn teleportiert!");
            return true;
        }

        return false;
    }
}
