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
        gui.setItem(4, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&4&lInformacje").setLore(Arrays.asList("&7Wykonuj &cmisje &7i zdobywaj", "&bpunkty&7, a pozniej wymieniaj", "&7je w &2Sklepie &7na &dnagrody", "&7Ale pamietaj:", "&4&lMISJE RESETUJA SIE CO 24H!")).toItemStack());

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
        if (magazynierUser.getMissions().isMission1done()) {
            gui.setItem(0, new ItemBuilder(m1.getMissionItem().clone()).addGlowing().setLoreCrafting(m1.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&a&lWykonano!"
            )).toItemStack().clone());
        } else {
            if (magazynierUser.getMissions().getSelectedMission() == Utils.getTagInt(m1.getMissionItem(), "mission")) {
                gui.setItem(0, new ItemBuilder(m1.getMissionItem().clone()).addGlowing().setLoreCrafting(m1.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                        " ",
                        "&7Postep: &6" + missions.getProgress() + "&7/&6" + m1.getReqAmount()
                )).toItemStack().clone());
            } else {
                gui.setItem(0, m1.getMissionItem().clone());
            }
        }
        if (magazynierUser.getMissions().isMission2done()) {
            gui.setItem(1, new ItemBuilder(m2.getMissionItem().clone()).addGlowing().setLoreCrafting(m2.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&a&lWykonano!"
            )).toItemStack().clone());
        } else {
            if (magazynierUser.getMissions().getSelectedMission() == Utils.getTagInt(m2.getMissionItem(), "mission")) {
                gui.setItem(1, new ItemBuilder(m2.getMissionItem().clone()).addGlowing().setLoreCrafting(m2.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                        " ",
                        "&7Postep: &6" + missions.getProgress() + "&7/&6" + m2.getReqAmount()
                )).toItemStack().clone());
            } else {
                gui.setItem(1, m2.getMissionItem().clone());
            }
        }
        if (magazynierUser.getMissions().isMission3done()) {
            gui.setItem(2, new ItemBuilder(m3.getMissionItem().clone()).addGlowing().setLoreCrafting(m3.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&a&lWykonano!"
            )).toItemStack().clone());
        } else {
            if (magazynierUser.getMissions().getSelectedMission() == Utils.getTagInt(m3.getMissionItem(), "mission")) {
                gui.setItem(2, new ItemBuilder(m3.getMissionItem().clone()).addGlowing().setLoreCrafting(m3.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                        " ",
                        "&7Postep: &6" + missions.getProgress() + "&7/&6" + m3.getReqAmount()
                )).toItemStack().clone());
            } else {
                gui.setItem(2, m3.getMissionItem().clone());
            }
        }
        if (magazynierUser.getMissions().isMission4done()) {
            gui.setItem(3, new ItemBuilder(m4.getMissionItem().clone()).addGlowing().setLoreCrafting(m4.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&a&lWykonano!"
            )).toItemStack().clone());
        } else {
            if (magazynierUser.getMissions().getSelectedMission() == Utils.getTagInt(m4.getMissionItem(), "mission")) {
                gui.setItem(3, new ItemBuilder(m4.getMissionItem().clone()).addGlowing().setLoreCrafting(m4.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                        " ",
                        "&7Postep: &6" + missions.getProgress() + "&7/&6" + m4.getReqAmount()
                )).toItemStack().clone());
            } else {
                gui.setItem(3, m4.getMissionItem().clone());
            }
        }
        if (magazynierUser.getMissions().isMission5done()) {
            gui.setItem(4, new ItemBuilder(m5.getMissionItem().clone()).addGlowing().setLoreCrafting(m5.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&a&lWykonano!"
            )).toItemStack().clone());
        } else {
            if (magazynierUser.getMissions().getSelectedMission() == Utils.getTagInt(m5.getMissionItem(), "mission")) {
                gui.setItem(4, new ItemBuilder(m5.getMissionItem().clone()).addGlowing().setLoreCrafting(m5.getMissionItem().clone().getItemMeta().getLore(), Arrays.asList(
                        " ",
                        "&7Postep: &6" + missions.getProgress() + "&7/&6" + m5.getReqAmount()
                )).toItemStack().clone());
            } else {
                gui.setItem(4, m5.getMissionItem().clone());
            }
        }

        player.openInventory(gui);
    }

    private void generateNewMissionSet(final MagazynierUser magazynierUser) {
        final MagazynierMissions missions = magazynierUser.getMissions();
        final List<Integer> missionList = new ArrayList<>(5);
        final Random random = new Random();
        while (missionList.size() < 5) {
            final int mission = random.nextInt(rpg.rpgcore.npc.magazynier.enums.MagazynierMissions.getMissionAmount()) + 1;
            if (!missionList.contains(mission)) {
                missionList.add(mission);
            }
        }
        missions.setMission1(missionList.get(0));
        missions.setMission2(missionList.get(1));
        missions.setMission3(missionList.get(2));
        missions.setMission4(missionList.get(3));
        missions.setMission5(missionList.get(4));
        magazynierUser.setResetTime(System.currentTimeMillis() + 86400000);
    }

    public void openMagazynierSklepGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier &8- &2Sklep"));

        gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&bOdblokowanie magazynu").setLore(Arrays.asList(
                "&8Oblokownuje kolejny magazyn",
                "&7Cena: &b1 000 punktow"
        )).addTagInt("price", 1_000).addGlowing().toItemStack());
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
        final Inventory gui = Bukkit.createInventory(null, 56, Utils.format("&c&lMagazyn #" + magazynNumber));
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
