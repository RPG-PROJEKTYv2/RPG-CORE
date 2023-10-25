package rpg.rpgcore.npc.nereus.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.alchemik.objects.AlchemikUser;
import rpg.rpgcore.npc.nereus.objects.NereusUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.AlchemikItems;

public class NereusInteractListener implements Listener {
    private final RPGCORE rpgcore = RPGCORE.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!e.getItem().hasItemMeta()) return;
        if (!e.getItem().getItemMeta().hasDisplayName()) return;

        final Player player = e.getPlayer();

        final NereusUser user = this.rpgcore.getNereusNPC().find(player.getUniqueId());

        if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < Utils.getTagInt(e.getItem(), "lvl")) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
            return;
        }

        switch (Utils.getTagString(e.getItem(), "relikt")) {
            case "Potegi":
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                if (e.getItem().getAmount() > 1) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                    return;
                }
                if (!user.getPotegi().getType().equals(Material.AIR)) {
                    player.sendMessage(Utils.format("&9&lNereus &8>> &cPosiadasz juz zalozony Relikt Potegi!"));
                    return;
                }
                user.setPotegi(e.getItem().clone());
                player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                break;
            case "Wiecznosci":
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                if (e.getItem().getAmount() > 1) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                    return;
                }
                if (!user.getWiecznosci().getType().equals(Material.AIR)) {
                    player.sendMessage(Utils.format("&9&lNereus &8>> &cPosiadasz juz zalozony Relikt Wiecznosci!"));
                    return;
                }
                final int hp = Utils.getTagInt(e.getItem(), "val");
                player.setMaxHealth(player.getMaxHealth() + (hp * 2));
                rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().setDodatkowehp(rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getDodatkowehp() + hp);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(player.getUniqueId(), rpgcore.getBonusesManager().find(player.getUniqueId())));
                user.setWiecznosci(e.getItem().clone());
                player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                break;
            case "Starozytnosci":
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                if (e.getItem().getAmount() > 1) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                    return;
                }
                if (!user.getStarozytnosci().getType().equals(Material.AIR)) {
                    player.sendMessage(Utils.format("&9&lNereus &8>> &cPosiadasz juz zalozony Relikt Starozytnosci!"));
                    return;
                }
                user.setStarozytnosci(e.getItem().clone());
                player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                break;
            case "Przodkow":
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                if (e.getItem().getAmount() > 1) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                    return;
                }
                if (!user.getPrzodkow().getType().equals(Material.AIR)) {
                    player.sendMessage(Utils.format("&9&lNereus &8>> &cPosiadasz juz zalozony Relikt Przodkow!"));
                    return;
                }
                user.setPrzodkow(e.getItem().clone());
                player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                break;
            default:
                return;
        }
        player.sendMessage(Utils.format("&9&lNereus &8>> &aPomyslnie zalozyles " + e.getItem().getItemMeta().getDisplayName() + " &a!"));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataNereus(user.getUuid(), user));
    }
}
