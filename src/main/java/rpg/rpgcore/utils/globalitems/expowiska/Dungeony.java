package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Dungeony {

    // ogolnikowe
    I_SAKIEWKA_ULEPSZACZY("I_SAKIEWKA_ULEPSZACZY", new ItemBuilder(Material.FLOWER_POT).setName("&fSakiewka Z Ulepszaczami").setLore(Arrays.asList("", "&7Kliknij aby otworzyc...")).toItemStack().clone()),
    // 60-70
    I_KLUCZ_PIEKIELNY_PRZEDSIONEK("I_KLUCZ_PIEKIELNY_PRZEDSIONEK", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&c&lKlucz Do Piekielnego Przedsionka").setLore(Arrays.asList(" ", "&7Klucz ten otwiera droge do &4&lPiekielnego Przedsionka&7!")).hideFlag().toItemStack().clone()),
    I_PIEKIELNY_PRZEDSIONEK_SKRZYNKA("I_PIEKIELNY_PRZEDSIONEK_SKRZYNKA", new ItemBuilder(Material.JUKEBOX).setName("&4Skrzynia Piekielnego Wladcy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // 70-80
    I_KLUCZ_KOLOSEUM("I_KLUCZ_KOLOSEUM", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&6&lKlucz Do Koloseum").setLore(Arrays.asList(" ", "&7Klucz ten otwiera droge do &6&lKoloseum&7!")).hideFlag().toItemStack().clone()),
    I_KOLOSEUM_SKRZYNKA("I_KOLOSEUM_SKRZYNKA", new ItemBuilder(Material.JUKEBOX).setName("&4Skrzynia Czempiona").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM("I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM", new ItemBuilder(Material.EXP_BOTTLE).setName("&eSakiewka ze Zlotym Proszkiem").setLore(Arrays.asList("&fDzieki niej uda ci sie wzmocnic podstawowe akcesorium")).toItemStack().clone()),
    // 80-90
    I_KLUCZ_TAJEMNICZE_PIASKI("I_KLUCZ_TAJEMNICZE_PIASKI", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&e&lKlucz Do Tajemniczych Piaskow").setLore(Arrays.asList(" ", "&7Klucz ten otwiera droge do &e&lTajemniczych Piaskow&7!")).hideFlag().toItemStack().clone()),
    I_TAJEMNICZE_PIASKI_SKRZYNKA("I_TAJEMNICZE_PIASKI_SKRZYNKA", new ItemBuilder(Material.JUKEBOX).setName("&4Skrzynia Cesarza").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // 90-100
    I_TAJEMNICZY_SARKOFAG("I_TAJEMNICZY_SARKOFAG", new ItemBuilder(Material.NETHER_WARTS).setName("&4Demoniczny Sarkofag").setLore(Arrays.asList("", "&7Pozwoli ci on zasilic &cdemoniczny krag&7!")).toItemStack().clone()),
    I_KLUCZ_DEMONICZNE_SALE("I_KLUCZ_DEMONICZNE_SALE", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&4&lKlucz Do Demoniczej Sali").setLore(Arrays.asList(" ", "&7Klucz ten otwiera droge do &4&lDemoniczej Sali&7!")).hideFlag().toItemStack().clone()),
    I_DEMONICZNE_SALE_SKRZYNKA("I_DEMONICZNE_SALE_SKRZYNKA", new ItemBuilder(Material.JUKEBOX).setName("&4Skrzynia Elitarnego Slugi").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone());

    private final ItemStack itemStack;
    private final String name;

    Dungeony(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Dungeony rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static Dungeony getByName(String name) {
        for (Dungeony rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Dungeony.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack.clone();
    }


    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
}
