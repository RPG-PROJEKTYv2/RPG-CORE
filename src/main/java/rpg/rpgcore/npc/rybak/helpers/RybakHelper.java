package rpg.rpgcore.npc.rybak.helpers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.enums.WedkaStats;
import rpg.rpgcore.npc.rybak.objects.RybakObject;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.List;

public class RybakHelper {

    public static void getDrop(final Player player) {
        final OsUser osUser = RPGCORE.getInstance().getOsManager().find(player.getUniqueId());
        osUser.setRybakProgress(osUser.getRybakProgress() + 1);
        if (RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 1) {
            RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMagazynier(player.getUniqueId(), RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId())));
        }
        if (player.getItemInHand() == null || !player.getItemInHand().getType().equals(Material.FISHING_ROD)) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cCos poszlo nie tak lub nie masz wedki w rece!"));
            return;
        }

        final RybakObject rybakObject = RPGCORE.getInstance().getRybakNPC().find(player.getUniqueId());
        final int mission = rybakObject.getRybakUser().getMission();

        final double podwojnyDrop = Utils.getTagDouble(player.getItemInHand(), "podwojnyDrop");
        final double kufer = Utils.getTagDouble(player.getItemInHand(), "kufer");
        double nies = Utils.getTagDouble(player.getItemInHand(), "nies");

        nies += (nies * (rybakObject.getRybakUser().getMorskieSzczescie() + Utils.getTagDouble(player.getItemInHand(), "szczescie")) / 100);

        boolean doubleDrop = false;
        boolean event = false;

        //if od eventu

        if (ChanceHelper.getChance(nies)) {
            final ItemStack niesItem = getNiesDrop();
            double exp = 1000;
            assert niesItem != null;
            increaseExp(player.getItemInHand(), exp);
            if (mission == 20) {
                rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + niesItem.getAmount());
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
            }
            osUser.setNiesyProgress(osUser.getNiesyProgress() + 1);
            player.getInventory().addItem(niesItem);
            player.sendMessage(Utils.format("&8[&a+&8] &6" + niesItem.getAmount() + "x " + niesItem.getItemMeta().getDisplayName()));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&6&lRybak &8>> &7Gracz &6" + player.getName() + " &7znalazl &b&lNiesamowity Przedmiot!"));
            return;
        }
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataOs(player.getUniqueId(), osUser));


        if (ChanceHelper.getChance(podwojnyDrop)) {
            doubleDrop = true;
        }

        if (ChanceHelper.getChance(kufer)) {
            final ItemStack kuferItem = RybakItems.I11.getItemStack();
            double exp = 150;
            assert kuferItem != null;
            if (event) {
                kuferItem.setAmount(kuferItem.getAmount() * 2);
                exp *=2;
                player.sendMessage(Utils.format("&8[&a✔&8] &aTwoj polow zostal podwojony przez Event Rybacki"));
            }
            if (doubleDrop) {
                exp *=2;
                kuferItem.setAmount(kuferItem.getAmount() * 2);
                player.sendMessage(Utils.format("&8[&a✔&8] &aTwoj polow zostal podwojony"));
            }
            increaseExp(player.getItemInHand(), exp);
            if (mission == 12 || mission == 24) {
                rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + kuferItem.getAmount());
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
            }
            player.getInventory().addItem(kuferItem);
            player.sendMessage(Utils.format("&8[&a+&8] &6" + kuferItem.getAmount() + "x " + kuferItem.getItemMeta().getDisplayName()));
            return;
        }


        final ItemStack item = RPGCORE.getInstance().getRybakNPC().getDrop();
        assert item != null;
        double exp = getExp(Utils.removeColor(item.getItemMeta().getDisplayName()));
        if (event) {
            item.setAmount(item.getAmount() * 2);
            exp *= 2;
            player.sendMessage(Utils.format("&8[&a✔&8] &aTwoj polow zostal podwojony przez Event Rybacki"));
        }
        if (doubleDrop) {
            item.setAmount(item.getAmount() * 2);
            exp *= 2;
            player.sendMessage(Utils.format("&8[&a✔&8] &aTwoj polow zostal podwojony"));
        }
        increaseExp(player.getItemInHand(), exp);
        switch (Utils.removeColor(item.getItemMeta().getDisplayName())) {
            case "Sledz":
                if (mission == 1) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Dorsz":
                if (mission == 2) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;

            case "Krasnopiorka":
                if (mission == 3) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Fladra":
                if (mission == 4) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Karas":
                if (mission == 5) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Karp":
                if (mission == 6) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Leszcz":
                if (mission == 8) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Makrela":
                if (mission == 9) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Mintaj":
                if (mission == 10) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
            case "Okon":
                if (mission == 11) {
                    rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + item.getAmount());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(player.getUniqueId(), rybakObject));
                }
                break;
        }

        player.getInventory().addItem(item);
        player.sendMessage(Utils.format("&8[&a+&8] &6" + item.getAmount() + "x " + item.getItemMeta().getDisplayName()));

    }

    private static void increaseExp(final ItemStack wedka, final double exp) {
        if (wedka == null || !wedka.getType().equals(Material.FISHING_ROD)) {
            return;
        }
        final int lvl = Utils.getTagInt(wedka, "lvl");
        double currentExp = Utils.getTagDouble(wedka, "exp");
        currentExp += exp;
        final double reqExp = Utils.getTagDouble(wedka, "reqExp");
        Utils.setTagDouble(wedka, "exp", currentExp);

        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();
        if (lvl == 20) {
            lore.set(1, "&bExp: &f" + String.format("%.2f", currentExp) + "&b/&4&lMAX");
        } else {
            lore.set(1, "&bExp: &f" + String.format("%.2f", currentExp) + "&b/&f" + reqExp);
        }
        meta.setLore(Utils.format(lore));

        wedka.setItemMeta(meta);

        if (lvl == 20) return;

        if (currentExp >= reqExp) {
            increaseLvl(wedka);
        }
    }

    private static void increaseLvl(final ItemStack wedka) {
        if (wedka == null || !wedka.getType().equals(Material.FISHING_ROD)) {
            return;
        }
        final int lvl = Utils.getTagInt(wedka, "lvl") + 1;

        final WedkaStats stats = WedkaStats.getByLvl(lvl);

        assert stats != null;

        Utils.setTagInt(wedka, "lvl", lvl);
        Utils.setTagDouble(wedka, "exp", 0);
        Utils.setTagDouble(wedka, "reqExp", stats.getExp());
        Utils.setTagDouble(wedka, "szczescie", stats.getMorskieSzczescie());
        Utils.setTagDouble(wedka, "podwojnyDrop", stats.getPodwojnyDrop());
        Utils.setTagDouble(wedka, "kufer", stats.getKufer());
        Utils.setTagDouble(wedka, "nies", stats.getNies());


        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();
        if (lvl == 20) {
            lore.set(0, "&bPoziom: &4&lMAX");
            lore.set(1, "&bExp: &f0&b/&4&lMAX");
        } else {
            lore.set(0, "&bPoziom: &f" + lvl);
            lore.set(1, "&bExp: &f0&b/&f" + stats.getExp());
        }
        lore.set(4, "&bMorskie Szczescie: &f+" + stats.getMorskieSzczescie());
        lore.set(5, "&bSzansa Na Podwojny Polow: &f+" + stats.getPodwojnyDrop() + "%");
        lore.set(6, "&bSzansa Na Kufer Rybacki: &f" + stats.getKufer() + "%");
        lore.set(7, "&bSzansa Na Niesamowity Przedmiot: &f" + stats.getNies() + "%");

        meta.setLore(Utils.format(lore));

        wedka.setItemMeta(meta);
        if (lvl % 5 == 0) {
            wedka.addEnchantment(Enchantment.LUCK, lvl / 5);
        }
        if (lvl % 10 == 0) {
            wedka.addEnchantment(Enchantment.LURE, lvl / 10);
        }
    }

    private static double getExp(final String dropName) {
        switch (dropName) {
            case "Sledz":
                return 1;
            case "Dorsz":
                return 1.5;
            case "Krasnopiorka":
            case "Fladra":
                return 2;
            case "Karas":
                return 2.5;
            case "Karp":
                return 3;
            case "Leszcz":
                return 3.5;
            case "Makrela":
            case "Mintaj":
                return 4;
            case "Okon":
                return 5;
            default:
                return 0;
        }
    }

    private static ItemStack getNiesDrop() {
        return null;
    }
}
