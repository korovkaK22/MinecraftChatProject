package messages;

import events.AdvancementsEvents;
import org.bukkit.ChatColor;

public class MessageBuilder {
    Message message;
    public MessageBuilder() {
        message  = new Message();
    }

    public MessageBuilder addSender(String sender){
        message.setSender(sender);
        return this;
    }

    public MessageBuilder addText(String text){
        message.setText(text);
        return this;
    }

    public MessageBuilder addType(MessageType type){
        message.setMessageType(type);
        return this;
    }

    public MessageBuilder addColor(ChatColor color){
        message.setChatColor(color);
        return this;
    }

    public MessageBuilder addPlatform(String platform){
        message.setPlatform(platform);
        return this;
    }

    public Message getResultMessage(){
        return message;
    }

}
