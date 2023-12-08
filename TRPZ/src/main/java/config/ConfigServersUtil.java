package config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ConfigServersUtil {
    private File file;
    private FileConfiguration config;

    private static ConfigServersUtil util;


    private ConfigServersUtil(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);

        if (!file.exists()) {
            makeDefaultSetting();
        }
    }

    public static ConfigServersUtil getOrCreateConfig(Plugin plugin) {
        if (util == null) {
            util = new ConfigServersUtil(plugin.getDataFolder().getAbsolutePath() + "/" + "servers.yml");
        }
        return util;
    }


    public static ConfigServersUtil getConfig() throws NullPointerException{
        return util;
    }

    private void makeDefaultSetting() {
        config.set("servers", new LinkedList<>(List.of("127.0.0.1:8090/postMessage", "127.0.0.1:8081/postMessage")));
        config.set("names", new LinkedList<>(List.of("Telegram", "Web","Discord")));
        config.set("server_port", 8000);
        save();
    }


    public List<String> getAllServersURL() {
        List<String> temp = null;
        try {
            temp = (List<String>) config.getList("servers");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (temp != null) {
            return temp;
        } else return new LinkedList<>();
    }

    public List<String> getAllServersName() {
        List<String> temp = null;
        try {
            temp = (List<String>) config.getList("names");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (temp != null) {
            return temp;
        } else return new LinkedList<>();
    }


    public int getPort() {
        try {
            return config.getInt("server_port");
        } catch (Exception e) {
            e.printStackTrace();
            config.set("server_port", 8000);
            save();
            return 8000;
        }

    }



    public void setValue(String key, String value){
        config.set(key,value);
        save();
    }


    private void save() {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
