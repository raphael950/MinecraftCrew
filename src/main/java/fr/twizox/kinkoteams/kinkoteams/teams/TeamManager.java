package fr.twizox.kinkoteams.kinkoteams.teams;

import com.j256.ormlite.dao.Dao;
import fr.twizox.kinkoteams.kinkoteams.databases.H2Database;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class TeamManager {

    private final Dao<Team, String> dao;
    private final ConcurrentHashMap<String, Team> teams;

    public TeamManager(H2Database database) {
        this.teams = new ConcurrentHashMap<>();
        database.registerTable(Team.class);
        this.dao = database.getDao(Team.class);
    }

    public ConcurrentHashMap<String, Team> getTeams() {
        return teams;
    }

    public void initDatabaseTeams() {
        try {
            List<Team> teams = dao.queryForAll();
            for (Team team : teams) {
                this.teams.put(team.getName(), team);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Team> getPlayerDataMap() {
        return teams;
    }

    public Team getTeam(String teamId) {
        return teams.get(teamId);
    }

    public CompletableFuture<Team> getDatabaseTeam(String teamName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return dao.queryForId(teamName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void saveTeam(Team team) {
        cache(team);
        CompletableFuture.runAsync(() -> {
            try {
                dao.createOrUpdate(team);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void deleteTeam(Team team) {
        uncache(team.getName());
        CompletableFuture.runAsync(() -> {
            try {
                dao.delete(team);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void cache(Team team) {
        teams.put(team.getName(), team);
    }

    public void uncache(String teamId) {
        teams.remove(teamId);
    }

    public Collection<String> getTeamsName() {
        return teams.keySet();
    }

    public boolean isExistingTeam(String teamName) {
        return teams.containsKey(teamName);
    }

}
