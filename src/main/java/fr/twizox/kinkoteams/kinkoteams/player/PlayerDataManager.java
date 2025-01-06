package fr.twizox.kinkoteams.kinkoteams.player;

import com.j256.ormlite.dao.Dao;
import fr.twizox.kinkoteams.kinkoteams.databases.H2Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    private final Dao<PlayerData, String> dao;
    private final ConcurrentHashMap<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(H2Database database) {
        this.playerDataMap = new ConcurrentHashMap<>();
        database.registerTable(PlayerData.class);
        this.dao = database.getDao(PlayerData.class);
        registerOnlinePlayerDatas();
    }

    private void registerOnlinePlayerDatas() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            getDatabaseData(player.getUniqueId()).whenComplete(((playerData, throwable) -> cache(playerData)));
        }
    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public CompletableFuture<PlayerData> getDatabaseData(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return dao.queryForId(uuid.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void savePlayerData(PlayerData playerData) {
        cache(playerData);
        CompletableFuture.runAsync(() -> {
            try {
                dao.createOrUpdate(playerData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void cache(PlayerData playerData) {
        playerDataMap.put(playerData.getUuid(), playerData);
    }

    public void uncache(PlayerData playerData) {
        uncache(playerData.getUuid());
    }

    public void uncache(UUID uuid) {
        playerDataMap.remove(uuid);
    }

}
