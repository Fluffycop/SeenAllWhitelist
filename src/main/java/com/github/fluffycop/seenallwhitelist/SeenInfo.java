package com.github.fluffycop.seenallwhitelist;

import com.earth2me.essentials.utils.DateUtil;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public class SeenInfo {
    private final long lastSeen;
    private final OfflinePlayer player;

    public SeenInfo(long lastSeen, OfflinePlayer player) {
        this.lastSeen = lastSeen;
        this.player = player;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String format() {
        if(lastSeen == -1) {
            return player.getName() + " has never been online";
        } else if(lastSeen == 0) {
            return player.getName() + " is currently online";
        } else {
            return player.getName() + " was last seen " + DateUtil.formatDateDiff(lastSeen);
        }
    }
}
