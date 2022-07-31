package rpg.rpgcore.newTarg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NewTargWystaw implements CommandExecutor {

    private final RPGCORE rpgcore;

    public NewTargWystaw(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ItemBuilder accept = new ItemBuilder(Material.STAINED_GLASS_PANE, 1 ,(short) 5);
    private final ItemBuilder cancel = new ItemBuilder(Material.STAINED_GLASS_PANE, 1 ,(short) 14);


    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.wystaw"))) {
            player.sendMessage(Utils.permisje("rpg.wystaw"));
            return false;
        }

        if (player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac cos w rece, zeby moc to wystawic"));
            return false;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przedmiot &cmusi &7posiadac nazwe"));
            return false;
        }

        if (args.length == 1) {
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
                    return false;
                }
                rpgcore.getNewTargManager().addToWystawia(player.getUniqueId());
                this.wystawGUI(player, player.getItemInHand(), cena);

                player.getInventory().removeItem(player.getItemInHand());


                return false;

            } catch (NumberFormatException e) {
                player.sendMessage(Utils.poprawneUzycie("wystaw <cena>"));
                return false;
            }
        }

        player.sendMessage(Utils.poprawneUzycie("wystaw <cena>"));
        return false;
    }

    private void wystawGUI(final Player player, final ItemStack is, final double kwota) {
        final Inventory wystawGUI = Bukkit.createInventory(null, 9, Utils.format("&7&lWystaw przedmiot"));

        accept.setName("&a&lWystaw przedmiot!");
        accept.addGlowing();

        cancel.setName("&c&lAnuluj!");
        cancel.addGlowing();

        for (int i = 0; i < wystawGUI.getSize(); i++) {
            if (i < 4) {
                wystawGUI.setItem(i, accept.toItemStack());
            } else {
                wystawGUI.setItem(i, cancel.toItemStack());
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
        lore.add("&8W wysokosci &c1% ceny wystawienia&8, czyli &6&o" + Utils.spaceNumber(Utils.kasaFormat.format((kwota * 1) /100)) + " &2$" );
        lore.add(" ");

        meta.setLore(Utils.format(lore));
        is.setItemMeta(meta);


        wystawGUI.setItem(4, is);


        player.openInventory(wystawGUI);
    }

}
