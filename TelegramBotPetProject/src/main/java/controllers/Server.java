package controllers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controllers.interfases.IModelSendToTelegram;
import controllers.interfases.ISendMessageToServer;
import exceptions.ServerDoNotInitializeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Server implements ISendMessageToServer {
    static IModelSendToTelegram maker;
    private static Server server;
    static boolean logger = true;

   private Server(){}

    public static Server initAndStart(String hostname, int port, IModelSendToTelegram maker, boolean logger){
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(hostname, port), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        server.createContext("/postMessage", new MyHandler());
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();

        System.out.println("Server start, "+hostname+":"+port);
        Server.maker = maker;
        Server.logger = logger;
        return new Server();
    }


    public static Server getServer() throws ServerDoNotInitializeException {
        if (server == null) {
            throw new ServerDoNotInitializeException("Server doesn't init!");
        }
        return server;
    }


    @Override
    public void sendMessage(String targetUrl, String params) {
        if(logger) System.out.println("Send message, url:" + targetUrl + "; params: " + params); //==========

        try {
            URL url = new URL("http://"+targetUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");

            OutputStream os = httpCon.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            // Get the input stream and read the server's response.
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Response from target server: " + inputLine);
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Can't send message: "+e.getMessage());
        }
    }


    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = ServerUtils.parsePostParameters(exchange);
                if (logger) System.out.println("Receive: "+ params);
                maker.sendToTelegramMessage(params);
            }


            OutputStream outputStream = exchange.getResponseBody();
            String response = "good";
            exchange.sendResponseHeaders(200, response.length());
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }


        static private class ServerUtils {
            private static Map<String, String> parsePostParameters(HttpExchange exchange) throws IOException {
                String str = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                return gson.fromJson(str, type);
            }
        }
    }


}
