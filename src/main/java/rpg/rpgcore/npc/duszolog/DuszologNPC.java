package rpg.rpgcore.npc.duszolog;


import com.google.common.collect.ImmutableSet;
import lombok.Getter;
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
import rpg.rpgcore.npc.duszolog.enums.DuszologMissions;
import rpg.rpgcore.npc.duszolog.enums.DuszologMissionsTextures;
import rpg.rpgcore.npc.duszolog.objects.DuszologUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.PageUtils;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class DuszologNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, DuszologUser> userMap;

    @Getter
    private final ItemStack helm = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("cbe35234-725b-42e1-a4c1-e92f4e6b477b", "skinf3f35cf8",
            "eyJ0aW1lc3RhbXAiOjE1ODY0NzU4ODYyNTIsInByb2ZpbGVJZCI6ImJlY2RkYjI4YTJjODQ5YjRhOWIwOTIyYTU4MDUxNDIwIiwicHJvZmlsZU5hbWUiOiJTdFR2Iiwic2lnbmF0dXJlUmV" +
                    "xdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83NjMyOWJkN2JlOWQwZGJmZjFhODE5ZGI0N2RmZjk1Z" +
                    "WY1NDE4MDAwYjgyODNhYmZlYTBmMjEwZTJkNjg5Y2UwIn19fQ==",
            "YXAwhfotf0YRTwoR+PNtlVt3jolNK2pTxy5CFnNRyXB70NW21ggpXvg9ioT/len+dG3Luty0UN8ekMyAjWiFl6ScGXcU28QGkEfmQJ1Mi+oIq+7N5/z2h+WJg8mLMuO+WjG3aUEn7BVOYLtoDG1p" +
                    "+2jmXL4hYUk0MHx1I9lWhb9qZJLoxUkgguPhaF9FS/DGLkTndv34ynScmHYc0xSZkxd2yldlpRVVyvX3S8X6vO8vvcXVzcH6yUMIO1yVvUBVqdq/lqoM+Fm82iU3SsEnSL5ev9ZfzrX33BNgFwUgwseeL" +
                    "a/tkQxWLG/d47azOFBcYGH3js0OB78yBSsbeb0OJ5E8gQG0h7pOCBO/pz9YVASyYImb8JtpDfYe2bE1RxqBdiEprCcMSl7EsWCy4iL0BFx9SwjW4Tqfb+5olcBnHi+f7FZN/V0rQtfKT5xMxFr/5Fl1RPb" +
                    "i55IXAIqLTiYV53PMiVw312pjXe2YctpFrSGCwftI292F8DEQjbGSCyls5dyGjkdD2iVtkUS8gvCI3XnwDD9VCqQas37J3PRl6FgoXPSC0g7CnFCrzl5Z3jxdRsjxVSvn/iXVr7+n2DP9dVmE2lmNGvm6Y1" +
                    "xM4A6isFTGj+B39l+kkWsE/ales/lksmzn2V0psQdsT6dUU29FthTo2FJrqTzVAsrkrxPEHwc=").toItemStack().clone();
    private final ItemBuilder leftArrow = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("a69bdf9a-b058-4dcf-8a02-37f76d34a646", "skinebf80341",
            "ewogICJ0aW1lc3RhbXAiIDogMTYwMDI3NzkyMzA0NywKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OGYyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgI" +
                    "nNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84OWE5OGJhOT" +
                    "k3ZGE3YThjMWRkMWRjOGEyMzM4YjZlNDdhNTY5M2E4ZTc5Y2Y3M2U1ODE1NzEwYmE4MjU3YTBmIgogICAgfQogIH0KfQ==",
            "vmtADHV7lwwkxy0j8r2BE9DJkm53pyyHBThmy1Upn5PqJQaqUPu87KI8PlSpPnuo5ckQsMZUbEu99btbqD0onU6b7uAkgk6ywH9/Enk2T5tngHn6sWj/dB6HtnYWyeLE8Z4E0fdwahyJ55Oy3AfFFd79qYHV4uxdf" +
                    "CA90C1fwyYWAlTSpJh5r+mYwHZQ/5Mxni3iscg65M7pwuHi05is4srZlADfDowSha9MKuFaJhpM8fQ/wO8YrcwULx0Cc09pH7O0osLhL0xP+xE1x5iXD2gGzBF8P3yi8ChneKSH6M/CKOKlXxHKumB0QoA0PbOb89A" +
                    "xXCrrrvWeXr9V3X4IIaWvxN5wdBpjsJv0ib1WpRGLeGDcdlPQ44zOzflwXZ1II9x05IVcYrjujh3ro8YjaBU9kw2YKcv/JTIL+hAnVJdn/wE8BFmcasoOJT94ZxrUqJmy/UZsvOMjWIRzM468xr/n7W99yj+hM8i+4" +
                    "waq3SHv7rOAW1pn2OlLY72XU+gSBJCC7ypdqQJTs8Iq5xd+UYJRbd2VAjg8JVjI5Z2Q5fB3cNxGM76iWGt3Hy2FhLw3bG8cBi06qrKCsCSs41PvnixEyI5hFOMxcy+D/G5VBf24ONFLqkokUY+KmsKJusfSX7PRP/ak" +
                    "+WyztJ+4B920EGUQxkunJgE6wEMpboHljNk="
    );
    private final ItemBuilder rightArrow = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("8bee9c8a-230c-4556-9dc1-ea507adc95db", "skindf86f1ed",
            "ewogICJ0aW1lc3RhbXAiIDogMTYwMDI3ODA4NjMzNCwKICAicHJvZmlsZUlkIiA6ICJkZTU3MWExMDJjYjg0ODgwOGZlN2M5ZjQ0OTZlY2RhZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfTWluZXNraW4iLAogICJz" +
                    "aWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY3ZTM4ZTQ1ZDJi" +
                    "ODc3OTg1MjVhZGU2ZDRhYjhkMTYwNzgyYjQ0NTljMjVkMjFiOWYzNjRhMDFlZGQxNTI4NCIKICAgIH0KICB9Cn0=",
            "f2ApMIPvJvuABCT4cT32F76/ZKZqnwF1rm9P3Br5ME5fTqtk1/yfIppMyHDYKzYRgEou/CgJze/huAML0n/YzoKXNTbeokJ+/Ru9xH5WykHkN1CLEuM/YyPWO3DCqmc7uf7iw5PfYKJM4Ger4hGdkGL3A46zSDcTS3Y" +
                    "hw/2vuYsr8ZcCMjQf3EErfOWSu0lEBFzqtTD8MFA4mIdKSGxF4OpO2Fbkv8+TvJHnMepOfGTSNyTlqy8JPCOc5QuK5AJVXTgQ49KXBolptbdHHVrqwLY7LYrx6XgG+IwaNWUlfKnijqO9FCEGSH2AGL8TXZkmeo7SN9f" +
                    "LCd87QOQIxSb900IU0fUqUL5MNlMTsd5rc7K7X6q+XViKaHOOicQ2JRcp9a9Bqel9ktCoYglTp5a0vVf/P8Fvjt5GpcF/iSbekWnj4jiHiHBan3h02iiS7I0DOVoZuZ5D/L7qZxDquWVQWcA2aQ9Yno0BjseemDBsx28" +
                    "ysbY9bwMk2g6G2RWOgpME7ieetZ5hX9UqxLgGvbEXnCYExoev0+462t6MclZeqB0zqKrQgdGIe1MstAJkAPkOz2/DFmVd+8pPrGb+zxepPTe/D17AX7rpg3t+dQQCDqHPT/cKmoerSBF35TDJzZHRRqepdameLYiFGc2" +
                    "KpIZI1LbVHogENr5omI1JezEASHs="
            );

    public DuszologNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllDuszolog();
    }
    /*
     0  1  2  3  4  5  6  7  8
     9  10 11 12 13 14 15 16 17
     18 19 20 21 22 23 24 25 26
     27 28 29 30 31 32 33 34 35
     36 37 38 39 40 41 42 43 44
     45 46 47 48 49 50 51 52 53
     */


    public void openMainGUI(final Player player, final int page) {
        final UUID uuid = player.getUniqueId();
        final DuszologUser duszologUser = this.find(uuid);
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&8Duszolog #" + page));

        for (int i = 0; i < gui.getSize(); i++) {
            if ((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44))
                if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
                else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 11).setName(" ").toItemStack());
        }

        final List<ItemStack> guiItems = new ArrayList<>();

        Arrays.stream(DuszologMissions.values()).forEach(mission -> {
            final DuszologMissionsTextures info = DuszologMissionsTextures.getMission(mission.getEntityName().split("\\.")[0].trim());
            assert info != null;
            guiItems.add(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&c" + mission.getEntityName())
                    .setSkullOwnerByURL(info.getUuid(), info.getNick(), info.getTexture(), info.getSignature())
                    .setLore(Arrays.asList(
                            "&7Postep: &f" + (duszologUser.getProgressMap().getOrDefault(mission.getEntityName(), 0)) + "&7/&f" + mission.getReqAmount(),
                            "",
                            "&f&lInformacje",
                            "&7Szansa na drop: &c" + mission.getSpawnChance() + "%",
                            "",
                            "&f&lNagroda",
                            "&8- &e+" + mission.getPrzeszyka() + "% szansy na przeszycie bloku ciosu",
                            "&8- &5+" + mission.getKrytyk() + "% szansy na cios krytyczny"
                    )).toItemStack().clone());
        });




        if (PageUtils.isPageValid(guiItems, page - 1, 28)) {
            gui.setItem(47, leftArrow.setName("&cStrona " + (page - 1)).toItemStack().clone());
        }
        if (PageUtils.isPageValid(guiItems, page + 1, 28)) {
            gui.setItem(51, leftArrow.setName("&cStrona " + (page + 1)).toItemStack().clone());
        }

        PageUtils.getPageItems(guiItems, page, 28).forEach(itemStack -> gui.setItem(gui.firstEmpty(), itemStack));

        gui.setItem(49, new ItemBuilder(Material.PAPER).setName("&cStatystyki").setLore(Arrays.asList(
                "&7Ukonczone misje: &c" + (int) duszologUser.getCompletionMap().values().stream().filter(aBoolean -> aBoolean).count() + "&7/&c" + DuszologMissions.values().length,
                "&7Szansa Na Przeszycie Bloku Ciosu: &c" + duszologUser.getPrzeszywka() + "%",
                "&7Szansa Na Cios Krytyczny: &c" + duszologUser.getKrytykk() + "%"
        )).toItemStack().clone());



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


    public void add(DuszologUser duszologUser) {
        this.userMap.put(duszologUser.getUuid(), duszologUser);
    }

    public DuszologUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void set(final UUID uuid, final DuszologUser duszologUser) {
        this.userMap.replace(uuid, duszologUser);
    }

    public ImmutableSet<DuszologUser> getDuszologObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
