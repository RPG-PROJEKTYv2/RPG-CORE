package rpg.rpgcore.npc.alchemik.events;

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
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.alchemik.objects.AlchemikUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.AlchemikItems;

public class AlchemikInteractListener implements Listener {
    private final RPGCORE rpgcore = RPGCORE.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!e.getItem().hasItemMeta()) return;
        if (!e.getItem().getItemMeta().hasDisplayName()) return;

        final Player player = e.getPlayer();

        final AlchemikUser user = this.rpgcore.getAlchemikNPC().find(player.getUniqueId());

        int krysztalLvl = Utils.getTagInt(e.getItem(), "krysztalLvl");

        final String name = Utils.removeColor(e.getItem().getItemMeta().getDisplayName());
        if (name.equals("Alchemiczny Podrecznik")) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (user.getLvl() == 50) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cOsiagnales juz maksymalny &5Poziom Alchemickii&c!"));
                return;
            }
            user.incrementProgress(player);
            player.getInventory().removeItem(new ItemBuilder(AlchemikItems.I2.getItemStack().clone()).setAmount(1).toItemStack());
        } else if (name.equals("Alchemicki Krysztal Potegi +" + krysztalLvl)) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                return;
            }
            if (!user.getPotegi().getType().equals(Material.AIR)) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cPosiadasz juz zalozony &c&lAlchemicki Krysztal Potegi!"));
                return;
            }
            if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < Utils.getTagInt(e.getItem(), "lvl")) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
                return;
            }
            if (user.getLvl() < Utils.getTagInt(e.getItem(), "alch-lvl")) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wymaganego &5Poziomu Alchemickiego&c, zeby zalozyc ten przedmiot!"));
                return;
            }
            user.setPotegi(e.getItem().clone());

            final double srDmg = Utils.getTagDouble(e.getItem(), "srDmg");
            final int dodatkowe = Utils.getTagInt(e.getItem(), "dodatkowe");

            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
            player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie zalozono &c&lAlchemicki Krysztal Potegi &8(&f" + srDmg + "&8/&f" + dodatkowe + "&8)"));
        } else if (name.equals("Alchemicki Krysztal Obrony +" + krysztalLvl)) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                return;
            }
            if (!user.getObrony().getType().equals(Material.AIR)) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cPosiadasz juz zalozony &9&lAlchemicki Krysztal Obrony!"));
                return;
            }
            if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < Utils.getTagInt(e.getItem(), "lvl")) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
                return;
            }
            if (user.getLvl() < Utils.getTagInt(e.getItem(), "alch-lvl")) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wymaganego &5Poziomu Alchemickiego&c, zeby zalozyc ten przedmiot!"));
                return;
            }
            user.setObrony(e.getItem().clone());

            final double srDef = Utils.getTagDouble(e.getItem(), "srDef");
            final double blok = Utils.getTagDouble(e.getItem(), "blok");

            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
            player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie zalozono &9&lAlchemicki Krysztal Obrony &8(&f" + srDef + "&8/&f" + blok + "&8)"));
        } else if (name.equals("Alchemicki Krysztal Potworow +" + krysztalLvl)) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                return;
            }
            if (!user.getPotworow().getType().equals(Material.AIR)) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cPosiadasz juz zalozony &2&lAlchemicki Krysztal Potworow!"));
                return;
            }
            if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < Utils.getTagInt(e.getItem(), "lvl")) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
                return;
            }
            if (user.getLvl() < Utils.getTagInt(e.getItem(), "alch-lvl")) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wymaganego &5Poziomu Alchemickiego&c, zeby zalozyc ten przedmiot!"));
                rpgcore.getAlchemikNPC().openAlchemickieKrysztaly(player);
                return;
            }
            user.setPotworow(e.getItem().clone());

            final double silnyNaMoby = Utils.getTagDouble(e.getItem(), "silnyNaMoby");
            final double defNaMoby = Utils.getTagDouble(e.getItem(), "defNaMoby");

            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
            player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie zalozono &2&lAlchemicki Krysztal Potworow &8(&f" + silnyNaMoby + "&8/&f" + defNaMoby + "&8)"));
        } else if (name.equals("Alchemicki Krysztal Ludzi +" + krysztalLvl)) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                return;
            }
            if (!user.getLudzi().getType().equals(Material.AIR)) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cPosiadasz juz zalozony &4&lAlchemicki Krysztal Ludzi!"));
                return;
            }
            if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < Utils.getTagInt(e.getItem(), "lvl")) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
                return;
            }
            if (user.getLvl() < Utils.getTagInt(e.getItem(), "alch-lvl")) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wymaganego &5Poziomu Alchemickiego&c, zeby zalozyc ten przedmiot!"));
                return;
            }
            user.setLudzi(e.getItem().clone());

            final double silnyNaLudzi = Utils.getTagDouble(e.getItem(), "silnyNaLudzi");
            final double defNaLudzi = Utils.getTagDouble(e.getItem(), "defNaLudzi");

            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
            player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie zalozono &4&lAlchemicki Krysztal Ludzi &8(&f" + silnyNaLudzi + "&8/&f" + defNaLudzi + "&8)"));
        } else return;

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataAlchemik(user.getUuid(), user));
    }
}
