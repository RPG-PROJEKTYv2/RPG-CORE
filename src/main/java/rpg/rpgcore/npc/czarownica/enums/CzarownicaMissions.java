package rpg.rpgcore.npc.czarownica.enums;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

import java.util.*;

public enum CzarownicaMissions {
    M1(1, 64, Arrays.asList(new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(2).toItemStack(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone())),
    M2(2, 320, Arrays.asList(GlobalItem.getCheck(12_000_000))),
    M3(3, 768, Arrays.asList(GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone())),
    M4(4, 16, Arrays.asList(GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone())),
    M5(5, 10_000, Arrays.asList(GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(2).toItemStack())),
    M6(6, 192, Arrays.asList(GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone())),
    M7(7, 15, Arrays.asList(GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone())),
    M8(8, 256, Arrays.asList(GlobalItem.I_KSIEGAMAGII.getItemStack().clone(), GlobalItem.I_KSIEGAMAGII.getItemStack().clone())),
    M9(9, 256, Arrays.asList(new ItemBuilder(Material.ENCHANTED_BOOK).setName("&f&kaa&5 Magiczna Receptura &f&kaa").setLore(Arrays.asList(
            "&7Receptura ta umozliwi Ci wytwarzanie",
            "&5&lKsiega Magii"
    )).toItemStack().clone()));


    private final int missionId;
    private final int reqAmountPer;
    private final List<ItemStack> rewards;

    CzarownicaMissions(final int missionId, final int reqAmountPer, final List<ItemStack> rewards) {
        this.missionId = missionId;
        this.reqAmountPer = reqAmountPer;
        this.rewards = rewards;
    }

    public int getMissionId() {
        return this.missionId;
    }

    public void addRewards(final Player player) {
        for (final ItemStack item : this.rewards) {
            player.getInventory().addItem(item);
        }
    }

    public LinkedHashMap<ItemStack, Integer> getReqProgressMap(final CzarownicaUser user) {
        final LinkedHashMap<ItemStack, Integer> map = new LinkedHashMap<>();
        final List<ItemStack> itemList = new ArrayList<>();
        switch (user.getMission()) {
            case 1:
            case 6:
                itemList.add(GlobalItem.I12.getItemStack());
                itemList.add(GlobalItem.I13.getItemStack());
                itemList.add(GlobalItem.I14.getItemStack());
                itemList.add(GlobalItem.I15.getItemStack());
                itemList.add(GlobalItem.I16.getItemStack());
                itemList.add(GlobalItem.I17.getItemStack());
                itemList.add(GlobalItem.I18.getItemStack());
                break;
            case 2:
                itemList.add(Skrzynki.I2.getItemStack());
                break;
            case 3:
                itemList.add(mission3Item);
                break;
            case 4:
                itemList.add(Skrzynki.I2.getItemStack());
                break;
            case 5:
                itemList.add(mission5Item);
                break;
            case 7:
                itemList.add(mission7Item);
                break;
            case 8:
//                itemList.add(RybakItems.I1.getItemStack());
//                itemList.add(RybakItems.I2.getItemStack());
//                itemList.add(RybakItems.I3.getItemStack());
//                itemList.add(RybakItems.I4.getItemStack());
//                itemList.add(RybakItems.I5.getItemStack());
//                itemList.add(RybakItems.I6.getItemStack());
//                itemList.add(RybakItems.I7.getItemStack());
//                itemList.add(RybakItems.I8.getItemStack());
//                itemList.add(RybakItems.I9.getItemStack());
//                itemList.add(RybakItems.I10.getItemStack());
                break;
            case 9:
                itemList.add(Skrzynki.I2.getItemStack());
                itemList.add(Skrzynki.I4.getItemStack());
                itemList.add(Skrzynki.I6.getItemStack());
                itemList.add(Skrzynki.I8.getItemStack());
                itemList.add(Skrzynki.I10.getItemStack());
                itemList.add(Skrzynki.I12.getItemStack());
                itemList.add(Skrzynki.I14.getItemStack());
                itemList.add(Skrzynki.I16.getItemStack());
                break;
            default:
                break;
        }

        for (final ItemStack item : itemList) {
            map.put(item, this.reqAmountPer);
        }

        if (user.getMission() == 9) {
            map.put(new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(1).toItemStack().clone(), 16);
            map.put(new ItemBuilder(SkrzynkiOther.I1.getItemStack().clone()).setAmount(1).toItemStack().clone(), 4);
            map.put(new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(1).toItemStack().clone(), 16);
            map.put(new ItemBuilder(SkrzynkiOther.I4.getItemStack().clone()).setAmount(1).toItemStack().clone(), 12);
            map.put(new ItemBuilder(SkrzynkiOther.I4.getItemStack().clone()).setAmount(1).toItemStack().clone(), 64);
            map.put(new ItemBuilder(SkrzynkiOther.I1.getItemStack().clone()).setAmount(1).toItemStack().clone(), 8);
        }

        for (final ItemStack item : map.keySet()) {
            if (user.getProgressMap().containsKey(item)) continue;
            user.getProgressMap().put(item, 0);
        }
        return map;
    }
    public static final ItemStack mission3Item = new ItemBuilder(Material.DIRT).setName("Misja 3").toItemStack();
    public static final ItemStack mission5Item = new ItemBuilder(Material.DIRT).setName("Misja 5").toItemStack();
    public static final ItemStack mission7Item = new ItemBuilder(Material.DIRT).setName("Misja 7").toItemStack();

    public void fix(final CzarownicaUser user) {
        final LinkedHashMap<ItemStack, Integer> req = this.getReqProgressMap(user);
        for (final ItemStack item : req.keySet()) {
            if (user.getProgressMap().containsKey(item)) continue;
            user.getProgressMap().put(item, 0);
        }
        final List<ItemStack> toRemove = new ArrayList<>();
        for (final ItemStack item : user.getProgressMap().keySet()) {
            if (item.getAmount() > 1) {
                toRemove.add(item);
            }
        }
        for (final ItemStack item : toRemove) {
            int reqAmount = user.getProgressMap().get(item);
            user.getProgressMap().remove(item);
            item.setAmount(1);
            user.getProgressMap().put(item, reqAmount);
        }
    }

    public ItemStack getMissionItem(final CzarownicaUser user) {
        final ItemBuilder builder = new ItemBuilder(Material.BOOK).setName("&5Misja " + this.missionId);
        final List<String> lore = new ArrayList<>();
        int progress;
        final LinkedHashMap<ItemStack, Integer> req = this.getReqProgressMap(user);
        this.fix(user);


        switch (user.getMission()) {
            case 1:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
                lore.add("&7Przynies mi:");
                for (final ItemStack item : user.getProgressMap().keySet()) {
                    progress = user.getProgressMap().get(item);
                    if (progress < req.get(item)) {
                        lore.add("&8 - &c✘ " + item.getItemMeta().getDisplayName() + "&8: " + progress + "/" + req.get(item) + " (" + DoubleUtils.round((double) progress / (double) req.get(item) * 100.0D, 2) + "%)");
                    } else {
                        lore.add("&8 - &a✓ " + item.getItemMeta().getDisplayName() + "&8: " + progress + "/" + req.get(item) + " (100%)");
                    }
                }
                break;
            case 3:
                lore.add("&7Przynies mi &f12 &7stackow dowolnych skrzyn");
                lore.add("&7Postep:");
                progress = user.getProgressMap().get(mission3Item);

                if (progress < req.get(mission3Item)) {
                    lore.add("&c" + progress + "/" + req.get(mission3Item) + " &8(" + DoubleUtils.round((double) progress / (double) req.get(mission3Item) * 100.0D, 2) + "%)");
                } else {
                    lore.add("&a" + progress + "/" + req.get(mission3Item) + " &8(100%)");
                }
                break;
            case 5:
                lore.add("&7Zabij &f10 000 &7potworow");
                lore.add("&7Postep:");
                progress = user.getProgressMap().get(mission5Item);

                if (progress < req.get(mission5Item)) {
                    lore.add("&c" + progress + "/" + req.get(mission5Item) + " &8(" + DoubleUtils.round((double) progress / (double) req.get(mission5Item) * 100.0D, 2) + "%)");
                } else {
                    lore.add("&a" + progress + "/" + req.get(mission5Item) + " &8(100%)");
                }
                break;
            case 7:
                lore.add("&7Zabij &f15 &7graczy");
                lore.add("&7Postep:");
                progress = user.getProgressMap().get(mission7Item);

                if (progress < req.get(mission7Item)) {
                    lore.add("&c" + progress + "/" + req.get(mission7Item) + " &8(" + DoubleUtils.round((double) progress / (double) req.get(mission7Item) * 100.0D, 2) + "%)");
                } else {
                    lore.add("&a" + progress + "/" + req.get(mission7Item) + " &8(100%)");
                }
                break;
        }
        lore.add(" ");
        lore.add("&f&lNagroda");
        for (final ItemStack item : this.rewards) {
            lore.add("&8 - &7x" + item.getAmount() + " " + item.getItemMeta().getDisplayName());
        }
        return builder.setLore(lore).toItemStack().clone();
    }

    public static CzarownicaMissions getMissionById(final int id) {
        for (final CzarownicaMissions mission : values()) {
            if (mission.getMissionId() == id) {
                return mission;
            }
        }
        return null;
    }
}
