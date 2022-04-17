package rpg.rpgcore.managers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class AkcesoriaManager {

    private final HashMap<UUID, Inventory> akcesoriaMap = new HashMap<>();
    private final ItemBuilder noAkcesoria = new ItemBuilder(Material.BARRIER, 1);
    private final ItemBuilder fillGUI = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);

    public Inventory createAkcesoriaGUINew(final UUID uuid) {
        final Inventory akcesoriaGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lAkcesoria gracza " + Bukkit.getPlayer(uuid).getName()));

        fillGUI.setName(" ");

        for (int i = 0 ; i < akcesoriaGUI.getSize(); i++) {
            akcesoriaGUI.setItem(i, fillGUI.toItemStack());
        }
        akcesoriaGUI.setItem(10, noAkcesoriaItem("Tarczy"));
        akcesoriaGUI.setItem(11, noAkcesoriaItem("Naszyjnika"));
        akcesoriaGUI.setItem(12, noAkcesoriaItem("Bransolety"));
        akcesoriaGUI.setItem(13, noAkcesoriaItem("Kolczykow"));
        akcesoriaGUI.setItem(14, noAkcesoriaItem("Pierscienia"));
        akcesoriaGUI.setItem(15, noAkcesoriaItem("Energii"));
        akcesoriaGUI.setItem(16, noAkcesoriaItem("Zegarka"));

        this.updateAkcesoriaGUI(uuid, akcesoriaGUI);
        return akcesoriaGUI;
    }

    public final ItemStack noAkcesoriaItem(final String nazwaAkcesorium) {
        noAkcesoria.setName("&c&lBrak " + nazwaAkcesorium);
        noAkcesoria.addGlowing();
        return noAkcesoria.toItemStack();
    }

    @Getter
    public Inventory getAkcesoriaGUI(final UUID uuid) {
        return this.akcesoriaMap.get(uuid);
    }

    @Getter
    public HashMap<UUID, Inventory> getAkcesoriaMap() {
        return this.akcesoriaMap;
    }

    @Setter
    public void updateAkcesoriaGUI(final UUID uuid, final Inventory inventory) {
        if (this.akcesoriaMap.containsKey(uuid)) {
            this.akcesoriaMap.replace(uuid, inventory);
            return;
        }
        this.akcesoriaMap.put(uuid, inventory);
    }

}
