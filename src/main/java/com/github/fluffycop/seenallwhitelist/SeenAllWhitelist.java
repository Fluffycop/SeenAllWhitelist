package com.github.fluffycop.seenallwhitelist;

import com.github.fluffycop.seenallwhitelist.cmd.CmdSeenAll;
import com.github.fluffycop.seenallwhitelist.io.FileIO;
import org.bukkit.plugin.java.JavaPlugin;

public class SeenAllWhitelist extends JavaPlugin {
    private FileIO io;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        setupFileIO();
        setupCommands();
    }

    private void setupFileIO() {
        getLogger().info("Setting up file I/O");
        io = new FileIO(this);
    }

    private void setupCommands() {
        getCommand("seenall").setExecutor(new CmdSeenAll(this));
    }

    public FileIO getIo() {
        return io;
    }
}
