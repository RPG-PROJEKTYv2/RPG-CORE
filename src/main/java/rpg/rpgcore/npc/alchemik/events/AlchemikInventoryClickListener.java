package rpg.rpgcore.npc.alchemik.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.alchemik.enums.KrysztalUpgrades;
import rpg.rpgcore.npc.alchemik.objects.AlchemikUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.AlchemikItems;

import java.util.UUID;

public class AlchemikInventoryClickListener implements Listener {

    private final RPGCORE rpgcore = RPGCORE.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory inv = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(inv.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Alchemik")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            switch (slot) {
                case 10:
                    rpgcore.getAlchemikNPC().openAlchemikCrafting(player);
                    return;
                case 16:
                    rpgcore.getAlchemikNPC().openAlchemickieKrysztaly(player);
                    return;
                case 22:
                    rpgcore.getAlchemikNPC().openUlepszanieKrysztalow(player);
                    return;
                default:
                    return;
            }
        }

        if (title.equals("Alchemik » Craftingi")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final User user = rpgcore.getUserManager().find(uuid);
            ItemStack reward;
            switch (slot) {
                case 11:
                    if (!(player.getInventory().containsAtLeast(AlchemikItems.I1.getItemStack(), 32) &&
                            player.getInventory().containsAtLeast(GlobalItem.I50.getItemStack(), 8) &&
                            player.getInventory().containsAtLeast(GlobalItem.I_KAMIENBAO.getItemStack(), 5) &&
                            player.getInventory().containsAtLeast(GlobalItem.I10.getItemStack(), 8) &&
                            user.getKasa() >= 35_000_000)) {
                        player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wystarczajacej ilosci przedmiotow!"));
                        player.closeInventory();
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(32).toItemStack(),
                            new ItemBuilder(GlobalItem.I50.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(5).toItemStack(),
                            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(8).toItemStack());
                    user.setKasa(user.getKasa() - 35_000_000);
                    reward = AlchemikItems.getAlchemicznyKrysztal("Potegi").clone();
                    player.getInventory().addItem(reward);

                    Bukkit.getServer().broadcastMessage(Utils.format("&5&lAlchemik &8>> &dGracz &5" + player.getName() + " &dwytworzyl &c&lAlchemicki Krysztal Potegi " +
                            "&8(&f" + Utils.getTagDouble(reward, "srDmg") + "&8/&f" + Utils.getTagInt(reward, "dodatkowe") + "&8)"));

                    break;
                case 12:
                    if (!(player.getInventory().containsAtLeast(AlchemikItems.I1.getItemStack(), 32) &&
                            player.getInventory().containsAtLeast(GlobalItem.I50.getItemStack(), 8) &&
                            player.getInventory().containsAtLeast(GlobalItem.I_KAMIENBAO.getItemStack(), 5) &&
                            player.getInventory().containsAtLeast(GlobalItem.I10.getItemStack(), 8) &&
                            user.getKasa() >= 35_000_000)) {
                        player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wystarczajacej ilosci przedmiotow!"));
                        player.closeInventory();
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(32).toItemStack(),
                            new ItemBuilder(GlobalItem.I50.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(5).toItemStack(),
                            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(8).toItemStack());
                    user.setKasa(user.getKasa() - 35_000_000);
                    reward = AlchemikItems.getAlchemicznyKrysztal("Obrony").clone();
                    player.getInventory().addItem(reward);

                    Bukkit.getServer().broadcastMessage(Utils.format("&5&lAlchemik &8>> &dGracz &5" + player.getName() + " &dwytworzyl &9&lAlchemicki Krysztal Obrony " +
                            "&8(&f" + Utils.getTagDouble(reward, "srDef") + "&8/&f" + Utils.getTagDouble(reward, "blok") + "&8)"));

                    break;
                case 14:
                    if (!(player.getInventory().containsAtLeast(AlchemikItems.I1.getItemStack(), 48) &&
                            player.getInventory().containsAtLeast(GlobalItem.I50.getItemStack(), 10) &&
                            player.getInventory().containsAtLeast(GlobalItem.I_KAMIENBAO.getItemStack(), 6) &&
                            player.getInventory().containsAtLeast(GlobalItem.I10.getItemStack(), 10) &&
                            user.getKasa() >= 45_000_000)) {
                        player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wystarczajacej ilosci przedmiotow!"));
                        player.closeInventory();
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(48).toItemStack(),
                            new ItemBuilder(GlobalItem.I50.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(6).toItemStack(),
                            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(10).toItemStack());
                    user.setKasa(user.getKasa() - 45_000_000);
                    reward = AlchemikItems.getAlchemicznyKrysztal("Potworow").clone();
                    player.getInventory().addItem(reward);

                    Bukkit.getServer().broadcastMessage(Utils.format("&5&lAlchemik &8>> &dGracz &5" + player.getName() + " &dwytworzyl &2&lAlchemicki Krysztal Potworow " +
                            "&8(&f" + Utils.getTagDouble(reward, "silnyNaMoby") + "&8/&f" + Utils.getTagDouble(reward, "defNaMoby") + "&8)"));

                    break;
                case 15:
                    if (!(player.getInventory().containsAtLeast(AlchemikItems.I1.getItemStack(), 48) &&
                            player.getInventory().containsAtLeast(GlobalItem.I50.getItemStack(), 10) &&
                            player.getInventory().containsAtLeast(GlobalItem.I_KAMIENBAO.getItemStack(), 6) &&
                            player.getInventory().containsAtLeast(GlobalItem.I10.getItemStack(), 10) &&
                            user.getKasa() >= 45_000_000)) {
                        player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wystarczajacej ilosci przedmiotow!"));
                        player.closeInventory();
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(48).toItemStack(),
                            new ItemBuilder(GlobalItem.I50.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GlobalItem.I_KAMIENBAO.getItemStack().clone()).setAmount(6).toItemStack(),
                            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(10).toItemStack());
                    user.setKasa(user.getKasa() - 45_000_000);
                    reward = AlchemikItems.getAlchemicznyKrysztal("Ludzi").clone();
                    player.getInventory().addItem(reward);

                    Bukkit.getServer().broadcastMessage(Utils.format("&5&lAlchemik &8>> &dGracz &5" + player.getName() + " &dwytworzyl &4&lAlchemicki Krysztal Ludzi " +
                            "&8(&f" + Utils.getTagDouble(reward, "silnyNaLudzi") + "&8/&f" + Utils.getTagDouble(reward, "defNaLudzi") + "&8)"));

                    break;
                default:
                    return;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
        }

        if (title.equals("Alchemik » Twoje Krysztaly")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final AlchemikUser user = rpgcore.getAlchemikNPC().find(uuid);
            switch (slot) {
                case 11:
                    if (user.getPotegi().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(user.getPotegi().clone());
                    final double srDmg = Utils.getTagDouble(user.getPotegi(), "srDmg");
                    final int dodatkowe = Utils.getTagInt(user.getPotegi(), "dodatkowe");

                    user.setPotegi(new ItemStack(Material.AIR));
                    player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie wyjeto &c&lAlchemicki Krysztal Potegi &8(&f" + srDmg + "&8/&f" + dodatkowe + "&8)"));
                    break;
                case 12:
                    if (user.getObrony().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(user.getObrony().clone());
                    final double srDef = Utils.getTagDouble(user.getObrony(), "srDef");
                    final double blok = Utils.getTagDouble(user.getObrony(), "blok");

                    user.setObrony(new ItemStack(Material.AIR));
                    player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie wyjeto &9&lAlchemicki Krysztal Obrony &8(&f" + srDef + "&8/&f" + blok + "&8)"));
                    break;
                case 14:
                    if (user.getPotworow().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(user.getPotworow().clone());
                    final double silnyNaMoby = Utils.getTagDouble(user.getPotworow(), "silnyNaMoby");
                    final double defNaMoby = Utils.getTagDouble(user.getPotworow(), "defNaMoby");

                    user.setPotworow(new ItemStack(Material.AIR));
                    player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie wyjeto &2&lAlchemicki Krysztal Potworow &8(&f" + silnyNaMoby + "&8/&f" + defNaMoby + "&8)"));
                    break;
                case 15:
                    if (user.getLudzi().getType().equals(Material.AIR)) return;
                    player.getInventory().addItem(user.getLudzi().clone());
                    final double silnyNaLudzi = Utils.getTagDouble(user.getLudzi(), "silnyNaLudzi");
                    final double defNaLudzi = Utils.getTagDouble(user.getLudzi(), "defNaLudzi");

                    user.setLudzi(new ItemStack(Material.AIR));
                    player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie wyjeto &4&lAlchemicki Krysztal Ludzi &8(&f" + silnyNaLudzi + "&8/&f" + defNaLudzi + "&8)"));
                    break;
                default:
                    return;
            }
            rpgcore.getAlchemikNPC().openAlchemickieKrysztaly(player);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataAlchemik(user.getUuid(), user));

        }

        if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Alchemik » Ulepszanie") && inv == player.getInventory()) {
            if (item == null || item.getType().equals(Material.AIR)) return;
            if (Utils.getTagString(item, "alchKrysztal").isEmpty()) return;
            if (Utils.getTagInt(item, "krysztalLvl") >= 9) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cTen krysztal osiagnal juz swoj pelen potencjal!"));
                return;
            }
            if (!player.getOpenInventory().getTopInventory().getItem(11).getType().equals(Material.IRON_FENCE)) {
                e.setCancelled(true);
                return;
            }
            if (item.getAmount() > 1) {
                player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie mozesz ulepszyc wiecej niz 1 przedmiotu. Rozdziel je!"));
                return;
            }
            e.setCancelled(true);
            final String krysztal = Utils.getTagString(item, "alchKrysztal");
            final int nextLvl = Utils.getTagInt(item, "krysztalLvl") + 1;
            player.getOpenInventory().getTopInventory().setItem(11, item.clone());
            player.getOpenInventory().getTopInventory().setItem(13,
                    rpgcore.getAlchemikNPC().getUlepszanieItem(krysztal, nextLvl, player));
            player.getOpenInventory().getTopInventory().setItem(15, rpgcore.getAlchemikNPC().getNextKrysztalTier(item, krysztal, nextLvl));
            player.getInventory().setItem(slot, new ItemStack(Material.AIR));
            return;
        }

        if (title.equals("Alchemik » Ulepszanie")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final User user = rpgcore.getUserManager().find(uuid);
            if (slot == 13) {
                if (inv.getItem(11).getType().equals(Material.IRON_FENCE)) return;
                final KrysztalUpgrades info = KrysztalUpgrades.find(Utils.getTagString(inv.getItem(11), "alchKrysztal"), Utils.getTagInt(inv.getItem(11), "krysztalLvl") + 1);
                if (info == null) return;
                if (user.getKasa() < info.getReqMoney()) {
                    player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wystarczajacej ilosci pieniedzy!"));
                    return;
                }
                for (final ItemStack stack : info.getReqItemList()) {
                    if (!player.getInventory().containsAtLeast(stack, stack.getAmount())) {
                        player.sendMessage(Utils.format("&5&lAlchemik &8>> &cNie posiadasz wystarczajacej ilosci przedmiotow!"));
                        return;
                    }
                }
                info.getReqItemList().forEach(stack -> player.getInventory().removeItem(stack));
                user.setKasa(user.getKasa() - info.getReqMoney());

                rpgcore.getAlchemikNPC().upgradePlayerKrysztal(inv.getItem(11), info, player);
                inv.setItem(11, new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na Alchemicki Krysztal").toItemStack());
                player.closeInventory();
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                return;
            }

            if (slot == 11 && !item.getType().equals(Material.IRON_FENCE)) {
                player.getInventory().addItem(item.clone());
                inv.setItem(11, new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na Alchemicki Krysztal").toItemStack());
                inv.setItem(13, rpgcore.getAlchemikNPC().getUlepszanieItem("", -1, player));
                inv.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack());
            }
        }
    }
}
