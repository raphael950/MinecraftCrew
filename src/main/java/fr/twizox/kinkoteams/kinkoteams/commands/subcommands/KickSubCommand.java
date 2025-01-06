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

public class KickSubCommand extends TeamSubCommand {

    public KickSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
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
        return messageConfig.kickUsage;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        PlayerData playerData = playerDataManager.getPlayerData(uuid);
        Team team = playerData.getTeam();

        if (!playerData.hasTeam()) return sendFormattedMessage(player, messageConfig.noTeam);
        if (!team.isOwner(uuid)) return sendFormattedMessage(player, messageConfig.notOwner);
        if (args.length != 2) return sendFormattedMessage(player, getUsage());
        if (player.getName().equals(args[1])) return sendFormattedMessage(player, messageConfig.cannotKickSelf);

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!target.hasPlayedBefore() && !target.isOnline())
            return sendFormattedMessage(player, String.format(messageConfig.playerNotFound, args[1]));

        if (!team.hasMember(target.getUniqueId()))
            return sendFormattedMessage(player, String.format(messageConfig.targetNotInTeam, target.getName()));

        team.removeMember(target.getUniqueId());
        teamManager.saveTeam(team);

        if (target.isOnline()) {
            sendFormattedMessage(target.getPlayer(), messageConfig.gotKicked);
        }

        for (UUID loopUuid : team.getMemberUuids()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loopUuid);
            if (offlinePlayer.isOnline())
                sendFormattedMessage(offlinePlayer.getPlayer(), String.format(messageConfig.teamMemberKicked, target.getName()));
        }

        PlayerData targetData = playerDataManager.getPlayerData(target.getUniqueId());
        if (targetData == null) {
            playerDataManager.getDatabaseData(target.getUniqueId()).whenComplete(((targetData1, throwable) -> {
                targetData1.setTeam(null);
                playerDataManager.savePlayerData(targetData1);
            }));
        } else {
            targetData.setTeam(null);
            playerDataManager.savePlayerData(targetData);
        }
        return true;
    }
}
