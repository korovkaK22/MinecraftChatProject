package messages;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessagesUtil {

    public static Optional<Message> convertFromHttpRequestToMessage(Map<String, String> i) {
        if (!validateMap(i)){
            return Optional.empty();
        }
        String platform = "undefined";
        if (i.containsKey("platform")) {
            platform = i.get("platform");
        }

        Message message = new MessageBuilder().addPlatform(platform).addText(i.get("message")).addType(MessageType.valueOf(i.get("type")))
                .addSender(i.get("sender")).addColor(ChatColor.valueOf(i.get("color"))).getResultMessage();
        return Optional.of(message);
    }



    private static boolean validateMap(Map<String, String> map) {
        if (!map.containsKey("message")) {
            Bukkit.getLogger().info("Missing message component: message");
            return false;
        }
        if (!map.containsKey("sender")) {
            Bukkit.getLogger().info("Missing message component: sender");
            return false;
        }
        if (!map.containsKey("color")) {
            Bukkit.getLogger().info("Missing message component: color");
            return false;
        } else {
            try {
                ChatColor c = ChatColor.valueOf(map.get("color"));
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().info("Invalid message color argument: " + e.getMessage());
                return false;
            }
        }
        if (!map.containsKey("type")) {
            Bukkit.getLogger().info("Missing message component: type");
            return false;
        } else {
            try {
                MessageType c = MessageType.valueOf(map.get("type"));
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().info("Invalid message type argument: " + e.getMessage());
                return false;
            }
        }
        return true;
    }



}
