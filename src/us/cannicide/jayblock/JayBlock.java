package us.cannicide.jayblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class JayBlock extends JavaPlugin {

    public static FileConfiguration config;

    public static JayBlock getPlugin() {
        return JayBlock.getPlugin(JayBlock.class);
    }

    public static void reloadConfig(CommandSender sender) {
        if (!(sender.hasPermission("jayblock.reload") || sender instanceof ConsoleCommandSender)) {
            sender.sendMessage("Insufficient permission.");
            return;
        }

        getPlugin().reloadConfig();
        config = getPlugin().getConfig();
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the config.");
    }

    public static String getColorString(String key) {
        return config.getString(key).replaceAll("&([0-9a-f])", "\u00A7$1");
    }

    public static void sendInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "JayBlock v" + getPlugin().getDescription().getVersion() + " by JayCraft2/Cannicide");
        sender.sendMessage(getColorString("messages.help").replaceAll("\\{nl}", "\n"));
    }

    @Override
    public void onDisable() {
        getPlugin().getLogger().info(ChatColor.GOLD + "Disabled JayBlock");
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();

        getPlugin().getLogger().info(ChatColor.GOLD + "Started JayBlock by JayCraft2/Cannicide");
        this.getCommand("jayblock").setExecutor(new JayBlockCmd());
        this.getCommand("jayblock").setTabCompleter(new JayBlockCmd());
        getServer().getPluginManager().registerEvents(new JayBlockListener(), this);
        config.set("version", getPlugin().getDescription().getVersion());
        this.saveConfig();
    }

    public static class JayBlockCmd implements CommandExecutor, TabCompleter {

        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (args.length >= 1 && args[0] != null) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        JayBlock.reloadConfig(sender);
                    }
                    else {
                        JayBlock.sendInfo(sender);
                    }
                }
                else {
                    JayBlock.sendInfo(sender);
                }

            return true;
        }

        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

            List<String> res = new ArrayList<>();
            res.add("reload");

            return res;

        }

    }

    public static class JayBlockListener implements Listener {
        @EventHandler
        public void onCommand(PlayerCommandPreprocessEvent e){
            if (e.getPlayer().hasPermission("jayblock.bypass") && e.getPlayer().hasPermission("jayblock.nonexist.bypass")) return;

            String msg = e.getMessage().substring(1);
            List<String> blocked = config.getStringList("blocked");

            if (!msg.startsWith("minecraft:") && !msg.startsWith("bukkit:") && Bukkit.getServer().getPluginCommand(msg.split(" ")[0]) == null && !config.getBoolean("allow-unregistered")) {
                //Command does not exist
                if (config.getBoolean("nonexist-enabled")) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(getColorString("messages.nonexist").replaceAll("\\{nl}", "\n"));
                }
                return;
            }

            if (e.getPlayer().hasPermission("jayblock.bypass")) return;

            for (String item : blocked) {
                boolean isColon = false;
                if (item.endsWith(":")) isColon = true;

                if (msg.equalsIgnoreCase(item) || (isColon ? msg.toLowerCase().startsWith(item.toLowerCase()) : msg.toLowerCase().startsWith(item.toLowerCase() + " "))) {
                    //Block it
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(getColorString("messages.isblocked").replace("{message}", msg).replace("{blocked-cmd}", item).replaceAll("\\{nl}", "\n"));
                }
                else if (Bukkit.getServer().getPluginCommand(item) != null) {

                    for (String alias : Bukkit.getServer().getPluginCommand(item).getAliases()) {
                        if (msg.equalsIgnoreCase(alias) || (isColon ? msg.toLowerCase().startsWith(alias.toLowerCase()) : msg.toLowerCase().startsWith(alias.toLowerCase() + " "))) {
                            //Block it
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(getColorString("messages.isblocked").replace("{message}", msg).replace("{blocked-cmd}", item + "(alias: " + alias + ")").replaceAll("\\{nl}", "\n"));
                        }
                    }
                }
            }
        }
    }
}