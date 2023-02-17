package rpg.rpgcore.wyszkolenie.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.util.UUID;

public class WyszkolenieInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public WyszkolenieInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) return;

        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.contains("Status Wyszkolenia ")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE || item.getType() == Material.IRON_FENCE) return;

            final WyszkolenieUser user = this.rpgcore.getWyszkolenieManager().find(uuid);

            if (user.getPunkty() == 0 && !(slot == 40 || slot == 53)) {
                user.setTotalPoints(50);
                return;
            }

            final Bonuses bonuses = this.rpgcore.getBonusesManager().find(uuid);

            if (user.isMaxed() && slot != 40) return;

            switch (slot) {
                case 19:
                    if (user.getSrDmg() == 10) return;
                    user.setSrDmg(user.getSrDmg() + 1);
                    user.setPunkty(user.getPunkty() - 1);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 1);
                    break;
                case 20:
                    if (user.getSrDef() == 10) return;
                    user.setSrDef(user.getSrDef() + 1);
                    user.setPunkty(user.getPunkty() - 1);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 1);
                    break;
                case 21:
                    if (user.getKryt() == 10) return;
                    user.setKryt(user.getKryt() + 1);
                    user.setPunkty(user.getPunkty() - 1);
                    bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + 1);
                    break;
                case 23:
                    if (user.getSzczescie() == 10) return;
                    user.setSzczescie(user.getSzczescie() + 1);
                    user.setPunkty(user.getPunkty() - 1);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + 1);
                    break;
                case 24:
                    if (user.getBlok() == 5) return;
                    user.setBlok(user.getBlok() + 1);
                    user.setPunkty(user.getPunkty() - 1);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 1);
                    break;
                case 25:
                    if (user.getHp() == 5) return;
                    user.setHp(user.getHp() + 1);
                    user.setPunkty(user.getPunkty() - 1);
                    bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + 1);
                    player.setMaxHealth(player.getMaxHealth() + 2);
                    break;
                case 40:
                    if (!user.isMaxed()) return;
                    player.closeInventory();
                    rpgcore.getWyszkolenieManager().openDrzewkoWyszkoleniaGUI(player);
                    return;
                case 53:
                    if (user.hasCooldown()) return;
                    final User serverUser = rpgcore.getUserManager().find(uuid);
                    if (serverUser.getKasa() < 1_000_000) {
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &cNie posiadasz wystarczajacej ilosci pieniedzy zeby to zrobic."));
                        return;
                    }
                    serverUser.setKasa(serverUser.getKasa() - 1_000_000);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, serverUser));
                    user.reset(bonuses);
                    player.closeInventory();
                    player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie zresetowales poziom swojego wyszkolenia!"));
                    return;
                default:
                    return;
            }
            user.save();
            player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie rozwinieto &6" + item.getItemMeta().getDisplayName() + "&a!"));
            this.rpgcore.getWyszkolenieManager().openWyszkolenieGUI(player);
        }
    }
}
