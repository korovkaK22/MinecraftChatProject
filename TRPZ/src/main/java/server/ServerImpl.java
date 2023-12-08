package server;

import com.google.gson.Gson;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import config.ConfigServersUtil;
import messages.Message;
import messages.MessageModel;
import messages.MessagesUtil;
import org.bukkit.Bukkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerImpl {
    private static ServerImpl server;

    private static void init() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", ConfigServersUtil.getConfig().getPort()), 0);
            Bukkit.getLogger().info("Java Server has started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.createContext("/postMessage", new MyHandler());
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
    }


    public static ServerImpl getServer() {
        if (server == null) {
            init();
            server = new ServerImpl();
        }
        return server;
    }


    public void sendMessage(String targetUrl, String params) {

        try {
            URL url = new URL("http://"+targetUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");

            OutputStream os = httpCon.getOutputStream();
            os.write(params.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            // Get the input stream and read the server's response.
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            //   String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("Response from target server: " + inputLine);
//            }
            in.close();
        } catch (MalformedURLException e){
            Bukkit.getLogger().info("Can't connect to server \""+ targetUrl+ "\":"+  e.getMessage());
        }
        catch (Exception e) {
           Bukkit.getLogger().info("Can't send message on server \""+ targetUrl+ "\": "+  e.getMessage());
        }
    }


    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*"); // Дозволяє запити з усіх джерел
            headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS"); // Дозволяє методи GET, POST та OPTIONS
            headers.add("Access-Control-Allow-Headers", "Content-Type,Authorization"); // Специфікує заголовки, які дозволені в запитах

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                // Обробляємо preflight-запит (CORS)
                exchange.sendResponseHeaders(204, -1);
                return;
            }



            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = ServerUtils.parsePostParameters(exchange);
                MessageModel messageModel = new MessageModel();
                messageModel.sendMessageToChatServer(params);
                String exclusive =  params.containsKey("platform")? params.get("platform"): "??";
                messageModel.sendMessageToAllServers(params,exclusive);
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
                Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                return gson.fromJson(str, type);
            }
        }
    }


}



