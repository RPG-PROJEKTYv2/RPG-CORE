package rpg.rpgcore.bao.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BAOEntityInteract implements Listener {

    private final RPGCORE rpgcore;

    public BAOEntityInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void baoPlayerInteractAtEntityEvent(final PlayerInteractAtEntityEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();


        if (e.getRightClicked().getType() != EntityType.ARMOR_STAND) {
            return;
        }

//        final ArmorStand as = (ArmorStand) e.getRightClicked();
//        player.sendMessage("x - " +as.getLocation().getX());
//        player.sendMessage("y - " +as.getLocation().getY());
//        player.sendMessage("z - " +as.getLocation().getZ());
//        player.sendMessage("yaw - " +as.getLocation().getYaw());
//        player.sendMessage("pitch - " +as.getLocation().getPitch());
//        player.sendMessage("HeadPose (X) - " + Math.toDegrees(as.getHeadPose().getX()));
//        player.sendMessage("HeadPose (Y) - " + Math.toDegrees(as.getHeadPose().getY()));
//        player.sendMessage("HeadPose (Z) - " + Math.toDegrees(as.getHeadPose().getZ()));
//        player.sendMessage("BodyPose (X) - " + Math.toDegrees(as.getBodyPose().getX()));
//        player.sendMessage("BodyPose (Y) - " + Math.toDegrees(as.getBodyPose().getY()));
//        player.sendMessage("BodyPose (Z) - " + Math.toDegrees(as.getBodyPose().getZ()));
//        player.sendMessage("LeftArmPose (X) - " + Math.toDegrees(as.getLeftArmPose().getX()));
//        player.sendMessage("LeftArmPose (Y) - " + Math.toDegrees(as.getLeftArmPose().getY()));
//        player.sendMessage("LeftArmPose (Z) - " + Math.toDegrees(as.getLeftArmPose().getZ()));
//        player.sendMessage("RightArmPose (X) - " + Math.toDegrees(as.getRightArmPose().getX()));
//        player.sendMessage("RightArmPose (Y) - " + Math.toDegrees(as.getRightArmPose().getY()));
//        player.sendMessage("RightArmPose (Z) - " + Math.toDegrees(as.getRightArmPose().getZ()));
//        player.sendMessage("LeftLegPose (X) - " + Math.toDegrees(as.getLeftLegPose().getX()));
//        player.sendMessage("LeftLegPose (Y) - " + Math.toDegrees(as.getLeftLegPose().getY()));
//        player.sendMessage("LeftLegPose (Z) - " + Math.toDegrees(as.getLeftLegPose().getZ()));
//        player.sendMessage("RightLegPose (X) - " + Math.toDegrees(as.getRightLegPose().getX()));
//        player.sendMessage("RightLegPose (Y) - " + Math.toDegrees(as.getRightLegPose().getY()));
//        player.sendMessage("RightLegPose (Z) - " + Math.toDegrees(as.getRightLegPose().getZ()));
//        player.sendMessage("Is Visable - " + as.isVisible());
//        player.sendMessage("Is Marker - " + as.isMarker());
//        player.sendMessage("Is Small - " + as.isSmall());
//        player.sendMessage("Is Custom Name Visable - " + as.isCustomNameVisible());
//        player.sendMessage("Custom Name - " + Utils.format(as.getCustomName()));


        if (!rpgcore.getBaoManager().checkIfClickedEntityIsInList(e.getRightClicked().getLocation())) return;

        e.setCancelled(true);
        if (rpgcore.getUserManager().find(uuid).getLvl() < 74) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c75 &7poziom, zeby uzywac &6STOLU MAGII"));
            return;
        }
        rpgcore.getBaoManager().openMainGUI(player, false);
    }
}
