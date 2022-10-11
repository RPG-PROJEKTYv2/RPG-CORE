package rpg.rpgcore.npc.gornik.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

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
            if (!e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                e.getPlayer().getInventory().addItem(new ItemBuilder(Material.WOOD_PICKAXE).setName("&6Kilof Gornika").toItemStack().clone());
                return;
            }
            if (!rpgcore.getOreManager().isOre(e.getBlock().getLocation())) {
                System.out.println("nie ore");
                return;
            }
            final Ore ore = rpgcore.getOreManager().find(e.getBlock().getLocation());
            final int oreHp = ore.getHp();
            final int oreMaxHP = ore.getMaxHp();

            e.setCancelled(true);
            ore.setHp(oreHp - 1);
            rpgcore.getNmsManager().sendActionBar(e.getPlayer(), this.getOreName(e.getBlock().getType()) + " &7HP: &c" + ore.getHp() + "&7/&c" + oreMaxHP);

            if (ore.getHp() == 0) {
                e.setCancelled(true);
                e.getPlayer().getInventory().addItem(getDrop(e.getBlock().getType()));
                e.getBlock().setType(Material.BEDROCK);
            }
        }
    }

    private String getOreName(final Material material) {
        switch (material) {
            case COAL_ORE:
                return "&1&lRuda Mroku";
            case IRON_ORE:
                return "&b&lRuda Cyrkonu";
            case GOLD_ORE:
                return "&e&lRuda Blasku";
            case DIAMOND_ORE:
                return "&1&lRuda Tanzanitu";
            case EMERALD_ORE:
                return "&2&lRuda Jadeitu";
            case REDSTONE_ORE:
                return "&c&lRuda Rubinu";
            case LAPIS_ORE:
                return "&3&lRuda Szafiru";
            default:
                return "Unknown!";
        }
    }

    private ItemStack getDrop(final Material material) {
        switch (material) {
            case COAL_ORE:
                return GornikItems.getItem("G1", 1);
            case IRON_ORE:
                return GornikItems.getItem("G2", 1);
            case GOLD_ORE:
                return GornikItems.getItem("G3", 1);
            case DIAMOND_ORE:
                return GornikItems.getItem("G6", 1);
            case EMERALD_ORE:
                return GornikItems.getItem("G5", 1);
            case REDSTONE_ORE:
                return GornikItems.getItem("G7", 1);
            case LAPIS_ORE:
                return GornikItems.getItem("G4", 1);
            default:
                return null;
        }
    }
}
