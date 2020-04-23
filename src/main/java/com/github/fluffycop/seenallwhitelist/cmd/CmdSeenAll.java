package com.github.fluffycop.seenallwhitelist.cmd;

import com.github.fluffycop.seenallwhitelist.Messages;
import com.github.fluffycop.seenallwhitelist.SeenAllWhitelist;
import com.github.fluffycop.seenallwhitelist.io.IOStatus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.CompletableFuture;

public class CmdSeenAll implements CommandExecutor {
    private static final String PERMISSION = "seenallwhitelist.seenall";

    private SeenAllWhitelist pl;

    public CmdSeenAll(SeenAllWhitelist pl) {
        this.pl = pl;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) { //permission check
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        //actually do it
        sender.sendMessage(Messages.BEGINNING_SCAN);
        CompletableFuture<IOStatus> future = pl.getIo().seenAll();
        future.thenAccept(status -> {
            if(status == IOStatus.FAIL) {
                sender.sendMessage(Messages.SCAN_FAIL);
            } else if(status == IOStatus.IN_PROGRESS) {
                sender.sendMessage(Messages.SCAN_IN_PROGRESS);
            } else {
                sender.sendMessage(Messages.SCAN_SUCCESS);
            }
        });
        return true;
    }
}
