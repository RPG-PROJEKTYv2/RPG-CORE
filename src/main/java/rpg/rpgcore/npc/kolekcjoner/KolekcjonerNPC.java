package rpg.rpgcore.npc.kolekcjoner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KolekcjonerNPC {

    private final RPGCORE rpgcore;
    public KolekcjonerNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final Map<Integer, String> kolekcjonerMISJE = new HashMap<>();
    private final Map<UUID, String> playerKolekcjoner = new HashMap<>();
    private final HashMap<UUID, Double> kolekcjonerSrednieDMG = new HashMap<>();
    private final HashMap<UUID, Double> kolekcjonerSredniDef = new HashMap<>();
    private final HashMap<UUID, Integer> kolekcjonerPOSTEP = new HashMap<>();

    private Inventory gui;
    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> itemLore = new ArrayList<>();

    private final ItemBuilder statystyki = new ItemBuilder(Material.PAPER);
    private final ItemBuilder misje = new ItemBuilder(Material.EXP_BOTTLE);
    private final ItemBuilder oddajITEMY = new ItemBuilder(Material.DISPENSER);

    private final ItemBuilder misjaPIERWSZA = new ItemBuilder(Material.BRICK);
    private final ItemBuilder misjaDRUGA = new ItemBuilder(Material.SNOW_BALL);
    private final ItemBuilder misjaTRZECIA = new ItemBuilder(Material.SLIME_BALL);

    public String getPlayerKolekcjoner(final UUID uuid) {
        return this.playerKolekcjoner.get(uuid);
    }

    public String getKolekcjonerMissions(final int numer) {
        return this.kolekcjonerMISJE.get(numer);
    }
    public void loadKolekcjonerMissions() {
        this.kolekcjonerMISJE.put(0, "Oddaj 10 [brick]");
        this.kolekcjonerMISJE.put(1, "Oddaj 10 [snowball]");
        this.kolekcjonerMISJE.put(2, "Oddaj 10 [slimeball]");
    }

    public final Inventory kolekcjonerMAIN(Player player) {
        this.gui = Bukkit.createInventory(null, 3 * 9, Utils.format("&6&lKolekcjoner"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }
        this.itemLore.clear();

        // statystyki
        statystyki.setName("&b&lTwoje Statystyki");
        this.itemLore.add("&3Statystyki gracza: &f" + player.getName());
        this.itemLore.add(" ");
        this.itemLore.add("&f&lMISJE");
        this.itemLore.add("&8* &bSrednie Obrazenia: &f+");
        this.itemLore.add("&8* &bSrednia Defensywa: &f+");
        this.itemLore.add(" ");
        statystyki.addGlowing();
        statystyki.setLore(itemLore);
        gui.setItem(26, statystyki.toItemStack());
        this.itemLore.clear();

        // oddajITEMY
        oddajITEMY.setName("&e&lSzafa &6&lKolekcjonerska");
        this.itemLore.add("&7Kliknij aby oddac...");
        this.itemLore.add(" ");
        this.itemLore.add("&f&lSTATUS");
        this.itemLore.add("&8* &bOddane: &c5" + "&8/&a10");
        this.itemLore.add("&f&lNAGRODA");
        this.itemLore.add("&8* ");
        this.itemLore.add(" ");
        this.itemLore.clear();

        // aktualnaMISJA
        final String[] playerMissionStatus = this.getPlayerKolekcjoner(player.getUniqueId()).split(",");
        for (int i = 1; i < gui.getSize(); i++) {
            this.itemLore.clear();
            final String[] missionLore = rpgcore.getKolekcjonerNPC().getKolekcjonerMissions(i - 1).split(";");
            if (missionLore.length == 3) {
                this.itemLore.add("");
                this.itemLore.add("&f" + missionLore[0] + " &c" + missionLore[1] + "x &6" + missionLore[2]);
            } else {
                this.itemLore.add("");
                this.itemLore.add("&f" + missionLore[0] + " &c" + missionLore[1] + "x &6" + missionLore[2]);
                this.itemLore.add("&8- &c" + missionLore[3] + "x " + missionLore[4]);
            }
            this.itemLore.add(" ");
            if (playerMissionStatus[i].equals("true")) {
                this.itemLore.add("&a&lOdblokowano");
            } else {
                this.itemLore.add("&c&lZablokowano");
            }
            gui.setItem(i, misje.setName("&c&lMisja " + i).setLore(this.itemLore).addGlowing().toItemStack().clone());
            player.openInventory(gui);
        }
        this.itemLore.clear();
        return this.gui;
    }
}
