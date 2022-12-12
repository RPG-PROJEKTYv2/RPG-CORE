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
        if (rpgcore.getConfig().get("auto_message") == null) {
            rpgcore.getConfig().addDefault("auto_message", true);
        }
        if (rpgcore.getConfig().get("auto_message_time") == null) {
            rpgcore.getConfig().addDefault("auto_message_time", 5000);
        }
        autoCfg.put("auto_message_1", "&7To jest podstawowa wiadomosc. Edytuj Plik config.yml, zeby ja zmienic!");
        if (rpgcore.getConfig().getConfigurationSection("auto_messages") == null) {
            rpgcore.getConfig().createSection("auto_messages", autoCfg);
        }
        if (rpgcore.getConfig().getConfigurationSection("Osiagniecia") == null) {
            rpgcore.getConfig().createSection("Osiagniecia");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Players");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Mobs");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Time");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Gornik");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Fish");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Chest");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Upgrades");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Nies");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Metins");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Trees");
        }
        rpgcore.getConfig().options().copyDefaults(true);
        rpgcore.saveConfig();
    }
}
