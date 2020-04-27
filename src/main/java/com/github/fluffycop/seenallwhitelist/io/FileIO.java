package com.github.fluffycop.seenallwhitelist.io;

import com.github.fluffycop.seenallwhitelist.SelectionSort;
import com.github.fluffycop.seenallwhitelist.SeenAllWhitelist;
import com.github.fluffycop.seenallwhitelist.SeenInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileIO {
    private SeenAllWhitelist pl;

    private ExecutorService seenAllWorker;
    boolean seenAllInProgress = false;

    private final File scanFolder;

    public FileIO(SeenAllWhitelist pl) {
        this.pl = pl;
        pl.getLogger().info("Setting up worker threads...");
        seenAllWorker = Executors.newSingleThreadExecutor();
        scanFolder = new File(pl.getDataFolder().getAbsolutePath());
        scanFolder.mkdirs();
    }

    public CompletableFuture<IOStatus> seenAll() {
        if(seenAllInProgress) {
            return CompletableFuture.completedFuture(IOStatus.IN_PROGRESS);
        }
        seenAllInProgress = true;
        return CompletableFuture.supplyAsync(() -> {
            List<SeenInfo> info = new ArrayList<>();
            for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
                if(p.isOnline()) {
                    info.add(new SeenInfo(0, p));
                } else if(!p.hasPlayedBefore()) {
                    info.add(new SeenInfo(-1, p));
                } else {
                    info.add(new SeenInfo(p.getLastPlayed(), p));
                }
            }
            SelectionSort.sort(info);
            try {
                File file = setupFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for(SeenInfo i : info) {
                    writer.write(i.format() + System.lineSeparator());
                }
                writer.close();
                copyLatest(file);
            } catch (IOException e) {
                e.printStackTrace();
                return IOStatus.FAIL;
            } finally {
                Bukkit.getScheduler().runTask(pl, () -> seenAllInProgress = false);
            }
            return IOStatus.SUCCESS;
        }, seenAllWorker);
    }

    private final DateFormat format = new SimpleDateFormat("YYYY-MM-dd");

    private File setupFile() throws IOException {
        Date date = new Date();
        File file = new File(scanFolder.getAbsolutePath() + File.separator+ format.format(date)+".txt");
        int i = 1;
        while(file.exists()) {
            file = new File(scanFolder.getAbsolutePath()+File.separator+format.format(date)+"-"+i+".txt");
            i++;
        }
        file.createNewFile();
        return file;
    }

    private void copyLatest(File file) throws IOException {
        File latest = new File(scanFolder.getAbsolutePath()+File.separator+"latest.txt");
        latest.delete();
        latest.createNewFile();
        FileUtil.copy(file, latest);
    }
}
