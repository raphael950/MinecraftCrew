package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import fr.twizox.kinkoteams.kinkoteams.utils.SenderType;
import fr.twizox.kinkoteams.kinkoteams.utils.SubCommand;
import org.bukkit.command.CommandSender;

public abstract class TeamSubCommand implements SubCommand {

    protected final MessagesConfiguration messageConfig;
    protected final PlayerDataManager playerDataManager;
    protected final TeamManager teamManager;
    protected final InviteManager inviteManager;

    @Override
    public SenderType getSenderType() {
        return SenderType.PLAYER;
    }

    @Override
    public String getPermission() {
        return "kinko.teams." + getName();
    }

    protected TeamSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        this.messageConfig = messageConfig;
        this.playerDataManager = playerDataManager;
        this.teamManager = teamManager;
        this.inviteManager = inviteManager;
    }

    protected String applyPrefix(String message) {
        return messageConfig.prefix + message;
    }

    protected boolean sendFormattedMessage(CommandSender sender, String message) {
        message = applyPrefix(message);
        sender.sendMessage(message);
        return true;
    }

}
