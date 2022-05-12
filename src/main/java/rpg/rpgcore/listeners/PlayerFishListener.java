package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.Utils;

public class PlayerFishListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerFishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
    private final RandomItems<String> firstRoll = new RandomItems<>();
    private final RandomItems<String> mob = new RandomItems<>();


    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {

        load();
        rpgcore.getRybakNPC().loadRybakDrops();

        e.setExpToDrop(0);
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.getCaught().remove();
            String result = firstRoll.next();


            final Player player = e.getPlayer();

            player.sendMessage(result);
            switch (result) {
                case "empty":
                    player.sendMessage(Utils.format(Utils.RYBAK + "&cNiestety ryba zerwala sie z linki"));
                    return;
                case "fish":
                    ItemStack is = rpgcore.getRybakNPC().getDrop();
                    player.getInventory().addItem(is);
                    player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + is.getAmount() + "x " + is.getItemMeta().getDisplayName()));
                    break;
                case "mob":

                    Location hook = e.getHook().getLocation();

                    Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        Entity mob = Bukkit.getWorld(player.getWorld().getName()).spawnEntity(hook, EntityType.ZOMBIE);
                        mob.setCustomName(Utils.format("&3Potwor z Glebin"));
                        player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + mob.getCustomName()));

                        Vector lookingAt = player.getLocation().getDirection().normalize();

                        double pushX = player.getLocation().getDirection().normalize().getX() * -2;
                        double pushY = player.getLocation().getDirection().normalize().getY() * -2;
                        double pushZ = player.getLocation().getDirection().normalize().getZ() * -2;


                        Vector push = new Vector(pushX, pushY, pushZ);

                        mob.setVelocity(push);


                    }, 1L);
                    break;
            }



            rpgcore.getRybakNPC().addStatsToRod(player, 2500);
            rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), rpgcore.getPlayerManager().getPlayerOsRybak(player.getUniqueId()) + 1);
            return;
        }
    }

    public void load() {

        firstRoll.add(0.375, "fish");
        firstRoll.add(0.375, "mob");
        firstRoll.add(0.25, "empty");

        ItemBuilder item = new ItemBuilder(Material.RAW_FISH);


        //fish.add(1, item.setName("&6Rybka").toItemStack());

        mob.add(1, "zombie");
    }
}
