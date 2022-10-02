package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.pets.enums.PetItems;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ZwierzakiManager {
    private final Set<Items> pety = Sets.newConcurrentHashSet();

    public ZwierzakiManager() {
        this.pety.add(new Items("1", 2.77, PetItems.getByName("Duszek-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("2", 2.77, PetItems.getByName("Duszek-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("3", 2.77, PetItems.getByName("Duszek-Epicki").getItemStack(), 1));
        this.pety.add(new Items("4", 2.77, PetItems.getByName("Duszek-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("5", 2.77, PetItems.getByName("Zlota-Rybka-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("6", 2.77, PetItems.getByName("Zlota-Rybka-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("7", 2.77, PetItems.getByName("Zlota-Rybka-Epicki").getItemStack(), 1));
        this.pety.add(new Items("8", 2.77, PetItems.getByName("Zlota-Rybka-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("9", 2.77, PetItems.getByName("Pancernik-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("10", 2.77, PetItems.getByName("Pancernik-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("11", 2.77, PetItems.getByName("Pancernik-Epicki").getItemStack(), 1));
        this.pety.add(new Items("12", 2.77, PetItems.getByName("Pancernik-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("13", 2.77, PetItems.getByName("Foka-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("14", 2.77, PetItems.getByName("Foka-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("15", 2.77, PetItems.getByName("Foka-Epicki").getItemStack(), 1));
        this.pety.add(new Items("16", 2.77, PetItems.getByName("Foka-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("17", 2.77, PetItems.getByName("Nietoperz-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("18", 2.77, PetItems.getByName("Nietoperz-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("19", 2.77, PetItems.getByName("Nietoperz-Epicki").getItemStack(), 1));
        this.pety.add(new Items("20", 2.77, PetItems.getByName("Nietoperz-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("21", 2.77, PetItems.getByName("Bobr-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("22", 2.77, PetItems.getByName("Bobr-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("23", 2.77, PetItems.getByName("Bobr-Epicki").getItemStack(), 1));
        this.pety.add(new Items("24", 2.77, PetItems.getByName("Bobr-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("25", 2.77, PetItems.getByName("Ognisty-Smok-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("26", 2.77, PetItems.getByName("Ognisty-Smok-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("27", 2.77, PetItems.getByName("Ognisty-Smok-Epicki").getItemStack(), 1));
        this.pety.add(new Items("28", 2.77, PetItems.getByName("Ognisty-Smok-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("29", 2.77, PetItems.getByName("Demon-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("30", 2.77, PetItems.getByName("Demon-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("31", 2.77, PetItems.getByName("Demon-Epicki").getItemStack(), 1));
        this.pety.add(new Items("32", 2.77, PetItems.getByName("Demon-Legendarny").getItemStack(), 1));
        this.pety.add(new Items("33", 2.77, PetItems.getByName("Wampir-Zwykly").getItemStack(), 1));
        this.pety.add(new Items("34", 2.77, PetItems.getByName("Wampir-Rzadki").getItemStack(), 1));
        this.pety.add(new Items("35", 2.77, PetItems.getByName("Wampir-Epicki").getItemStack(), 1));
        this.pety.add(new Items("36", 2.77, PetItems.getByName("Wampir-Legendarny").getItemStack(), 1));
    }



    public Items getDrawnItems(final Player player) {
        for (Items item : this.pety) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
