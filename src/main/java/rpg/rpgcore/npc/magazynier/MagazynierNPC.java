package rpg.rpgcore.npc.magazynier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class MagazynierNPC {

    private final RPGCORE rpgcore;
    public MagazynierNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final Map<UUID, String> playerMagazyny = new HashMap<>();
    private final Map<UUID, Map<Integer, Inventory>> magazyny = new HashMap<>();

    private final ItemBuilder open = new ItemBuilder(Material.CHEST);
    private final ItemBuilder missions = new ItemBuilder(Material.BOOK_AND_QUILL);
    private final ItemBuilder artifactPlace = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder mission = new ItemBuilder(Material.BOOK);
    private final ItemBuilder unlocked = new ItemBuilder(Material.CHEST);
    private final ItemBuilder locked = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder back = new ItemBuilder(Material.ARROW);

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

    public void openMagazynierMagazyny(final Player player) {
        final Inventory inventory = rpgcore.getServer().createInventory(null, InventoryType.HOPPER, Utils.format("&6&lLista Magazynow"));
        if (!this.isInPlayerMagazyny(player.getUniqueId())) {
            this.setPlayerMagazyn(player.getUniqueId(), "true,false,false,false,false");
        }
        final String[] unlockedMag = getPlayerMagazyny(player.getUniqueId()).split(",");
        lore.clear();
        lore.add(" ");
        for (int i = 0; i < inventory.getSize(); i++) {
            lore.clear();
            lore.add(" ");
            String name = "&cMagazyn #" + (i + 1);
            if (unlockedMag[i].equals("true")) {
                lore.add("&a&lOdblokowano");
                inventory.setItem(i, unlocked.setName(name).setLore(lore).toItemStack().clone());
            } else {
                lore.add("&c&lZablokowano");
                inventory.setItem(i, locked.setName(name).setLore(lore).toItemStack().clone());
            }

        }
        player.openInventory(inventory);

    }

    public Inventory createEmptyMagazyn(final int numer) {
        final Inventory magazyn = Bukkit.createInventory(null, 54, Utils.format("&6&lMagazyn #" + numer));
        fill.setName(" ").addGlowing();

        for(int i =45 ; i < 54 ; i++){
            magazyn.setItem(i, fill.toItemStack());
        }

        back.setName(Utils.format("&c&lPowrot")).addGlowing();
        magazyn.setItem(49, back.toItemStack());
        return magazyn;
    }

    public Inventory openMagazyn(final UUID uuid, final int numer) {
        if (!isInPlayerMagazynContent(uuid, numer)) {
            addToPlayerMagazynContent(uuid);
            for (int i = 1; i <= 5; i++) {
                setPlayerMagazynContent(uuid, i, createEmptyMagazyn(i));
            }
        }
        return getPlayerMagazynContent(uuid, numer);
    }

    public void setPlayerMagazyn(final UUID uuid, final String magazyn) {
        this.playerMagazyny.put(uuid, magazyn);
    }

    public void updatePlayerMagazyn(final UUID uuid, final String magazyn) {
        this.playerMagazyny.replace(uuid, magazyn);
    }
    public String getPlayerMagazyny(final UUID uuid) {
        return this.playerMagazyny.get(uuid);
    }

    public boolean isInPlayerMagazyny(final UUID uuid) {
        return this.playerMagazyny.containsKey(uuid);
    }

    public void updatePlayerMagazynContent(final UUID uuid, final int numer, final Inventory magazyn) {
        this.magazyny.get(uuid).replace(numer, magazyn);
    }

    public void addToPlayerMagazynContent(final UUID uuid) {
        this.magazyny.put(uuid, new HashMap<>(5));
    }

    public void setPlayerMagazynContent(final UUID uuid, final int numer, final Inventory magazyn) {
        this.magazyny.get(uuid).put(numer, magazyn);
    }

    public Inventory getPlayerMagazynContent(final UUID uuid, final int numer) {
        return this.magazyny.get(uuid).get(numer);
    }

    public boolean isInPlayerMagazynContent(final UUID uuid, final int numer) {
        return this.magazyny.containsKey(uuid) && this.magazyny.get(uuid).containsKey(numer);
    }

}
