package rpg.rpgcore.newTarg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewTargWystawCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public NewTargWystawCommand(RPGCORE rpgcore) {
        super("wystaw");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("wystaw <cena>"));
            return;
        }
        if (player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac cos w rece, zeby moc to wystawic"));
            return;
        }
        if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przedmiot &cmusi &7posiadac nazwe"));
            return;
        }
        if (args[0].contains("k")) {
            int kIndex = args[0].indexOf('k');
            double beforeK = Double.parseDouble(args[0].substring(0, kIndex));
            double afterK = Double.parseDouble(args[0].substring(kIndex).replaceAll("k", "000").replaceFirst("0", "1") + "0");

            args[0] = String.valueOf(beforeK * afterK);
        }
        try {
            final double cena = Double.parseDouble(args[0]);

            if (cena < 1) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz wystawic przedmiotu za taka kwote"));
                return;
            }
            rpgcore.getNewTargManager().addToWystawia(player.getUniqueId());
            this.wystawGUI(player, player.getItemInHand(), DoubleUtils.round(cena, 2));
            player.getInventory().removeItem(player.getItemInHand());
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.poprawneUzycie("wystaw <cena>"));
        }
    }
    private void wystawGUI(final Player player, final ItemStack is, final double kwota) {
        final Inventory wystawGUI = Bukkit.createInventory(null, 9, Utils.format("&7&lWystaw przedmiot"));
        for (int i = 0; i < wystawGUI.getSize(); i++) {
            if (i < 4) {
                wystawGUI.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&a&lWystaw przedmiot!").addGlowing().toItemStack());
            } else {
                wystawGUI.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&c&lAnuluj!").addGlowing().toItemStack().clone());
            }
        }

        final ItemMeta meta = is.getItemMeta();

        List<String> lore = new ArrayList<>();

        if (meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(" ");
        lore.add(Utils.format("&7Wlasciciel: &c" + player.getName()));
        lore.add(Utils.format("&7Cena: &6" + Utils.spaceNumber(String.format("%.2f", kwota)) + "&2$"));
        lore.add(" ");
        lore.add("&8Wystawiajac ten przedmiot za kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(kwota)) + " &2$");
        lore.add("&8Zaplacisz podatek za wystawienie przedmiotu");
        lore.add("&8W wysokosci &c1% ceny wystawienia&8, czyli &6&o" + Utils.spaceNumber(Utils.kasaFormat.format((kwota * 1) / 100)) + " &2$");
        lore.add(" ");

        meta.setLore(Utils.format(lore));
        is.setItemMeta(meta);

        wystawGUI.setItem(4, is);

        player.openInventory(wystawGUI);
    }
}
