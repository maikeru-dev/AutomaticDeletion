package eu.maikeru.automaticdeletion.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class PlayerDataRemover {
    private static OfflinePlayer[] getExpiredPlayers(int time_period) {
        return (OfflinePlayer[]) Arrays.stream(Bukkit.getServer().getOfflinePlayers())
                .filter(offlinePlayer ->
                        (offlinePlayer.getLastSeen() + time_period > Instant.now().getEpochSecond())).toArray();
    }
    public static void delete(FileConfiguration config) {
        final int time_period = config.getInt("deletion.time-period", 2160)*1000;
        final List<String> directories = config.getStringList("deletion.directories");

        for (OfflinePlayer player : getExpiredPlayers(time_period)) {
            for (String directory : directories) {
                File file = findPlayerFile(directory, player);
                if (file.exists()) {
                    if (true) {
                        Bukkit.getLogger().info(file.getPath() + " deleted of player " + player.getName());
                    }else {
                        Bukkit.getLogger().warning(file.getPath() + " of player " + " could not be deleted.");
                    }
                }else {
                    Bukkit.getLogger().warning(file.getPath() + " of player " + player.getName() + " does not exist.");
                }
            }
        }

        // Load the directory list from the config
        // Get expired players then check for their existence in each directory
    }
    private static File findPlayerFile(String searchTerm, OfflinePlayer player) {

        String filetype = searchTerm.substring(searchTerm.lastIndexOf(".")); // get the file type
        String[] directory = (String[]) Arrays.stream(searchTerm.split("/"))
                .limit(searchTerm.split("/").length-1)
                .toArray();
        String path = String.join("/", directory) + player.getUniqueId().toString() + filetype;

        return new File(path);
    }

}
