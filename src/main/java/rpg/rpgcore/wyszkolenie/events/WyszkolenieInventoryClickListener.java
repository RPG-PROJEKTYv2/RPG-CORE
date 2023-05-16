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
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.enums.WyszkolenieItems;
import rpg.rpgcore.wyszkolenie.objects.DrzewkoWyszkoleniaUser;
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

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE || item.getType() == Material.IRON_FENCE)
                return;

            final WyszkolenieUser user = this.rpgcore.getWyszkolenieManager().find(uuid);

            if (user.getPunkty() == 0 && !(slot == 40 || slot == 53)) return;

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
                    player.setMaxHealth(player.getMaxHealth() - (user.getHp() * 2));
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
            return;
        }

        if (title.contains("Drzewko Rozwoju ")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            final int lvl = this.rpgcore.getUserManager().find(uuid).getLvl();
            final WyszkolenieUser wyszkolenieUser = this.rpgcore.getWyszkolenieManager().find(uuid);
            final DrzewkoWyszkoleniaUser user = wyszkolenieUser.getDrzewkoWyszkoleniaUser();
            final Bonuses bonuses = this.rpgcore.getBonusesManager().find(uuid);
            
            switch (slot) {
                case 0:
                    player.closeInventory();
                    player.getInventory().addItem(WyszkolenieItems.getItem("I1", 1), WyszkolenieItems.getItem("I2", 1), WyszkolenieItems.getItem("I3", 1),
                            WyszkolenieItems.getItem("I4", 1), WyszkolenieItems.getItem("I5", 1), WyszkolenieItems.getItem("I6", 1), WyszkolenieItems.getItem("I7", 1),
                            WyszkolenieItems.getItem("I8", 1), WyszkolenieItems.getItem("I9", 1), WyszkolenieItems.getItem("I10", 1), WyszkolenieItems.getItem("I11", 1),
                            WyszkolenieItems.getItem("I12", 1), WyszkolenieItems.getItem("I13", 1), WyszkolenieItems.getItem("I14", 1), WyszkolenieItems.getItem("I15", 1),
                            WyszkolenieItems.getItem("I16", 1), WyszkolenieItems.getItem("I17", 1));
                    return;
                case 40:
                    final DrzewkoWyszkoleniaUser.D1 d1 = user.getD1();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(d1.getReqItem(), 1), 1))
                        return;
                    if (d1.isUnlocked()) {
                        if (d1.getUpgradeLvl() == d1.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(d1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        d1.setUpgradeLvl(d1.getUpgradeLvl() + 1);
                        d1.setSrDmg(d1.getSrDmg() + DoubleUtils.round(d1.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + DoubleUtils.round(d1.getPerLvl(), 2));
                        
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + d1.getName() + "&a!"));
                    } else {
                        if (lvl < d1.getReqLvl()) return;
                        
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(d1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        d1.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + d1.getName() + "&a!"));
                    }
                    break;
                case 31:
                    final DrzewkoWyszkoleniaUser.D2 d2 = user.getD2();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(d2.getReqItem(), 1), 1))
                        return;
                    if (d2.isUnlocked()) {
                        if (d2.getUpgradeLvl() == d2.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(d2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        d2.setUpgradeLvl(d2.getUpgradeLvl() + 1);
                        d2.setSzczescie(d2.getSzczescie() + d2.getPerLvl());
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + d2.getPerLvl());

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + d2.getName() + "&a!"));
                    } else {
                        if (lvl < d2.getReqLvl()) return;
                        if (!user.getD1().isUnlocked()) return;
                        
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(d2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        d2.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + d2.getName() + "&a!"));
                    }
                    break;
                // LEWO    
                case 30:
                    final DrzewkoWyszkoleniaUser.DL1 dl1 = user.getDl1();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dl1.getReqItem(), 1), 1))
                        return;
                    if (dl1.isUnlocked()) {
                        if (dl1.getUpgradeLvl() == dl1.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl1.setUpgradeLvl(dl1.getUpgradeLvl() + 1);
                        dl1.setKrytyk(dl1.getKrytyk() + DoubleUtils.round(dl1.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + DoubleUtils.round(dl1.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dl1.getName() + "&a!"));
                    } else {
                        if (lvl < dl1.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl1.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dl1.getName() + "&a!"));
                    }
                    break;
                case 29:
                    final DrzewkoWyszkoleniaUser.DL2 dl2 = user.getDl2();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dl2.getReqItem(), 1), 1))
                        return;
                    if (dl2.isUnlocked()) {
                        if (dl2.getUpgradeLvl() == dl2.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl2.setUpgradeLvl(dl2.getUpgradeLvl() + 1);
                        dl2.setSrDef(dl2.getSrDef() + DoubleUtils.round(dl2.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + DoubleUtils.round(dl2.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dl2.getName() + "&a!"));
                    } else {
                        if (lvl < dl2.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDl1().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl2.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dl2.getName() + "&a!"));
                    }
                    break;
                case 20:
                    final DrzewkoWyszkoleniaUser.DL3 dl3 = user.getDl3();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dl3.getReqItem(), 1), 1))
                        return;
                    if (dl3.isUnlocked()) {
                        if (dl3.getUpgradeLvl() == dl3.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl3.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl3.setUpgradeLvl(dl3.getUpgradeLvl() + 1);
                        dl3.setSilnyNaLudzi(dl3.getSilnyNaLudzi() + DoubleUtils.round(dl3.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + DoubleUtils.round(dl3.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dl3.getName() + "&a!"));
                    } else {
                        if (lvl < dl3.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDl1().isUnlocked() && user.getDl2().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl3.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl3.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dl3.getName() + "&a!"));
                    }
                    break;
                case 19:
                    final DrzewkoWyszkoleniaUser.DL4 dl4 = user.getDl4();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dl4.getReqItem(), 1), 1))
                        return;
                    if (dl4.isUnlocked()) {
                        if (dl4.getUpgradeLvl() == dl4.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl4.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl4.setUpgradeLvl(dl4.getUpgradeLvl() + 1);
                        dl4.setBlok(dl4.getBlok() + DoubleUtils.round(dl4.getPerLvl(), 2));
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + DoubleUtils.round(dl4.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dl4.getName() + "&a!"));
                    } else {
                        if (lvl < dl4.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDl1().isUnlocked() && user.getDl2().isUnlocked() &&
                                user.getDl3().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl4.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl4.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dl4.getName() + "&a!"));
                    }
                    break;
                case 18:
                    final DrzewkoWyszkoleniaUser.DL5 dl5 = user.getDl5();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dl5.getReqItem(), 1), 1))
                        return;
                    if (dl5.isUnlocked()) {
                        if (dl5.getUpgradeLvl() == dl5.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl5.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl5.setUpgradeLvl(dl5.getUpgradeLvl() + 1);
                        dl5.setHp(dl5.getHp() + dl5.getPerLvl());
                        player.setMaxHealth(player.getMaxHealth() + (dl5.getPerLvl() * 2));
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + dl5.getPerLvl());

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dl5.getName() + "&a!"));
                    } else {
                        if (lvl < dl5.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDl1().isUnlocked() && user.getDl2().isUnlocked() &&
                                user.getDl3().isUnlocked() && user.getDl4().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl5.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl5.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dl5.getName() + "&a!"));
                    }
                    break;
                case 10:
                    final DrzewkoWyszkoleniaUser.DL6 dl6 = user.getDl6();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dl6.getReqItem(), 1), 1))
                        return;
                    if (dl6.isUnlocked()) {
                        if (dl6.getUpgradeLvl() == dl6.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl6.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl6.setUpgradeLvl(dl6.getUpgradeLvl() + 1);
                        dl6.setSrDmg(dl6.getSrDmg() + DoubleUtils.round(dl6.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + DoubleUtils.round(dl6.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dl6.getName() + "&a!"));
                    } else {
                        if (lvl < dl6.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDl1().isUnlocked() && user.getDl2().isUnlocked() &&
                                user.getDl3().isUnlocked() && user.getDl4().isUnlocked() && user.getDl5().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dl6.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dl6.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dl6.getName() + "&a!"));
                    }
                    break;
                // GORA
                case 22:
                    final DrzewkoWyszkoleniaUser.DG1 dg1 = user.getDg1();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dg1.getReqItem(), 1), 1))
                        return;
                    if (dg1.isUnlocked()) {
                        if (dg1.getUpgradeLvl() == dg1.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dg1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dg1.setUpgradeLvl(dg1.getUpgradeLvl() + 1);
                        dg1.setOdpornoscNaMoby(dg1.getOdpornoscNaMoby() + DoubleUtils.round(dg1.getPerLvl(), 2));
                        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + DoubleUtils.round(dg1.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dg1.getName() + "&a!"));
                    } else {
                        if (lvl < dg1.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dg1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dg1.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dg1.getName() + "&a!"));
                    }
                    break;
                case 13:
                    final DrzewkoWyszkoleniaUser.DG2 dg2 = user.getDg2();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dg2.getReqItem(), 1), 1))
                        return;
                    if (dg2.isUnlocked()) {
                        if (dg2.getUpgradeLvl() == dg2.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dg2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dg2.setUpgradeLvl(dg2.getUpgradeLvl() + 1);
                        dg2.setPrzeszywka(dg2.getPrzeszywka() + DoubleUtils.round(dg2.getPerLvl(), 2));
                        bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + DoubleUtils.round(dg2.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dg2.getName() + "&a!"));
                    } else {
                        if (lvl < dg2.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDg1().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dg2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dg2.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dg2.getName() + "&a!"));
                    }
                    break;
                case 4:
                    final DrzewkoWyszkoleniaUser.DG3 dg3 = user.getDg3();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dg3.getReqItem(), 1), 1))
                        return;
                    if (dg3.isUnlocked()) {
                        if (dg3.getUpgradeLvl() == dg3.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dg3.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dg3.setUpgradeLvl(dg3.getUpgradeLvl() + 1);
                        dg3.setBlok(dg3.getBlok() + DoubleUtils.round(dg3.getPerLvl(), 2));
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + DoubleUtils.round(dg3.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dg3.getName() + "&a!"));
                    } else {
                        if (lvl < dg3.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDg1().isUnlocked() && user.getDg2().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dg3.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dg3.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dg3.getName() + "&a!"));
                    }
                    break;
                // PRAWO
                case 32:
                    final DrzewkoWyszkoleniaUser.DP1 dp1 = user.getDp1();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dp1.getReqItem(), 1), 1))
                        return;
                    if (dp1.isUnlocked()) {
                        if (dp1.getUpgradeLvl() == dp1.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp1.setUpgradeLvl(dp1.getUpgradeLvl() + 1);
                        dp1.setHp(dp1.getHp() + dp1.getPerLvl());
                        player.setMaxHealth(player.getMaxHealth() + (dp1.getPerLvl() * 2));
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + dp1.getPerLvl());

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dp1.getName() + "&a!"));
                    } else {
                        if (lvl < dp1.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp1.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp1.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dp1.getName() + "&a!"));
                    }
                    break;
                case 33:
                    final DrzewkoWyszkoleniaUser.DP2 dp2 = user.getDp2();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dp2.getReqItem(), 1), 1))
                        return;
                    if (dp2.isUnlocked()) {
                        if (dp2.getUpgradeLvl() == dp2.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp2.setUpgradeLvl(dp2.getUpgradeLvl() + 1);
                        dp2.setDodatkowyDmg(dp2.getDodatkowyDmg() + dp2.getPerLvl());
                        bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + dp2.getPerLvl());

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dp2.getName() + "&a!"));
                    } else {
                        if (lvl < dp2.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDp1().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp2.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp2.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dp2.getName() + "&a!"));
                    }
                    break;
                case 24:
                    final DrzewkoWyszkoleniaUser.DP3 dp3 = user.getDp3();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dp3.getReqItem(), 1), 1))
                        return;
                    if (dp3.isUnlocked()) {
                        if (dp3.getUpgradeLvl() == dp3.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp3.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp3.setUpgradeLvl(dp3.getUpgradeLvl() + 1);
                        dp3.setSilnyNaMoby(dp3.getSilnyNaMoby() + DoubleUtils.round(dp3.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + DoubleUtils.round(dp3.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dp3.getName() + "&a!"));
                    } else {
                        if (lvl < dp3.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDp1().isUnlocked() && user.getDp2().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp3.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp3.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dp3.getName() + "&a!"));
                    }
                    break;
                case 25:
                    final DrzewkoWyszkoleniaUser.DP4 dp4 = user.getDp4();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dp4.getReqItem(), 1), 1))
                        return;
                    if (dp4.isUnlocked()) {
                        if (dp4.getUpgradeLvl() == dp4.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp4.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp4.setUpgradeLvl(dp4.getUpgradeLvl() + 1);
                        dp4.setOdpornoscNaGraczy(dp4.getOdpornoscNaGraczy() + DoubleUtils.round(dp4.getPerLvl(), 2));
                        bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + DoubleUtils.round(dp4.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dp4.getName() + "&a!"));
                    } else {
                        if (lvl < dp4.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDp1().isUnlocked() && user.getDp2().isUnlocked() && user.getDp3().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp4.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp4.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dp4.getName() + "&a!"));
                    }
                    break;
                case 26:
                    final DrzewkoWyszkoleniaUser.DP5 dp5 = user.getDp5();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dp5.getReqItem(), 1), 1))
                        return;
                    if (dp5.isUnlocked()) {
                        if (dp5.getUpgradeLvl() == dp5.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp5.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp5.setUpgradeLvl(dp5.getUpgradeLvl() + 1);
                        dp5.setSzczescie(dp5.getSzczescie() + dp5.getPerLvl());
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + dp5.getPerLvl());

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dp5.getName() + "&a!"));
                    } else {
                        if (lvl < dp5.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDp1().isUnlocked() && user.getDp2().isUnlocked() && user.getDp3().isUnlocked() &&
                                user.getDp4().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp5.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp5.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dp5.getName() + "&a!"));
                    }
                    break;
                case 16:
                    final DrzewkoWyszkoleniaUser.DP6 dp6 = user.getDp6();
                    if (!player.getInventory().containsAtLeast(WyszkolenieItems.getItem(dp6.getReqItem(), 1), 1))
                        return;
                    if (dp6.isUnlocked()) {
                        if (dp6.getUpgradeLvl() == dp6.getMaxUpgradeLvl()) return;
                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp6.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp6.setUpgradeLvl(dp6.getUpgradeLvl() + 1);
                        dp6.setSrDef(dp6.getSrDef() + DoubleUtils.round(dp6.getPerLvl(), 2));
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + DoubleUtils.round(dp6.getPerLvl(), 2));

                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses));
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie ulepszyles &6" + dp6.getName() + "&a!"));
                    } else {
                        if (lvl < dp6.getReqLvl()) return;
                        if (!(user.getD1().isUnlocked() && user.getD2().isUnlocked() && user.getDp1().isUnlocked() && user.getDp2().isUnlocked() && user.getDp3().isUnlocked() &&
                                user.getDp4().isUnlocked() && user.getDp5().isUnlocked())) return;

                        player.getInventory().removeItem(new ItemBuilder(WyszkolenieItems.getItem(dp6.getReqItem(), 1)).setAmount(1).toItemStack().clone());
                        dp6.setUnlocked(true);
                        player.sendMessage(Utils.format("&3&lWyszkolenie &8>> &aPomyslnie odblokowales &6" + dp6.getName() + "&a!"));
                    }
                    break;
                default:
                    return;
            }
            rpgcore.getWyszkolenieManager().openDrzewkoWyszkoleniaGUI(player);
            wyszkolenieUser.save();
        }
    }
}
