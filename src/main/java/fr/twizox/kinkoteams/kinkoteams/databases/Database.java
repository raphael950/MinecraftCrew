package fr.twizox.kinkoteams.kinkoteams.databases;

import com.j256.ormlite.support.ConnectionSource;
import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;

public abstract class Database {

    protected final String name;
    protected final KinkoTeams kinkoTeams;
    protected ConnectionSource connectionSource;

    protected Database(String name, KinkoTeams kinkoTeams) {
        this.name = name;
        this.kinkoTeams = kinkoTeams;
    }

    public String getName() {
        return name;
    }

    public abstract void init();

    public abstract void close();

}
