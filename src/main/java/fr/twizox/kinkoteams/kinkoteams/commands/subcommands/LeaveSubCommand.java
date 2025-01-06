package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LeaveSubCommand extends TeamSubCommand{

    public LeaveSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        super(messageConfig, playerDataManager, teamManager, inviteManager);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        PlayerData playerData = playerDataManager.getPlayerData(uuid);
        Team team = playerData.getTeam();

        if (!playerData.hasTeam()) return sendFormattedMessage(player, messageConfig.noTeam);
        if (team.isOwner(uuid)) {
            if (team.getMembers().size() != 1) {
                return sendFormattedMessage(player, messageConfig.changeOwnerBefore);
            }
        }

        team.removeMember(uuid);

        for (UUID loopUuid : team.getMemberUuids()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loopUuid);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(applyPrefix(String.format(messageConfig.teamMemberQuit, player.getName())));
            }
        }

        teamManager.saveTeam(team);
        playerData.setTeam(null);
        playerDataManager.savePlayerData(playerData);

        return sendFormattedMessage(player, messageConfig.teamLeft);
    }
}
