package rpg.rpgcore.managers.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MagazynierNPC {

    private final RPGCORE rpgcore;
    public MagazynierNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ItemBuilder open = new ItemBuilder(Material.CHEST);
    private final ItemBuilder missions = new ItemBuilder(Material.BOOK_AND_QUILL);
    private final ItemBuilder artifactPlace = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);

    private final List<String> lore = new ArrayList<>();
    private final List<String> artifactOwner = new ArrayList<>(1);

    public void openMagazynierMain(final Player player) {
        final Inventory inventory = rpgcore.getServer().createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier"));

        fill.setName(" ");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, fill.toItemStack());
        }
        lore.clear();
        lore.add(" ");
        lore.add("&8&oKliknij &8zeby otworzyc jeden ze swoich magazynow.");

        open.setName(Utils.format("&b&lOtwórz magazyn")).setLore(lore);
        inventory.setItem(0, open.toItemStack());

        lore.clear();
        lore.add(" ");
        lore.add("&8Miejsce na artefakt &b&lMagazyniera.");
        if (!artifactOwner.isEmpty()) {
            lore.add("&8Podobno zdobyl go:");
            lore.add("&f- &6" + artifactOwner.get(0));
        } else {
            lore.add("&8Podobno nikt go jeszcze nie zdobyl.");
        }

        artifactPlace.setName(Utils.format("&6&lMiejsce na artefakt")).setLore(lore);
        inventory.setItem(2, artifactPlace.toItemStack());

        lore.clear();
        lore.add(" ");
        lore.add("&8&oKliknij &8zeby otworzyc kampanie &b&lmagazyniera.");

        missions.setName(Utils.format("&c&lKampania Magazyniera")).setLore(lore);
        inventory.setItem(4, missions.toItemStack());

        player.openInventory(inventory);
    }

}
