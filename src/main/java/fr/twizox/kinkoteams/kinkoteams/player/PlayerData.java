package fr.twizox.kinkoteams.kinkoteams.player;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import fr.twizox.kinkoteams.kinkoteams.teams.Team;

import java.util.UUID;

@DatabaseTable(tableName = "player_data")
public class PlayerData {

    @DatabaseField(id = true)
    private String uuid;

    @DatabaseField
    private String team;

    public PlayerData() {
        // ORMLite needs a no-arg constructor
    }

    public PlayerData(UUID uuid, String teamName) {
        this.uuid = uuid.toString();
        this.team = teamName;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public boolean hasTeam() {
        return team != null;
    }

    public Team getTeam() {
        if (team == null) return null;
        return KinkoTeams.getInstance().getTeamManager().getTeam(team);
    }

    public String getTeamName() {
        return team;
    }

    public void setTeam(String teamName) {
        this.team = teamName;
    }

}
