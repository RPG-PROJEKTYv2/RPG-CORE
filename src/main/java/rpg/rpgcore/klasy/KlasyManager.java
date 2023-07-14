package rpg.rpgcore.klasy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.klasy.enums.KlasySide;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class KlasyManager {
    private final RPGCORE rpgcore;

    private final Map<UUID, Klasa> klasyMap;
    @Getter
    private final Cache<UUID, Long> berserkLMB = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    @Getter
    private final List<UUID> berserkRMB = new ArrayList<>();
    @Getter
    private final Cache<UUID, Long> paladinPassive = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    @Getter
    private final Cache<UUID, Long> ninjaRMB = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    @Getter
    private final List<UUID> usedSkrytoPassive = new ArrayList<>();
    @Getter
    private final List<UUID> magRMB = new ArrayList<>();

    public KlasyManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.klasyMap = rpgcore.getMongoManager().loadAllKlasy();
    }

    public void openGUI(final Player player) {
        if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < 30) {
            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz miec &c30 &7poziom aby moc wybrac klase!"));
            return;
        }

        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&c&lDowodca Strazy &8>> &7Wybor Klasy"));
        final Klasa klasa = this.find(player.getUniqueId());

        for (int i = 0; i < gui.getSize(); i++) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());

        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        gui.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(12, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(14, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        gui.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(25, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(30, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(32, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(33, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        gui.setItem(20, new ItemBuilder(Material.BANNER, 1, (short) 1).setName((klasa.getMainKlasa() == KlasyMain.WOJOWNIK ? "&c&lWOJOWNIK &8(&a&lWYBRANO&8)" : "&c&lWOJOWNIK")).setLore(Arrays.asList(
                "",
                "&b&lBONUSY",
                "&c+5% Obrazen w Potwory",
                "&c+5% Obrazen w Graczy",
                "",
                (klasa.getMainKlasa() != KlasyMain.NIE_WYBRANO ? "&bZresetuj &7aby wybrac klase" : "&7Kliknij &eLPM &7aby wybrac klase"),
                "&7Kliknij &ePPM &7aby zobaczyc podklasy"
        )).hideFlag().toItemStack());
        gui.setItem(22, new ItemBuilder(Material.WOOD_SWORD).setName((klasa.getMainKlasa() == KlasyMain.ZABOJCA ? "&4&lZABOJCA &8(&a&lWYBRANO&8)" : "&4&lZABOJCA")).setLore(Arrays.asList(
                "",
                "&b&lBONUSY",
                "&c+8% Obrazen Ogolnych",
                "&e+5% Szansy Na Cios Krytyczny",
                "",
                (klasa.getMainKlasa() != KlasyMain.NIE_WYBRANO ? "&bZresetuj &7aby wybrac klase" : "&7Kliknij &eLPM &7aby wybrac klase"),
                "&7Kliknij &ePPM &7aby zobaczyc podklasy"
        )).hideFlag().toItemStack());
        gui.setItem(24, new ItemBuilder(Material.POTION, 1, (short) 16425).setName((klasa.getMainKlasa() == KlasyMain.CZARODZIEJ ? "&5&lCZARODZIEJ &8(&a&lWYBRANO&8)" : "&5&lCZARODZIEJ")).setLore(Arrays.asList(
                "",
                "&b&lBONUSY",
                "&6+5% Szansy Na Blok",
                "&c+3% Obrazen Ogolnych",
                "",
                (klasa.getMainKlasa() != KlasyMain.NIE_WYBRANO ? "&bZresetuj &7aby wybrac klase" : "&7Kliknij &eLPM &7aby wybrac klase"),
                "&7Kliknij &ePPM &7aby zobaczyc podklasy"
        )).hideFlag().addFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack());
        gui.setItem(40, new ItemBuilder(Material.TORCH).setName("&7Reset klasy &8&l(&6INFORMACJA&8&l)").setLore(Arrays.asList(
                "&7Pierwszy &breset &7jest na nasz koszt.",
                "&7Do kolejnych potrzebujesz:",
                "&8- &fZmiana Klasy &7x&e1",
                "&8- &65 000 000&2$"
        )).toItemStack());


        player.openInventory(gui);
    }

    public void openPodWoj(final Player player) {
        final Inventory gui = this.getGui("&c&lDowodca Strazy &8>> &c&lWOJOWNIK");
        final Klasa klasa = this.find(player.getUniqueId());
        gui.setItem(12, new ItemBuilder(Material.IRON_SWORD).setName("&fBerserk").setLore(Arrays.asList(
                "            &b&lUMIEJETNOSCI",
                " &cSzal Bojowy &8- &7Przez &e10 sekund &7zadajesz",
                "                   &7&c+10% obrazen &8(cooldown 3 min)",
                " &4Brutal &8- &7Twoj nastepny atak zadaje",
                "              &2+250% obrazen w potwory &8(cooldown 5 min)",
                "",
                (klasa.getPodKlasa() == KlasySide.BERSERK ? "&bZresetuj&7, zeby wybrac podklase!": "&7Kliknij, zeby wybrac podklase!"),
                "&cWymagany poziom: &650"
        )).hideFlag().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.DIAMOND_SWORD).setName("&fPaladyn").setLore(Arrays.asList(
                "            &b&lUMIEJETNOSCI",
                " &eTwardy Jak Skala &8- &7Gdy twoje zdrowie spadnie ponizej",
                "                   &a50% maksymalnego zdrowia &7otrzymujesz",
                "                   &2+10% defensywy &7na &e10 sekund &8(cooldown 3 min)",
                " &aOwoc Zycia &8- &7Zyskujesz &a+35% maksymalnego zdrowia",
                "                  &8(cooldown 5 min)",
                "",
                (klasa.getPodKlasa() == KlasySide.PALADYN ? "&bZresetuj&7, zeby wybrac podklase!": "&7Kliknij, zeby wybrac podklase!"),
                "&cWymagany poziom: &650"
        )).hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }
    public void openPodZab(final Player player) {
        final Inventory gui = this.getGui("&c&lDowodca Strazy &8>> &4&lZABOJCA");
        final Klasa klasa = this.find(player.getUniqueId());
        gui.setItem(12, new ItemBuilder(Material.NETHER_STAR).setName("&fNinja").setLore(Arrays.asList(
                "            &b&lUMIEJETNOSCI",
                " &4&lWKROTCE &8- &4&lWKROTCE",
                "                   ",
                " &fJe-stem Szybki &8- &7Zyskujesz &f+25% predkosci ruchu &7na",
                "                  &e10 sekund &7dodatkowo zadajesz &c+5% obrazen",
                "                  &8(cooldown 3 min)",
                "",
                (klasa.getPodKlasa() == KlasySide.NINJA ? "&bZresetuj&7, zeby wybrac podklase!": "&7Kliknij, zeby wybrac podklase!"),
                "&cWymagany poziom: &650"
        )).hideFlag().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.FEATHER).setName("&fSkrytobojca").setLore(Arrays.asList(
                "            &b&lUMIEJETNOSCI",
                " &dAsasynacja &8- &7Gdy twoje zdrowie spadnie ponizej",
                "                  &a30% maksymalnego zdrowia &7otrzymujesz",
                "                  &dniewidzialnosc &7na &e4 sekundy &7oraz",
                "                  &7twoj nastepny cios bedac niewidzialnym",
                "                  &7zada &c+5% obrazen &8(cooldown 3 min)",
                " &eBurza Ostrzy &8- &7Podrzuca wszystkich wrogow w zasiegu &55 kratek",
                "                    &7dodatkowo &dspowalnia &7ich na &e3 sekundy &7oraz",
                "                    &7kradnie &4+10% ich aktualnego zdrowia &7i zwraca",
                "                    &7je tobie (max 3 graczy) &8(cooldown 2 min)",
                "",
                (klasa.getPodKlasa() == KlasySide.SKRYTOBOJCA ? "&bZresetuj&7, zeby wybrac podklase!": "&7Kliknij, zeby wybrac podklase!"),
                "&cWymagany poziom: &650"
        )).hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }
    public void openPodCzaro(final Player player) {
        final Inventory gui = this.getGui("&c&lDowodca Strazy &8>> &5&lCZARODZIEJ");
        final Klasa klasa = this.find(player.getUniqueId());
        gui.setItem(12, new ItemBuilder(Material.SKULL_ITEM).setName("&fNekromanta").setLore(Arrays.asList(
                "            &b&lUMIEJETNOSCI",
                " &4&lWKROTCE &8- &4&lWKROTCE",
                "                   ",
                " &cOddech Smoka &8- &7Rzucasz w kierunku wroga &6Ognista Kula",
                "                    &7ktory zadaje &c20% aktualnego zdrowia &7oraz",
                "                    &5spowalnia &7go o &550% aktualnej predkosci ruchu",
                "                    &8(cooldown 5 min)",
                "",
                (klasa.getPodKlasa() == KlasySide.NEKROMANTA ? "&bZresetuj&7, zeby wybrac podklase!": "&7Kliknij, zeby wybrac podklase!"),
                "&cWymagany poziom: &650"
        )).hideFlag().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.GLOWSTONE_DUST).setName("&fMag Zywiolow").setLore(Arrays.asList(
                "            &b&lUMIEJETNOSCI",
                " &5Puszka Pandory &8- &7Nadaje losowy &cnegatywny efekt &7dla",
                "                      &7kazdego wroga w zasiegu &510 kratek &8(cooldown 5 min)",
                "                      &7Dostepne &cnegatywne efekty&7:",
                "                        &8- &ePodpalenie",
                "                        &8- &2Otrucie",
                "                        &8- &5Oslepienie",
                "                        &8- &dSpowolnienie",
                "                        &8- &bZamrozenie",
                "                        &8- &cKradziez Zycia",
                "                  ",
                " &aTrzesienie Ziemi &8- &7Twoj nastepny atak zada &cobrazenia obszarowe",
                "                        &7Uderzony potwor/gracz otrzyma &c100% obrazen&7,",
                "                        &7reszta w zaleznosci od odleglosci od celu",
                "                        &7(&c1-3 kratki &7- &c75%&7, &c3-5 kratki &7- &c50%&7, &c5-8 kratek &7- &c25%&7)",
                "                        &8(cooldown 2 min)",
                "",
                (klasa.getPodKlasa() == KlasySide.MAG_ZYWIOLOW ? "&bZresetuj&7, zeby wybrac podklase!": "&7Kliknij, zeby wybrac podklase!"),
                "&cWymagany poziom: &650"
        )).hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }


    private Inventory getGui(final String title) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format(title));

        for (int i = 0; i < gui.getSize(); i++) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").hideFlag().toItemStack());

        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());
        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());
        gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());

        gui.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());
        gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());
        gui.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());

        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());
        gui.setItem(22, Utils.powrot());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").hideFlag().toItemStack());

        return gui;
    }


    public Klasa find(final UUID uuid) {
        return klasyMap.get(uuid);
    }

    public void add(final Klasa klasa) {
        klasyMap.put(klasa.getUuid(), klasa);
    }

    public ImmutableSet<Klasa> getKlasy() {
        return ImmutableSet.copyOf(klasyMap.values());
    }

}
