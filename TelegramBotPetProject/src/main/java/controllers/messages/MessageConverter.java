package controllers.messages;

import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

import static controllers.messages.ChatColor.WHITE;

public class MessageConverter {


    public static Message convertMapToMessage(Map<String, String> i) {
        return new MessageBuilder().addPlatform(i.get("platform")).addText(i.get("message")).addType(MessageType.valueOf(i.get("type")))
                .addSender(i.get("sender")).addColor(ChatColor.valueOf(i.get("color"))).getResultMessage();
    }


    public static Message getMinecraftMessageFromTelegramMessage(org.telegram.telegrambots.meta.api.objects.Message message) {
        User user = message.getFrom();
        return new MessageBuilder().addSender(user.getUserName()).addColor(WHITE).addType(MessageType.CHAT_MESSAGE)
                .addPlatform("Telegram").addText(message.getText()).getResultMessage();
    }

    public static String getParamsFromMinecraftMessage(Message message) {
        Map<String, String> map = new HashMap<>();
        map.put("type", message.getMessageType().name());
        map.put("sender", message.getSender());
        map.put("message", message.getText());
        map.put("color", message.getChatColor().name());
        map.put("platform", message.getPlatform());
        Gson gson = new Gson();
        return gson.toJson(map);
    }


    public static String convertMessageChatFromMinecraftMessage(Message i) {

        String platf = i.getPlatform();
        switch (platf) {
            case "Server", "Telegram" -> platf = "";
            case "Discord" -> platf = "ds ";
            case "Web" -> platf = "web ";
            default -> platf = "?? ";
        }

        switch (i.getMessageType()) {
            case JOIN_MESSAGE, QUIT_MESSAGE -> {
                return "`<" + i.getSender() + "> " + i.getText() + "`";
            }
            default -> {
                return "**" + platf + "** __<" + i.getSender() + ">__ : " + i.getText() + " ";
            }
        }
    }


}

