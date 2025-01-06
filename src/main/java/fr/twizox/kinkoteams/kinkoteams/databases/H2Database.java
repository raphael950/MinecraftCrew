package fr.twizox.kinkoteams.kinkoteams.databases;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;

public class H2Database extends Database {

    public H2Database(KinkoTeams kinkoTeams, String name) {
        super(name, kinkoTeams);
    }

    public void init() {
        String connectionUrl = "jdbc:h2:" + kinkoTeams.getDataFolder().getAbsolutePath() + File.separator + name;
        try {
            kinkoTeams.getLogger().log(Level.INFO, "Successfully connected to database: " + name);
            connectionSource = new JdbcConnectionSource(connectionUrl);
        } catch (SQLException e) {
            kinkoTeams.getLogger().log(Level.SEVERE, "Could not connect the database" + name + "! Disabling the plugin", e);
            Bukkit.getServer().getPluginManager().disablePlugin(kinkoTeams);
        }
    }

    public void close() {
        try {
            connectionSource.close();
            kinkoTeams.getLogger().log(Level.INFO, "Successfully disconnected from the database " + name);
        } catch (Exception e) {
            kinkoTeams.getLogger().log(Level.SEVERE, "Could not disconnect from the database "+ name + " !", e);
        }
    }

    public <T> Dao<T, String> getDao(Class<T> tClass) {
        try {
            return DaoManager.createDao(connectionSource, tClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void registerTable(Class<T> tClass) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, tClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }
}