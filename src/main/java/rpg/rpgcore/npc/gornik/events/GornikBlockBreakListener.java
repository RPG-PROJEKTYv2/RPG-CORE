package rpg.rpgcore.npc.gornik.events;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.enums.PickaxePriority;
import rpg.rpgcore.npc.gornik.ore.enums.Ores;
import rpg.rpgcore.npc.gornik.ore.objects.Ore;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.MobDropHelper;
import rpg.rpgcore.utils.Utils;

public class GornikBlockBreakListener implements Listener {
    private final RPGCORE rpgcore;

    public GornikBlockBreakListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getBlock().getLocation();

        if (!location.getWorld().getName().equals("Kopalnia")) return;

        e.setCancelled(true);
        if (rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).getRankUser().getRankType().getPriority() > RankType.HA.getPriority() && rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).isAdminCodeLogin() && player.getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(false);
            return;
        }
        if (player.getItemInHand() == null || !player.getItemInHand().getType().toString().contains("_PICKAXE")) return;
        if (!player.getItemInHand().getItemMeta().hasDisplayName()) return;
        if (!Utils.getTagString(player.getItemInHand(), "owner").equals(e.getPlayer().getName()) || !Utils.getTagString(player.getItemInHand(), "owner-uuid").equals(e.getPlayer().getUniqueId().toString())) {
            e.getPlayer().sendMessage(Utils.format("&6&lGornik &8» &cNie mozesz uzywac nie swojego sprzetu!"));
            return;
        }
        if (rpgcore.getOreManager().find(location) == null) return;
        if (e.getBlock().getType().equals(Material.BEDROCK)) return;

        final Ore ore = rpgcore.getOreManager().find(location);
        final Ores info = Ores.getOre(ore.getType());
        final int priority = PickaxePriority.getPickaxePriority(player.getItemInHand().getType());

        assert info != null;

        if (priority < info.getReqPriority()) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player, "&4&lBlad!", "&7Posiadasz za slaby kilof!", 5, 10));
            return;
        }
        ore.setCurrentHp(ore.getCurrentHp() - 1);
        if (ore.getCurrentHp() <= 0) {
            ore.breakOre();
            if (ChanceHelper.getChance(MobDropHelper.getDropChance(player.getUniqueId(), 5))) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0));
            }
            if (ChanceHelper.getChance(info.getFailChance())) {
                player.sendMessage(Utils.format("&cNiestety ruda ulegla zniszczeniu..."));
                return;
            }
            final ItemStack reward = info.getReward().clone();
            double chestDropChance = 1.0;
            double doubleDrop = 5.0;
            for (final ItemStack armor : player.getInventory().getArmorContents()) {
                if (armor != null && armor.getType().toString().contains("LEATHER") && armor.getItemMeta().hasDisplayName() && armor.getItemMeta().getDisplayName().contains("Gornika")) {
                    chestDropChance += Utils.getTagDouble(armor, "chestDrop");
                    doubleDrop += Utils.getTagDouble(armor, "doubleDrop");
                }
            }
            if (ChanceHelper.getChance(doubleDrop)) {
                reward.setAmount(reward.getAmount() * 2);
                player.sendMessage(Utils.format("&6&lGornik &8>> &aOtrzymales dodatkowa rude!"));
            }
            player.getInventory().addItem(reward.clone());
            rpgcore.getGornikNPC().updateExp(player.getItemInHand(), info.getExp());
            if (ChanceHelper.getChance(MobDropHelper.getDropChance(player.getUniqueId(), chestDropChance))) {
                rpgcore.getGornikNPC().getChestLocations().add(location);
                final Block block = location.getBlock();
                block.setType(Material.CHEST);
                final MaterialData data = block.getState().getData();
                final Directional directional = ((Directional) data);
                directional.setFacingDirection(Utils.getDirection(player).getOppositeFace());
                block.getState().setData(data);
                block.getState().update(true);
                player.sendMessage(Utils.format("&aZnalazles zakopana skrzynie!"));
                player.sendMessage(Utils.format("&8Kliknij na nia i zobacz co skrywa!"));
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> location.getBlock().setType(Material.BEDROCK), 240L);
            }
            return;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player, info.getReward().getItemMeta().getDisplayName(), "&e" + ore.getCurrentHp() + "&7/&e" + ore.getMaxHp(), 5, 10));
    }




    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (e.getClickedBlock() == null) return;
        if (!e.getClickedBlock().getType().equals(Material.CHEST)) return;
        if (!e.getClickedBlock().getLocation().getWorld().getName().equals("Kopalnia")) return;
        if (!rpgcore.getGornikNPC().getChestLocations().contains(e.getClickedBlock().getLocation())) return;
        e.setCancelled(true);
        rpgcore.getGornikNPC().getChestLocations().remove(e.getClickedBlock().getLocation());
        e.getPlayer().sendMessage(Utils.format("&aOtwierasz skrzynie..."));
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(
                new BlockPosition(
                        e.getClickedBlock().getLocation().getBlockX(),
                        e.getClickedBlock().getLocation().getBlockY(),
                        e.getClickedBlock().getLocation().getBlockZ()),
                CraftMagicNumbers.getBlock(e.getClickedBlock()), 1, 1);
        ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            e.getClickedBlock().setType(Material.BEDROCK);
            rpgcore.getGornikNPC().giveRandomChestReward(e.getPlayer());
        }, 20L);
    }
}
