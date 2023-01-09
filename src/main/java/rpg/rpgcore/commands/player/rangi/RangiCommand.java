package rpg.rpgcore.commands.player.rangi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class RangiCommand extends CommandAPI {
    public RangiCommand() {
        super("rangi");
        this.setAliases(Arrays.asList("vip", "svip", "elita"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("rangi"));
            return;
        }
        this.openRangiInventory(player);
    }

    private void openRangiInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.BREWING, Utils.format("&cRangi"));


        gui.setItem(0, new ItemBuilder(Material.BOOK).setName("&e&lVip").setLore(Arrays.asList(" ",
                "&7Ranga ta posiada:",
                "&7* Prefix: &8[&bLvl. &fX&8] &e&lVip &7" + player.getName() + "&f: wiadomosc",
                "&7* &eDodatkowy exp: &c+15%",
                "&7* &eDodatkowa kasa z mobow i metinow: &c+5%",
                "&7* &eZwiekszony % na drop z mobow: &c+5%",
                "&7* &e/zestawvip &c- co 24 godziny"
        )).addGlowing().toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.BOOK).setName("&6&lS&e&lvip").setLore(Arrays.asList(" ",
                "&7Ranga ta posiada:",
                "&7* Prefix: &8[&bLvl. &fX&8] &6&lS&e&lvip &7" + player.getName() + "&f: wiadomosc",
                "&7* &eDodatkowy exp: &c+25%",
                "&7* &eDodatkowa kasa z mobow i metinow: &c+10%",
                "&7* &eZwiekszony % na drop z mobow: &c+10%",
                "&7* &e/zestawsvip &c- co 24 godziny",
                "&7 &e/enderchest"
        )).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.BOOK).setName("&5&lELITA").setLore(Arrays.asList(" ",
                "&7Ranga ta posiada:",
                "&7* Prefix: &8[&bLvl. &fX&8] &5&lELITA &7" + player.getName() + "&f: wiadomosc",
                "&7* &eDodatkowy exp: &c+35%",
                "&7* &eDodatkowa kasa z mobow i metinow: &c+15%",
                "&7* &eZwiekszony % na drop z mobow: &c+15%",
                "&7* &e/zestawelita &c- co 24 godziny",
                "&7 &e/enderchest"
        )).addGlowing().toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&c&lInformacje").setLore(Arrays.asList(" ", "&7Wszystkie rangi mozna zakupic na discordzie:", "&7* &edc.hellrpg.pl", "&7lub na naszej stronie", "&7* &ewww.hellrpg.pl")).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
}
