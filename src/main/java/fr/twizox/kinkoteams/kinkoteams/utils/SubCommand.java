package fr.twizox.kinkoteams.kinkoteams.utils;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String getName();
    SenderType getSenderType();
    String getDescription();
    String getUsage();
    String getPermission();
    boolean execute(CommandSender sender, String[] args);

}
