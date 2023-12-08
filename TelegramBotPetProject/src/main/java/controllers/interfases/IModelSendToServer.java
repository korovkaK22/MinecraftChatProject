package controllers.interfases;

import java.util.Map;

public interface IModelSendToServer {
    public void sendToServer(org.telegram.telegrambots.meta.api.objects.Message message);
}
