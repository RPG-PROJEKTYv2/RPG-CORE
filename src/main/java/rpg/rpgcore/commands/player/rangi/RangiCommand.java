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
        this.setAliases(Arrays.asList("vip", "elita"));
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
                "&7* &eDodatkowy exp: &c+25%",
                "&7* &eDodatkowa kasa z mobow i metinow: &c+25%",
                "&7* &eZwiekszony % na drop z mobow: &c+25%",
                "&7* &e/zestawvip &c- co 24 godziny"
        )).addGlowing().toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.BOOK).setName("&9&lTworca").setLore(Arrays.asList(" ",
                "&7Ranga ta posiada:",
                "&7* Prefix: &8[&bLvl. &fX&8] &9&lTworca &7" + player.getName() + "&f: wiadomosc",
                "&7* &eDodatkowy exp: &c+25%",
                "&7* &eDodatkowa kasa z mobow i metinow: &c+25%",
                "&7* &eZwiekszony % na drop z mobow: &c+25%",
                "&7* &e/zestawvip &c- co 24 godziny",
                "",
                "&7Wymagania:",
                "&4&lYou&f&lTube&7: &eminimum 200 subskrypcji",
                "                  &eoraz 500 wyswietlen/mies.",
                "&5&lTwitch&7: &eminimum 100 follow",
                "&8&lTik&d&lTok&7: &eminimum 1 000 obserwujacych",
                "              &eoraz 5000 wyswietlen (najlepszy film)",
                " ",
                "&4&lPo wspolprace zapraszamy: &e&ldc.hellrpg.pl"
        )).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.BOOK).setName("&5&lELITA").setLore(Arrays.asList(" ",
                "&7Ranga ta posiada:",
                "&7* Prefix: &8[&bLvl. &fX&8] &5&lELITA &7" + player.getName() + "&f: wiadomosc",
                "&7* &eDodatkowy exp: &c+50%",
                "&7* &eDodatkowa kasa z mobow i metinow: &c+50%",
                "&7* &eZwiekszony % na drop z mobow: &c+50%",
                "&7* &e/zestawelita &c- co 24 godziny",
                "&7 &e/enderchest"
        )).addGlowing().toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&c&lInformacje").setLore(Arrays.asList(
                " ",
                "&7Wszystkie rangi mozesz zakupic u &a&lHandlarza",
                "&7za walute serwerowa '&4&lHell&6&lS'y&7' ktora zakupisz",
                "&7na stronie serwera: &ewww.hellrpg.pl",
                " ",
                "&7Sprawdz swoj pozostaly czas rangi: &e/ranktime"
        )).addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
}
