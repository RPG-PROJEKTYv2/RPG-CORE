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

public class WojownikNPC {
    private final RPGCORE rpgcore;

    public WojownikNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.loadAllMissions();
    }

    private final Map<Integer, String> missionMap= new HashMap<>();

    public void loadAllMissions() {
        this.missionMap.put(1, "&7Zabij;500;mob_name;10;125");
        this.missionMap.put(2, "&7Zabij;1000;mob_name;25;250");
    }

    public String rollMission(final UUID uuid) {
        int min = 1;
        int max = this.missionMap.size();

        int random = (int) Math.floor(Math.random()*(max-min+1)+min);

        rpgcore.getklasyHelper().find(uuid).getKlasaUser().setMission(this.missionMap.get(random));
        rpgcore.getklasyHelper().find(uuid).getKlasaUser().setMissionProgress(0);
        rpgcore.getMongoManager().saveKlasyData(uuid);

        return this.missionMap.get(random);
    }

    public void openWojownikMainGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, "&6&lWojownik");
        final KlasaUser klasaUser = this.rpgcore.getklasyHelper().find(uuid).getKlasaUser();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.GOLD_NUGGET).setName("&6Sciezka Treningowa").setLore(Arrays.asList(
                "&bDostepne punkty: &f" + klasaUser.getPoints(),
                "&bSilny Przeciwko Ludziom: &f" + klasaUser.getValue1(),
                "&bSilny Przeciwko Potworom: &f" + klasaUser.getValue2(),
                "&bSzansa na Cios Krytyczny: &f" + klasaUser.getValue3(),
                "&bSrednie Obrazenia: &f" + klasaUser.getValue4(),
                "&bWydluzony Czas Umiejetnosci Pasywnej (s): &f" + klasaUser.getValue5()
        )).toItemStack().clone());
        gui.setItem(2, this.getMission(uuid));

        //TODO Zrobic informacje co to kurwa jest i jak to gowno dziala

        player.openInventory(gui);
    }

    public ItemStack getMission(final UUID uuid) {
        if (!rpgcore.getklasyHelper().isKlasy(uuid)) {
            Utils.sendToAdministration("&4&lWystapil blad podczas otwierania gui Wojownika gracza: &6&l" + uuid + "&4&l - MISSION");
            return new ItemBuilder(Material.DIRT).setName("&4c&lCos sie popsulo :<").setLore(Arrays.asList("&4&lJesli widzisz ten item, niezwlocznie zglos", "&4&lsie do wyzszej administracji!")).toItemStack().clone();
        }
        String[] mission = rpgcore.getklasyHelper().find(uuid).getKlasaUser().getMission().split(";");
        if (rpgcore.getklasyHelper().find(uuid).getKlasaUser().getMission().equals("")) {
            mission = this.rollMission(uuid).split(";");
        }
        return new ItemBuilder(Material.BOOK_AND_QUILL).setName(mission[0] + " &cx" + mission[1] + " " + mission[2].replace("mob_name", this.getMobName(uuid))).setLore(Arrays.asList("&6&lNagroda", "&8- &b" + mission[3] + " pkt", "&8- &6" + mission[4] + " XP", " ", "&7Postep: &c" + rpgcore.getklasyHelper().find(uuid).getKlasaUser().getMissionProgress() + "&7/&c" + mission[1])).toItemStack().clone();
    }

    private String getMobName(final UUID uuid) {
        final int lvl = rpgcore.getPlayerManager().getPlayerLvl(uuid);
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
