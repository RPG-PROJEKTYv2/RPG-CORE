package rpg.rpgcore.managers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class AkcesoriaManager {

    private final RPGCORE rpgcore;

    private final HashMap<UUID, Inventory> akcesoriaMap = new HashMap<>();
    private final ItemBuilder noAkcesoria = new ItemBuilder(Material.BARRIER, 1);
    private final ItemBuilder fillGUI = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);

    public AkcesoriaManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public Inventory createAkcesoriaGUINew(final UUID uuid) {
        final Inventory akcesoriaGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lAkcesoria gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

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

    public Inventory createAkcesoriaGUI(final UUID uuid, final ItemStack[] akcesoria) {
        final Inventory akcesoriaGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lAkcesoria gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

        fillGUI.setName(" ");

        for (int i = 0 ; i < akcesoriaGUI.getSize(); i++) {
            akcesoriaGUI.setItem(i, fillGUI.toItemStack());
        }

        for (int i = 0; i < akcesoria.length; i++) {
            akcesoriaGUI.setItem(10 + i, akcesoria[i]);
        }


        this.updateAkcesoriaGUI(uuid, akcesoriaGUI);
        return akcesoriaGUI;
    }

    public final ItemStack noAkcesoriaItem(final String nazwaAkcesorium) {
        noAkcesoria.setName("&c&lBrak " + nazwaAkcesorium);
        noAkcesoria.addGlowing();
        return noAkcesoria.toItemStack();
    }

    public final ItemStack[] getAllAkcesoria(final UUID uuid) {
        ItemStack[] akcesoria = new ItemStack[7];
        if (!(rpgcore.getAkcesoriaManager().getAkcesoriaMap().containsKey(uuid))) {
            rpgcore.getAkcesoriaManager().createAkcesoriaGUINew(uuid);
        }
        Inventory gui = this.getAkcesoriaGUI(uuid);

        for (int i = 0; i < akcesoria.length; i++) {
            akcesoria[i] = gui.getItem(10 + i);
        }

        return akcesoria;
    }

    public final int getAkcesoriaBonus(final UUID uuid, final int slotAkcesorium , final String nazwaBonusu) {
        int wartoscBonusu = 0;

        Inventory akcesoria = this.getAkcesoriaGUI(uuid);

        ItemStack akce = akcesoria.getItem(slotAkcesorium);

        for (int i = 0; i < akce.getItemMeta().getLore().size(); i++) {
            if (akce.getItemMeta().getLore().get(i).trim().contains(nazwaBonusu)) {
                wartoscBonusu = Integer.parseInt(Utils.removeColor(akce.getItemMeta().getLore().get(i).trim().replace(nazwaBonusu + ": ", "").replace("%", "")));
            }
        }

        return wartoscBonusu;
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
