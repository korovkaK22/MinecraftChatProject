package model;

import configs.Config;
import controllers.interfases.IModelSendToTelegram;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import controllers.Server;

public class ModelInit {
    public static void main(String[] args) throws TelegramApiException {
        boolean logger = true;

        @Override
        MessageModel messageModel = new MessageModel();
        Server server = Server.initAndStart(Config.getHostName(), Config.getPort(), messageModel, logger);
        messageModel.setServSend(server);
        messageModel.setTgSend(TelegramBot.getBot());

        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new TelegramBot());

        TelegramBot.init(messageModel, logger);

    }
}
