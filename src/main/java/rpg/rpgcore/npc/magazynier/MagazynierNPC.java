package rpg.rpgcore.npc.magazynier;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.magazynier.objects.MagazynierMissions;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.*;

public class MagazynierNPC {
    private final Map<UUID, MagazynierUser> userMap;

    public MagazynierNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMagazynier();
    }

    public void openMagazynierMainGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier"));

        gui.setItem(0, new ItemBuilder(Material.CHEST, 1).setName(Utils.format("&a&lLista Magazynow")).setLore(Arrays.asList("&8&oKliknij&8, zeby otworzyc", "&8liste twoich magazynow")).toItemStack());
        gui.setItem(1, new ItemBuilder(Material.EMERALD).setName("&2&lSklep").setLore(Arrays.asList("&8Kupisz tu mozliwosc odblokowania", "&8nowych magazynow i wiele innych", "&8przydatnych rzeczy")).toItemStack());
        gui.setItem(2, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&c&lMisje").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(4, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&4&lInformacje").setLore(Arrays.asList(
                "&7Wykonuj &cmisje &7i zdobywaj",
                "&bpunkty&7, a pozniej wymieniaj",
                "&7je w &2Sklepie &7na &dnagrody",
                " ",
                "&7Ilosc posiadanych punktow: &b" + this.find(player.getUniqueId()).getPoints() + " pkt",
                " ",
                "&7Ale pamietaj:",
                "&4&lMISJE RESETUJA SIE CO 24H!",
                "&4&lMUSISZ KLIKNAC W MISJE, ZEBY JA WYBRAC!")).toItemStack());

        player.openInventory(gui);
    }

    public void openMagazynierMisjeGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier &8- &cMisje"));
        final MagazynierUser magazynierUser = this.find(player.getUniqueId());

        if (!magazynierUser.getMissions().isGenerated()) {
            this.generateNewMissionSet(magazynierUser);
        }

        if (System.currentTimeMillis() >= magazynierUser.getResetTime()) {
            this.generateNewMissionSet(magazynierUser);
        }

        final MagazynierMissions missions = magazynierUser.getMissions();
        final rpg.rpgcore.npc.magazynier.enums.MagazynierMissions m1 = rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionById(missions.getMission1());
        final rpg.rpgcore.npc.magazynier.enums.MagazynierMissions m2 = rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionById(missions.getMission2());
        final rpg.rpgcore.npc.magazynier.enums.MagazynierMissions m3 = rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionById(missions.getMission3());
        final rpg.rpgcore.npc.magazynier.enums.MagazynierMissions m4 = rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionById(missions.getMission4());
        final rpg.rpgcore.npc.magazynier.enums.MagazynierMissions m5 = rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionById(missions.getMission5());

        assert m1 != null;
        assert m2 != null;
        assert m3 != null;
        assert m4 != null;
        assert m5 != null;
        gui.setItem(0, isMissionDone(magazynierUser, 1, m1));
        gui.setItem(1, isMissionDone(magazynierUser, 2, m2));
        gui.setItem(2, isMissionDone(magazynierUser, 3, m3));
        gui.setItem(3, isMissionDone(magazynierUser, 4, m4));
        gui.setItem(4, isMissionDone(magazynierUser, 5, m5));

        player.openInventory(gui);
    }

    public void generateNewMissionSet(final MagazynierUser magazynierUser) {
        final MagazynierMissions missions = magazynierUser.getMissions();
        final List<Integer> missionList = new ArrayList<>(5);
        final Random random = new Random();
        while (missionList.size() < 5) {
            final int mission = random.nextInt(rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionAmount()) + 1;
            if (!missionList.contains(mission)) {
                missionList.add(mission);
            }
        }
        missions.setProgress(0);
        missions.setSelectedMission(0);
        missions.setMission1(missionList.get(0));
        missions.setMission2(missionList.get(1));
        missions.setMission3(missionList.get(2));
        missions.setMission4(missionList.get(3));
        missions.setMission5(missionList.get(4));
        missions.setMission1done(false);
        missions.setMission2done(false);
        missions.setMission3done(false);
        missions.setMission4done(false);
        missions.setMission5done(false);
        magazynierUser.setResetTime(System.currentTimeMillis() + 86400000L);
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMagazynier(magazynierUser.getUuid(), magazynierUser));
    }

    public ItemStack isMissionDone(final MagazynierUser magazynierUser, final int mission, final rpg.rpgcore.npc.magazynier.enums.MagazynierMissions missions) {
        final MagazynierMissions missionsU = magazynierUser.getMissions();
        boolean done = false;
        switch (mission) {
            case 1: if (missionsU.isMission1done()) {
                done = true;
                break;
            }
            case 2: if (missionsU.isMission2done()) {
                done = true;
                break;
            }
            case 3: if (missionsU.isMission3done()) {
                done = true;
                break;
            }
            case 4: if (missionsU.isMission4done()) {
                done = true;
                break;
            }
            case 5: if (missionsU.isMission5done()) {
                done = true;
                break;
            }
        }
        String progress = String.valueOf(missionsU.getProgress());
        String reqAmount = String.valueOf(missions.getReqAmount());
        if (magazynierUser.getMissions().getSelectedMission() == 9) {
            progress = Utils.durationToString((long) magazynierUser.getMissions().getProgress(), true);
            reqAmount = Utils.durationToString((long) missions.getReqAmount(), true);
        }
        if (done) return new ItemBuilder(missions.getMissionItem().clone()).addGlowing().setLoreCrafting(missions.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&a&lWykonano!"
            )).toItemStack().clone();
        if (magazynierUser.getMissions().getSelectedMission() == Utils.getTagInt(missions.getMissionItem(), "mission")) return new ItemBuilder(missions.getMissionItem().clone()).addGlowing().setLoreCrafting(missions.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&7Postep: &6" + progress + "&7/&6" + reqAmount
            )).toItemStack().clone();

        return missions.getMissionItem().clone();
    }

    private int getPrice(final Player player) {
        switch (this.find(player.getUniqueId()).getUnlocked()) {
            case 0: return 150;
            case 1: return 250;
            case 2: return 500;
            case 3: return 750;
            case 4: return 1_500;
            default: return 999_999;
        }
    }

    public void openMagazynierSklepGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier &8- &2Sklep"));
        final int price = this.getPrice(player);
        if (this.find(player.getUniqueId()).getUnlocked() == 5) {
            gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&bOdblokowanie magazynu").setLore(Arrays.asList(
                    "&8Oblokownuje kolejny magazyn",
                    "&a&lOdblokowano wszystkie magazyny!"
            )).addTagInt("price", price).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&bOdblokowanie magazynu").setLore(Arrays.asList(
                    "&8Oblokownuje kolejny magazyn",
                    "&7Cena: &b" + Utils.spaceNumber(price) + " punktow"
            )).addTagInt("price", price).addGlowing().toItemStack().clone());
        }
        gui.setItem(1, new ItemBuilder(Material.COMMAND).setName("&cOdblokowanie komendy /magazyny").setLore(Arrays.asList(
                "&7Cena: &b15 000 punktow"
        )).addTagInt("price", 15_000).addGlowing().toItemStack());
        player.openInventory(gui);
    }

    public void openMagazynyList(final Player player) {
        final MagazynierUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&c&lLista Magazynow"));
        if (user.isUnlocked1()) {
            gui.setItem(0, new ItemBuilder(Material.CHEST).setName("&aMagazyn #1").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(0, new ItemBuilder(Material.CHEST).setName("&cMagazyn #1").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked2()) {
            gui.setItem(1, new ItemBuilder(Material.CHEST).setName("&aMagazyn #2").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(1, new ItemBuilder(Material.CHEST).setName("&cMagazyn #2").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked3()) {
            gui.setItem(2, new ItemBuilder(Material.CHEST).setName("&aMagazyn #3").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(2, new ItemBuilder(Material.CHEST).setName("&cMagazyn #3").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked4()) {
            gui.setItem(3, new ItemBuilder(Material.CHEST).setName("&aMagazyn #4").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(3, new ItemBuilder(Material.CHEST).setName("&cMagazyn #4").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked5()) {
            gui.setItem(4, new ItemBuilder(Material.CHEST).setName("&aMagazyn #5").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(4, new ItemBuilder(Material.CHEST).setName("&cMagazyn #5").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }

        player.openInventory(gui);
    }


    public void openMagazyn(final Player player, final String magazynContents, final int magazynNumber) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&c&lMagazyn #" + magazynNumber));
        if (magazynContents.isEmpty()) {
            player.openInventory(gui);
            return;
        }
        try {
            ItemStack[] contents = Utils.itemStackArrayFromBase64(magazynContents);
            gui.setContents(contents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.openInventory(gui);
    }

    public MagazynierUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final MagazynierUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public ImmutableSet<MagazynierUser> getUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
