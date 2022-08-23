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
        if (rpgcore.getConfig().getConfigurationSection("auto_message") != null) {
            rpgcore.getConfig().createSection("auto_messages", autoCfg);
        }
        if (rpgcore.getConfig().get("max_lvl") == null) {
            rpgcore.getConfig().addDefault("max_lvl", 130);
        }
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
        if (rpgcore.getConfig().getConfigurationSection("exp_za_moby") == null) {
            rpgcore.getConfig().createSection("exp_za_moby");
            rpgcore.getConfig().getConfigurationSection("exp_za_moby").addDefault("[Lvl. 1] Podwladny Wody", 20.0);
        }

        if (rpgcore.getConfig().getConfigurationSection("Osiagniecia") == null) {
            rpgcore.getConfig().createSection("Osiagniecia");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Moby");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Gracze");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Metiny");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Sakwy");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Niesy");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Rybak");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Drwal");
            rpgcore.getConfig().getConfigurationSection("Osiagniecia").createSection("Gornik");


            for (int i = 1; i <= 10; i++){
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Moby").addDefault("Moby_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gracze").addDefault("Gracze_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Metiny").addDefault("Metiny_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Sakwy").addDefault("Sakwy_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Niesy").addDefault("Niesy_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Rybak").addDefault("Rybak_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Drwal").addDefault("Drwal_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").addDefault("Gornik_" + i,1 );
            }

        }

        if (rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List") == null) {
            rpgcore.getConfig().createSection("Kupiec_Item_List");
            rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").addDefault("Item_List", "MINECART,DIAMOND_CHESTPLATE");
            rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").createSection("Item_Price_List");
            rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getConfigurationSection("Item_Price_List").createSection("MINECART");
            rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getConfigurationSection("Item_Price_List").getConfigurationSection("MINECART").createSection("Example_Item_Name");
            rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getConfigurationSection("Item_Price_List").getConfigurationSection("MINECART").getConfigurationSection("Example_Item_Name").addDefault("Price", 100.0);

        }

        rpgcore.getConfig().options().copyDefaults(true);
        rpgcore.saveConfig();

        Utils.MAXLVL = rpgcore.getConfig().getInt("max_lvl");
    }
}
