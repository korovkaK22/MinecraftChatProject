package messages;

import com.google.gson.Gson;
import config.ConfigServersUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import server.ServerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageModel {
    ConfigServersUtil configServersUtil = ConfigServersUtil.getConfig();
    ServerImpl server = ServerImpl.getServer();


    public void sendMessageToAllServers(Message message) {
        Map<String, String> map = new HashMap<>();
        map.put("type", message.getMessageType().name());
        map.put("sender", message.getSender());
        map.put("message", message.getText());
        map.put("color", message.getChatColor().name());
        map.put("platform", message.getPlatform());
        Gson gson = new Gson();
        String param = gson.toJson(map);

        List<String> adresses = configServersUtil.getAllServersURL();

        for (String adress : adresses) {
            server.sendMessage(adress, param);
        }
    }


    public void sendMessageToAllServers(Map<String, String> message, String exclusive) {
        Gson gson = new Gson();
        String param = gson.toJson(message);

        List<String> adresses = configServersUtil.getAllServersURL();
        List<String> names = configServersUtil.getAllServersName();

        for (int i = 0; i < adresses.size(); i++) {
            if (!exclusive.equals(names.get(i))) {
                server.sendMessage(adresses.get(i), param);
            }
        }
    }

    public void sendMessageToChatServer(Message message) {
        String s = message.getPlatform();
        switch (s) {
            case "Telegram" -> s = "tg";
            case "Discord" -> s = "ds";
            case "Web" -> s = "web";
            default -> s = "??";
        }

        Bukkit.broadcastMessage(s + " " + ChatColor.ITALIC + "<" + message.getSender() + "> " + message.getChatColor() + message.getText());
    }


    public void sendMessageToChatServer(Map<String, String> messageMap) {
        Optional<Message> message = MessagesUtil.convertFromHttpRequestToMessage(messageMap);
        if (message.isEmpty()) {
            Bukkit.getLogger().info("Corrupt message, discard");
        } else {
            this.sendMessageToChatServer(message.get());
        }
    }


}
