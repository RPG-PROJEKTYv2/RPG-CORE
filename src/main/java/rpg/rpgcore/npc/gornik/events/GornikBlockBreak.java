package rpg.rpgcore.npc.gornik.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class GornikBlockBreak implements Listener {
    final List<UUID> diggingList = new ArrayList<>();
    private final RPGCORE rpgcore;

    public GornikBlockBreak(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        RPGCORE.getProtocolManager().addPacketListener(new PacketAdapter(RPGCORE.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player.getWorld().getName().equals("kopalnia")) {
                    if (event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                        if (player.getGameMode() == GameMode.CREATIVE) {
                            return;
                        }
                        if (event.getPacket().getPlayerDigTypes().getValues().get(0) == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK ||
                                event.getPacket().getPlayerDigTypes().getValues().get(0) == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                            diggingList.remove(player.getUniqueId());
                        } else if (event.getPacket().getPlayerDigTypes().getValues().get(0) == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                            diggingList.add(player.getUniqueId());
                        }
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockDamageEvent e) {

        final Player player = e.getPlayer();
        final Block block = e.getBlock();

        if (player.getWorld().getName().equals("kopalnia")) {
            e.setCancelled(true);

        }
        if (block.getType().equals(Material.STONE)) {
            if (e.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999, -1, false, false));
                digAnimation(player, e.getBlock(), 200);
            }


        }

    }



    public boolean isDigging(final UUID uuid) {
        return this.diggingList.contains(uuid);
    }

    public void digAnimation(final Player player, final Block block, final long totalTime) {
        long time = totalTime / 9;
        final UUID uuid = player.getUniqueId();

        if (!isDigging(player.getUniqueId())) {
            return;
        }
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            this.sendAnimationPacket(player, block, 0);
            if (!isDigging(uuid)) {
                this.sendAnimationPacket(player, block, 0);
                return;
            }
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                this.sendAnimationPacket(player, block, 1);
                if (!isDigging(uuid)) {
                    this.sendAnimationPacket(player, block, 0);
                    return;
                }
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    this.sendAnimationPacket(player, block, 2);
                    if (!isDigging(uuid)) {
                        this.sendAnimationPacket(player, block, 0);
                        return;
                    }
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        this.sendAnimationPacket(player, block, 3);
                        if (!isDigging(uuid)) {
                            this.sendAnimationPacket(player, block, 0);
                            return;
                        }
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            this.sendAnimationPacket(player, block, 4);
                            if (!isDigging(uuid)) {
                                this.sendAnimationPacket(player, block, 0);
                                return;
                            }
                            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                this.sendAnimationPacket(player, block, 5);
                                if (!isDigging(uuid)) {
                                    this.sendAnimationPacket(player, block, 0);
                                    return;
                                }
                                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                    this.sendAnimationPacket(player, block, 6);
                                    if (!isDigging(uuid)) {
                                        this.sendAnimationPacket(player, block, 0);
                                        return;
                                    }
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                        this.sendAnimationPacket(player, block, 7);
                                        if (!isDigging(uuid)) {
                                            this.sendAnimationPacket(player, block, 0);
                                            return;
                                        }
                                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                            this.sendAnimationPacket(player, block, 8);
                                            if (!isDigging(uuid)) {
                                                this.sendAnimationPacket(player, block, 0);
                                                return;
                                            }
                                            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                                this.sendAnimationPacket(player, block, 9);
                                                if (!isDigging(uuid)) {
                                                    this.sendAnimationPacket(player, block, 0);
                                                    return;
                                                }
                                                block.setType(Material.AIR);
                                                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                            }, time);
                                        }, time);
                                    }, time);
                                }, time);
                            }, time);
                        }, time);
                    }, time);
                }, time);
            }, time);
        }, 0L);

        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            if (!Bukkit.getWorld("kopalnia").getBlockAt(block.getLocation()).getType().equals(Material.AIR)) {
                PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(20000), new BlockPosition(block.getX(), block.getY(), block.getZ()), 0);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }, time + 1);
    }

    private void sendAnimationPacket(final Player player, final Block block, final int i) {
        player.sendMessage(i + "");
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(20000), new BlockPosition(block.getX(), block.getY(), block.getZ()), i);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
