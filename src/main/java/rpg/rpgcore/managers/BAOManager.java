package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class BAOManager {

    private final RPGCORE rpgcore;

    public BAOManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private HashMap<UUID, String> baoBonusy = new HashMap<>();
    private HashMap<UUID, String> baoBonusyWartosci = new HashMap<>();


    public String getBaoBonusy(final UUID uuid) {
        return this.baoBonusy.get(uuid);
    }

    public String getBaoBonusyWartosci(final UUID uuid) {
        return this.baoBonusyWartosci.get(uuid);
    }

    public Inventory baoGUI() {
        Inventory baoGUI = Bukkit.createInventory(null, 3*9, Utils.format("&6&lSTOL MAGII"));

        ItemStack item;
        ItemMeta itemMeta;




        return baoGUI;
    }
}
