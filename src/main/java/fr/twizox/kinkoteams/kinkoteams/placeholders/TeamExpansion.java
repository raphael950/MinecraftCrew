package fr.twizox.kinkoteams.kinkoteams.placeholders;

import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamExpansion extends PlaceholderExpansion {

    private final PlayerDataManager playerDataManager = KinkoTeams.getInstance().getPlayerDataManager();
    private final TeamManager teamManager = KinkoTeams.getInstance().getTeamManager();

    @Override
    public String getAuthor() {
        return "Twiz0x";
    }

    @Override
    public String getIdentifier() {
        return "equipage";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        final PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId());

        if (!playerData.hasTeam()) return null;

        final Team team = playerData.getTeam();

        switch (identifier) {
            case "name":
                return playerData.getTeamName();
            case "owner": {
                final UUID owner = playerData.getTeam().getOwnerUuid();
                return Bukkit.getOfflinePlayer(owner).getName();
            }
            case "members": {
                return Integer.toString(team.getMembers().size());
            }
            default:
                return "";
        }
    }

}