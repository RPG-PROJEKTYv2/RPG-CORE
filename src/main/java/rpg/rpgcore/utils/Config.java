package rpg.rpgcore.utils;

import rpg.rpgcore.RPGCORE;

import java.util.HashMap;

public class Config {

    private final RPGCORE rpgcore;
    private final HashMap<String, String> autoCfg = new HashMap<>();

    public Config(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void createConfig() {
        rpgcore.getConfig().addDefault("hostname", "hostname.pl");
        rpgcore.getConfig().addDefault("port", "3306");
        rpgcore.getConfig().addDefault("database", "minecraft");
        rpgcore.getConfig().addDefault("username", "username");
        rpgcore.getConfig().addDefault("password", "password");
        rpgcore.getConfig().addDefault("auto_message", true);
        rpgcore.getConfig().addDefault("auto_message_time", 500);
        autoCfg.put("auto_message_1", "&7To jest podstawowa wiadomosc. Edytuj Plik config.yml, zeby ja zmienic!");
        rpgcore.getConfig().createSection("auto_messages", autoCfg);
        rpgcore.getConfig().options().copyDefaults(true);
        rpgcore.saveConfig();
    }
}
