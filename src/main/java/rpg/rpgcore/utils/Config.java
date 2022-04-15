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
        rpgcore.getConfig().addDefault("auto_message_time", 5000);
        autoCfg.put("auto_message_1", "&7To jest podstawowa wiadomosc. Edytuj Plik config.yml, zeby ja zmienic!");
        rpgcore.getConfig().createSection("auto_messages", autoCfg);
        rpgcore.getConfig().addDefault("max_lvl", 130);
        if (rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl") == null) {
            rpgcore.getConfig().createSection("wymaganyexp_na_lvl");
            double value = 500.0;
            for (int i = 1; i <= Utils.MAXLVL; i++) {
                if (!(rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl").contains("wymaganyexp_lvl_" + i))) {
                    rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl").addDefault("wymaganyexp_lvl_" + i, value);
                    value = value + 5000.0;
                }
            }
        }
        rpgcore.getConfig().createSection("exp_za_moby");
        rpgcore.getConfig().getConfigurationSection("exp_za_moby").addDefault("[Lvl. 1] Podwladny Wody", 20.0);

        if (rpgcore.getConfig().getConfigurationSection("Osiagniecia") == null) {
            rpgcore.getConfig().createSection("Osiagniecia");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Moby");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Gracze");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Sakwy");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Niesy");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Rybak");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Drwal");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Gornik");


            for (int i = 1; i <= 10; i++){
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Moby").addDefault("Moby_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gracze").addDefault("Gracze_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Sakwy").addDefault("Sakwy_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Niesy").addDefault("Niesy_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Rybak").addDefault("Rybak_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Drwal").addDefault("Drwal_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").addDefault("Gornik_" + i,1 );
            }

        }

        rpgcore.getConfig().options().copyDefaults(true);
        rpgcore.saveConfig();

        Utils.MAXLVL = rpgcore.getConfig().getInt("max_lvl");
    }
}
