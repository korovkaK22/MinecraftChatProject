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

import java.util.LinkedList;

public class PlayerQuitEvents implements Listener {
    MessageModel messageModel = new MessageModel();

    public PlayerQuitEvents(TRPZ plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void playerQuitEvent(PlayerQuitEvent event){

        String playerName = event.getPlayer().getName();
        String str = "leave the server";
        Message msg = new MessageBuilder().addPlatform("Server").addText(str).addSender(playerName).addColor(ChatColor.YELLOW)
                .addType(MessageType.QUIT_MESSAGE).getResultMessage();

        messageModel.sendMessageToAllServers(msg);
    }

}
