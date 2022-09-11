package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetDmgCommand extends CommandAPI {

    private List<String> lore = new ArrayList<>();
    public SetDmgCommand() {
        super("setdmg");
        this.setRankLevel(RankType.DEV);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        lore.clear();

        if (player.getItemInHand() == null){
            player.sendMessage("NIE MASZ ITEMU DEBILU");
            return;
        }

        if (player.getItemInHand().getItemMeta().getLore() == null) {
            player.sendMessage("NIE MA DMG NA MIECZU LORE NULL");
            lore.add(" ");
            lore.add(Utils.format("&7Obrazenia: &c100"));
            ItemMeta meta = player.getItemInHand().getItemMeta();
            meta.setLore(lore);
            player.getItemInHand().setItemMeta(meta);
            return;
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
            return;
        }

        if (args.length == 1) {
            try {
                int i = Integer.parseInt(args[0]);
                if (!(i >= 1)) {
                    return;
                }
                lore = player.getItemInHand().getItemMeta().getLore();


                for (int j = 0; j < lore.size(); j++) {
                    if(lore.get(j).trim().contains("Obrazenia: ")) {
                        lore.set(j, Utils.format("&7Obrazenia: &c" + i));

                        ItemMeta meta = player.getItemInHand().getItemMeta();
                        meta.setLore(lore);

                        player.getItemInHand().setItemMeta(meta);
                        player.sendMessage("Ustawiono dmg w mieczu na " + i);
                        return;
                    }
                }
            } catch (final NumberFormatException e){
                e.printStackTrace();
            }
        }
    }
}
