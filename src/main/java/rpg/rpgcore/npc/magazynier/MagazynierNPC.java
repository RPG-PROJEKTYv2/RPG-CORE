package rpg.rpgcore.npc.magazynier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.*;

public class MagazynierNPC {

    private final RPGCORE rpgcore;
    public MagazynierNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final Map<UUID, String> playerMagazyny = new HashMap<>();
    private final Map<UUID, Map<Integer, Inventory>> magazyny = new HashMap<>();
    private final Map<Integer, String> missionLore = new HashMap<>(4);
    private final Map<UUID, Integer> playerProgress = new HashMap<>();

    private final ItemBuilder open = new ItemBuilder(Material.CHEST);
    private final ItemBuilder missions = new ItemBuilder(Material.BOOK_AND_QUILL);
    private final ItemBuilder artifactPlace = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder mission = new ItemBuilder(Material.BOOK);
    private final ItemBuilder unlocked = new ItemBuilder(Material.CHEST);
    private final ItemBuilder locked = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder back = new ItemBuilder(Material.ARROW);
    private final ItemBuilder info = new ItemBuilder(Material.REDSTONE_TORCH_ON);

    private final List<String> lore = new ArrayList<>();
    private final List<String> artifactOwner = new ArrayList<>(1);



    public void loadMagazynierMissions() {
        this.missionLore.put(0, "Oddaj;6400;dowolnych skrzynek");
        this.missionLore.put(1, "Sprzedaj;15000;dowolnych przedmiotow");
        this.missionLore.put(2, "Zabij;50000;dowolnych potworow;2;&bBon na powiekszenie magazynu");
        this.missionLore.put(3, "Oddaj;3;&bBon na powiekszenie magazynu");
        this.artifactOwner.add("null");
    }

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

        if (!player.getName().equals(artifactOwner.get(0))) {
            lore.clear();
            lore.add(" ");
            lore.add("&8Miejsce na artefakt &b&lMagazyniera.");
            if (!artifactOwner.get(0).equals("null")) {
                lore.add("&8Podobno zdobyl go:");
                lore.add("&f- &6" + artifactOwner.get(0));
            } else {
                lore.add("&8Podobno nikt go jeszcze nie zdobyl.");
            }

            artifactPlace.setName(Utils.format("&6&lMiejsce na artefakt")).setLore(lore);
            inventory.setItem(2, artifactPlace.toItemStack());
        } else {
            inventory.setItem(2, GlobalItems.getArtefaktMagazynier(player.getName()));
        }

        lore.clear();
        lore.add(" ");
        lore.add("&8&oKliknij &8zeby otworzyc kampanie &b&lmagazyniera.");

        missions.setName(Utils.format("&c&lKampania Magazyniera")).setLore(lore);
        inventory.setItem(4, missions.toItemStack());

        player.openInventory(inventory);
    }

    public void openMagazynierMagazyny(final Player player) {
        final Inventory inventory = rpgcore.getServer().createInventory(null, InventoryType.HOPPER, Utils.format("&6&lLista Magazynow"));
        if (!this.isInPlayerMagazynyAccess(player.getUniqueId())) {
            this.setPlayerMagazynAccess(player.getUniqueId(), "true,false,false,false,false");
        }
        final String[] unlockedMag = getPlayerMagazynyAccess(player.getUniqueId()).split(",");
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

        for(int i = 45 ; i < 54 ; i++){
            magazyn.setItem(i, fill.toItemStack());
        }

        back.setName(Utils.format("&c&lPowrot")).addGlowing();
        magazyn.setItem(49, back.toItemStack());
        return magazyn;
    }

    public void createAll(final UUID uuid) {
        addToPlayerMagazynContent(uuid);
        for (int i = 1; i <= 5; i++) {
            setPlayerMagazynContent(uuid, i, this.createEmptyMagazyn(i));
        }
    }

    public void loadAll(final UUID uuid, final String magazyny) {
        final String[] magazynyList = magazyny.split(",");
        addToPlayerMagazynContent(uuid);
        for (int i = 1; i <= 5; i++) {
            try {
                setPlayerMagazynContent(uuid, i, Utils.fromBase64(magazynyList[i - 1], Utils.format("&6&lMagazyn #" + i)));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Inventory openMagazyn(final UUID uuid, final int numer) {
        if (!isInPlayerMagazynContent(uuid, numer)) {
            createAll(uuid);
        }
        return getPlayerMagazynContent(uuid, numer);
    }

    public void openMagazynierKampania(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&c&lKampania Magazyniera"));

        lore.clear();
        lore.add("&c&lUWAGA!");
        lore.add("&8Npc zabiera wszytskie skrzynki");
        lore.add("&8z ekwipunku podczas oddawania&c&l!");
        lore.add("&c&lUWAGA!");
        lore.add("&8Musisz miec przy sobie wymagana ilosc");
        lore.add("&bBonu na powiekszenie magazynu&8, zeby misja dzialala&c&l!");

        gui.setItem(0, info.setName(Utils.format("&c&lInformacje!")).setLore(lore).addGlowing().toItemStack());

        final String[] playerMissionStatus = this.getPlayerMagazynyAccess(player.getUniqueId()).split(",");
        for (int i = 1; i < gui.getSize(); i++) {
            lore.clear();
            final String[] missionLore = rpgcore.getMagazynierNPC().getMissionLore(i - 1).split(";");
            if (missionLore.length == 3) {
                lore.add("");
                lore.add("&f" + missionLore[0] + " &c" + missionLore[1] + "x &6" + missionLore[2]);
            } else {
                lore.add("");
                lore.add("&f" + missionLore[0] + " &c" + missionLore[1] + "x &6" + missionLore[2]);
                lore.add("&8- &c" + missionLore[3] + "x " + missionLore[4]);
            }
            lore.add(" ");
            if (playerMissionStatus[i].equals("true")) {
                lore.add("&a&lOdblokowano");
            } else {
                lore.add("&c&lZablokowano");
            }
            gui.setItem(i, mission.setName("&c&lMisja " + i).setLore(lore).addGlowing().toItemStack().clone());
            player.openInventory(gui);
        }
    }

    public String getPlayerAllMagazyny(final UUID uuid) {
        String magazyny = Utils.toBase64(this.getPlayerMagazynContent(uuid, 1)) + "," + Utils.toBase64(this.getPlayerMagazynContent(uuid, 2)) + "," + Utils.toBase64(this.getPlayerMagazynContent(uuid, 3)) + "," + Utils.toBase64(this.getPlayerMagazynContent(uuid, 4)) + "," + Utils.toBase64(this.getPlayerMagazynContent(uuid, 5));
        return magazyny;
    }

    public void setPlayerMagazynAccess(final UUID uuid, final String magazyn) {
        this.playerMagazyny.put(uuid, magazyn);
    }

    public void updatePlayerMagazynAccess(final UUID uuid, final String magazyn) {
        this.playerMagazyny.replace(uuid, magazyn);
    }
    public String getPlayerMagazynyAccess(final UUID uuid) {
        return this.playerMagazyny.get(uuid);
    }

    public boolean isInPlayerMagazynyAccess(final UUID uuid) {
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

    public String getMissionLore(final int numer) {
        return this.missionLore.get(numer);
    }

    public int getPlayerProgress(final UUID uuid) {
        return this.playerProgress.get(uuid);
    }

    public void setPlayerProgress(final UUID uuid, final int progress) {
        this.playerProgress.put(uuid, progress);
    }

    public void updatePlayerProgress(final UUID uuid, final int progress) {
        this.playerProgress.replace(uuid, this.playerProgress.get(uuid) + progress);
    }

    public boolean isInPlayerProgress(final UUID uuid) {
        return this.playerProgress.containsKey(uuid);
    }


    public void setArtifactOwner(final String playerName) {
        this.artifactOwner.set(0, playerName);
    }

    public String getArtifactOwner() {
        return this.artifactOwner.get(0);
    }

}
