package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AcceptSubCommand extends TeamSubCommand {

    public AcceptSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
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

        if (playerData.hasTeam()) return sendFormattedMessage(player, messageConfig.alreadyInTeam);
        if (args.length < 2) return sendFormattedMessage(player, messageConfig.joinUsage);

        Player inviter = Bukkit.getPlayer(args[1]);

        if (!inviteManager.acceptInvitation(inviter, uuid))
            return sendFormattedMessage(player, String.format(messageConfig.inviteExpired, inviter.getName()));

        sendFormattedMessage(player, String.format(messageConfig.inviteAccepted, inviter.getName()));
        String joinMessage = String.format(messageConfig.teamMemberJoined, player.getName());

        for (UUID loopUuid : playerData.getTeam().getMemberUuids()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loopUuid);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(joinMessage);
            }
        }
        return true;
    }
}
