package fr.twizox.kinkoteams.kinkoteams.commands;

import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import fr.twizox.kinkoteams.kinkoteams.commands.subcommands.*;
import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import fr.twizox.kinkoteams.kinkoteams.utils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamCommand implements CommandExecutor {

    private final MessagesConfiguration messageConfig;
    private final TeamManager teamManager;
    private final PlayerDataManager playerDataManager;
    private final InviteManager inviteManager;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public TeamCommand(KinkoTeams kinkoTeam) {
        this.teamManager = kinkoTeam.getTeamManager();
        this.playerDataManager = kinkoTeam.getPlayerDataManager();
        this.messageConfig = kinkoTeam.getMessageConfiguration();
        this.inviteManager = kinkoTeam.getInviteManager();
        registerSubsCommand();
    }

    private void registerSubsCommand() {
        subCommands.put("invite", new InviteSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));

        subCommands.put("accept", new AcceptSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));

        subCommands.put("create", new CreateSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));
        subCommands.put("delete", new DeleteSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));
        subCommands.put("setowner", new SetOwnerSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));
        subCommands.put("list", new ListSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));

        subCommands.put("leave", new LeaveSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));
        subCommands.put("kick", new KickSubCommand(messageConfig, playerDataManager, teamManager, inviteManager));
    }

    private String applyPrefix(String message) {
        return messageConfig.prefix + message;
    }

    private String getPlayerPrefix(Team team, UUID uuid) {
        if (team.getOwnerUuid().equals(uuid)) {
            return "§b";
        } else {
            return "§7";
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez être un joueur pour éxecuter cette commande !");
            return true;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        PlayerData playerData = playerDataManager.getPlayerData(uuid);
        Team team = playerData.getTeam();

        if (args.length == 0) {
            if (team == null) player.sendMessage(applyPrefix(messageConfig.noTeam));
            else listMembers(player, team);
            return true;
        }

        if (subCommands.containsKey(args[0])) {
            player.sendMessage(args[0]);
            player.sendMessage(Arrays.stream(args).skip(1).toArray(String[]::new));
            subCommands.get(args[0]).execute(player, Arrays.stream(args).skip(1).toArray(String[]::new));
            return true;
        } else sendHelp(player);

        return true;
    }

    private boolean sendHelp(CommandSender sender) {
        sender.sendMessage(applyPrefix(messageConfig.help));
        return true;
    }

    private void listMembers(CommandSender commandSender, Team team) {
        StringBuilder msg = new StringBuilder("§f§l" + team.getName()).append("\n \n");
        msg.append("§aEn ligne (%s):\n §7- ");
        StringBuilder offline = new StringBuilder("§cHors ligne (%s):\n §7- ");
        int onlines = 0, offlines = 0;
        for (UUID loopUuid : team.getMembers().keySet()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loopUuid);
            if (offlinePlayer.isOnline()) {
                onlines++;
                msg.append(getPlayerPrefix(team, loopUuid)).append(offlinePlayer.getName()).append("§7, ");
            } else {
                offlines++;
                offline.append(getPlayerPrefix(team, loopUuid)).append(offlinePlayer.getName()).append("§7, ");
            }
        }
        msg.delete(msg.length() - 2, msg.length());
        if (offlines > 0) {
            offline.delete(offline.length() - 2, offline.length());
        } else {
            offline.delete(offline.length() - 7, offline.length());
        }

        msg.append("\n \n").append(offline).append("\n \n").append("§8➥ §7Besoin d'aide ? /equipage help");
        commandSender.sendMessage(applyPrefix(String.format(msg.toString(), onlines, offlines)));
    }

}
