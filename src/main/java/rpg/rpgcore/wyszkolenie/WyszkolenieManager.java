package rpg.rpgcore.wyszkolenie;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.enums.WyszkolenieItems;
import rpg.rpgcore.wyszkolenie.objects.DrzewkoWyszkoleniaUser;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class WyszkolenieManager {
    private final Map<UUID, WyszkolenieUser> userMap;

    public WyszkolenieManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllWyszkolenie();
    }

    public void openWyszkolenieGUI(final Player player) {
        final WyszkolenieUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&3&lStatus Wyszkolenia " + player.getName()));
        final ItemStack fillItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillItem);
        }

        final ItemStack fillItem2 = new ItemBuilder(Material.IRON_FENCE).setName(" ").toItemStack();

        gui.setItem(4, new ItemBuilder(Material.DOUBLE_PLANT).setName("&6&lDostepne punkty: &e&l" + user.getPunkty()).addGlowing().toItemStack().clone());

        gui.setItem(10, fillItem2);
        gui.setItem(11, fillItem2);
        gui.setItem(12, fillItem2);
        gui.setItem(14, fillItem2);
        gui.setItem(15, fillItem2);
        gui.setItem(16, fillItem2);

        gui.setItem(18, fillItem2);
        gui.setItem(22, fillItem2);
        gui.setItem(26, fillItem2);

        gui.setItem(28, fillItem2);
        gui.setItem(29, fillItem2);
        gui.setItem(30, fillItem2);
        gui.setItem(32, fillItem2);
        gui.setItem(33, fillItem2);
        gui.setItem(34, fillItem2);


        gui.setItem(19, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6&lSrednie Obrazenia").setLore(Arrays.asList("&7Postep: &e" + user.getSrDmg() + "%&7/&e10%")).hideFlag().toItemStack().clone());
        gui.setItem(20, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6&lSrednia Defensywa").setLore(Arrays.asList("&7Postep: &e" + user.getSrDef() + "%&7/&e10%")).hideFlag().toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Material.BOW).setName("&6&lSzansa Na Cios Krytyczny").setLore(Arrays.asList("&7Postep: &e" + user.getKryt() + "%&7/&e10%")).hideFlag().toItemStack().clone());
        gui.setItem(23, new ItemBuilder(Material.GOLD_INGOT).setName("&6&lSzczescie").setLore(Arrays.asList("&7Postep: &e" + user.getSzczescie() + "&7/&e10")).toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.BONE).setName("&6&lBlok Ciosu").setLore(Arrays.asList("&7Postep: &e" + user.getBlok() + "%&7/&e5%")).toItemStack().clone());
        gui.setItem(25, new ItemBuilder(Material.APPLE).setName("&6&lDodatkowe HP").setLore(Arrays.asList("&7Postep: &e" + user.getHp() + "HP&7/&e5HP")).toItemStack().clone());

        gui.setItem(40, new ItemBuilder(Material.BEACON).setName("&e&lDrzewko Rozwoju").setLore((user.isMaxed() ? Arrays.asList("") : Arrays.asList("", "&c&lZablokowano"))).addGlowing().toItemStack().clone());


        gui.setItem(53, new ItemBuilder(Material.WORKBENCH).setName("&4Reset Wyszkolenia").setLore(Arrays.asList("&7Kliknij aby zresetowac postep swojego wyszkolenie", " ", "&7Cooldown: " + (user.hasCooldown() ? "&c" + Utils.durationToString(user.getResetCooldown(), false) : "&aGotowe"), " ", "&7Koszt:", "   &8- &610 000 000 &2$", " ", "&7Resetujac otrzymasz &5" + (user.getTotalPoints() - user.getPunkty()) + " punktow wyszkolenia", "&7oraz cooldown na kolejny reset", "&7w wysokosci &c1 godziny!")).toItemStack().clone());
        player.openInventory(gui);
    }

    public void openDrzewkoWyszkoleniaGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&e&lDrzewko Rozwoju " + player.getName()));
        final DrzewkoWyszkoleniaUser user = this.find(player.getUniqueId()).getDrzewkoWyszkoleniaUser();
        final ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack();

        for (int i = 45; i < gui.getSize(); i++) {
            gui.setItem(i, fill);
        }

        gui.setItem(40, new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName(user.getD1().getName()).setLore(
                (user.getD1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getD1().getUpgradeLvl() + "&7/&e" + user.getD1().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getD1().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getD1().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Srednie Obrazenia: &e" + user.getD1().getSrDmg() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getD1().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getD1().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        gui.setItem(31, new ItemBuilder(Material.INK_SACK, 1, (short) 11).setName(user.getD2().getName()).setLore(
                (user.getD2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getD2().getUpgradeLvl() + "&7/&e" + user.getD2().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getD2().getPerLvl(), "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getD2().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Szczescie: &e" + user.getD2().getSzczescie())
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getD2().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getD2().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        // LEWO

        gui.setItem(30, new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName(user.getDl1().getName()).setLore(
                (user.getDl1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDl1().getUpgradeLvl() + "&7/&e" + user.getDl1().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDl1().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDl1().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Szansa Na Cios Krytyczny: &e" + user.getDl1().getKrytyk() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDl1().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDl1().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        gui.setItem(29, new ItemBuilder(Material.INK_SACK, 1, (short) 12).setName(user.getDl2().getName()).setLore(
                (user.getDl2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDl2().getUpgradeLvl() + "&7/&e" + user.getDl2().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDl2().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDl2().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Srednia Defensywa: &e" + user.getDl2().getSrDef() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDl2().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDl2().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        gui.setItem(20, new ItemBuilder(Material.NETHER_BRICK_ITEM).setName(user.getDl3().getName()).setLore(
                (user.getDl3().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDl3().getUpgradeLvl() + "&7/&e" + user.getDl3().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDl3().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDl3().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Silny Na Ludzi: &e" + user.getDl3().getSilnyNaLudzi() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDl3().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDl3().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        gui.setItem(19, new ItemBuilder(Material.INK_SACK, 1, (short) 14).setName(user.getDl4().getName()).setLore(
                (user.getDl4().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDl4().getUpgradeLvl() + "&7/&e" + user.getDl4().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDl4().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDl4().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Blok Ciosu: &e" + user.getDl4().getBlok() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDl4().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDl4().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        gui.setItem(18, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 2).setName(user.getDl5().getName()).setLore(
                (user.getDl5().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDl5().getUpgradeLvl() + "&7/&e" + user.getDl5().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDl5().getPerLvl() + " HP", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDl5().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Dodatkowe HP: &e" + user.getDl5().getHp() + " HP")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDl5().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDl5().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        gui.setItem(10, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName(user.getDl6().getName()).setLore(
                (user.getDl6().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDl6().getUpgradeLvl() + "&7/&e" + user.getDl6().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDl6().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDl6().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Srednie Obrazenia: &e" + user.getDl6().getSrDmg() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDl6().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDl6().getReqItem()).getItemStack().getItemMeta().getDisplayName())
        )).toItemStack().clone());

        // GORA
        gui.setItem(22, new ItemBuilder(Material.BLAZE_POWDER).setName(user.getDg1().getName()).setLore(
                (user.getDg1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDg1().getUpgradeLvl() + "&7/&e" + user.getDg1().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDg1().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDg1().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Defensywa Przeciwko Potworom: &e" + user.getDg1().getOdpornoscNaMoby() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDg1().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDg1().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.INK_SACK, 1, (short) 8).setName(user.getDg2().getName()).setLore(
                (user.getDg2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDg2().getUpgradeLvl() + "&7/&e" + user.getDg2().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDg2().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDg2().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Przeszycie Bloku Ciosu: &e" + user.getDg2().getPrzeszywka() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDg2().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDg2().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 1).setName(user.getDg3().getName()).setLore(
                (user.getDg3().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDg3().getUpgradeLvl() + "&7/&e" + user.getDg3().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDg3().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDg3().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Blok Ciosu: &e" + user.getDg3().getBlok() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDg3().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDg3().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());

        // PRAWO
        gui.setItem(32, new ItemBuilder(Material.INK_SACK, 1, (short) 9).setName(user.getDp1().getName()).setLore(
                (user.getDp1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDp1().getUpgradeLvl() + "&7/&e" + user.getDp1().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDp1().getPerLvl() + " HP", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDp1().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Dodatkowe HP: &e" + user.getDp1().getHp() + " HP")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDp1().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDp1().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(33, new ItemBuilder(Material.INK_SACK, 1, (short) 10).setName(user.getDp2().getName()).setLore(
                (user.getDp2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDp2().getUpgradeLvl() + "&7/&e" + user.getDp2().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDp2().getPerLvl() + " DMG", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDp2().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Dodatkowe Obrazenia: &e" + user.getDp2().getDodatkowyDmg() + " DMG")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDp2().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDp2().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.BLAZE_ROD).setName(user.getDp3().getName()).setLore(
                (user.getDp3().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDp3().getUpgradeLvl() + "&7/&e" + user.getDp3().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDp3().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDp3().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Silny Na Potwory: &e" + user.getDp3().getSilnyNaMoby() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDp3().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDp3().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(25, new ItemBuilder(Material.NETHER_STALK).setName(user.getDp4().getName()).setLore(
                (user.getDp4().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDp4().getUpgradeLvl() + "&7/&e" + user.getDp4().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDp4().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDp4().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Defensywa Przeciwko Ludziom: &e" + user.getDp4().getOdpornoscNaGraczy() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDp4().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDp4().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(26, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 4).setName(user.getDp5().getName()).setLore(
                (user.getDp5().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDp5().getUpgradeLvl() + "&7/&e" + user.getDp5().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDp5().getPerLvl(), "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDp5().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Szczescie: &e" + user.getDp5().getSzczescie())
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDp5().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDp5().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 3).setName(user.getDp6().getName()).setLore(
                (user.getDp6().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Postep: &e" + user.getDp6().getUpgradeLvl() + "&7/&e" + user.getDp6().getMaxUpgradeLvl(), "&7Ulepszenie co poziom: &e" + user.getDp6().getPerLvl() + "%", "&7Koszt: &e1x  " + WyszkolenieItems.getByName(user.getDp6().getReqItem()).getItemStack().getItemMeta().getDisplayName(), " ", "&f&lBONUS", "  &7Srednia Defensywa: &e" + user.getDp6().getSrDef() + "%")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&7Wymagany Poziom: &e" + user.getDp6().getReqLvl(), "&7Koszt Odblokowania: &e1x " + WyszkolenieItems.getByName(user.getDp6().getReqItem()).getItemStack().getItemMeta().getDisplayName())
                )).toItemStack().clone());

        player.openInventory(gui);
    }

    public WyszkolenieUser find(final UUID uuid) {
        return userMap.get(uuid);
    }
    public void add(final WyszkolenieUser wyszkolenieUser) {
        userMap.put(wyszkolenieUser.getUuid(), wyszkolenieUser);
    }

    public void set(final UUID uuid, final WyszkolenieUser wyszkolenieUser) {
        userMap.replace(uuid, wyszkolenieUser);
    }
    public ImmutableSet<WyszkolenieUser> getWyszkolenieUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }

}
