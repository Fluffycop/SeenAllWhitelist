package com.github.fluffycop.seenallwhitelist;

import org.bukkit.ChatColor;

public interface Messages {
    String NO_PERMISSION = ChatColor.RED + "You do not have permission to do that";
    String BEGINNING_SCAN = ChatColor.WHITE + "Scanning all whitelisted players' activity...";

    String SCAN_FAIL = ChatColor.RED + "Activity scan has failed unexpectedly";
    String SCAN_SUCCESS = ChatColor.WHITE + "Scan successful! The player activity data has been stored in " + ChatColor.BLUE + "plugins/SeenAllWhitelist/latest";
    String SCAN_IN_PROGRESS = ChatColor.RED + "An activity scan is already in progress. Please try again later.";
}
