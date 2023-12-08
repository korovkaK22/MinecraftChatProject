package model;

import configs.Config;
import controllers.interfases.IModelSendToServer;
import controllers.interfases.ISendMessageToTelegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;

public class TelegramBot extends TelegramLongPollingBot implements ISendMessageToTelegram {
    static boolean logger = false;
    static IModelSendToServer serv;
    static TelegramBot bot = new TelegramBot();

    static void init(IModelSendToServer serv, boolean logger){
        TelegramBot.logger = logger;
        TelegramBot.serv = serv;
    }

    TelegramBot(){};

    public static TelegramBot getBot(){
        return bot;
    }

    @Override
    public String getBotUsername() {
        return Config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return Config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            System.out.println("get msg from \u001B[97m"+message.getFrom().getUserName()+": "+ message.getText()+ "\u001B[0m");
            serv.sendToServer(message);

        }
    }


    public void sendTextMessage(String text, String chatId){
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        message.setChatId(chatId);
        if (logger) System.out.println("send to chat msg: "+ message.getText());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(text);
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void sendMessage(String message) {
        sendTextMessage(message, Config.getChatId());
    }
}