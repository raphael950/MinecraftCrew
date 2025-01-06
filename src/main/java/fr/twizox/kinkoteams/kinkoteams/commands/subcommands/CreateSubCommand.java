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

public class CreateSubCommand extends TeamSubCommand {

    public CreateSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        super(messageConfig, playerDataManager, teamManager, inviteManager);
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a team";
    }

    @Override
    public String getUsage() {
        return messageConfig.createTeamUsage;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        PlayerData playerData = playerDataManager.getPlayerData(uuid);

        if (playerData.hasTeam()) return sendFormattedMessage(player, messageConfig.alreadyInTeam);

        if (args.length != 2) return sendFormattedMessage(player, messageConfig.createTeamUsage);
        if (teamManager.isExistingTeam(args[1])) return sendFormattedMessage(player, messageConfig.teamAlreadyExists);

        teamManager.saveTeam(new Team(uuid, args[1], player));
        playerData.setTeam(args[1]);
        playerDataManager.savePlayerData(playerData);
        return sendFormattedMessage(player, messageConfig.teamCreated);
    }

}
