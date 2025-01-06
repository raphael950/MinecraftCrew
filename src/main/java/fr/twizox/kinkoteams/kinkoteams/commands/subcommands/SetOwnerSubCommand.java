package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetOwnerSubCommand extends TeamSubCommand {

    public SetOwnerSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        super(messageConfig, playerDataManager, teamManager, inviteManager);
    }

    @Override
    public String getName() {
        return "setowner";
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

        if (!playerData.hasTeam()) return sendFormattedMessage(player, messageConfig.noTeam);

        Team team = playerData.getTeam();

        if (!team.isOwner(uuid)) return sendFormattedMessage(player, messageConfig.notOwner);
        if (player.getName().equals(args[1])) return sendFormattedMessage(player, messageConfig.cannotOwnerSelf);
        if (args.length != 2) return sendFormattedMessage(player, messageConfig.setOwnerUsage);
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) return sendFormattedMessage(player, String.format(messageConfig.playerNotFound, args[1]));
        if (!team.hasMember(target.getUniqueId())) return sendFormattedMessage(player, String.format(messageConfig.targetNotInTeam, target.getName()));

        team.setOwner(target.getUniqueId());
        teamManager.saveTeam(team);
        return sendFormattedMessage(player, String.format(messageConfig.ownerSet, target.getName()));
    }
}
