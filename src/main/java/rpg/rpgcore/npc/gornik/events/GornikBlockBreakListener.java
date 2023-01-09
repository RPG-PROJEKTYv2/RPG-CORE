package rpg.rpgcore.npc.gornik.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.gornik.GornikUser;
import rpg.rpgcore.npc.gornik.enums.GornikOres;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class GornikBlockBreakListener implements Listener {
    private final RPGCORE rpgcore;

    public GornikBlockBreakListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMine(final BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals("Gornik")) {
            e.setCancelled(true);
            if (rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).getRankUser().isHighStaff() && rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).isAdminCodeLogin()) {
                e.setCancelled(false);
                return;
            }
            if (!String.valueOf(e.getPlayer().getItemInHand().getType()).contains("_PICKAXE")) {
                return;
            }
            if (!Utils.getTagString(e.getPlayer().getItemInHand(), "owner").equals(e.getPlayer().getName())) {
                e.getPlayer().sendMessage(Utils.format("&6&lGornik &8» &cNie mozesz uzywac nie swojego sprzetu!"));
                return;
            }
            if (!String.valueOf(e.getBlock().getType()).contains("_ORE")) {
                return;
            }
            if (!e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                return;
            }
            if (!rpgcore.getOreManager().isOre(e.getBlock().getLocation())) {
                return;
            }
            final Ore ore = rpgcore.getOreManager().find(e.getBlock().getLocation());

            switch (ore.getOreMaterial()) {
                case EMERALD_ORE:
                case LAPIS_ORE:
                    if (e.getPlayer().getItemInHand().getType() != Material.GOLD_PICKAXE && e.getPlayer().getItemInHand().getType() != Material.IRON_PICKAXE
                            && e.getPlayer().getItemInHand().getType() != Material.DIAMOND_PICKAXE) {
                        e.getPlayer().sendMessage(Utils.format("&6&lGornik &8>> &7Wydaje mi sie ze tym rupieciem tego nie wykopiesz."));
                        e.getPlayer().sendMessage(Utils.format("&8Zeby wydobyc te rude potrzebujesz co najmniej zlotego kilofa."));
                        return;
                    }
                    break;
                case DIAMOND_ORE:
                    if (e.getPlayer().getItemInHand().getType() != Material.IRON_PICKAXE
                            && e.getPlayer().getItemInHand().getType() != Material.DIAMOND_PICKAXE) {
                        e.getPlayer().sendMessage(Utils.format("&6&lGornik &8>> &7Wydaje mi sie ze tym rupieciem tego nie wykopiesz."));
                        e.getPlayer().sendMessage(Utils.format("&8Zeby wydobyc te rude potrzebujesz co najmniej zelaznego kilofa."));
                        return;
                    }
                    break;
                case REDSTONE_ORE:
                case GLOWING_REDSTONE_ORE:
                    if (e.getPlayer().getItemInHand().getType() != Material.DIAMOND_PICKAXE) {
                        e.getPlayer().sendMessage(Utils.format("&6&lGornik &8>> &7Wydaje mi sie ze tym rupieciem tego nie wykopiesz."));
                        e.getPlayer().sendMessage(Utils.format("&8Zeby wydobyc te rude potrzebujesz co najmniej diamentowego kilofa."));
                        return;
                    }
                    break;
            }

            final GornikOres gornikOre = GornikOres.getOre(e.getBlock().getType());
            final int oreHp = ore.getHp();
            final int oreMaxHP = ore.getMaxHp();

            ore.setHp(oreHp - 1);
            rpgcore.getNmsManager().sendActionBar(e.getPlayer(), gornikOre.getName() + " &7HP: &c" + ore.getHp() + "&7/&c" + oreMaxHP);

            if (ore.getHp() == 0) {
                if (!ChanceHelper.getChance(gornikOre.getDropChance())) {
                    e.getPlayer().sendMessage(Utils.format("&cNiestety nie udalo Ci sie wydobyc tej rudy."));
                    ore.setOreMaterial(Material.BEDROCK);
                    e.getBlock().setType(Material.BEDROCK);
                    return;
                }
                final Bonuses bonuses = rpgcore.getBonusesManager().find(e.getPlayer().getUniqueId());
                final GornikObject object = rpgcore.getGornikNPC().find(e.getPlayer().getUniqueId());
                final GornikUser user = object.getGornikUser();
                final double szczescie = ((double) bonuses.getBonusesUser().getSzczescie()) / 100;
                if (ChanceHelper.getChance(0.025 + (0.025 * szczescie / 100))) {
                    e.getPlayer().getInventory().addItem(GlobalItem.getItem("I21", 1));
                    e.getPlayer().sendMessage(Utils.format("&8[&2+&8] &fx1 &8Skrzynia Gornika"));
                    if (user.getMission() == 8 || user.getMission() == 15 || user.getMission() == 22 || user.getMission() == 27) {
                        user.setProgress(user.getProgress() + 1);
                    }
                }
                switch (e.getBlock().getType()) {
                    case COAL_ORE:
                        if (user.getMission() == 1 || user.getMission() == 6) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case IRON_ORE:
                        if (user.getMission() == 2 || user.getMission() == 13) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case GOLD_ORE:
                        if (user.getMission() == 5) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case LAPIS_ORE:
                        if (user.getMission() == 7) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case EMERALD_ORE:
                        if (user.getMission() == 11 || user.getMission() == 19) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case DIAMOND_ORE:
                        if (user.getMission() == 21) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                    case GLOWING_REDSTONE_ORE:
                    case REDSTONE_ORE:
                        if (user.getMission() == 24) {
                            user.setProgress(user.getProgress() + 1);
                        }
                        break;
                }

                if (RPGCORE.getInstance().getMagazynierNPC().find(e.getPlayer().getUniqueId()).getMissions().getSelectedMission() == 8) {
                    RPGCORE.getInstance().getMagazynierNPC().find(e.getPlayer().getUniqueId()).getMissions().setProgress(RPGCORE.getInstance().getMagazynierNPC().find(e.getPlayer().getUniqueId()).getMissions().getProgress() + 1);
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMagazynier(e.getPlayer().getUniqueId(), RPGCORE.getInstance().getMagazynierNPC().find(e.getPlayer().getUniqueId())));
                }
                e.getPlayer().getInventory().addItem(gornikOre.getDrop());
                e.getPlayer().setItemInHand(RPGCORE.getInstance().getGornikNPC().updateKilofExp(e.getPlayer().getItemInHand(), gornikOre.getExp()));
                ore.setOreMaterial(Material.BEDROCK);
                e.getBlock().setType(Material.BEDROCK);
            }
        }
    }
}
