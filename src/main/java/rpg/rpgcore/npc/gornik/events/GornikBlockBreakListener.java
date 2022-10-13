package rpg.rpgcore.npc.gornik.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.gornik.GornikUser;
import rpg.rpgcore.npc.gornik.enums.GornikOres;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

public class GornikBlockBreakListener implements Listener {
    private final RPGCORE rpgcore;

    public GornikBlockBreakListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMine(final BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals("Gornik")) {
            e.setCancelled(true);
            if (!String.valueOf(e.getPlayer().getItemInHand().getType()).contains("_PICKAXE")) {
                return;
            }
            if (!String.valueOf(e.getBlock().getType()).contains("_ORE")) {
                return;
            }
            if (!e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                e.getPlayer().getInventory().addItem(new ItemBuilder(Material.WOOD_PICKAXE).setName("&6Kilof Gornika").toItemStack().clone());
                return;
            }
            if (!rpgcore.getOreManager().isOre(e.getBlock().getLocation())) {
                System.out.println("nie ore");
                return;
            }
            final Ore ore = rpgcore.getOreManager().find(e.getBlock().getLocation());
            final GornikOres gornikOre = GornikOres.getOre(e.getBlock().getType());
            final int oreHp = ore.getHp();
            final int oreMaxHP = ore.getMaxHp();

            ore.setHp(oreHp - 1);
            rpgcore.getNmsManager().sendActionBar(e.getPlayer(), gornikOre.getName() + " &7HP: &c" + ore.getHp() + "&7/&c" + oreMaxHP);

            if (ore.getHp() == 0) {
                if (!ChanceHelper.getChance(gornikOre.getDropChance())) {
                    e.getPlayer().sendMessage(Utils.format("&cNiestety nie udalo Ci sie wydobyc tej rudy."));
                    e.getBlock().setType(Material.BEDROCK);
                    return;
                }
                final GornikObject object = rpgcore.getGornikNPC().find(e.getPlayer().getUniqueId());
                final GornikUser user = object.getGornikUser();
                switch (e.getBlock().getType()) {
                    case COAL_ORE:
                        if (user.getMission() == 1 || user.getMission() == 6 || user.getMission() == 13) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case IRON_ORE:
                        if (user.getMission() == 2) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case GOLD_ORE:
                        break;
                    case LAPIS_ORE:
                        break;
                    case EMERALD_ORE:
                        break;
                    case DIAMOND_ORE:
                        break;
                    case REDSTONE_ORE:
                        break;
                }
                
                
                e.getPlayer().getInventory().addItem(gornikOre.getDrop());
                e.getPlayer().setItemInHand(RPGCORE.getInstance().getGornikNPC().updateKilofExp(e.getPlayer().getItemInHand(), gornikOre.getExp()));
                e.getBlock().setType(Material.BEDROCK);
            }
        }
    }
}
