package fr.twizox.kinkoteams.kinkoteams.invitations;

import fr.twizox.kinkoteams.kinkoteams.teams.Team;
import fr.twizox.kinkoteams.kinkoteams.utils.Pair;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvitationGuest {

    private final Team team;
    private final Map<String, Pair<Long, Team>> teams; // <playerName, <timestamp, team>>
    private final UUID guestUuid;

    public InvitationGuest(Player inviter, UUID target, Team team) {
        this.teams = new HashMap<>();
        teams.put(inviter.getName(), new Pair<>(System.currentTimeMillis(), team));
        this.guestUuid = target;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public UUID getGuestUuid() {
        return guestUuid;
    }

    public Pair<Long, Team> getInvite(String playerName) {
        return teams.get(playerName);
    }

    public void addInvite(String playerName, Team team) {
        teams.put(playerName, new Pair<>(System.currentTimeMillis(), team));
    }

    public void removeInvite(String playerName) {
        teams.remove(playerName);
    }

}
