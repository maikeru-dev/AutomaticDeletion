package eu.maikeru.automaticdeletion;

import eu.maikeru.automaticdeletion.utils.PlayerDataRemover;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import eu.maikeru.automaticdeletion.commands.*;
import static eu.maikeru.automaticdeletion.utils.PlayerDataRemover.*;

import java.io.File;

public final class AutomaticDeletion extends JavaPlugin {
    /*
        Purpose:
            Stop players from flooding the playerdata folder, schedule a deletion of an account after a time period.
        Method:
            When player leaves, add player.UID to plugin-data file with UNIX timestamp
            Load file up when time-period arrives and check timestamp. Use key/value sets.
            Delete player files if player has not been online for a period of time.
        Time period ideas:
            Delete on restart
            Delete on delayed task
            Delete on command
        Extra ideas:
            Scale removal date with how much playtime a player has.
            Provide scan command - Done!
            Provide a config file - Done!
        Problems:
            Player data will be removed, but not other plugins plugin-data. - Solved?
        Config:
            - Time period for removal - Done!
            - List of directories to scan and delete files from - Done!

        Concepts:
            ScanCommand Class
            PlayerDataRemover Class
            --PlayerQuit Listener-- Bukkit provides a timestamp, might still be needed
            --PluginDataAccessor Class-- Bukkit provides a timestamp

     */
    @Override
    public void onEnable() {
        getCommand("scan").setExecutor(new ScanCommand());
        Bukkit.getLogger().info("Enabled AutomaticDeletion v1.0.1");
        PlayerDataRemover.delete(getConfiguration());

    }

    public @NotNull FileConfiguration getConfiguration() {
        final @NotNull File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

}
