package rpg.rpgcore.npc.alchemik;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.alchemik.enums.KrysztalUpgrades;
import rpg.rpgcore.npc.alchemik.objects.AlchemikUser;
import rpg.rpgcore.utils.*;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.AlchemikItems;

import java.util.*;

@Getter
@Setter
public class AlchemikNPC {

    private final RPGCORE rpgcore;
    private final Map<UUID, AlchemikUser> userMap;

    public AlchemikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllAlchemik();
    }


    public void openAlchemikGUI(final Player player) {
        final AlchemikUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&5&lAlchemik"));

        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0)
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack());
        }

        gui.setItem(10, new ItemBuilder(Material.WORKBENCH).setName("&eCraftingi").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&CInformacje").setLore(Arrays.asList(
                "&7Poziom Alchemickii: &d" + user.getLvl(),
                "&7Doswiadczenie Alchemickie: " + getProgressBar(user.getProgress()),
                "",
                "&f&lInformacje o Dropie",
                "&6Alchemiczny Pyl&7: " + MobDropHelper.getDropChance(player.getUniqueId(), 0.5) + "%",
                "&5Alchemiczny Podrecznik&7: " + MobDropHelper.getDropChance(player.getUniqueId(), 0.35) + "%",
                "&eAlchemiczna Sakwa&7: " + MobDropHelper.getDropChance(player.getUniqueId(), 0.15) + "%",
                "&aAlchemiczny Rdzen&7: " + MobDropHelper.getDropChance(player.getUniqueId(), 0.0025) + "%"
        )).toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.BEACON).setName("&eTwoje Alchemickie Krysztaly").toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.ANVIL).setName("&eUlepszanie Alchemickich Krysztalow").toItemStack());

        player.openInventory(gui);
    }

    public void openAlchemikCrafting(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&5&lAlchemik &8>> &eCraftingi"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0)
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&eWytworz &c&lAlchemicki Krysztal Potegi").setLore(Arrays.asList(
                "&7Srednie Obrazenia: &5+0.01% - 2%",
                "&7Dodatkowe Obrazenia: &5+1 DMG - 25 DMG",
                "",
                "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                "&cWymagany Poziom: &650 lvl",
                "",
                "&f&lWymagane Przedmioty",
                "&8- &6Alchemiczny Pyl &7x32 &8(" + Utils.getPlayerInventoryItemCount(player, AlchemikItems.I1.getItemStack()) + "/32)",
                "&8- &9&lMagiczne Zaczarowanie &7x8 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I50.getItemStack()) + "/8)",
                "&8- &3&lKamien Zaczarowania Stolu &7x5 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_KAMIENBAO.getItemStack()) + "/5)",
                "&8- &a&lPodrecznik Kowala &7x8 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I10.getItemStack()) + "/8)",
                "&8- &635 000 000&2$"
        )).toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.INK_SACK, 1, (short) 12).setName("&eWytworz &9&lAlchemicki Krysztal Obrony").setLore(Arrays.asList(
                "&7Srednia Defensywa: &5+0.01% - 2%",
                "&7Blok Ciosu: &5+0.01% - 2%",
                "",
                "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                "&cWymagany Poziom: &650 lvl",
                "",
                "&f&lWymagane Przedmioty",
                "&8- &6Alchemiczny Pyl &7x32 &8(" + Utils.getPlayerInventoryItemCount(player, AlchemikItems.I1.getItemStack()) + "/32)",
                "&8- &9&lMagiczne Zaczarowanie &7x8 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I50.getItemStack()) + "/8)",
                "&8- &3&lKamien Zaczarowania Stolu &7x5 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_KAMIENBAO.getItemStack()) + "/5)",
                "&8- &a&lPodrecznik Kowala &7x8 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I10.getItemStack()) + "/8)",
                "&8- &635 000 000&2$"
        )).toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.INK_SACK, 1, (short) 11).setName("&eWytworz &2&lAlchemicki Krysztal Potworow").setLore(Arrays.asList(
                "&7Silny Przeciwko Potwora: &5+0.01% - 5%",
                "&7Odpornosc na Potwory: &5+0.01% - 3%",
                "",
                "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                "&cWymagany Poziom: &650 lvl",
                "",
                "&f&lWymagane Przedmioty",
                "&8- &6Alchemiczny Pyl &7x48 &8(" + Utils.getPlayerInventoryItemCount(player, AlchemikItems.I1.getItemStack()) + "/48)",
                "&8- &9&lMagiczne Zaczarowanie &7x10 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I50.getItemStack()) + "/10)",
                "&8- &3&lKamien Zaczarowania Stolu &7x6 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_KAMIENBAO.getItemStack()) + "/6)",
                "&8- &a&lPodrecznik Kowala &7x10 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I10.getItemStack()) + "/10)",
                "&8- &645 000 000&2$"
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.INK_SACK, 1, (short) 7).setName("&eWytworz &4&lAlchemicki Krysztal Ludzi").setLore(Arrays.asList(
                "&7Silny Przeciwko Ludziom: &5+0.01% - 5%",
                "&7Odpornosc na Ludziom: &5+0.01% - 3%",
                "",
                "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                "&cWymagany Poziom: &650 lvl",
                "",
                "&f&lWymagane Przedmioty",
                "&8- &6Alchemiczny Pyl &7x48 &8(" + Utils.getPlayerInventoryItemCount(player, AlchemikItems.I1.getItemStack()) + "/48)",
                "&8- &9&lMagiczne Zaczarowanie &7x10 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I50.getItemStack()) + "/10)",
                "&8- &3&lKamien Zaczarowania Stolu &7x6 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_KAMIENBAO.getItemStack()) + "/6)",
                "&8- &a&lPodrecznik Kowala &7x10 &8(" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I10.getItemStack()) + "/10)",
                "&8- &645 000 000&2$"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    private ItemStack getNoItem(final String name) {
        return new ItemBuilder(Material.IRON_FENCE).setName("&c&lBrak Alchemickiego Krysztalu " + name).toItemStack().clone();
    }

    public void openAlchemickieKrysztaly(final Player player) {
        final AlchemikUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&5&lAlchemik &8>> &eTwoje Krysztaly"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0)
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack());
        }

        gui.setItem(11, (user.getPotegi().getType().equals(Material.AIR) ? this.getNoItem("Potegi") : user.getPotegi()));
        gui.setItem(12, (user.getObrony().getType().equals(Material.AIR) ? this.getNoItem("Obrony") : user.getObrony()));
        gui.setItem(14, (user.getPotworow().getType().equals(Material.AIR) ? this.getNoItem("Potworow") : user.getPotworow()));
        gui.setItem(15, (user.getLudzi().getType().equals(Material.AIR) ? this.getNoItem("Ludzi") : user.getLudzi()));

        player.openInventory(gui);
    }

    public void openUlepszanieKrysztalow(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&5&lAlchemik &8>> &eUlepszanie"));

        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0)
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na Alchemicki Krysztal").toItemStack());
        gui.setItem(13, getUlepszanieItem("", -1, player));

        player.openInventory(gui);
    }

    public ItemStack getUlepszanieItem(final String krysztal, final int lvl, final Player player) {
        if (lvl == -1 && krysztal.isEmpty()) {
            return new ItemBuilder(Material.ANVIL).setName("&e&lUlepsz Swoj Krysztal").setLore(Arrays.asList(
                    "&8Wsadz dowolny &5Alchemicki Krysztal&8,",
                    "&8zeby poznac liste potrzebnych przedmiotow"
            )).toItemStack();
        }
        final KrysztalUpgrades info = KrysztalUpgrades.find(krysztal, lvl);

        if (info == null)
            return new ItemBuilder(Material.ANVIL).setName("&e&lUlepsz Swoj Krysztal").setLore(Arrays.asList(
                    "&c&lCos poszlo nie tak...",
                    "&c!&7Sprobuj wsadzic inny krysztal&c!",
                    "",
                    "&7Jesli problem sie powtorzy, zglos to do administracji!"
            )).toItemStack();

        final List<String> lore = new ArrayList<>();
        info.getReqItemList().forEach(item -> {
            lore.add("&8- " + item.getItemMeta().getDisplayName() + " &7x" + item.getAmount() + " &8(" + Utils.getPlayerInventoryItemCount(player, item) + "/" + item.getAmount() + ")");
        });

        return new ItemBuilder(Material.ANVIL).setName("&e&lUlepsz Swoj Krysztal").setLoreCrafting(lore, Arrays.asList(
                "&8- &6" + Utils.spaceNumber(info.getReqMoney()) + "&2$",
                "",
                "&7Szansa na powodzenie: &a" + info.getAcceptChance() + "%"
                )).toItemStack().clone();
    }

    public ItemStack getNextKrysztalTier(final ItemStack item, final String krysztal, final int lvl) {
        final KrysztalUpgrades info = KrysztalUpgrades.find(krysztal, lvl);
        if (info == null)
            return new ItemBuilder(Material.BARRIER).setName("&cCos poszlo nie tak :<").setLore(Arrays.asList(
                    "&8Cos poszlo nie tak podczas generowania",
                    "&8nastepnego poziomu krysztalu!"
            )).toItemStack().clone();

        switch (krysztal) {
            case "Potegi":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "srDmg") + info.getToAddVal1(), 2),
                        Utils.getTagInt(item, "dodatkowe") + info.getToAddVal2(), lvl, info.getNewAlchLvl(), info.getNewLvl());
            case "Obrony":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "srDef") + info.getToAddVal1(), 2),
                        DoubleUtils.round(Utils.getTagDouble(item, "blok") + info.getToAddVal2(), 2), lvl, info.getNewAlchLvl(), info.getNewLvl());
            case "Potworow":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "silnyNaMoby") + info.getToAddVal1(), 2),
                        DoubleUtils.round(Utils.getTagDouble(item, "defNaMoby") + info.getToAddVal2(), 2), lvl, info.getNewAlchLvl(), info.getNewLvl());
            case "Ludzi":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "silnyNaLudzi") + info.getToAddVal1(), 2),
                        DoubleUtils.round(Utils.getTagDouble(item, "defNaLudzi") + info.getToAddVal2(), 2), lvl, info.getNewAlchLvl(), info.getNewLvl());
            default:
                return new ItemBuilder(Material.BARRIER).setName("&cCos poszlo nie tak :<").setLore(Arrays.asList(
                        "&8Cos poszlo nie tak podczas generowania",
                        "&8nastepnego poziomu krysztalu!"
                )).toItemStack().clone();
        }
    }
    public ItemStack getPrevKrysztalTier(final ItemStack item, final String krysztal, final int lvl) {
        final KrysztalUpgrades infoNow = KrysztalUpgrades.find(krysztal, lvl);
        final KrysztalUpgrades infoPrev = KrysztalUpgrades.find(krysztal, lvl - 1);
        if (infoNow == null)
            return new ItemBuilder(Material.BARRIER).setName("&cCos poszlo nie tak :<").setLore(Arrays.asList(
                    "&8Cos poszlo nie tak podczas generowania",
                    "&8nastepnego poziomu krysztalu!"
            )).toItemStack().clone();

        switch (krysztal) {
            case "Potegi":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "srDmg") - infoNow.getToAddVal1(), 2),
                        Utils.getTagInt(item, "dodatkowe") - infoNow.getToAddVal2(), infoPrev.getLvl(), infoPrev.getNewAlchLvl(), infoPrev.getNewLvl());
            case "Obrony":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "srDef") - infoNow.getToAddVal1(), 2),
                        DoubleUtils.round(Utils.getTagDouble(item, "blok") - infoNow.getToAddVal2(), 2), infoPrev.getLvl(), infoPrev.getNewAlchLvl(), infoPrev.getNewLvl());
            case "Potworow":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "silnyNaMoby") - infoNow.getToAddVal1(), 2),
                        DoubleUtils.round(Utils.getTagDouble(item, "defNaMoby") - infoNow.getToAddVal2(), 2), infoPrev.getLvl(), infoPrev.getNewAlchLvl(), infoPrev.getNewLvl());
            case "Ludzi":
                return AlchemikItems.getSpecificKrysztal(krysztal, DoubleUtils.round(Utils.getTagDouble(item, "silnyNaLudzi") - infoNow.getToAddVal1(), 2),
                        DoubleUtils.round(Utils.getTagDouble(item, "defNaLudzi") - infoNow.getToAddVal2(), 2), infoPrev.getLvl(), infoPrev.getNewAlchLvl(), infoPrev.getNewLvl());
            default:
                return new ItemBuilder(Material.BARRIER).setName("&cCos poszlo nie tak :<").setLore(Arrays.asList(
                        "&8Cos poszlo nie tak podczas generowania",
                        "&8nastepnego poziomu krysztalu!"
                )).toItemStack().clone();
        }
    }

    public void upgradePlayerKrysztal(final ItemStack item, final KrysztalUpgrades info, final Player player) {
        if (info == null) {
            player.sendMessage(Utils.format("&5&lAlchemik &8>> &cCos poszlo nie tak podczas ulepszania krysztalu!"));
            return;
        }

        if (ChanceHelper.getChance(info.getAcceptChance())) {
            player.getInventory().addItem(this.getNextKrysztalTier(item, info.getId(), info.getLvl()));

            Bukkit.broadcastMessage(Utils.format("&5&lAlchemik &8>> &dGracz &5" + player.getName() + " &dulepszyl swoj " +
                    item.getItemMeta().getDisplayName().replace(Utils.format("&8&l+" + info.getLvl()), "") + " &dna &8&l+" + info.getLvl() + "&d!"));
        } else {
            player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie udalo Ci sie ulepszyc swojego krysztalu!"));
            Bukkit.broadcastMessage(Utils.format("&5&lAlchemik &8>> &dGracz &5" + player.getName() + " &dspalil swoj " +
                    item.getItemMeta().getDisplayName() + "&d!"));
            if (info.getLvl() == 1) {
                player.getInventory().addItem(item);
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPoniewaz jest to podstawowy krysztal, to dostales go w pelnej okazalosci!"));
                return;
            }
            if (player.getInventory().containsAtLeast(AlchemikItems.I8.getItemStack(), 1)) {
                player.getInventory().removeItem(new ItemBuilder(AlchemikItems.I8.getItemStack().clone()).setAmount(1).toItemStack());
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPoniewaz posiadasz &aAlchemiczny Rdzen &d, to twoj krysztal nie zostal spalony!"));
                player.getInventory().addItem(item);
                return;
            }
            player.getInventory().addItem(this.getPrevKrysztalTier(item, info.getId(), info.getLvl() - 1));
        }

    }


    private String getProgressBar(final int progress) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < progress) stringBuilder.append("&5■");
            else stringBuilder.append("&f■");
        }
        return stringBuilder.toString();
    }

    public AlchemikUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final AlchemikUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public void remove(final UUID uuid) {
        this.userMap.remove(uuid);
    }

    public ImmutableSet<AlchemikUser> getAlchemikUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

}
