package model;

import configs.Config;
import controllers.interfases.*;
import controllers.messages.Message;
import controllers.messages.MessageConverter;

import java.util.Map;

public class MessageModel implements IModelSendToTelegram, IModelSendToServer {
    ISendMessageToTelegram tgSend;
    ISendMessageToServer servSend;

    @Override
    public void sendToTelegramMessage(Map<String, String> map) {
       Message message = MessageConverter.convertMapToMessage(map);
       String text= MessageConverter.convertMessageChatFromMinecraftMessage(message);
       tgSend.sendMessage(text);
    }

    @Override
    public void sendToServer(org.telegram.telegrambots.meta.api.objects.Message message) {
        Message msg = MessageConverter.getMinecraftMessageFromTelegramMessage(message);
        String params = MessageConverter.getParamsFromMinecraftMessage(msg);
        servSend.sendMessage(Config.getServerAddressURL(),params);
    }

    public void setTgSend(ISendMessageToTelegram tgSend) {
        this.tgSend = tgSend;
    }

    public void setServSend(ISendMessageToServer servSend) {
        this.servSend = servSend;
    }
}
