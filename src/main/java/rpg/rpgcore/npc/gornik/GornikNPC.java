package rpg.rpgcore.npc.gornik;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.npc.gornik.enums.GornikLevels;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GornikNPC {
    private final RPGCORE rpgcore;

    private final Map<UUID, GornikUser> userMap;
    private final List<UUID> toPayList = new ArrayList<>();
    private final Set<Items> chestRewards = Sets.newConcurrentHashSet();
    @Getter
    private final List<Location> chestLocations = new ArrayList<>();

    public GornikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllGornik();
        this.initChestRewards();
    }

    private void initChestRewards() {
        this.chestRewards.add(new Items("37", 0.0005, GornikItems.I9.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("38", 0.0005, GornikItems.I10.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("39", 0.0005, GornikItems.I11.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("40", 0.0005, GornikItems.I12.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("1", 0.1, GornikItems.I1_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("2", 0.1, GornikItems.I2_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("3", 0.1, GornikItems.I3_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("4", 0.1, GornikItems.I4_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("5", 0.1, GornikItems.I5_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("6", 0.1, GornikItems.I6_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("7", 0.1, GornikItems.I7_1.getItemStack().clone(), 1));
        this.chestRewards.add(new Items("8", 3, GornikItems.I1.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("9", 3, GornikItems.I2.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("10", 3, GornikItems.I3.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("11", 3, GornikItems.I4.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("12", 3, GornikItems.I5.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("13", 3, GornikItems.I6.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("14", 3, GornikItems.I7.getItemStack().clone(), 16));
        this.chestRewards.add(new Items("15", 5, GornikItems.I8.getItemStack().clone(), 3));
        this.chestRewards.add(new Items("16", 8, GornikItems.I1.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("17", 8, GornikItems.I2.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("18", 8, GornikItems.I3.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("19", 8, GornikItems.I4.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("20", 8, GornikItems.I5.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("21", 8, GornikItems.I6.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("22", 8, GornikItems.I7.getItemStack().clone(), 8));
        this.chestRewards.add(new Items("23", 12.5, GornikItems.I1.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("24", 12.5, GornikItems.I2.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("25", 12.5, GornikItems.I3.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("26", 12.5, GornikItems.I4.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("27", 12.5, GornikItems.I5.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("28", 12.5, GornikItems.I6.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("29", 12.5, GornikItems.I7.getItemStack().clone(), 4));
        this.chestRewards.add(new Items("30", 25, GornikItems.I1.getItemStack().clone(), 2));
        this.chestRewards.add(new Items("31", 25, GornikItems.I2.getItemStack().clone(), 2));
        this.chestRewards.add(new Items("32", 25, GornikItems.I3.getItemStack().clone(), 2));
        this.chestRewards.add(new Items("33", 25, GornikItems.I4.getItemStack().clone(), 2));
        this.chestRewards.add(new Items("34", 25, GornikItems.I5.getItemStack().clone(), 2));
        this.chestRewards.add(new Items("35", 25, GornikItems.I6.getItemStack().clone(), 2));
        this.chestRewards.add(new Items("36", 25, GornikItems.I7.getItemStack().clone(), 2));
    }

    public void onClick(final Player player) {
        final User user = rpgcore.getUserManager().find(player.getUniqueId());

        if (user.getLvl() < 60) {
            player.sendMessage(Utils.format("&6&lGornik &8>> &7Osiagnij &e&l60 &7poziom... Moze wtedy bedziesz godny rozmowy ze mna"));
            return;
        }

        final GornikUser gornikUser = this.find(player.getUniqueId());

        if (gornikUser.getFreePass() <= System.currentTimeMillis()) {
            player.sendMessage(Utils.format("&6&lGornik &8>> &7Tym razem Ci odpuszcze... Ale nastepna wycieczka jest na twoj koszt!"));
            player.sendMessage(Utils.format("&8(Kolejna darmowa wycieczka do kopalni bedzie dostepna za 4H)"));
            gornikUser.giveFreePassCooldown();
            gornikUser.setTimeLeft(gornikUser.getMaxTimeLeft());
            player.teleport(new Location(Bukkit.getWorld("kopalnia"), -143.5, 76, -3.5, -45, 0));
        } else {
            player.sendMessage(Utils.format("&6&lGornik &8>> &7Hola hola, tym razem to ty placisz!"));
            final TextComponent message = new TextComponent(Utils.format("&a&l[Kliknij, aby zaplacic!]"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(Utils.format("&7Koszt: &e250 000&2$"))}));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gornikzaplac"));
            this.addToPayList(player.getUniqueId());
            player.spigot().sendMessage(message);
        }
    }


    public void openGornik(final Player player) {
        final GornikUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lGornik"));

        for (int i = 0; i < gui.getSize(); i++)
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());


        gui.setItem(10, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&4&lKampania").setLore(Arrays.asList(
                "&7Postep: &e" + (user.getMission() - 1) + "&7/&e27 &7(&e" + DoubleUtils.round((user.getMission() - 1) / 27.0 * 100, 2) + "%&7)"
        )).toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.EMERALD).setName("&a&lSklep Gorniczy").addGlowing().toItemStack());
        gui.setItem(16, new ItemBuilder(Material.WORKBENCH).setName("&eCraftingi Gornicze").addGlowing().toItemStack());


        player.openInventory(gui);
    }

    public void openKampania(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lGornik &8>> &4&lKampania"));
        final GornikUser user = this.find(player.getUniqueId());
        for (int i = 0; i < gui.getSize(); i++) {
            if ((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44)) continue;
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        for (final GornikMissions mission : GornikMissions.values()) {
            if (mission.getId() < user.getMission()) gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BOOK).setName("&7Misja &c" + mission.getId()).setLore(Arrays.asList("&a&lUkonczono")).addGlowing().toItemStack().clone());
            else if (mission.getId() == user.getMission()) gui.setItem(gui.firstEmpty(), new ItemBuilder(mission.getItem().clone()).setLoreCrafting(mission.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&7Postep: &e" + user.getProgress() + "&7/&e" + mission.getReqAmount() + " &7(&e" + DoubleUtils.round(user.getProgress() / (double) mission.getReqAmount() * 100, 2) + "%&7)"
            )).toItemStack().clone());
        }

        gui.setItem(49, Utils.powrot().clone());
        player.openInventory(gui);
    }

    public void openSklep(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lGornik &8>> &a&lSklep Gorniczy"));

        gui.setItem(0, new ItemBuilder(GornikItems.getKilof(player).clone()).setLoreCrafting(GornikItems.getKilof(player).clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Cena: &e" + Utils.spaceNumber(50_000_000) + "&2$"
        )).addTagInt("price", 50_000_000).toItemStack().clone());

        gui.setItem(8, Utils.powrot().clone());
        player.openInventory(gui);
    }

    public void openCraftingi(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lGornik &8>> &e&lCraftingi Gornicze"));
        for (int i = 0; i < gui.getSize(); i++)
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());

        gui.setItem(1, new ItemBuilder(Material.COAL_ORE).setName("&8Ruda Wegla &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &8Ruda wegla &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(2, new ItemBuilder(Material.IRON_ORE).setName("&7Ruda Zelaza &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &7Ruda Zelaza &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(3, new ItemBuilder(Material.GOLD_ORE).setName("&eRuda Topazu &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &eRuda Topazu &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(4, new ItemBuilder(Material.LAPIS_ORE).setName("&1Ruda Tanzanitu &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &1Ruda Tanzanitu &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(5, new ItemBuilder(Material.EMERALD_ORE).setName("&aRuda Jadeitu &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &aRuda Jadeitu &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(6, new ItemBuilder(Material.DIAMOND_ORE).setName("&bRuda Diamentu &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &bRuda Diamentu &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(7, new ItemBuilder(Material.REDSTONE_ORE).setName("&cRuda Rubinu &8&l(x64)").setLore(Arrays.asList(
                "&7Potrzebne przedmioty:",
                "&8- &cRuda Rubinu &7x64",
                "&8- &650 000&2$"
        )).hideFlag().toItemStack());

        // SET GORNICZY
        gui.setItem(10, new ItemBuilder(GornikItems.I9.getItemStack().clone()).setLoreCrafting(GornikItems.I9.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &8Ruda Wegla &8&l(x64) &7x10",
                "&8- &7Ruda Zelaza &8&l(x64) &7x10",
                "&8- &eRuda Topazu &8&l(x64) &7x10",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x10",
                "&8- &aRuda Jadeitu &8&l(x64) &7x10",
                "&8- &bRuda Diamentu &8&l(x64) &7x10",
                "&8- &cRuda Rubinu &8&l(x64) &7x10",
                "&8- &635 000 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(19, new ItemBuilder(GornikItems.I10.getItemStack().clone()).setLoreCrafting(GornikItems.I10.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &8Ruda Wegla &8&l(x64) &7x24",
                "&8- &7Ruda Zelaza &8&l(x64) &7x24",
                "&8- &eRuda Topazu &8&l(x64) &7x24",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x24",
                "&8- &aRuda Jadeitu &8&l(x64) &7x24",
                "&8- &bRuda Diamentu &8&l(x64) &7x24",
                "&8- &cRuda Rubinu &8&l(x64) &7x24",
                "&8- &675 000 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(11, new ItemBuilder(GornikItems.I11.getItemStack().clone()).setLoreCrafting(GornikItems.I11.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &8Ruda Wegla &8&l(x64) &7x16",
                "&8- &7Ruda Zelaza &8&l(x64) &7x16",
                "&8- &eRuda Topazu &8&l(x64) &7x16",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x16",
                "&8- &aRuda Jadeitu &8&l(x64) &7x16",
                "&8- &bRuda Diamentu &8&l(x64) &7x16",
                "&8- &cRuda Rubinu &8&l(x64) &7x16",
                "&8- &650 000 000&2$"
        )).hideFlag().toItemStack());
        gui.setItem(20, new ItemBuilder(GornikItems.I12.getItemStack().clone()).setLoreCrafting(GornikItems.I12.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &8Ruda Wegla &8&l(x64) &7x8",
                "&8- &7Ruda Zelaza &8&l(x64) &7x8",
                "&8- &eRuda Topazu &8&l(x64) &7x8",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x8",
                "&8- &aRuda Jadeitu &8&l(x64) &7x8",
                "&8- &bRuda Diamentu &8&l(x64) &7x8",
                "&8- &cRuda Rubinu &8&l(x64) &7x8",
                "&8- &625 000 000&2$"
        )).hideFlag().toItemStack());

        // PRZEDMIOTY SPECJALNE
        gui.setItem(13, new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setLoreCrafting(GlobalItem.I_KAMIENBAO.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &eRuda Topazu &8&l(x64) &7x4",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x4",
                "&8- &bRuda Diamentu &8&l(x64) &7x4",
                "&8- &cRuda Rubinu &8&l(x64) &7x4",
                "&8- &65 000 000&2$"
        )).toItemStack());
        gui.setItem(22, new ItemBuilder(GlobalItem.I_METAL.getItemStack().clone()).setLoreCrafting(GlobalItem.I_METAL.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &8Ruda Wegla &8&l(x64) &7x4",
                "&8- &7Ruda Zelaza &8&l(x64) &7x2",
                "&8- &aRuda Jadeitu &8&l(x64) &7x1",
                "&8- &61 000 000&2$"
        )).toItemStack());

        //ULEPSZENIA KILOFA
        final ItemStack playerPickaxe = GornikItems.getKilof(player).clone();
        gui.setItem(15, new ItemBuilder(playerPickaxe.clone()).setType(Material.IRON_PICKAXE).setLoreCrafting(playerPickaxe.clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &6Kilof Gornika &8&l(&7&lKamienny&8&l) &7min Lvl. &e10",
                "&8- &8Ruda Wegla &8&l(x64) &7x5",
                "&8- &7Ruda Zelaza &8&l(x64) &7x5",
                "&8- &610 000 000&2$",
                " ",
                "&4&lUWAGA!",
                "&cZeby wykonac ten crafting musisz posiadac swoj kilof w rece!",
                "&cPoziom oraz exp zostana zachowane!",
                "&4&lUWAGA!"
        )).hideFlag().toItemStack().clone());
        gui.setItem(24, new ItemBuilder(playerPickaxe.clone()).setType(Material.GOLD_PICKAXE).setLoreCrafting(playerPickaxe.clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &6Kilof Gornika &8&l(&f&lZelazny&8&l) &7min Lvl. &e17",
                "&8- &8Ruda Wegla &8&l(x64) &7x10",
                "&8- &7Ruda Zelaza &8&l(x64) &7x10",
                "&8- &eRuda Topazu &8&l(x64) &7x5",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x5",
                "&8- &625 000 000&2$",
                " ",
                "&4&lUWAGA!",
                "&cZeby wykonac ten crafting musisz posiadac swoj kilof w rece!",
                "&cPoziom oraz exp zostana zachowane!",
                "&4&lUWAGA!"
        )).hideFlag().toItemStack().clone());
        gui.setItem(16, new ItemBuilder(playerPickaxe.clone()).setType(Material.DIAMOND_PICKAXE).setLoreCrafting(playerPickaxe.clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Potrzebne przedmioty:",
                "&8- &6Kilof Gornika &8&l(&6&lZloty&8&l) &7min Lvl. &e25",
                "&8- &8Ruda Wegla &8&l(x64) &7x20",
                "&8- &7Ruda Zelaza &8&l(x64) &7x20",
                "&8- &eRuda Topazu &8&l(x64) &7x10",
                "&8- &1Ruda Tanzanitu &8&l(x64) &7x10",
                "&8- &aRuda Jadeitu &8&l(x64) &7x5",
                "&8- &bRuda Diamentu &8&l(x64) &7x5",
                "&8- &650 000 000&2$",
                " ",
                "&4&lUWAGA!",
                "&cZeby wykonac ten crafting musisz posiadac swoj kilof w rece!",
                "&cPoziom oraz exp zostana zachowane!",
                "&4&lUWAGA!"
        )).hideFlag().toItemStack().clone());


        gui.setItem(26, Utils.powrot().clone());
        player.openInventory(gui);
    }


    public void updateExp(final ItemStack item, final int exp) {
        final int itemExp = Utils.getTagInt(item, "exp");
        final int reqExp = Utils.getTagInt(item, "reqExp");
        Utils.setTagInt(item, "exp", itemExp + exp);
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();
        if (Utils.getTagInt(item, "lvl") == 31) {
            lore.set(1, Utils.format("&7Exp: &6" + Utils.spaceNumber(itemExp + exp) + "&7/&c&lMAX"));
        } else {
            lore.set(1, Utils.format("&7Exp: &6" + Utils.spaceNumber(itemExp + exp) + "&7/&6" + Utils.spaceNumber(reqExp)));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        if (itemExp + exp >= reqExp) {
            this.updateLvl(item);
        }
    }

    public void updateLvl(final ItemStack item) {
        final int lvl = Utils.getTagInt(item, "lvl");
        final int reqExp = GornikLevels.getByLvl(lvl + 1).getReqExp();
        Utils.setTagInt(item, "lvl", lvl + 1);
        Utils.setTagInt(item, "exp", 0);
        Utils.setTagInt(item, "reqExp", reqExp);
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();
        if (lvl == 30) {
            lore.set(0, Utils.format("&7Poziom: &c&lMAX"));
            lore.set(1, Utils.format("&7Exp: &60&7/&c&lMAX"));
            lore.add(" ");
            lore.add("&6Umiejetnosc: Prawdziwy Kopacz &e&lRMB");
            lore.add("&7Otrzymaj efekt &eHaste II &7na &e60 &7sekund");
            lore.add("&7oraz nadaj go wszystkim graczom w zasiegu &a10 &7kratek");
            lore.add("&7Dodatkowo otrzymaj &a+25% &7szansy na podwojenie rudy");
            lore.add("&7na &a30 &7sekund &8(Cooldown: 5 &8minut)");
        } else {
            lore.set(0, Utils.format("&7Poziom: &e" + (lvl + 1)));
            lore.set(1, Utils.format("&7Exp: &60&7/&6" + Utils.spaceNumber(reqExp)));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
    }


    public void giveRandomChestReward(final Player player) {
        player.sendMessage(Utils.format("&f&lZawartosc Skrzyni:"));
        for (int i = 0; i < 5; i++) getDrawnItems(player);
    }

    private void getDrawnItems(final Player player) {
        for (Items item : this.chestRewards) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (item.getName().equals("37") || item.getName().equals("38") || item.getName().equals("39") || item.getName().equals("40")) {
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        Bukkit.getServer().broadcastMessage(Utils.format("            &8&lKopalnia            "));
                        Bukkit.getServer().broadcastMessage(Utils.format("&7Gracz &e" + player.getName() + " &7znalazl &6" + item.getRewardItem().getItemMeta().getDisplayName() + "&7!"));
                        Bukkit.getServer().broadcastMessage(Utils.format("            &8&lKopalnia            "));
                    }, 20L);
                }
                player.sendMessage(Utils.format("&8- " + item.getRewardItem().getItemMeta().getDisplayName() + " &7x" + item.getAmount()));
                final ItemStack reward = item.getRewardItem().clone();
                reward.setAmount(item.getAmount());
                player.getInventory().addItem(reward);
                return;
            }
        }
    }


    public void addToPayList(final UUID uuid) {
        this.toPayList.add(uuid);
    }

    public void removeFromPayList(final UUID uuid) {
        this.toPayList.remove(uuid);
    }

    public boolean isInPayList(final UUID uuid) {
        return this.toPayList.contains(uuid);
    }

    public GornikUser find(final UUID uuid) {
        return userMap.get(uuid);
    }

    public void add(final GornikUser gornikUser) {
        userMap.put(gornikUser.getUuid(), gornikUser);
    }

    public void remove(final GornikUser gornikUser) {
        userMap.remove(gornikUser.getUuid());
    }

    public ImmutableSet<GornikUser> getGornikUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }

}
