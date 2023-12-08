package servercore;

import config.ConfigServersUtil;
import events.AdvancementsEvents;
import events.ChatEvents;
import events.PlayerJoinEvents;
import events.PlayerQuitEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import server.ServerImpl;

public final class TRPZ extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("TRPZ was enabled");

        ConfigServersUtil util = ConfigServersUtil.getOrCreateConfig(this);

        new ChatEvents(this);
        new PlayerJoinEvents(this);
        new PlayerQuitEvents(this);
        new AdvancementsEvents(this);

        ServerImpl server = ServerImpl.getServer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("TRPZ was disabled");
    }
}
