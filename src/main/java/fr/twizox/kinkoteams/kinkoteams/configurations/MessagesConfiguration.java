package fr.twizox.kinkoteams.kinkoteams.configurations;

import fr.twizox.kinkoteams.kinkoteams.KinkoTeams;
import ru.xezard.configurations.Configuration;
import ru.xezard.configurations.ConfigurationField;

import java.io.File;

public class MessagesConfiguration extends Configuration {

    public MessagesConfiguration(KinkoTeams kinkoTeams) {
        super(kinkoTeams.getDataFolder().getAbsolutePath() + File.separator + "messages.yml");
    }

    @ConfigurationField("prefix")
    public String prefix = "§b§lEquipage §8» §7";

    @ConfigurationField("help")
    public String help = "/equipage : liste des commandes\n" +
            "/equipage create <nom> : créer un équipage avec un nom\n" +
            "/equipage setowner <pseudo> : mettre un joueur chef de l'équipage\n" +
            "/equipage invite <pseudo> : inviter un joueur dans son équipage (chef seulement)\n" +
            "/equipage kick <pseudo> : retirer un joueur de son équipage (chef seulement)\n" +
            "/equipage delete : dissoudre l'équipage (chef uniquement) ";

    @ConfigurationField("team-created")
    public String teamCreated = "Equipe créée avec succès";

    @ConfigurationField("already-in-team")
    public String alreadyInTeam = "Vous etes déjà dans un equipage";

    @ConfigurationField("team-create-usage")
    public String createTeamUsage = "/equipage create <nom>";

    @ConfigurationField("team-already-exists")
    public String teamAlreadyExists = "L'equipage existe déjà";

    @ConfigurationField("owner-set")
    public String ownerSet = "Le nouvel owner est %s";

    @ConfigurationField("team-member-joined")
    public String teamMemberJoined = "%s a rejoint l'equipage";

    @ConfigurationField("no-team")
    public String noTeam = "Vous n'avez pas d'équipage, demandez à quelqu'un de vous inviter ou faites /equipage create <nom> pour en créer un";

    @ConfigurationField("not-owner")
    public String notOwner = "Erreur: vous n'etes pas le chef de votre equipage";

    @ConfigurationField("invite-done")
    public String inviteDone = "Vous avez bien invité %s dans votre equipage";

    @ConfigurationField("no-player-found")
    public String playerNotFound = "Erreur: le joueur %s n'a pas été trouvé";

    @ConfigurationField("set-owner-usage")
    public String setOwnerUsage = "/equipage setowner <pseudo>";

    @ConfigurationField("cannot-owner-self")
    public String cannotOwnerSelf = "Vous êtes déjà le chef de votre Equipage !";

    @ConfigurationField("target-not-in-team")
    public String targetNotInTeam = "Erreur: le joueur %s n'est pas dans votre equipage";

    @ConfigurationField("target-already-in-team")
    public String targetAlreadyInTeam = "Erreur: le joueur %s est déjà dans votre equipage";

    @ConfigurationField("target-already-in-a-team")
    public String targetAlreadyInATeam = "Erreur: le joueur %s a déjà un equipage";

    @ConfigurationField("team-delete")
    public String teamDeleted = "Vous avez bien dissoud l'équipage";

    @ConfigurationField("kick-usage")
    public String kickUsage = "/equipage kick <pseudo>";

    @ConfigurationField("got-kicked")
    public String gotKicked = "Vous avez été expulsé de l'équipage";

    @ConfigurationField("team-member-kicked")
    public String teamMemberKicked = "%s a été expulsé du equipage";

    @ConfigurationField("team-member-quit")
    public String teamMemberQuit = "%s a quitté l'equipage";

    @ConfigurationField("team-left")
    public String teamLeft = "Vous avez quitté l'équipage";

    @ConfigurationField("kick-done")
    public String kickDone = "Vous avez bien kick %s de votre equipage";

    @ConfigurationField("cannot-kick-self")
    public String cannotKickSelf = "Erreur: vous ne pouvez pas vous kick vous même";

    @ConfigurationField("invite-usage")
    public String inviteUsage = "/equipage invite <pseudo>";

    @ConfigurationField("cannot-invite-self")
    public String cannotInviteSelf = "Erreur: vous ne pouvez pas vous inviter vous même";

    @ConfigurationField("join-usage")
    public String joinUsage = "/equipage join <pseudo>";

    @ConfigurationField("invite-delay")
    public String inviteDelay = "Erreur: vous avez déjà invité ce joueur ! L'invitation expire dans %d";

    @ConfigurationField("invite-expired")
    public String inviteExpired = "Erreur: l'invitation a expiré ou n'existe pas";

    @ConfigurationField("invite-accepted")
    public String inviteAccepted = "Vous avez bien accepté l'invitation de %s";

    @ConfigurationField("invite-notification")
    public String inviteNotification = "Vous avez été invité dans l'équipage %s ! \n" +
            "Cliquez ici pour accepter l'invitation de %s";

    @ConfigurationField("hover-accept")
    public String hoverInvite = "/equipage join %s";

    @ConfigurationField("change-owner-before")
    public String changeOwnerBefore = "Veuillez transferer votre équipage avant de quitter";

}
