package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.BossBarUtil;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

public class NMSManager {

    private PacketPlayOutChat makeActionBar(final String str) {
        return new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), (byte) 2);
    }

    public PacketPlayOutTitle makeTitle(final String str, final int fadeIn, final int last, final int fadeOut) {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), fadeIn, last, fadeOut);
    }

    public PacketPlayOutTitle makeSubTitle(final String str, final int fadeIn, final int last, final int fadeOut) {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), fadeIn, last, fadeOut);
    }

    public void sendActionBar(final Player player, final String msg) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(makeActionBar(msg));
    }

    public void sendTitleAndSubTitle(final Player player, final PacketPlayOutTitle title, final PacketPlayOutTitle subtitle) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
    }

    public void sendMobInfo(final Player player, final LivingEntity entity) {
        if (BossBarUtil.hasBar(player.getUniqueId())) {
            BossBarUtil.updateBar(player, Utils.format(
                    entity.getCustomName() +
                            "&7: &c" +
                            DoubleUtils.round(entity.getHealth(), 2) +
                            "&7/&c" +
                            DoubleUtils.round(entity.getMaxHealth(), 0)
                    ), (float) (entity.getHealth() / entity.getMaxHealth()) * 100);
        } else {
            BossBarUtil.setBar(player, Utils.format(
                    entity.getCustomName() +
                            "&7: &c" +
                            DoubleUtils.round(entity.getHealth(), 2) +
                            "&7/&c" +
                            DoubleUtils.round(entity.getMaxHealth(), 0)
            ), (float) (entity.getHealth() / entity.getMaxHealth()) * 100);
        }
        RPGCORE.getInstance().getCooldownManager().giveBossBarCooldown(player.getUniqueId());
    }
}
