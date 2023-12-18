package configs;

public class Config {
    static String hostName = "localhost";
    static int port = 8090;
    static String botUsername = "Server Chat Bot";
    static String botTokens = "23423498:AAF46Tj09VeQHw-eC82oNz6gsNLM9VFM2d8";
    static String chatId = "-4002026887"; // 891204961 //-4002026887
    static String serverAddressURL = "127.0.0.1:8000/postMessage";

    public static String getServerAddressURL() {
        return serverAddressURL;
    }

    public static String getChatId() {
        return chatId;
    }

    public static String getHostName() {
        return hostName;
    }
    public static int getPort() {
        return port;
    }

    public static String getBotUsername() {
        return botUsername;
    }

    public static String getBotToken() {
        return botToken;
    }


}
