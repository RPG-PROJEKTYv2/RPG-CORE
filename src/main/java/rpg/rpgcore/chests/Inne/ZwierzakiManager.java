package rpg.rpgcore.chests.Inne;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.pets.enums.PetItems;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.Utils;


public class ZwierzakiManager {
    private final RandomItems<String> randomItems = new RandomItems<>();
    private final RandomItems<ItemStack> zwykle = new RandomItems<>();
    private final RandomItems<ItemStack> rzadkie = new RandomItems<>();
    private final RandomItems<ItemStack> epickie = new RandomItems<>();
    private final RandomItems<ItemStack> legendarne = new RandomItems<>();
    

    public ZwierzakiManager() {
        randomItems.add(0.1, "pusta");
        randomItems.add(0.6, "zwykly");
        randomItems.add(0.2, "rzadki");
        randomItems.add(0.09, "epicki");
        randomItems.add(0.01, "legendarny");
        this.zwykle.add(0.1111, PetItems.getByName("Duszek-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Zlota-Rybka-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Pancernik-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Foka-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Nietoperz-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Bobr-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Ognisty-Smok-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Demon-Zwykly").getItemStack());
        this.zwykle.add(0.1111, PetItems.getByName("Wampir-Zwykly").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Duszek-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Zlota-Rybka-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Pancernik-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Foka-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Bobr-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Nietoperz-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Ognisty-Smok-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Demon-Rzadki").getItemStack());
        this.rzadkie.add(0.1111, PetItems.getByName("Wampir-Rzadki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Duszek-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Zlota-Rybka-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Pancernik-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Foka-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Bobr-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Nietoperz-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Ognisty-Smok-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Demon-Epicki").getItemStack());
        this.epickie.add(0.1111, PetItems.getByName("Wampir-Epicki").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Duszek-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Zlota-Rybka-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Pancernik-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Foka-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Nietoperz-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Bobr-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Ognisty-Smok-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Demon-Legendarny").getItemStack());
        this.legendarne.add(0.1111, PetItems.getByName("Wampir-Legendarny").getItemStack());
    }



    public ItemStack getDrawnItems(final Player player) {
        switch (randomItems.next()) {
            case "pusta":
                player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
                return null;
            case "zwykly":
                return zwykle.next().clone();
            case "rzadki":
                return rzadkie.next().clone();
            case "epicki":
                Bukkit.broadcastMessage(Utils.format("&6&lZWIERZAKI &8>> &7Gracz &6" + player.getName() + " &7znalazl &5&lEPICKIEGO &7zwierzaka!"));
                return epickie.next().clone();
            case "legendarny":
                Bukkit.broadcastMessage(Utils.format("&6&lZWIERZAKI &8>> &7Gracz &6" + player.getName() + " &7znalazl &6&lLEGENDARNEGO &7zwierzaka!"));
                return legendarne.next().clone();
            default:
                return null;
        }
    }
}
