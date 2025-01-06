package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.command.CommandSender;

public class ListSubCommand extends TeamSubCommand {

    public ListSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        super(messageConfig, playerDataManager, teamManager, inviteManager);
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "List all teams";
    }

    @Override
    public String getUsage() {
        return "/equipage list";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("kinko.teams.admin")) return true;

        sender.sendMessage("Teams :");
        for (String loopTeam : teamManager.getTeamsName()) {
            sender.sendMessage(" -> " + loopTeam);
        }
        sender.sendMessage("End of teams");
        return true;

    }
}
