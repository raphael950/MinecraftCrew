package fr.twizox.kinkoteams.kinkoteams.commands.subcommands;

import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InviteSubCommand extends TeamSubCommand {

    public InviteSubCommand(MessagesConfiguration messageConfig, PlayerDataManager playerDataManager, TeamManager teamManager, InviteManager inviteManager) {
        super(messageConfig, playerDataManager, teamManager, inviteManager);
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invite a player to your team";
    }

    @Override
    public String getUsage() {
        return messageConfig.inviteUsage;
    }

    @Override
    public String getPermission() {
        return "kinkoteams.team.invite";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        PlayerData playerData = playerDataManager.getPlayerData(uuid);
        if (!playerData.hasTeam()) return sendFormattedMessage(player, messageConfig.noTeam);
        Team team = playerData.getTeam();

        if (!team.isOwner(uuid)) return sendFormattedMessage(player, messageConfig.notOwner);
        if (args.length == 0) return sendFormattedMessage(player, getUsage());
        if (player.getName().equals(args[0])) sendFormattedMessage(player, messageConfig.cannotInviteSelf);
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) return sendFormattedMessage(player, String.format(messageConfig.playerNotFound, args[1]));

        if (team.hasMember(target.getUniqueId()))
            return sendFormattedMessage(player, String.format(messageConfig.targetAlreadyInTeam, target.getName()));

        if (playerDataManager.getPlayerData(target.getUniqueId()).hasTeam())
            return sendFormattedMessage(player, String.format(messageConfig.targetAlreadyInATeam, target.getName()));

        if (!inviteManager.createInvitation(player, target.getUniqueId(), team))
            return sendFormattedMessage(player, String.format(messageConfig.inviteDelay,
                    inviteManager.cooldown(target.getUniqueId(), player)));

        sendFormattedMessage(player, String.format(messageConfig.inviteDone, target.getName()));

        TextComponent text = new TextComponent(String.format(messageConfig.inviteNotification, team.getName(), player.getName()));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(messageConfig.hoverInvite).create()));
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "§e/equipage join " + player.getName()));
        target.spigot().sendMessage(text);
        return true;
    }

}
