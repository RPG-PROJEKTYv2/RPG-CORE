package rpg.rpgcore.npc.rybak;

import org.bukkit.Bukkit;
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {
        e.setExpToDrop(0);
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.getCaught().remove();
            final RandomItems<String> firstRoll = new RandomItems<>();
            final Player player = e.getPlayer();

            double mobChance = Double.parseDouble(Utils.removeColor(player.getItemInHand().getItemMeta().getLore().get(8)).replace("-", "").replace("Szansa na wylowienie podwodnego stworzenia:", "").replace(" ", "").replace("%", "").trim());


            firstRoll.add(0.7 - (0.1 + (mobChance / 100)), "fish");
            firstRoll.add(0.1 + (mobChance / 100), "mob");
            firstRoll.add(0.3, "empty");

            String result = firstRoll.next();

            int currentMission = rpgcore.getRybakNPC().getPlayerCurrentMission(player.getUniqueId());
            switch (result) {
                case "empty":
                    player.sendMessage(Utils.format(Utils.RYBAK + "&cNiestety ryba zerwala sie z linki"));
                    return;
                case "fish":
                    final RandomItems<String> chestDrop = new RandomItems<>();
                    final RandomItems<String> doubleDrops = new RandomItems<>();

                    ItemStack is = rpgcore.getRybakNPC().getDrop();
                    final double caseDrop = Double.parseDouble(Utils.removeColor(player.getItemInHand().getItemMeta().getLore().get(7)).replace("-", "").replace("Szansa na skrzynie rybaka:", "").replace(" ", "").replace("%", "").trim());
                    final double doubleDrop = Double.parseDouble(Utils.removeColor(player.getItemInHand().getItemMeta().getLore().get(6)).replace("-", "").replace("Szansa na podwojne wylowienie:", "").replace(" ", "").replace("%", "").trim());

                    player.sendMessage("Chest - " + caseDrop);
                    player.sendMessage("Double - " + doubleDrop);
                    player.sendMessage("chest #1 - " + (caseDrop/100));
                    player.sendMessage("chest #2 - " + (1 - (caseDrop/100)));
                    player.sendMessage("double #1 - " + (doubleDrop/100));
                    player.sendMessage("double #2 - " + (1 - (doubleDrop/100)));

                    chestDrop.add((caseDrop/100), "chest");
                    chestDrop.add((1 - (caseDrop/100)), "empty");

                    String chestResult = chestDrop.next();
                    if (chestResult.equals("chest")) {
                        is = rpgcore.getRybakNPC().getChest();
                    }

                    doubleDrops.add(doubleDrop/100, "double");
                    doubleDrops.add(1.0 - (doubleDrop/100), "single");

                    String doubleResult = doubleDrops.next();

                    is.setAmount(1);

                    if (doubleResult.equals("double")) {
                        is.setAmount(2);
                    }


                    player.getInventory().addItem(is);
                    player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + is.getAmount() + "x " + is.getItemMeta().getDisplayName()));

                    if (is.getItemMeta().getDisplayName().contains("Sledz")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 5);
                        if (currentMission == 1 || currentMission == 35) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Dorsz")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 5);
                        if (currentMission == 2 || currentMission == 36) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Losos")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 5);
                        if (currentMission == 3 || currentMission == 37) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Krasnopiorka")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 7);
                        if (currentMission == 5 || currentMission == 38) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Dorsz Czarny")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 7);
                        if (currentMission == 6 || currentMission == 39) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Dorada")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 7);
                        if (currentMission == 7 || currentMission == 40) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Cierniczek")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 9);
                        if (currentMission == 11) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Fladra")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 9);
                        if (currentMission == 12) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Karas")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 12);
                        if (currentMission == 18) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Karp")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 12);
                        if (currentMission == 19) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }


                    if (is.getItemMeta().getDisplayName().contains("Leszcz")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 12);
                        if (currentMission == 23) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Makrela")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 15);
                        if (currentMission == 24) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Mintaj")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 15);
                        if (currentMission == 26) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Okon")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 20);
                        if (currentMission == 30) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Plotka")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 20);
                        if (currentMission == 31) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Skrzynia Rybaka")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 25);
                        if (currentMission == 15 || currentMission == 29) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (is.getItemMeta().getDisplayName().contains("Niesamowity przedmiot rybacki")) {
                        rpgcore.getRybakNPC().addStatsToRod(player, 100);
                        if (currentMission == 16 || currentMission == 21 || currentMission == 32 || currentMission == 44) {
                            rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                            return;
                        }
                    }

                    if (currentMission == 42) {
                        rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), is.getAmount());
                        return;
                    }
                    break;
                case "mob":
                    String mob = rpgcore.getRybakNPC().getMob();
                    switch (mob) {
                        case "nurek":
                            rpgcore.getRybakNPC().spawnNurekGlebinowy(player, e.getHook().getLocation());

                            rpgcore.getRybakNPC().addStatsToRod(player, 100);
                            if (currentMission == 9) {
                                rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), 1);
                                return;
                            }

                            break;
                        case "wladca":
                            rpgcore.getRybakNPC().spawnPodwodnyWladca(player, e.getHook().getLocation());

                            rpgcore.getRybakNPC().addStatsToRod(player, 350);
                            if (currentMission == 18 || currentMission == 28 || currentMission == 45) {
                                rpgcore.getRybakNPC().updatePlayerPostep(player.getUniqueId(), 1);
                                return;
                            }

                            break;
                    }
                    break;
            }



            rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), rpgcore.getPlayerManager().getPlayerOsRybak(player.getUniqueId()) + 1);
        }
    }
}
