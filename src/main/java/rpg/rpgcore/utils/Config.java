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
            rpgcore.getConfig().getConfigurationSection("exp_za_moby").addDefault("Test", 20.0);
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


            for (int i = 1; i < 10; i++){
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Players").addDefault("Players_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Mobs").addDefault("Mobs_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Time").addDefault("Time_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").addDefault("Gornik_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Fish").addDefault("Fish_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Chest").addDefault("Chest_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Upgrades").addDefault("Upgrades_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Nies").addDefault("Nies_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Metins").addDefault("Metins_" + i,1 );
                rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Trees").addDefault("Trees_" + i,1 );
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

        if (rpgcore.getConfig().getConfigurationSection("ListaNPC") == null) {
            rpgcore.getConfig().createSection("ListaNPC");
            rpgcore.getConfig().getConfigurationSection("ListaNPC").createSection("NPC_1");
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("world", "world");
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("x", 0.0);
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("y", 0.0);
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("z", 0.0);
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("yaw", 0.0);
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("pitch", 0.0);
            rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection("NPC_1").addDefault("Type", "POBOCZNE");
        }

        rpgcore.getConfig().options().copyDefaults(true);
        rpgcore.saveConfig();

        Utils.MAXLVL = rpgcore.getConfig().getInt("max_lvl");
    }
}
