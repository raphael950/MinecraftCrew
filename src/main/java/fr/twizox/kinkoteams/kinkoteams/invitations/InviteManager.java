package fr.twizox.kinkoteams.kinkoteams.invitations;

import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import fr.twizox.kinkoteams.kinkoteams.configurations.PluginConfiguration;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerData;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class InviteManager {

    private final HashMap<UUID, InvitationGuest> invitations;
    private final PluginConfiguration pluginConfiguration;
    private final PlayerDataManager playerDataManager;
    private final TeamManager teamManager;

    public InviteManager(KinkoTeams kinkoTeams) {
        this.invitations = new HashMap<>();
        this.pluginConfiguration = kinkoTeams.getPluginConfiguration();
        this.teamManager = kinkoTeams.getTeamManager();
        this.playerDataManager = kinkoTeams.getPlayerDataManager();
    }

    public boolean hasPendingInvitation(UUID uuid) {
        return invitations.containsKey(uuid);
    }

    public InvitationGuest getInvitations(UUID uuid) {
        return invitations.get(uuid);
    }

    public boolean createInvitation(Player player, UUID target, Team team) {
        if (playerDataManager.getPlayerData(target).hasTeam()) return false;
        InvitationGuest invitationGuest = invitations.get(target);
        if (invitationGuest != null) {
            if (isInvitationExpired(invitationGuest, player)) {
                invitationGuest.addInvite(player.getName(), team);
                return true;
            }
            return false;
        }
        invitations.put(target, new InvitationGuest(player, target, team));
        return true;
    }

    public void deleteInvitations(UUID uuid) {
        invitations.remove(uuid);
    }

    public int cooldown(UUID target, Player player) {
        InvitationGuest invitationGuest = invitations.get(target);
        if (invitationGuest == null) return 0;
        final long timeElapsed = System.currentTimeMillis() - invitationGuest.getInvite(player.getName()).fst;
        final int elapsedInt = (int) timeElapsed / 1000;
        return pluginConfiguration.invitationExpireSeconds - elapsedInt;
    }

    public boolean isInvitationExpired(InvitationGuest invitationGuest, Player player) {
        if (invitationGuest == null || (invitationGuest.getInvite(player.getName()) == null)) return true;
        return cooldown(invitationGuest.getGuestUuid(), player) <= 0;
    }

    public boolean acceptInvitation(Player player, UUID target) {
        InvitationGuest invitationGuest = invitations.get(target);
        if (invitationGuest == null || isInvitationExpired(invitationGuest, player) ||
                invitationGuest.getTeam() == null || invitationGuest.getTeam().getMembers().size() == 0) return false;
        invitations.remove(target);
        Team team = invitationGuest.getTeam();
        PlayerData playerData = playerDataManager.getPlayerData(target);
        playerData.setTeam(team.getName());
        team.addMember(target, Bukkit.getOfflinePlayer(target).getName());
        playerDataManager.savePlayerData(playerData);
        teamManager.saveTeam(team);
        return true;
    }

}
