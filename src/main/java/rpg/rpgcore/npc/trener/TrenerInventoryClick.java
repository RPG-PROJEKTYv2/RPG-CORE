package rpg.rpgcore.npc.trener;

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
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class TrenerInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TrenerInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void trenerInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.contains("Trener - Punkty: ")) {
            final TrenerObject object = rpgcore.getTrenerNPC().find(uuid);
            final TrenerUser user = object.getTrenerUser();
            e.setCancelled(true);
            if (clickedSlot != 8 && user.getPoints() <= 0) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.TRENER + "&cNie posiadasz zadnych punktow do rozdania"));
                return;
            }

            if (clickedSlot != 8 && rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
                return;
            }

            if (clickedSlot == 8 && clickedItem.getType().equals(Material.BEACON) && (user.getSredniDmg() == 20 && user.getSredniDef() == 20 && user.getDodatkoweHp() == 10 && user.getBlokCiosu() == 10 && user.getSzczescie() == 20
                    && user.getSilnyNaLudzi() == 20 && user.getDefNaLudzi() == 20 && user.getKryt() == 10)) {
                player.sendMessage(Utils.format("&cTa opcja jest na razie nie dostępna!"));
                player.closeInventory();
                return;
            }
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
            final BonusesUser bonusesUser = bonuses.getBonusesUser();
            switch (clickedSlot) {
                case 0:
                    if (user.getSredniDmg() == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setSredniDmg(user.getSredniDmg() + 1);
                    bonusesUser.setSrednieobrazenia(bonusesUser.getSrednieobrazenia() + 1);
                    break;
                case 1:
                    if (user.getSredniDef() == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setSredniDef(user.getSredniDef() + 1);
                    bonusesUser.setSredniadefensywa(bonusesUser.getSredniadefensywa() + 1);
                    break;
                case 2:
                    if (user.getDodatkoweHp() == 10) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setDodatkoweHp(user.getDodatkoweHp() + 1);
                    bonusesUser.setDodatkowehp(bonusesUser.getDodatkowehp() + 1);
                    break;
                case 3:
                    if (user.getBlokCiosu() == 10) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setBlokCiosu(user.getBlokCiosu() + 1);
                    bonusesUser.setBlokciosu(bonusesUser.getBlokciosu() + 1);
                    break;
                case 4:
                    if (user.getSzczescie() == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setSzczescie(user.getSzczescie() + 1);
                    bonusesUser.setSzczescie(bonusesUser.getSzczescie() + 1);
                    break;
                case 5:
                    if (user.getSilnyNaLudzi() == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setSilnyNaLudzi(user.getSilnyNaLudzi() + 1);
                    bonusesUser.setSilnynaludzi(bonusesUser.getSilnynaludzi() + 1);
                    break;
                case 6:
                    if (user.getDefNaLudzi() == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setDefNaLudzi(user.getDefNaLudzi() + 1);
                    bonusesUser.setDefnaludzi(bonusesUser.getDefnaludzi() + 1);
                    break;
                case 7:
                    if (user.getKryt() == 10) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    user.setKryt(user.getKryt() + 1);
                    bonusesUser.setSzansanakryta(bonusesUser.getSzansanakryta() + 1);
                    break;
                case 8:
                    if (rpgcore.getUserManager().find(uuid).getKasa() < 5000000) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cNie posiadasz tylu pieniedzy, zeby zresetowac swoje statystyki"));
                        player.closeInventory();
                        return;
                    }
                    bonusesUser.setSrednieobrazenia(bonusesUser.getSrednieobrazenia() - user.getSredniDmg());
                    bonusesUser.setSredniadefensywa(bonusesUser.getSredniadefensywa() - user.getSredniDef());
                    bonusesUser.setDodatkowehp(bonusesUser.getDodatkowehp() - user.getDodatkoweHp());
                    bonusesUser.setBlokciosu(bonusesUser.getBlokciosu() - user.getBlokCiosu());
                    bonusesUser.setSzczescie(bonusesUser.getSzczescie() - user.getSzczescie());
                    bonusesUser.setSilnynaludzi(bonusesUser.getSilnynaludzi() - user.getSilnyNaLudzi());
                    bonusesUser.setDefnaludzi(bonusesUser.getDefnaludzi() - user.getDefNaLudzi());
                    bonusesUser.setSzansanakryta(bonusesUser.getSzansanakryta() - user.getKryt());
                    user.reset();
                    rpgcore.getUserManager().find(uuid).setKasa(rpgcore.getUserManager().find(uuid).getKasa() - 5000000);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, rpgcore.getUserManager().find(uuid)));
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.TRENER + "&aPomyslnie zresetowałes/as swoj postep"));
                    return;
            }
            user.setPoints(user.getPoints() - 1);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataTrener(object.getId(), object);
                rpgcore.getMongoManager().saveDataUser(uuid, rpgcore.getUserManager().find(uuid));
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getCooldownManager().givePlayerTrenerCooldown(uuid);
            player.closeInventory();
            player.sendMessage(Utils.format(Utils.TRENER + "&aPomyslnie zwiekszyles/as " + clickedItem.getItemMeta().getDisplayName() + " &ao 1 %"));
            rpgcore.getTrenerNPC().openTrenerGUI(player);

        }
    }
}
