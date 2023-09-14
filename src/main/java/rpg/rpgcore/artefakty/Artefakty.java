package rpg.rpgcore.artefakty;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Artefakty {
    A1("Bifrost", new ItemBuilder(Material.COMPASS).setName("&b&lBifrost").setLore(Arrays.asList(
            "&7Czas Odnowienia: &e30 sec",
            "",
            "&8Zapomniany artefakt, dzieki ktoremu mozesz",
            "&8podrozowac po swiecie.",
            "&4&lARTEFAKT"
    )).addGlowing().toItemStack().clone()),
    A2("Krwisty-Legendarny-Rog", new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&4&lKrwisty Legendarny Rog").setLore(Arrays.asList(
            "&7Czas Odnowienia: &e45 sek",
            "&7Ilosc Potworow: &e15-30",
            "",
            "&8Odebrany od Elitarnego Wodza Orkow",
            "&8pozwala na przywolanie wszystkich stworzen",
            "&8w zasiegu jego dzialania",
            "&4&lARTEFAKT"
    )).addTagInt("range", 35).addGlowing().toItemStack().clone()),
    A3("Serce-Yothuna", new ItemBuilder(Material.WATCH).setName("&c&lSerce Yothuna").setLore(Arrays.asList(
            "&7Czas Odnowienia: &e2 min",
            "&7Zasieg: &e5 kratek",
            "",
            "&8Zdobyte podczas Wielkiej Wojny pomiedzy",
            "&8Krolestwem Chunjo i Shinsoo, pozwala na",
            "&8nalozenie efektu &5Oslabienia I &8na wszystkich",
            "&8pobliskich wrogow.",
            "&5Oslabienie I &8zmniejsza defensywe przeciwnika o &510%&8.",
            "&4&lARTEFAKT"
    )).addGlowing().toItemStack().clone()),
    A4("Eliksir-Potegi", new ItemBuilder(Material.LAVA_BUCKET).setName("&6&lEliksir Potegi").setLore(Arrays.asList(
            "&7Czas Odnowienia: &e10 min",
            "&7Czas trwania: &e5 min",
            "",
            "&8Odebrany od Potwora z glebin z dna Glebokiego Morza.",
            "&8Wypicie go powoduje uczucie palenia w gardle",
            "&8dzieki czemu wpadamy w &cfurie&8.",
            "&4&lARTEFAKT"
    )).toItemStack().clone()),
    A5("Eliksir-Obroncy", new ItemBuilder(Material.WATER_BUCKET).setName("&6&lEliksir Obroncy").setLore(Arrays.asList(
            "&7Czas Odnowienia: &e10 min",
            "&7Czas trwania: &e5 min",
            "",
            "&8Wydobyty spod gruzow starej kopalni.",
            "&8Wypicie go powoduje przyplyw tajemniczej energii",
            "&8dzieki ktorej stajemy sie &3niepokonani&8.",
            "&4&lARTEFAKT"
    )).toItemStack().clone()),
    A6("Egzekutor", new ItemBuilder(Material.SKULL_ITEM, 1, (short) 1).setName("&c&lEgzekutor").setLore(Arrays.asList(
            "&7Czas Odnowienia: &e10 min",
            "&7Czas dzialania: &e5 sek",
            "&7Szansa na aktywacje: &e2%",
            "",
            "&8Stworzony przez &4Diabla &8przedmiot, dzieki ktoremu",
            "&8wpadamy w &cSzal&8, bijac &4zwiekszone obrazenia",
            "&8i nakladamy na przeciwnika efekt &5Oslabienia II&8,",
            "&8ktory zmniejsza jego defensywe o &520%&8.",
            "&4&lARTEFAKT"
    )).addGlowing().toItemStack().clone());


    private final String name;
    private final ItemStack item;

    Artefakty(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public static void listAll(final Player player) {
        player.sendMessage(Utils.format("&8&m------------{-&4&l Artefakty &8&m-}------------"));
        for (final Artefakty artefakt : Artefakty.values()) {
            player.sendMessage(Utils.format("&8» &c" + artefakt.getName()));
        }
        player.sendMessage(Utils.format("&8&m------------{-&4&l Artefakty &8&m-}------------"));
    }

    public static ItemStack getArtefakt(final String name, final Player owner) {
        for (Artefakty artefakt : Artefakty.values()) {
            if (artefakt.getName().equals(name)) {
                return new ItemBuilder(artefakt.getItem().clone()).setLoreCrafting(Arrays.asList("&7Wlasnosc: &e" + owner.getName()), artefakt.getItem().clone().getItemMeta().getLore())
                        .addTagString("owner_name", owner.getName()).addTagString("owner_uuid", owner.getUniqueId().toString()).toItemStack().clone();
            }
        }
        return null;
    }
}
