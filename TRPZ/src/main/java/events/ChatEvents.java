package events;

import io.papermc.paper.event.player.AsyncChatEvent;
import messages.*;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import servercore.TRPZ;

public class ChatEvents implements Listener {
    MessageModel messageModel;

    public ChatEvents(TRPZ plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        messageModel = new MessageModel();

    }

    @EventHandler
    private void chatEvent(AsyncChatEvent event) {
        //Bukkit.broadcastMessage("<message>");


        String str = ((TextComponent)event.message()).content();
        Message msg = new MessageBuilder().addText(str).addPlatform("Server").addSender(event.getPlayer().getName()).addColor(ChatColor.WHITE)
                        .addType(MessageType.CHAT_MESSAGE).getResultMessage();

        messageModel.sendMessageToAllServers(msg);


       // Bukkit.getLogger().info( "Event: "+ event.getPlayer().getName() + ": "+ str);
    }


}
