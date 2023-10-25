package rpg.rpgcore.npc.nereus.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.nereus.objects.NereusUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.NereusItems;

import java.util.UUID;

public class NereusInventoryClickListener implements Listener {

    private final RPGCORE rpgcore = RPGCORE.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Nereus")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final NereusUser nereusUser = rpgcore.getNereusNPC().find(player.getUniqueId());
            switch (slot) {
                case 22:
                    final User user = rpgcore.getUserManager().find(uuid);
                    if (user.getKasa() < 20_000_000) {
                        player.sendMessage(Utils.format("&9&lNereus &8>> &cNie posiadasz wystarczajacej ilosci pieniedzy!"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(NereusItems.I1.getItemStack(), 12)) {
                        player.sendMessage(Utils.format("&9&lNereus &8>> &cNie posiadasz wystarczajacej ilosci fragmentow!"));
                        return;
                    }

                    player.getInventory().removeItem(new ItemBuilder(NereusItems.I1.getItemStack().clone()).setAmount(12).toItemStack());
                    user.setKasa(user.getKasa() - 20_000_000);

                    final ItemStack itemStack = NereusItems.getRandomRelikt();

                    if (itemStack == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cCos poszlo nie tak podczas wytwarzania reliktu :<"));
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cZglos sie z ss'em tej wiadomosci do administracji!"));
                        return;
                    }
                    if (Utils.getTagString(itemStack, "relikt").equals("Wiecznosci")) Bukkit.getServer().broadcastMessage(Utils.format("&9&lNereus &8>> &7Gracz &9" + player.getName() + " &7wytworzyl " + itemStack.getItemMeta().getDisplayName() + " &f"
                            + Utils.getTagInt(itemStack, "val") + "HP&7!"));
                    else Bukkit.getServer().broadcastMessage(Utils.format("&9&lNereus &8>> &7Gracz &9" + player.getName() + " &7wytworzyl " + itemStack.getItemMeta().getDisplayName() + " &f"
                            + Utils.getTagInt(itemStack, "val") + "%&7!"));

                    player.getInventory().addItem(itemStack);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
                    return;
                case 4:
                    if (nereusUser.getPotegi().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(nereusUser.getPotegi());
                    player.sendMessage(Utils.format("&9&lNereus &8>> &aPomyslnie wyjales " + nereusUser.getPotegi().getItemMeta().getDisplayName() + "&a!"));
                    nereusUser.setPotegi(new ItemStack(Material.AIR));
                    break;
                case 19:
                    if (nereusUser.getStarozytnosci().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(nereusUser.getStarozytnosci());
                    player.sendMessage(Utils.format("&9&lNereus &8>> &aPomyslnie wyjales " + nereusUser.getStarozytnosci().getItemMeta().getDisplayName() + "&a!"));
                    nereusUser.setStarozytnosci(new ItemStack(Material.AIR));
                    break;
                case 25:
                    if (nereusUser.getPrzodkow().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(nereusUser.getPrzodkow());
                    player.sendMessage(Utils.format("&9&lNereus &8>> &aPomyslnie wyjales " + nereusUser.getPrzodkow().getItemMeta().getDisplayName() + "&a!"));
                    nereusUser.setPrzodkow(new ItemStack(Material.AIR));
                    break;
                case 40:
                    if (nereusUser.getWiecznosci().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(nereusUser.getWiecznosci());
                    final int hp = Utils.getTagInt(nereusUser.getWiecznosci(), "val");
                    player.setMaxHealth(player.getMaxHealth() - (hp * 2));
                    rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().setDodatkowehp(rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getDodatkowehp() - hp);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(player.getUniqueId(), rpgcore.getBonusesManager().find(player.getUniqueId())));
                    player.sendMessage(Utils.format("&9&lNereus &8>> &aPomyslnie wyjales " + nereusUser.getWiecznosci().getItemMeta().getDisplayName() + "&a!"));
                    nereusUser.setWiecznosci(new ItemStack(Material.AIR));
                    break;
            }
            rpgcore.getNereusNPC().openNereusGUI(player);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataNereus(uuid, nereusUser));
        }
    }
}
