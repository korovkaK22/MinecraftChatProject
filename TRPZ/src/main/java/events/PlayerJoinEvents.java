package events;

import io.papermc.paper.event.player.AsyncChatEvent;
import messages.*;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import servercore.TRPZ;

public class PlayerJoinEvents implements Listener {
    MessageModel messageModel = new MessageModel();

    public PlayerJoinEvents(TRPZ plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void playerJoinEvent(PlayerJoinEvent event){
        String playerName = event.getPlayer().getName();
        String str = "join the server";
        Message msg = new MessageBuilder().addPlatform("Server").addText(str).addSender(playerName).addColor(ChatColor.YELLOW)
                .addType(MessageType.JOIN_MESSAGE).getResultMessage();
        messageModel.sendMessageToAllServers(msg);
    }

}
