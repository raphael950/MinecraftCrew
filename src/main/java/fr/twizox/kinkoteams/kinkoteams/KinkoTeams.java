package fr.twizox.kinkoteams.kinkoteams;

import fr.twizox.kinkoteams.kinkoteams.placeholders.TeamExpansion;
import fr.twizox.kinkoteams.kinkoteams.commands.TeamCommand;
import fr.twizox.kinkoteams.kinkoteams.configurations.MessagesConfiguration;
import fr.twizox.kinkoteams.kinkoteams.configurations.PluginConfiguration;
import fr.twizox.kinkoteams.kinkoteams.databases.H2Database;
import fr.twizox.kinkoteams.kinkoteams.invitations.InviteManager;
import fr.twizox.kinkoteams.kinkoteams.listeners.PlayerListeners;
import fr.twizox.kinkoteams.kinkoteams.player.PlayerDataManager;
import fr.twizox.kinkoteams.kinkoteams.teams.TeamManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KinkoTeams extends JavaPlugin {

    private H2Database database;
    private TeamManager teamManager;
    private PlayerDataManager playerDataManager;
    private MessagesConfiguration messagesConfiguration;
    private PluginConfiguration pluginConfiguration;
    private InviteManager inviteManager;
    private static KinkoTeams instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.database = new H2Database(this, "kinkoteams");
        database.init();

        this.playerDataManager = new PlayerDataManager(database);
        this.teamManager = new TeamManager(database);
        teamManager.initDatabaseTeams();

        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TeamExpansion().register();
        }

        pluginConfiguration = new PluginConfiguration(this);
        pluginConfiguration.load();

        messagesConfiguration = new MessagesConfiguration(this);
        messagesConfiguration.load();

        inviteManager = new InviteManager(this);
        getCommand("equipage").setExecutor(new TeamCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
    }

    @Override
    public void onDisable() {
        database.close();
        messagesConfiguration = null;
        instance = null;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public H2Database getDatabase() {
        return database;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public static KinkoTeams getInstance() {
        return instance;
    }


    public PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    public MessagesConfiguration getMessageConfiguration() {
        return this.messagesConfiguration;
    }

    public InviteManager getInviteManager() {
        return inviteManager;
    }
}
