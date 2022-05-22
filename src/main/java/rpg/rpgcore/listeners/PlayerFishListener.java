package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
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

            int currentMission = rpgcore.getRybakNPC().getPlayerCurrentMission(player.getUniqueId());
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

                    if (is.getItemMeta().getDisplayName().contains("Sledz")) {
                        if (currentMission == 1) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Dorsz")) {
                        if (currentMission == 2) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Losos")) {
                        if (currentMission == 3) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Krasnopiorka")) {
                        if (currentMission == 4) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Dorsz Czarny")) {
                        if (currentMission == 5) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Dorada")) {
                        if (currentMission == 6) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Cierniczek")) {
                        if (currentMission == 7) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Fladra")) {
                        if (currentMission == 8) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Karas")) {
                        if (currentMission == 9) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Karp")) {
                        if (currentMission == 10) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }


                    if (is.getItemMeta().getDisplayName().contains("Leszcz")) {
                        if (currentMission == 11) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Makrela")) {
                        if (currentMission == 12) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Mintaj")) {
                        if (currentMission == 13) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Okon")) {
                        if (currentMission == 14) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Plotka")) {
                        if (currentMission == 15) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Skrzynia Rybaka")) {
                        if (currentMission == 16) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Niesamowity przedmiot rybacki")) {
                        if (currentMission == 17) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }


                    break;
                case "mob":
                    String mob = rpgcore.getRybakNPC().getMob();
                    switch (mob) {
                        case "nurek":
                            rpgcore.getRybakNPC().spawnNurekGlebinowy(player, e.getHook().getLocation());

                            if (currentMission == 18) {
                                rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), 1);
                                return;
                            }

                            break;
                        case "wladca":
                            rpgcore.getRybakNPC().spawnPodwodnyWladca(player, e.getHook().getLocation());

                            if (currentMission == 18) {
                                rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), 1);
                                return;
                            }

                            break;
                    }
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
