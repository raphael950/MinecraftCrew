package fr.twizox.kinkoteams.kinkoteams.configurations;

import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import ru.xezard.configurations.Configuration;
import ru.xezard.configurations.ConfigurationComments;
import ru.xezard.configurations.ConfigurationField;

import java.io.File;

public class PluginConfiguration extends Configuration {

    public PluginConfiguration(KinkoTeams kinkoTeams) {
        super(kinkoTeams.getDataFolder().getAbsolutePath() + File.separator + "messages.yml");
    }

    @ConfigurationField("invitation-expire-seconds")
    @ConfigurationComments("Temps d'expiration des invitations en secondes")
    public int invitationExpireSeconds = 300;

}
