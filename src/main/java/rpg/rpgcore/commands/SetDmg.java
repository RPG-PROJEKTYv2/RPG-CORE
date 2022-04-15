package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SetDmg implements CommandExecutor {

    private final RPGCORE rpgcore;

    public SetDmg(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private List<String> lore = new ArrayList<>();

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        lore.clear();

        if (player.getItemInHand() == null){
            player.sendMessage("NIE MASZ ITEMU DEBILU");
            return false;
        }

        if (player.getItemInHand().getItemMeta().getLore() == null) {
            player.sendMessage("NIE MA DMG NA MIECZU LORE NULL");
            lore.add(" ");
            lore.add(Utils.format("&7Obrazenia: &c100"));
            ItemMeta meta = player.getItemInHand().getItemMeta();
            meta.setLore(lore);
            player.getItemInHand().setItemMeta(meta);
            return false;
        }
        player.sendMessage(String.valueOf(player.getItemInHand().getItemMeta().getLore()));

        boolean jest = true;

        for(String str: player.getItemInHand().getItemMeta().getLore()) {
            jest = str.trim().contains("Obrazenia: ");
        }

        if (!jest) {
            player.sendMessage("NIE MA DMG NA MIECZU BRAK OBRAZEN");
            lore.add(" ");
            lore.add(Utils.format("&7Obrazenia: &c100"));
            ItemMeta meta = player.getItemInHand().getItemMeta();
            meta.setLore(lore);
            player.getItemInHand().setItemMeta(meta);
            return false;
        }

        if (args.length == 1) {
            try {
                int i = Integer.parseInt(args[0]);
                if (!(i >= 1)) {
                    return false;
                }
                lore = player.getItemInHand().getItemMeta().getLore();


                for (int j = 0; j < lore.size(); j++) {
                    if(lore.get(j).trim().contains("Obrazenia: ")) {
                        lore.set(j, Utils.format("&7Obrazenia: &c" + i));

                        ItemMeta meta = player.getItemInHand().getItemMeta();
                        meta.setLore(lore);

                        player.getItemInHand().setItemMeta(meta);
                        player.sendMessage("Ustawiono dmg w mieczu na " + i);
                        return false;
                    }
                }
            } catch (final NumberFormatException e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
