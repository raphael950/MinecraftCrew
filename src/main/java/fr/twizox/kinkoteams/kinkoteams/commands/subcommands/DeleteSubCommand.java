package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeleteSubCommand extends TeamSubCommand {

    public DeleteSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        super(messageConfig, playerDataManager, teamManager, inviteManager);
    }

    @Override
    public String getName() {
        return "deleteteam";
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
        if (!team.isOwner(uuid)) return sendFormattedMessage(player, messageConfig.notOwner);

        teamManager.deleteTeam(teamManager.getTeam(playerData.getTeamName()));
        playerData.setTeam(null);
        playerDataManager.savePlayerData(playerData);
        return sendFormattedMessage(player, messageConfig.teamDeleted);
    }
}
