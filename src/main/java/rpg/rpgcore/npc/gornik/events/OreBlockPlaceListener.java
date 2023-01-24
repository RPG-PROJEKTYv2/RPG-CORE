package rpg.rpgcore.npc.gornik.events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.enums.GornikOres;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.utils.Utils;

import java.util.Objects;

public class OreBlockPlaceListener implements Listener {
    private final RPGCORE rpgcore;

    public OreBlockPlaceListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(final BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        if (!e.getBlock().getWorld().getName().equalsIgnoreCase("kopalnia")) return;

        if (!e.getBlock().getType().toString().contains("ORE")) return;
        final Block block = e.getBlockPlaced();
        final int id = rpgcore.getOreManager().getHighestId();
        final int hp = Objects.requireNonNull(GornikOres.getOre(block.getType())).getMaxHp();
        final Ore ore = new Ore(id + 1, block.getType(), block.getLocation(), hp, hp);
        this.rpgcore.getOreManager().add(block.getLocation(), ore);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().addDataOreLocation(ore));
        e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano rude o id &6" + id + " &amateriale &6" + block.getType().name() + "&a i lokalizacji x:&6" + block.getLocation().getBlockX() + " &ay:&6" + block.getLocation().getBlockY() + " &az:&6" + block.getLocation().getBlockZ()));

    }
}
