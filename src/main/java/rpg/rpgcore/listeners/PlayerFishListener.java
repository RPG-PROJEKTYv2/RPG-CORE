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
    private final RandomItems<ItemStack> fish = new RandomItems<>();
    private final RandomItems<String> doubleDrops = new RandomItems<>();
    private final RandomItems<String> chestDrop = new RandomItems<>();
    private final RandomItems<String> mob = new RandomItems<>();


    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {

        load();

        e.setExpToDrop(0);
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.getCaught().remove();

            final Player player = e.getPlayer();

            firstRoll.clear();

            double mobChance = Double.parseDouble(Utils.removeColor(player.getItemInHand().getItemMeta().getLore().get(8)).replace("-", "").replace("Szansa na wylowienie podwodnego stworzenia:", "").replace(" ", "").replace("%", "").trim());

            firstRoll.add(1, "mob");

            /*firstRoll.add(0.7 - (0.1 + (mobChance / 100)), "fish");
            firstRoll.add(0.1 + (mobChance / 100), "mob");
            firstRoll.add(0.3, "empty");*/

            String result = firstRoll.next();

            player.sendMessage(result);
            switch (result) {
                case "empty":
                    player.sendMessage(Utils.format(Utils.RYBAK + "&cNiestety ryba zerwala sie z linki"));
                    return;
                case "fish":
                    ItemStack is = rpgcore.getRybakNPC().getDrop();
                    double caseDrop = Double.parseDouble(Utils.removeColor(player.getItemInHand().getItemMeta().getLore().get(7)).replace("-", "").replace("Szansa na skrzynie rybaka:", "").replace(" ", "").replace("%", "").trim());
                    double doubleDrop = Double.parseDouble(Utils.removeColor(player.getItemInHand().getItemMeta().getLore().get(6)).replace("-", "").replace("Szansa na podwojne wylowienie:", "").replace(" ", "").replace("%", "").trim());

                    chestDrop.clear();
                    chestDrop.add(caseDrop/100, "chest");
                    chestDrop.add(1 - (caseDrop/100), "empty");

                    if (chestDrop.next().equals("chest")) {
                        is = rpgcore.getRybakNPC().getChest();
                    }

                    doubleDrops.clear();
                    doubleDrops.add(doubleDrop/100, "double");
                    doubleDrops.add(1.0 - (doubleDrop/100), "single");


                    if (doubleDrops.next().equals("double")) {
                        is.setAmount(2);
                    } else {
                        is.setAmount(1);
                    }


                    player.getInventory().addItem(is);
                    player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + is.getAmount() + "x " + is.getItemMeta().getDisplayName()));

                    int currentMission = rpgcore.getRybakNPC().getPlayerCurrentMission(player.getUniqueId());
                    if (is.getItemMeta().getDisplayName().contains("Karas")) {
                        if (currentMission == 1) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                        }
                    }

                    break;
                case "mob":
                    rpgcore.getRybakNPC().spawnNurekGlebinowy(player, e.getHook().getLocation());
                    break;
            }



            rpgcore.getRybakNPC().addStatsToRod(player, 2500);
            rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), rpgcore.getPlayerManager().getPlayerOsRybak(player.getUniqueId()) + 1);
            return;
        }
    }

    public void load() {
        mob.add(1, "zombie");
    }
}
