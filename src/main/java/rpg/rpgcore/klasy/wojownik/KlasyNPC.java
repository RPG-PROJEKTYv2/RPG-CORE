package rpg.rpgcore.klasy.wojownik;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.objects.KlasaUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KlasyNPC {
    private final RPGCORE rpgcore;

    public KlasyNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.loadAllMissions();
    }

    private final Map<Integer, String> wojownikMissionMap = new HashMap<>();
    private final Map<Integer, String> obroncaMissionMap = new HashMap<>();
    private final Map<Integer, String> magMissionMap = new HashMap<>();

    public void loadAllMissions() {
        this.wojownikMissionMap.put(1, "&7Zabij;500;mob_name;10;125");
        this.wojownikMissionMap.put(2, "&7Zabij;1000;mob_name;25;250");
    }

    public String rollMission(final String className, final UUID uuid) {
        int min = 1;
        Map<Integer, String> toSellect;
        if (className.equalsIgnoreCase("wojownik")) {
            toSellect = this.wojownikMissionMap;
        } else if (className.equalsIgnoreCase("obronca")) {
            toSellect = this.obroncaMissionMap;
        } else if (className.equalsIgnoreCase("mag")) {
            toSellect = this.magMissionMap;
        } else {
            toSellect = new HashMap<>();
        }
        int max = toSellect.size();

        int random = (int) Math.floor(Math.random()*(max-min+1)+min);

        rpgcore.getklasyHelper().find(uuid).getKlasaUser().setMission(toSellect.get(random));
        rpgcore.getklasyHelper().find(uuid).getKlasaUser().setMissionProgress(0);
        rpgcore.getMongoManager().saveKlasyData(uuid);

        return toSellect.get(random);
    }

    public void openMainGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&l" + rpgcore.getklasyHelper().find(uuid).getKlasaUser().getName()));
        final KlasaUser klasaUser = this.rpgcore.getklasyHelper().find(uuid).getKlasaUser();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }
        switch (klasaUser.getName()) {
            case "Wojownik":
                gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&6Sciezka Treningowa").setLore(Arrays.asList(
                        "&bDostepne punkty: &f" + klasaUser.getPoints(),
                        "&bSilny Przeciwko Ludziom: &f" + klasaUser.getValue1(),
                        "&bSilny Przeciwko Potworom: &f" + klasaUser.getValue2(),
                        "&bSzansa na Cios Krytyczny: &f" + klasaUser.getValue3(),
                        "&bSrednie Obrazenia: &f" + klasaUser.getValue4(),
                        "&bWydluzony Czas Umiejetnosci Pasywnej (s): &f" + klasaUser.getValue5()
                )).hideFlag().toItemStack().clone());
                break;
            case "Obronca":
                gui.setItem(0, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6Sciezka Treningowa").setLore(Arrays.asList(
                        "&bDostepne punkty: &f" + klasaUser.getPoints(),
                        "&bOdpornosc Przeciwko Ludziom: &f" + klasaUser.getValue1(),
                        "&bOdpornosc Przeciwko Potworom: &f" + klasaUser.getValue2(),
                        "&bSzansa na Blok Ciosu: &f" + klasaUser.getValue3(),
                        "&bSrednia Odpornosc: &f" + klasaUser.getValue4(),
                        "&bWydluzony Czas Umiejetnosci Pasywnej (s): &f" + klasaUser.getValue5()
                )).hideFlag().toItemStack().clone());
                break;
            case "Mag":
                gui.setItem(0, new ItemBuilder(Material.RED_ROSE, 1, (short) 2).setName("&6Sciezka Treningowa").setLore(Arrays.asList(
                        "&bDostepne punkty: &f" + klasaUser.getPoints(),
                        "&bSzansa na zatrucie: &f" + klasaUser.getValue1(),
                        "&bSzansa na spowolnienie: &f" + klasaUser.getValue2(),
                        "&bSzansa na oslepienie: &f" + klasaUser.getValue3(),
                        "&bSzansa na oslabienie przeciwnika: &f" + klasaUser.getValue4(),
                        "&bWydluzony Czas Umiejetnosci Pasywnej (s): &f" + klasaUser.getValue5()
                )).hideFlag().toItemStack().clone());
                break;
            default:
                gui.setItem(0, new ItemBuilder(Material.DIRT).setName("&c&lCos sie popsulo :(").setLore(Arrays.asList("&4&lJesli widzisz ten item", "&4&ljak najszybciej zglos sie do wyzszej administracji")).toItemStack().clone());
                break;
        }

        gui.setItem(2, this.getMission(klasaUser, uuid));

        gui.setItem(4, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&cInformacje").setLore(Arrays.asList(
                "&7Wykonuj zadania u odpowiedniego NPC dla danej klasy",
                "&7Za &ckazda misje &7otrzymasz odpowiednia ilosc",
                "&bpunktow &7i &2EXP'a &7, ktory umozliwia zwiekszanie poziomu",
                "&7swojej klasy.",
                "",
                "&7Wraz ze &azwiekszaniem poziomu &7swojej klasy,",
                "&7zwieksza sie wartosc &aumiejetnosci pasywnej.",
                "",
                "&bPunkty &7potrzebne sa do &azwiekszania &7wartosci,",
                "&7ktore dodawane sa po uzyciu &aumiejetnosci specjalnej."
        )).hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }

    public ItemStack getMission(final KlasaUser klasaUser, final UUID uuid) {
        if (!rpgcore.getklasyHelper().isKlasy(uuid)) {
            Utils.sendToAdministration("&4&lWystapil blad podczas otwierania gui Mentora klasowego gracza: &6&l" + uuid + "&4&l - MISSION");
            return new ItemBuilder(Material.DIRT).setName("&4c&lCos sie popsulo :<").setLore(Arrays.asList("&4&lJesli widzisz ten item, niezwlocznie zglos", "&4&lsie do wyzszej administracji!")).toItemStack().clone();
        }
        String[] mission = klasaUser.getMission().split(";");
        if (klasaUser.getMission().equals("")) {
            mission = this.rollMission(klasaUser.getName(), uuid).split(";");
        }
        return new ItemBuilder(Material.BOOK_AND_QUILL).setName(mission[0] + " &cx" + mission[1] + " " + mission[2].replace("mob_name", this.getMobName(uuid))).setLore(Arrays.asList("&6&lNagroda", "&8- &b" + mission[3] + " pkt", "&8- &2" + mission[4] + " XP", " ", "&7Postep: &c" + rpgcore.getklasyHelper().find(uuid).getKlasaUser().getMissionProgress() + "&7/&c" + mission[1])).toItemStack().clone();
    }

    private String getMobName(final UUID uuid) {
        final int lvl = rpgcore.getUserManager().find(uuid).getLvl();
        if (lvl < 10) {
            return "&6Mobek 1-10";
        }
        if (lvl < 20) {
            return "&6Mobek 10-20";
        }
        if (lvl < 30) {
            return "&6Mobek 20-30";
        }
        if (lvl < 40) {
            return "&6Mobek 30-40";
        }
        if (lvl < 50) {
            return "&6Mobek 40-50";
        }
        if (lvl < 60) {
            return "&6Mobek 50-60";
        }
        if (lvl < 70) {
            return "&6Mobek 60-70";
        }
        if (lvl < 80) {
            return "&6Mobek 70-80";
        }
        if (lvl < 90) {
            return "&6Mobek 80-90";
        }
        if (lvl < 100) {
            return "&6Mobek 90-100";
        }
        if (lvl < 110) {
            return "&6Mobek 100-110";
        }
        if (lvl < 120) {
            return "&6Mobek 110-120";
        }
        if (lvl <= 130) {
            return "&6Mobek 120-130";
        }
        return "";
    }
}
