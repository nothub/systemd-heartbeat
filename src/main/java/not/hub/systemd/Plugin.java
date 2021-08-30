package not.hub.systemd;

import info.faljse.SDNotify.SDNotify;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new Metrics(this, 12657);
        if (!SDNotify.isWatchdogEnabled()) {
            getLogger().warning("systemd watchdog is not available!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("sending startup notification");
        SDNotify.sendNotify();
        new BukkitRunnable() {
            @Override
            public void run() {
                SDNotify.sendWatchdog();
            }
        }.runTaskTimer(this, 0, 1);
    }

}
