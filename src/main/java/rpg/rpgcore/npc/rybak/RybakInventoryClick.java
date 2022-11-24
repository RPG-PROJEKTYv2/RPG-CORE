package rpg.rpgcore.npc.rybak;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RybakInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public RybakInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rybakInventoryClick(final InventoryClickEvent e) {

        /*final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.contains("Menu Rybaka")) {
            e.setCancelled(true);
            if (clickedSlot == 10) {
                rpgcore.getRybakNPC().openRybakKampania(player);
                return;
            }
            if (clickedSlot == 16) {
                rpgcore.getRybakNPC().openRybakSklep(player);
                return;
            }
        }

        if (clickedInventoryTitle.contains("Sklep Rybacki")) {
            e.setCancelled(true);
            //                  KUPOWANIE WEDKI             \\
            if (clickedSlot == 0) {
                final double cenaWedki = Double.parseDouble(Utils.removeColor(clickedItem.getItemMeta().getLore().get(clickedItem.getItemMeta().getLore().size() - 1)).replace("Cena:", "").replace(" ", "").replace("$", "").trim());
                if (rpgcore.getUserManager().find(playerUUID).getKasa() < cenaWedki) {
                    player.sendMessage(Utils.format(Utils.RYBAK + "&cCzy ty probujesz mnie oszukac? Nie stac cie na moja wedke"));
                    player.closeInventory();
                    return;
                }

                rpgcore.getUserManager().find(playerUUID).setKasa(rpgcore.getUserManager().find(playerUUID).getKasa() - cenaWedki);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(playerUUID, rpgcore.getUserManager().find(playerUUID)));

                player.getInventory().addItem(rpgcore.getRybakNPC().givePlayerRod(player));
                player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie zakupiles moja wedka. &6Udanych lowow!"));
                player.closeInventory();
                return;
            }


            //                  SPRZEDAWANIE RYBEK             \\

            ItemStack is = clickedItem.clone();
            ItemMeta im = is.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(Utils.format("&8&oChyba &8&n&orybak&r &8&otego potrzebuje"));
            im.setDisplayName(clickedItem.getItemMeta().getDisplayName());
            im.setLore(lore);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            im.addItemFlags(ItemFlag.HIDE_DESTROYS);
            is.setItemMeta(im);

            int amount = 0;

            if (e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.SHIFT_LEFT)) amount = 1;
            if (e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                for (ItemStack is2 : player.getInventory().getContents()) {
                    if (is2 != null && is2.getType() != null && is2.getItemMeta().getDisplayName() != null) {
                        if (is2.getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName())) {
                            amount += is2.getAmount();
                        }
                    }
                }
            }

            is.setAmount(amount);
            lore.clear();


            if (player.getInventory().containsAtLeast(is, amount)) {
                player.getInventory().removeItem(is);

                lore = clickedItem.getItemMeta().getLore();
                double cena = 0.0;
                for (String s : lore) {
                    if (s.contains("Cena: ")) {
                        cena = Double.parseDouble(Utils.removeColor(s).replace("Cena: ", "").replace(" ", "").replace("$", "").trim());
                        break;
                    }
                }
                rpgcore.getUserManager().find(playerUUID).setKasa(rpgcore.getUserManager().find(playerUUID).getKasa() + (cena * amount));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(playerUUID, rpgcore.getUserManager().find(playerUUID)));
                player.sendMessage(Utils.format(Utils.RYBAK + "&aSprzedales &6&o" + amount + " &a&orybek za &6&o" + (cena * amount) + " &a$"));
                player.closeInventory();
                final int mission = rpgcore.getRybakNPC().find(playerUUID).getRybakUser().getMission();
                if (mission == 41 || mission == 43) {
                    rpgcore.getRybakNPC().find(playerUUID).getRybakUser().setProgress(rpgcore.getRybakNPC().find(playerUUID).getRybakUser().getProgress() + amount);
                   rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(playerUUID, rpgcore.getRybakNPC().find(playerUUID)));
                }
                return;
            }
            player.sendMessage(Utils.format(Utils.RYBAK + "&cNie masz tego rodzaju rybek w swoim ekwipunku"));
            player.closeInventory();
            return;
        }

        if (clickedInventoryTitle.contains("Kampania Rybacka")) {
            e.setCancelled(true);

            if (!clickedItem.getType().equals(Material.BOOK_AND_QUILL)){
                return;
            }
            final RybakUser rybakUser = rpgcore.getRybakNPC().find(playerUUID).getRybakUser();
            final int currentMission = rybakUser.getMission();

            if (clickedSlot < currentMission) {
                return;
            }

            final String[] misja = rpgcore.getRybakNPC().getMisja(clickedSlot).split(";");

            if (currentMission + 1 == 4) {
                final ItemBuilder sledz = new ItemBuilder(Material.RAW_FISH, 256);
                final ItemBuilder dorsz = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);
                final ItemBuilder losos = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);

                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                sledz.setName("&6Sledz").setLore(lore).hideFlag();
                dorsz.setName("&6Dorsz").setLore(lore).hideFlag();
                losos.setName("&6Losos").setLore(lore).hideFlag();


                if (player.getInventory().containsAtLeast(sledz.toItemStack(), 256) && player.getInventory().containsAtLeast(dorsz.toItemStack(), 256)
                        && player.getInventory().containsAtLeast(losos.toItemStack(), 256)) {
                    player.getInventory().removeItem(sledz.toItemStack());
                    player.getInventory().removeItem(dorsz.toItemStack());
                    player.getInventory().removeItem(losos.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 8) {
                final ItemBuilder krasnopiorka = new ItemBuilder(Material.RAW_FISH, 256, (short) 2);
                final ItemBuilder dorszCzarny = new ItemBuilder(Material.COOKED_FISH, 256, (short) 1);
                final ItemBuilder dorada = new ItemBuilder(Material.RAW_FISH, 256);

                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                krasnopiorka.setName("&6Krasnopiorka").setLore(lore);
                dorszCzarny.setName("&6Dorsz Czarny").setLore(lore);
                dorada.setName("&6Dorada").setLore(lore);


                if (player.getInventory().containsAtLeast(krasnopiorka.toItemStack(), 256) && player.getInventory().containsAtLeast(dorszCzarny.toItemStack(), 256)
                        && player.getInventory().containsAtLeast(dorada.toItemStack(), 256)) {
                    player.getInventory().removeItem(krasnopiorka.toItemStack());
                    player.getInventory().removeItem(dorszCzarny.toItemStack());
                    player.getInventory().removeItem(dorada.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 13) {
                final ItemBuilder cierniczek = new ItemBuilder(Material.COOKED_FISH, 512);


                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                cierniczek.setName("&6Cierniczek").setLore(lore);


                //player.getInventory().addItem(sledz.toItemStack(), dorsz.toItemStack(), losos.toItemStack());

                if (player.getInventory().containsAtLeast(cierniczek.toItemStack(), 512)) {
                    player.getInventory().removeItem(cierniczek.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 14) {
                final ItemBuilder fladra = new ItemBuilder(Material.RAW_FISH, 512, (short) 3);


                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                fladra.setName("&6Fladra").setLore(lore);

                if (player.getInventory().containsAtLeast(fladra.toItemStack(), 512)) {
                    player.getInventory().removeItem(fladra.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 20) {
                final ItemBuilder chest = new ItemBuilder(Material.CHEST, 64);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&n&oKliknij&r &8&ozeby otworzyc skrzynie i otrzymac przedmioty");

                chest.setName("&a&lSkrzynia Rybaka").setLore(lore);

                if (player.getInventory().containsAtLeast(chest.toItemStack(), 64)) {
                    player.getInventory().removeItem(chest.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 22) {
                final ItemBuilder karas = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);
                final ItemBuilder karp = new ItemBuilder(Material.COOKED_FISH, 256);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                karas.setName("&6Karas").setLore(lore);
                karp.setName("&6Karp").setLore(lore);

                if (player.getInventory().containsAtLeast(karas.toItemStack(), 256) && player.getInventory().containsAtLeast(karp.toItemStack(), 256)) {
                    player.getInventory().removeItem(karas.toItemStack());
                    player.getInventory().removeItem(karp.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 27) {
                final ItemBuilder leszcz = new ItemBuilder(Material.COOKED_FISH, 256, (short) 1);
                final ItemBuilder makrela = new ItemBuilder(Material.COOKED_FISH, 256);
                final ItemBuilder mintaj = new ItemBuilder(Material.COOKED_FISH, 256);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                leszcz.setName("&6Leszcz").setLore(lore);
                makrela.setName("&6Makrela").setLore(lore);
                mintaj.setName("&6Mintaj").setLore(lore);

                if (player.getInventory().containsAtLeast(leszcz.toItemStack(), 256) && player.getInventory().containsAtLeast(makrela.toItemStack(), 256)
                        && player.getInventory().containsAtLeast(mintaj.toItemStack(), 256)) {
                    player.getInventory().removeItem(leszcz.toItemStack());
                    player.getInventory().removeItem(makrela.toItemStack());
                    player.getInventory().removeItem(mintaj.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 33) {
                final ItemBuilder okon = new ItemBuilder(Material.RAW_FISH, 256, (short) 3);
                final ItemBuilder plotka = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                okon.setName("&6Okon").setLore(lore);
                plotka.setName("&6Plotka").setLore(lore);

                if (player.getInventory().containsAtLeast(okon.toItemStack(), 256) && player.getInventory().containsAtLeast(plotka.toItemStack(), 256)) {
                    player.getInventory().removeItem(okon.toItemStack());
                    player.getInventory().removeItem(plotka.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                    return;
                }
            }

            if (rybakUser.getProgress() < Integer.parseInt(misja[1])) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rybakUser.getProgress()) + " " + misja[2]));
                return;
            }

            rybakUser.setMission(currentMission + 1);
            rybakUser.setProgress(0);
            rpgcore.getRybakNPC().addReward(playerUUID, misja[3], misja[4]);
            rpgcore.getRybakNPC().addReward(playerUUID, misja[5], misja[6]);




            player.closeInventory();
            if (clickedSlot == 44) {
                for (int i=0; i<20;i++) {
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.RYBAK + "&7Gracz &6" + player.getName() + " &7ukonczyl wlasnie moja &4&lCALA KAMPANIE.&6Gratulacje!"));
                }
                return;
            }
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.RYBAK + "&7Gracz &6" + player.getName() + " &7ukonczyl wlasnie moja &6" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName().replace("Misja #", "")) + " &7misje.&6Gratulacje!"));
        }*/

    }

}
