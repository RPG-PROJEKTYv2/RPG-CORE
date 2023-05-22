package rpg.rpgcore.npc.duszolog;


import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class DuszologNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, DuszologObject> userMap;

    private final ItemStack helm = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("cbe35234-725b-42e1-a4c1-e92f4e6b477b", "skinf3f35cf8",
            "eyJ0aW1lc3RhbXAiOjE1ODY0NzU4ODYyNTIsInByb2ZpbGVJZCI6ImJlY2RkYjI4YTJjODQ5YjRhOWIwOTIyYTU4MDUxNDIwIiwicHJvZmlsZU5hbWUiOiJTdFR2Iiwic2lnbmF0dXJlUmV" +
                    "xdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83NjMyOWJkN2JlOWQwZGJmZjFhODE5ZGI0N2RmZjk1Z" +
                    "WY1NDE4MDAwYjgyODNhYmZlYTBmMjEwZTJkNjg5Y2UwIn19fQ==",
            "YXAwhfotf0YRTwoR+PNtlVt3jolNK2pTxy5CFnNRyXB70NW21ggpXvg9ioT/len+dG3Luty0UN8ekMyAjWiFl6ScGXcU28QGkEfmQJ1Mi+oIq+7N5/z2h+WJg8mLMuO+WjG3aUEn7BVOYLtoDG1p" +
                    "+2jmXL4hYUk0MHx1I9lWhb9qZJLoxUkgguPhaF9FS/DGLkTndv34ynScmHYc0xSZkxd2yldlpRVVyvX3S8X6vO8vvcXVzcH6yUMIO1yVvUBVqdq/lqoM+Fm82iU3SsEnSL5ev9ZfzrX33BNgFwUgwseeL" +
                    "a/tkQxWLG/d47azOFBcYGH3js0OB78yBSsbeb0OJ5E8gQG0h7pOCBO/pz9YVASyYImb8JtpDfYe2bE1RxqBdiEprCcMSl7EsWCy4iL0BFx9SwjW4Tqfb+5olcBnHi+f7FZN/V0rQtfKT5xMxFr/5Fl1RPb" +
                    "i55IXAIqLTiYV53PMiVw312pjXe2YctpFrSGCwftI292F8DEQjbGSCyls5dyGjkdD2iVtkUS8gvCI3XnwDD9VCqQas37J3PRl6FgoXPSC0g7CnFCrzl5Z3jxdRsjxVSvn/iXVr7+n2DP9dVmE2lmNGvm6Y1" +
                    "xM4A6isFTGj+B39l+kkWsE/ales/lksmzn2V0psQdsT6dUU29FthTo2FJrqTzVAsrkrxPEHwc=").toItemStack().clone();

    public DuszologNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllDuszolog();
    }


    public void openMainGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final DuszologUser duszologUser = this.find(uuid).getDuszologUser();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&8Duszolog"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }


        gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&b&lStatystyki").setLore(Arrays.asList(
                "&fBonus1: &c" + duszologUser.getValue1() + "%",
                "&fBonus2: &c" + duszologUser.getValue2() + "%"
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.BOOK).setName("&c&lKampania").addGlowing().toItemStack());
        gui.setItem(8, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&4&lInformajce").addGlowing().toItemStack());


        player.openInventory(gui);
    }

    public void spawnDusza(final Player player, final Entity entity) {
        ArmorStand as = (ArmorStand) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() - 1.5, entity.getLocation().getZ(), entity.getLocation().getYaw(), entity.getLocation().getPitch()), EntityType.ARMOR_STAND);
        as.setCustomName(entity.getName() + " - " + player.getName());
        as.setCustomNameVisible(false);
        as.setHelmet(helm);
        as.setVisible(false);
        as.setGravity(false);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            if (!as.isDead()) {
                as.remove();
            }
        }, 200L);
    }


    public ItemStack getHelm() {
        return helm;
    }


    public void add(DuszologObject duszologObject) {
        this.userMap.put(duszologObject.getID(), duszologObject);
    }

    public DuszologObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new DuszologObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<DuszologObject> getDuszologObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public boolean isDuszologObject(final UUID string) {
        return this.userMap.containsKey(string);
    }
}
