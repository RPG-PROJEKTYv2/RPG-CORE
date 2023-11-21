package rpg.rpgcore.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AkcesoriaFixManager {
    public static final RPGCORE rpgcore = RPGCORE.getInstance();
    private static final List<Material> akcesoria = Arrays.asList(
            Material.ITEM_FRAME,
            Material.HOPPER_MINECART,
            Material.STORAGE_MINECART,
            Material.EXPLOSIVE_MINECART,
            Material.WATCH
    );

    public static void fixAkce(final Player player) {
        final UUID uuid = player.getUniqueId();
        final User user = rpgcore.getUserManager().find(uuid);
        final MagazynierUser magazynier = rpgcore.getMagazynierNPC().find(uuid);
        ItemStack[] playerInventory;
        ItemStack[] playerEnderchest;
        ItemStack[] magazyn1;
        ItemStack[] magazyn2;
        ItemStack[] magazyn3;
        ItemStack[] magazyn4;
        ItemStack[] magazyn5;
        try {
            playerInventory = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory());
            playerEnderchest = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getEnderchest());
            magazyn1 = Utils.itemStackArrayFromBase64(magazynier.getMagazyn1());
            magazyn2 = Utils.itemStackArrayFromBase64(magazynier.getMagazyn2());
            magazyn3 = Utils.itemStackArrayFromBase64(magazynier.getMagazyn3());
            magazyn4 = Utils.itemStackArrayFromBase64(magazynier.getMagazyn4());
            magazyn5 = Utils.itemStackArrayFromBase64(magazynier.getMagazyn5());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (final ItemStack item : playerInventory) {
            if (item == null || !akcesoria.contains(item.getType())) continue;
            changeAkcesoria(item);
        }
    }

    private static void changeAkcesoria(final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        final int lvl = Utils.getTagInt(item, "lvl");
        if (lvl >= 1 && lvl <= 10) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 11 && lvl <= 20) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 21 && lvl <= 30) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 31 && lvl <= 40) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 41 && lvl <= 50) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 51 && lvl <= 60) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 61 && lvl <= 70) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 71 && lvl <= 80) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 81 && lvl <= 90) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 91 && lvl <= 100) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 101 && lvl <= 110) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 111 && lvl <= 120) {
            switch (item.getType()) {
                case ITEM_FRAME:
                    break;
                case HOPPER_MINECART:
                    break;
                case STORAGE_MINECART:
                    break;
                case EXPLOSIVE_MINECART:
                    break;
                case WATCH:
                    break;
            }
        } else if (lvl >= 121 && lvl <= 130) {

        }

    }

}
