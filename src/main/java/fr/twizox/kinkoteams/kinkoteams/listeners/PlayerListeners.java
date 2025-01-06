package fr.twizox.kinkoteams.kinkoteams.listeners;

import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListeners implements Listener {

    private final PlayerDataManager playerDataManager;
    private final InviteManager inviteManager;

    public PlayerListeners(KinkoTeams kinkoTeams) {
        this.playerDataManager = kinkoTeams.getPlayerDataManager();
        this.inviteManager = kinkoTeams.getInviteManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerDataManager.getDatabaseData(player.getUniqueId()).whenComplete(((playerData, throwable) -> {
            if (playerData == null) {
                playerDataManager.savePlayerData(new PlayerData(player.getUniqueId(), null));
            } else {
                playerDataManager.cache(playerData);
            }
        }));

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        playerDataManager.uncache(uuid);
        inviteManager.deleteInvitations(uuid);
    }

}
