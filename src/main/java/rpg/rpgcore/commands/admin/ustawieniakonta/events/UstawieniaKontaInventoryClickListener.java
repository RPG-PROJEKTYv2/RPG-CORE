package rpg.rpgcore.commands.admin.ustawieniakonta.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.commands.admin.ustawieniakonta.UstawieniaKontaManager;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerObject;
import rpg.rpgcore.npc.lesnik.LesnikObject;
import rpg.rpgcore.npc.lowca.LowcaObject;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikObject;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.objects.RybakObject;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikObject;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.DrzewkoWyszkoleniaUser;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.util.UUID;

public class UstawieniaKontaInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public UstawieniaKontaInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.contains("Konto - ")) {
            final UstawieniaKontaManager ustawieniaKontaManager = this.rpgcore.getUstawieniaKontaManager();
            e.setCancelled(true);
            switch (slot) {
                case 10:
                    ustawieniaKontaManager.clearInventory(player, this.getUUIDFromTitle(title), e.getCurrentItem().clone());
                    player.closeInventory();
                    return;
                case 11:
                    ustawieniaKontaManager.clearEnderchest(player, this.getUUIDFromTitle(title), e.getCurrentItem().clone());
                    player.closeInventory();
                    return;
                case 12:
                    ustawieniaKontaManager.openMagazyny(player, this.getUUIDFromTitle(title));
                    return;
                case 13:
                    ustawieniaKontaManager.openOsiagniecia(player, this.getUUIDFromTitle(title));
                    return;
                case 14:
                    ustawieniaKontaManager.openMisje(player, this.getUUIDFromTitle(title));
                    return;
                case 15:
                    ustawieniaKontaManager.openLvlIExp(player, this.getUUIDFromTitle(title));
                    return;
                case 16:
                    ustawieniaKontaManager.openKasaIHS(player, this.getUUIDFromTitle(title));
            }
            return;
        }
        if (title.contains("Magazynny - ")) {
            final UstawieniaKontaManager ustawieniaKontaManager = this.rpgcore.getUstawieniaKontaManager();
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() != Material.CHEST) return;
            if (!Utils.getTagBoolean(e.getCurrentItem(), "unlocked")) return;
            ustawieniaKontaManager.clearMagazyn(player, this.getUUIDFromTitle(title), slot - 10, e.getCurrentItem());
            player.closeInventory();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOsClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ClickType click = e.getClick();

        if (title.contains("Osiagniecia - ")) {
            final UstawieniaKontaManager ustawieniaKontaManager = this.rpgcore.getUstawieniaKontaManager();
            e.setCancelled(true);
            switch (slot) {
                case 10:
                    ustawieniaKontaManager.openGracze(player, this.getUUIDFromTitle(title));
                    return;
                case 11:
                    ustawieniaKontaManager.openMoby(player, this.getUUIDFromTitle(title));
                    return;
                case 12:
                    ustawieniaKontaManager.openMetin(player, this.getUUIDFromTitle(title));
                    return;
                case 14:
                    ustawieniaKontaManager.openSkrzynie(player, this.getUUIDFromTitle(title));
                    return;
                case 15:
                    ustawieniaKontaManager.openNies(player, this.getUUIDFromTitle(title));
                    return;
                case 16:
                    ustawieniaKontaManager.openKowal(player, this.getUUIDFromTitle(title));
                    return;
                case 28:
                    ustawieniaKontaManager.openRybak(player, this.getUUIDFromTitle(title));
                    return;
                case 29:
                    ustawieniaKontaManager.openGornik(player, this.getUUIDFromTitle(title));
                    return;
            }
            return;
        }

        if (title.contains("Osiagniecia » Gracze - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setGracze(osUser.getGracze() + 1);
                        break;
                    case RIGHT:
                        osUser.setGracze(osUser.getGracze() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openGracze(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setGraczeProgress(osUser.getGraczeProgress() + 1);
                        break;
                    case RIGHT:
                        osUser.setGraczeProgress(osUser.getGraczeProgress() - 1);
                        break;
                    case SHIFT_LEFT:
                        osUser.setGraczeProgress(osUser.getGraczeProgress() + 10);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setGraczeProgress(osUser.getGraczeProgress() - 10);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openGracze(player, osUser.getUuid());
                return;
            }
            return;
        }

        if (title.contains("Osiagniecia » Moby - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setMoby(osUser.getMoby() + 1);
                        break;
                    case RIGHT:
                        osUser.setMoby(osUser.getMoby() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openMoby(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setMobyProgress(osUser.getMobyProgress() + 10);
                        break;
                    case RIGHT:
                        osUser.setMobyProgress(osUser.getMobyProgress() - 10);
                        break;
                    case SHIFT_LEFT:
                        osUser.setMobyProgress(osUser.getMobyProgress() + 100);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setMobyProgress(osUser.getMobyProgress() - 100);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openMoby(player, osUser.getUuid());
                return;
            }
            return;
        }


        if (title.contains("Osiagniecia » Metin - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setMetiny(osUser.getMetiny() + 1);
                        break;
                    case RIGHT:
                        osUser.setMetiny(osUser.getMetiny() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openMetin(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setMetinyProgress(osUser.getMetinyProgress() + 1);
                        break;
                    case RIGHT:
                        osUser.setMetinyProgress(osUser.getMetinyProgress() - 1);
                        break;
                    case SHIFT_LEFT:
                        osUser.setMetinyProgress(osUser.getMetinyProgress() + 10);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setMetinyProgress(osUser.getMetinyProgress() - 10);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openMetin(player, osUser.getUuid());
                return;
            }
            return;
        }

        if (title.contains("Osiagniecia » Skrzynie - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setSkrzynki(osUser.getSkrzynki() + 1);
                        break;
                    case RIGHT:
                        osUser.setSkrzynki(osUser.getSkrzynki() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openSkrzynie(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 10);
                        break;
                    case RIGHT:
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() - 10);
                        break;
                    case SHIFT_LEFT:
                        osUser.setSkrzynkiProgress(osUser.getMetinyProgress() + 100);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() - 100);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openSkrzynie(player, osUser.getUuid());
                return;
            }
            return;
        }

        if (title.contains("Osiagniecia » Nies - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setNiesy(osUser.getNiesy() + 1);
                        break;
                    case RIGHT:
                        osUser.setNiesy(osUser.getNiesy() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openNies(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setNiesyProgress(osUser.getNiesyProgress() + 1);
                        break;
                    case RIGHT:
                        osUser.setNiesyProgress(osUser.getNiesyProgress() - 1);
                        break;
                    case SHIFT_LEFT:
                        osUser.setNiesyProgress(osUser.getNiesyProgress() + 10);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setNiesyProgress(osUser.getNiesyProgress() - 10);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openNies(player, osUser.getUuid());
                return;
            }
            return;
        }


        if (title.contains("Osiagniecia » Kowal - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setUlepszenia(osUser.getUlepszenia() + 1);
                        break;
                    case RIGHT:
                        osUser.setUlepszenia(osUser.getUlepszenia() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openKowal(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setUlepszeniaProgress(osUser.getUlepszeniaProgress() + 1);
                        break;
                    case RIGHT:
                        osUser.setUlepszeniaProgress(osUser.getUlepszeniaProgress() - 1);
                        break;
                    case SHIFT_LEFT:
                        osUser.setUlepszeniaProgress(osUser.getUlepszeniaProgress() + 10);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setUlepszeniaProgress(osUser.getUlepszeniaProgress() - 10);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openKowal(player, osUser.getUuid());
                return;
            }
            return;
        }


        if (title.contains("Osiagniecia » Rybak - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setRybak(osUser.getRybak() + 1);
                        break;
                    case RIGHT:
                        osUser.setRybak(osUser.getRybak() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openRybak(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setRybakProgress(osUser.getRybakProgress() + 10);
                        break;
                    case RIGHT:
                        osUser.setRybakProgress(osUser.getRybakProgress() - 10);
                        break;
                    case SHIFT_LEFT:
                        osUser.setRybakProgress(osUser.getRybakProgress() + 100);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setRybakProgress(osUser.getRybakProgress() - 100);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openRybak(player, osUser.getUuid());
                return;
            }
            return;
        }


        if (title.contains("Osiagniecia » Gornik - ")) {
            e.setCancelled(true);
            final OsUser osUser = this.rpgcore.getOsManager().find(this.getUUIDFromTitle(title));
            if (slot == 1) {
                switch (click) {
                    case LEFT:
                        osUser.setGornik(osUser.getGornik() + 1);
                        break;
                    case RIGHT:
                        osUser.setGornik(osUser.getGornik() - 1);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openGornik(player, osUser.getUuid());
                return;
            }
            if (slot == 3) {
                switch (click) {
                    case LEFT:
                        osUser.setGornikProgress(osUser.getGornikProgress() + 10);
                        break;
                    case RIGHT:
                        osUser.setGornikProgress(osUser.getGornikProgress() - 10);
                        break;
                    case SHIFT_LEFT:
                        osUser.setGornikProgress(osUser.getGornikProgress() + 100);
                        break;
                    case SHIFT_RIGHT:
                        osUser.setGornikProgress(osUser.getGornikProgress() - 100);
                        break;
                    default:
                        return;
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(osUser.getUuid(), osUser));
                rpgcore.getUstawieniaKontaManager().openGornik(player, osUser.getUuid());
            }
        }


    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMisjeClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.contains("Misje - ")) {
            final UstawieniaKontaManager ustawieniaKontaManager = this.rpgcore.getUstawieniaKontaManager();
            e.setCancelled(true);
            final UUID target = this.getUUIDFromTitle(title);
            switch (slot) {
                case 10:
                    ustawieniaKontaManager.openBao(player, target);
                    return;
                case 11:
                    ustawieniaKontaManager.openBonusy(player, target);
                    return;
                case 12:
                    ustawieniaKontaManager.openPustelnik(player, target);
                    return;
                case 13:
                    ustawieniaKontaManager.openMistrzYang(player, target);
                    return;
                case 14:
                    ustawieniaKontaManager.openCzarownica(player, target);
                    return;
                case 15:
                    //ustawieniaKontaManager.openBonusy(player, target);
                    return;
                case 16:
                    ustawieniaKontaManager.openGornikM(player, target);
                    return;
                case 19:
                    //ustawieniaKontaManager.openBonusy(player, target);
                    return;
                case 20:
                    ustawieniaKontaManager.openKolekcjoner(player, target);
                    return;
                case 21:
                    ustawieniaKontaManager.openLesnik(player, target);
                    return;
                case 22:
                    ustawieniaKontaManager.openLowca(player, target);
                    return;
                case 23:
                    ustawieniaKontaManager.openMagazynier(player, target);
                    return;
                case 24:
                    ustawieniaKontaManager.openMedrzec(player, target);
                    return;
                case 25:
                    ustawieniaKontaManager.openMetinolog(player, target);
                    return;
                case 28:
                    ustawieniaKontaManager.openPrzyrodnik(player, target);
                    return;
                case 29:
                    ustawieniaKontaManager.openRybakM(player, target);
                    return;
                case 30:
                    ustawieniaKontaManager.openWyslannik(player, target);
                    return;
                case 31:
                    ustawieniaKontaManager.openWyszkolenie(player, target);
                    return;
            }
            return;
        }

        if (title.contains("Misje » Bao - ")) {
            e.setCancelled(true);
            final UUID uuid = this.getUUIDFromTitle(title);
            final BaoObject bao = rpgcore.getBaoManager().find(uuid);
            final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
            final int value = this.getValueFromClick(e.getClick());
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            switch (slot) {
                case 11:
                    switch (bao.getBaoUser().getBonus1()) {
                        case "Srednie obrazenia":
                            bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - bao.getBaoUser().getValue1());
                            bao.getBaoUser().setValue1(0);
                            bao.getBaoUser().setBonus1("Silny Na Ludzi");
                            break;
                        case "Silny Na Ludzi":
                            bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() - bao.getBaoUser().getValue1());
                            bao.getBaoUser().setValue1(0);
                            bao.getBaoUser().setBonus1("Silny Na Potwory");
                            break;
                        case "Silny Na Potwory":
                            bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - bao.getBaoUser().getValue1());
                            bao.getBaoUser().setValue1(0);
                            bao.getBaoUser().setBonus1("Srednie obrazenia");
                            break;
                    }
                    break;
                case 20:
                    bao.getBaoUser().setValue1(bao.getBaoUser().getValue1() + value);
                    switch (bao.getBaoUser().getBonus1()) {
                        case "Srednie obrazenia":
                            bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + value);
                            break;
                        case "Silny Na Ludzi":
                            bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + value);
                            break;
                        case "Silny Na Potwory":
                            bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + value);
                            break;
                    }
                    break;
                case 12:
                    switch (bao.getBaoUser().getBonus2()) {
                        case "Srednia defensywa":
                            bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - bao.getBaoUser().getValue2());
                            bao.getBaoUser().setValue2(0);
                            bao.getBaoUser().setBonus2("Srednia defensywa przeciwko ludziom");
                            break;
                        case "Srednia defensywa przeciwko ludziom":
                            bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() - bao.getBaoUser().getValue2());
                            bao.getBaoUser().setValue2(0);
                            bao.getBaoUser().setBonus2("Srednia defensywa przeciwko potworom");
                            break;
                        case "Srednia defensywa przeciwko potworom":
                            bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - bao.getBaoUser().getValue2());
                            bao.getBaoUser().setValue2(0);
                            bao.getBaoUser().setBonus2("Srednia defensywa");
                            break;
                    }
                    break;
                case 21:
                    bao.getBaoUser().setValue2(bao.getBaoUser().getValue2() + value);
                    switch (bao.getBaoUser().getBonus2()) {
                        case "Srednia defensywa":
                            bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                            break;
                        case "Srednia defensywa przeciwko ludziom":
                            bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + value);
                            break;
                        case "Srednia defensywa przeciwko potworom":
                            bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + value);
                            break;
                    }
                    break;
                case 13:
                    switch (bao.getBaoUser().getBonus3()) {
                        case "Szansa na cios krytyczny":
                            bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - bao.getBaoUser().getValue3());
                            bao.getBaoUser().setValue3(0);
                            bao.getBaoUser().setBonus3("Wzmocnienie ciosu krytycznego");
                            break;
                        case "Wzmocnienie ciosu krytycznego":
                            bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() - bao.getBaoUser().getValue3());
                            bao.getBaoUser().setValue3(0);
                            bao.getBaoUser().setBonus3("Dodatkowe obrazenia");
                            break;
                        case "Dodatkowe obrazenia":
                            bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() - bao.getBaoUser().getValue3());
                            bao.getBaoUser().setValue3(0);
                            bao.getBaoUser().setBonus3("Szansa na cios krytyczny");
                            break;
                    }
                    break;
                case 22:
                    bao.getBaoUser().setValue3(bao.getBaoUser().getValue3() + value);
                    switch (bao.getBaoUser().getBonus3()) {
                        case "Szansa na cios krytyczny":
                            bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + value);
                            break;
                        case "Wzmocnienie ciosu krytycznego":
                            bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() + value);
                            break;
                        case "Dodatkowe obrazenia":
                            bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + value);
                            break;
                    }
                    break;
                case 14:
                    switch (bao.getBaoUser().getBonus4()) {
                        case "Predkosc ruchu":
                            bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - bao.getBaoUser().getValue4());
                            bao.getBaoUser().setValue4(0);
                            bao.getBaoUser().setBonus4("Dodatkowy EXP");
                            break;
                        case "Dodatkowy EXP":
                            bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - bao.getBaoUser().getValue4());
                            bao.getBaoUser().setValue4(0);
                            bao.getBaoUser().setBonus4("Szczescie");
                            break;
                        case "Szczescie":
                            bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() - bao.getBaoUser().getValue4());
                            bao.getBaoUser().setValue4(0);
                            bao.getBaoUser().setBonus4("Predkosc ruchu");
                            break;
                    }
                    break;
                case 23:
                    bao.getBaoUser().setValue4(bao.getBaoUser().getValue4() + value);
                    switch (bao.getBaoUser().getBonus4()) {
                        case "Predkosc ruchu":
                            bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + value);
                            break;
                        case "Dodatkowy EXP":
                            bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() + value);
                            break;
                        case "Szczescie":
                            bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + value);
                            break;
                    }
                    break;
                case 15:
                    switch (bao.getBaoUser().getBonus5()) {
                        case "Dodatkowe HP":
                            bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - bao.getBaoUser().getValue5());
                            if (Bukkit.getPlayer(uuid) != null) {
                                Bukkit.getPlayer(uuid).setMaxHealth(Bukkit.getPlayer(uuid).getMaxHealth() - bao.getBaoUser().getValue5() * 2);
                            }
                            bao.getBaoUser().setValue5(0);
                            bao.getBaoUser().setBonus5("Blok Ciosu");
                            break;
                        case "Blok Ciosu":
                            bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - bao.getBaoUser().getValue5());
                            bao.getBaoUser().setValue5(0);
                            bao.getBaoUser().setBonus5("Dodatkowe HP");
                            break;
                    }
                    break;
                case 24:
                    bao.getBaoUser().setValue5(bao.getBaoUser().getValue5() + value);
                    switch (bao.getBaoUser().getBonus5()) {
                        case "Dodatkowe HP":
                            bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + value);
                            if (Bukkit.getPlayer(uuid) != null) {
                                Bukkit.getPlayer(uuid).setMaxHealth(Bukkit.getPlayer(uuid).getMaxHealth() + value * 2);
                            }
                            break;
                        case "Blok Ciosu":
                            bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + value);
                            break;
                    }
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBao(uuid, bao);
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openBao(player, bao.getId());
            return;
        }

        if (title.contains("Misje » Bonusy - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final Bonuses bonuses = rpgcore.getBonusesManager().find(this.getUUIDFromTitle(title));
            final double value = this.getValueFromClickKey(e.getClick());

            switch (slot) {
                case 10:
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + value);
                    break;
                case 11:
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + (int) value);
                    break;
                case 12:
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + value);
                    break;
                case 13:
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + value);
                    break;
                case 14:
                    bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + value);
                    break;
                case 15:
                    bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() + value);
                    break;
                case 16:
                    bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() + value);
                    break;
                case 19:
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + value);
                    break;
                case 20:
                    bonuses.getBonusesUser().setMinussrednieobrazenia(bonuses.getBonusesUser().getMinussrednieobrazenia() + value);
                    break;
                case 21:
                    bonuses.getBonusesUser().setMinusobrazenianaludzi(bonuses.getBonusesUser().getMinusobrazenianaludzi() + value);
                    break;
                case 22:
                    bonuses.getBonusesUser().setMinusobrazenianamoby(bonuses.getBonusesUser().getMinusobrazenianamoby() + value);
                    break;
                case 23:
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                    break;
                case 24:
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + value);
                    break;
                case 25:
                    bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + value);
                    break;
                case 28:
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + value);
                    break;
                case 29:
                    bonuses.getBonusesUser().setMinussredniadefensywa(bonuses.getBonusesUser().getMinussredniadefensywa() + value);
                    break;
                case 30:
                    bonuses.getBonusesUser().setMinusdefnaludzi(bonuses.getBonusesUser().getMinusdefnaludzi() + value);
                    break;
                case 31:
                    bonuses.getBonusesUser().setMinusdefnamoby(bonuses.getBonusesUser().getMinusdefnamoby() + value);
                    break;
                case 32:
                    bonuses.getBonusesUser().setTruedamage(bonuses.getBonusesUser().getTruedamage() + (int) value);
                    break;
                case 33:
                    bonuses.getBonusesUser().setSzansanakrwawienie(bonuses.getBonusesUser().getSzansanakrwawienie() + value);
                    break;
                case 34:
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + (int) value);
                    break;
                case 37:
                    bonuses.getBonusesUser().setSpowolnienie(bonuses.getBonusesUser().getSpowolnienie() + value);
                    break;
                case 38:
                    bonuses.getBonusesUser().setOslepienie(bonuses.getBonusesUser().getOslepienie() + value);
                    break;
                case 39:
                    bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() + value);
                    break;
                case 40:
                    bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + value);
                    break;
                case 41:
                    bonuses.getBonusesUser().setWampiryzm(bonuses.getBonusesUser().getWampiryzm() + value);
                    break;
                case 42:
                    bonuses.getBonusesUser().setDmgMetiny(bonuses.getBonusesUser().getDmgMetiny() + (int) value);
                    break;
                case 43:
                    bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + (int) value);
                    if (Bukkit.getPlayer(bonuses.getId()) != null) {
                        Bukkit.getPlayer(bonuses.getId()).setMaxHealth(Bukkit.getPlayer(bonuses.getId()).getMaxHealth() + value * 2);
                    }
                    break;
                case 49:
                    bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + (int) value);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses));
            rpgcore.getUstawieniaKontaManager().openBonusy(player, bonuses.getId());
            return;
        }

        if (title.contains("Misje » Pustelnik - ")) {
            e.setCancelled(true);
            if (item == null) return;

            final PustelnikUser pustelnik = rpgcore.getPustelnikNPC().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());
            switch (slot) {
                case 1:
                    pustelnik.setMissionId(pustelnik.getMissionId() + value);
                    break;
                case 3:
                    pustelnik.setProgress(pustelnik.getProgress() + value);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataPustelnik(pustelnik.getUuid(), pustelnik));
            rpgcore.getUstawieniaKontaManager().openPustelnik(player, pustelnik.getUuid());
            return;
        }

        if (title.contains("Misje » Mistrz Yang - ")) {
            e.setCancelled(true);
            if (item == null) return;

            final MistrzYangUser mistrzYang = rpgcore.getMistrzYangNPC().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());
            switch (slot) {
                case 1:
                    mistrzYang.setMissionId(mistrzYang.getMissionId() + value);
                    break;
                case 3:
                    mistrzYang.setProgress(mistrzYang.getProgress() + value);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMistrzYang(mistrzYang.getUuid(), mistrzYang));
            rpgcore.getUstawieniaKontaManager().openMistrzYang(player, mistrzYang.getUuid());
            return;
        }

        if (title.contains("Misje » Czarownica - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final CzarownicaUser czarownica = rpgcore.getCzarownicaNPC().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());

            switch (slot) {
                case 3:
                    czarownica.setMission(czarownica.getMission() + value);
                    czarownica.getProgressMap().clear();
                    break;
                case 5:
                    czarownica.setUnlocked(!czarownica.isUnlocked());
                    break;
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                    czarownica.getProgressMap().replace(gui.getItem(slot - 9), czarownica.getProgressMap().get(gui.getItem(slot - 9)) + value);
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataCzarownica(czarownica.getUuid(), czarownica));
            rpgcore.getUstawieniaKontaManager().openCzarownica(player, czarownica.getUuid());
            return;
        }

        if (title.contains("Misje » Gornik - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final GornikObject gornik = rpgcore.getGornikNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(gornik.getID());
            final double value = this.getValueFromClickKey(e.getClick());

            switch (slot) {
                case 0:
                    gornik.getGornikUser().setMission(gornik.getGornikUser().getMission() + (int) value);
                    break;
                case 1:
                    gornik.getGornikUser().setProgress(gornik.getGornikUser().getProgress() + (int) value);
                    break;
                case 2:
                    gornik.getGornikUser().setBlokCiosu(gornik.getGornikUser().getBlokCiosu() + value);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + value);
                    break;
                case 3:
                    gornik.getGornikUser().setSredniaOdpornosc(gornik.getGornikUser().getSredniaOdpornosc() + value);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                    break;
                case 4:
                    gornik.getGornikUser().setPrzeszycieBloku(gornik.getGornikUser().getPrzeszycieBloku() + value);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + value);
                    break;
                case 8:
                    rpgcore.getUstawieniaKontaManager().openGornikDrzewko(player, gornik.getID());
                    return;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataGornik(gornik.getID(), gornik);
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openGornikM(player, gornik.getID());
            return;
        }

        if (title.contains("Misje » Gornik » Drzewko - ")) {
            e.setCancelled(true);

            if (item == null || (item.getType() == Material.STAINED_GLASS_PANE && item.getDurability() == 7)) return;

            final GornikObject gornik = rpgcore.getGornikNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(gornik.getID());


            switch (slot) {
                case 3:
                    gornik.getGornikUser().setD7_3(!gornik.getGornikUser().isD7_3());
                    bonuses.getBonusesUser().setSzczescie(gornik.getGornikUser().isD7_3() ? bonuses.getBonusesUser().getSzczescie() + 15 : bonuses.getBonusesUser().getSzczescie() - 15);
                    break;
                case 5:
                    gornik.getGornikUser().setD7_4(!gornik.getGornikUser().isD7_4());
                    bonuses.getBonusesUser().setPrzebiciePancerza(gornik.getGornikUser().isD7_4() ? bonuses.getBonusesUser().getPrzebiciePancerza() + 2 : bonuses.getBonusesUser().getPrzebiciePancerza() - 2);
                    break;
                case 4:
                    gornik.getGornikUser().setD6_5(!gornik.getGornikUser().isD6_5());
                    break;
                case 13:
                    gornik.getGornikUser().setD5_5(!gornik.getGornikUser().isD5_5());
                    bonuses.getBonusesUser().setSilnynapotwory(gornik.getGornikUser().isD5_5() ? bonuses.getBonusesUser().getSilnynapotwory() + 5 : bonuses.getBonusesUser().getSilnynapotwory() - 5);
                    bonuses.getBonusesUser().setSzczescie(gornik.getGornikUser().isD5_5() ? bonuses.getBonusesUser().getSzczescie() + 10 : bonuses.getBonusesUser().getSzczescie() - 10);
                case 22:
                    gornik.getGornikUser().setD4_7(!gornik.getGornikUser().isD4_7());
                    bonuses.getBonusesUser().setSilnynapotwory(gornik.getGornikUser().isD4_7() ? bonuses.getBonusesUser().getSilnynapotwory() + 5 : bonuses.getBonusesUser().getSilnynapotwory() - 5);
                    bonuses.getBonusesUser().setDefnamoby(gornik.getGornikUser().isD4_7() ? bonuses.getBonusesUser().getDefnamoby() + 5 : bonuses.getBonusesUser().getDefnamoby() - 5);
                    break;
                case 31:
                    gornik.getGornikUser().setD3_3(!gornik.getGornikUser().isD3_3());
                    bonuses.getBonusesUser().setPrzebiciePancerza(gornik.getGornikUser().isD3_3() ? bonuses.getBonusesUser().getPrzebiciePancerza() + 1 : bonuses.getBonusesUser().getPrzebiciePancerza() - 1);
                    break;
                case 40:
                    gornik.getGornikUser().setD2(!gornik.getGornikUser().isD2());
                    break;
                case 49:
                    gornik.getGornikUser().setD1(!gornik.getGornikUser().isD1());
                    bonuses.getBonusesUser().setSrednieobrazenia(gornik.getGornikUser().isD1() ? bonuses.getBonusesUser().getSrednieobrazenia() + 1 : bonuses.getBonusesUser().getSrednieobrazenia() - 1);
                    break;
                case 0:
                    gornik.getGornikUser().setD9_2(!gornik.getGornikUser().isD9_2());
                    bonuses.getBonusesUser().setPrzeszyciebloku(gornik.getGornikUser().isD9_2() ? bonuses.getBonusesUser().getPrzeszyciebloku() + 5 : bonuses.getBonusesUser().getPrzeszyciebloku() - 5);
                    break;
                case 9:
                    gornik.getGornikUser().setD8_2(!gornik.getGornikUser().isD8_2());
                    bonuses.getBonusesUser().setSilnynaludzi(gornik.getGornikUser().isD8_2() ? bonuses.getBonusesUser().getSilnynaludzi() + 3 : bonuses.getBonusesUser().getSilnynaludzi() - 3);
                    break;
                case 10:
                    gornik.getGornikUser().setD7_2(!gornik.getGornikUser().isD7_2());
                    bonuses.getBonusesUser().setDefnaludzi(gornik.getGornikUser().isD7_2() ? bonuses.getBonusesUser().getDefnaludzi() + 2 : bonuses.getBonusesUser().getDefnaludzi() - 2);
                    break;
                case 11:
                    gornik.getGornikUser().setD6_4(!gornik.getGornikUser().isD6_4());
                    break;
                case 20:
                    gornik.getGornikUser().setD5_4(!gornik.getGornikUser().isD5_4());
                    bonuses.getBonusesUser().setDefnaludzi(gornik.getGornikUser().isD5_4() ? bonuses.getBonusesUser().getDefnaludzi() + 2 : bonuses.getBonusesUser().getDefnaludzi() - 2);
                    break;
                case 29:
                    gornik.getGornikUser().setD4_6(!gornik.getGornikUser().isD4_6());
                    bonuses.getBonusesUser().setDefnaludzi(gornik.getGornikUser().isD4_6() ? bonuses.getBonusesUser().getDefnaludzi() + 5 : bonuses.getBonusesUser().getDefnaludzi() - 5);
                    bonuses.getBonusesUser().setSilnynaludzi(gornik.getGornikUser().isD4_6() ? bonuses.getBonusesUser().getSilnynaludzi() + 5 : bonuses.getBonusesUser().getSilnynaludzi() - 5);
                    break;
                case 28:
                    gornik.getGornikUser().setD5_3(!gornik.getGornikUser().isD5_3());
                    bonuses.getBonusesUser().setSilnynaludzi(gornik.getGornikUser().isD5_3() ? bonuses.getBonusesUser().getSilnynaludzi() + 2 : bonuses.getBonusesUser().getSilnynaludzi() - 2);
                    break;
                case 27:
                    gornik.getGornikUser().setD6_3(!gornik.getGornikUser().isD6_3());
                    bonuses.getBonusesUser().setDefnaludzi(gornik.getGornikUser().isD6_3() ? bonuses.getBonusesUser().getDefnaludzi() + 1 : bonuses.getBonusesUser().getDefnaludzi() - 1);
                    break;
                case 38:
                    gornik.getGornikUser().setD4_4(!gornik.getGornikUser().isD4_4());
                    bonuses.getBonusesUser().setPrzeszyciebloku(gornik.getGornikUser().isD4_4() ? bonuses.getBonusesUser().getPrzeszyciebloku() + 3 : bonuses.getBonusesUser().getPrzeszyciebloku() - 3);
                    break;
                case 47:
                    gornik.getGornikUser().setD4_5(!gornik.getGornikUser().isD4_5());
                    bonuses.getBonusesUser().setPrzeszyciebloku(gornik.getGornikUser().isD4_5() ? bonuses.getBonusesUser().getPrzeszyciebloku() + 5 : bonuses.getBonusesUser().getPrzeszyciebloku() - 5);
                    break;
                case 39:
                    gornik.getGornikUser().setD3_1(!gornik.getGornikUser().isD3_1());
                    bonuses.getBonusesUser().setPrzeszyciebloku(gornik.getGornikUser().isD3_1() ? bonuses.getBonusesUser().getPrzeszyciebloku() + 2 : bonuses.getBonusesUser().getPrzeszyciebloku() - 2);
                    break;
                case 8:
                    gornik.getGornikUser().setD9_1(!gornik.getGornikUser().isD9_1());
                    bonuses.getBonusesUser().setDodatkoweobrazenia(gornik.getGornikUser().isD9_1() ? bonuses.getBonusesUser().getDodatkoweobrazenia() + 100 : bonuses.getBonusesUser().getDodatkoweobrazenia() - 100);
                    break;
                case 17:
                    gornik.getGornikUser().setD8_1(!gornik.getGornikUser().isD8_1());
                    bonuses.getBonusesUser().setDodatkoweobrazenia(gornik.getGornikUser().isD8_1() ? bonuses.getBonusesUser().getDodatkoweobrazenia() + 50 : bonuses.getBonusesUser().getDodatkoweobrazenia() - 50);
                    break;
                case 16:
                    gornik.getGornikUser().setD7_1(!gornik.getGornikUser().isD7_1());
                    bonuses.getBonusesUser().setDodatkoweobrazenia(gornik.getGornikUser().isD7_1() ? bonuses.getBonusesUser().getDodatkoweobrazenia() + 50 : bonuses.getBonusesUser().getDodatkoweobrazenia() - 50);
                    break;
                case 15:
                    gornik.getGornikUser().setD6_2(!gornik.getGornikUser().isD6_2());
                    break;
                case 24:
                    gornik.getGornikUser().setD5_2(!gornik.getGornikUser().isD5_2());
                    bonuses.getBonusesUser().setDodatkoweobrazenia(gornik.getGornikUser().isD5_2() ? bonuses.getBonusesUser().getDodatkoweobrazenia() + 50 : bonuses.getBonusesUser().getDodatkoweobrazenia() - 50);
                    break;
                case 33:
                    gornik.getGornikUser().setD4_3(!gornik.getGornikUser().isD4_3());
                    bonuses.getBonusesUser().setDodatkoweobrazenia(gornik.getGornikUser().isD4_3() ? bonuses.getBonusesUser().getDodatkoweobrazenia() + 150 : bonuses.getBonusesUser().getDodatkoweobrazenia() - 150);
                    bonuses.getBonusesUser().setSrednieobrazenia(gornik.getGornikUser().isD4_3() ? bonuses.getBonusesUser().getSrednieobrazenia() + 4 : bonuses.getBonusesUser().getSrednieobrazenia() - 4);
                    break;
                case 34:
                    gornik.getGornikUser().setD5_1(!gornik.getGornikUser().isD5_1());
                    bonuses.getBonusesUser().setSrednieobrazenia(gornik.getGornikUser().isD5_1() ? bonuses.getBonusesUser().getSrednieobrazenia() + 2 : bonuses.getBonusesUser().getSrednieobrazenia() - 2);
                    break;
                case 35:
                    gornik.getGornikUser().setD6_1(!gornik.getGornikUser().isD6_1());
                    bonuses.getBonusesUser().setSrednieobrazenia(gornik.getGornikUser().isD6_1() ? bonuses.getBonusesUser().getSrednieobrazenia() + 3 : bonuses.getBonusesUser().getSrednieobrazenia() - 3);
                    break;
                case 42:
                    gornik.getGornikUser().setD4_1(!gornik.getGornikUser().isD4_1());
                    bonuses.getBonusesUser().setBlokciosu(gornik.getGornikUser().isD4_1() ? bonuses.getBonusesUser().getBlokciosu() + 3 : bonuses.getBonusesUser().getBlokciosu() - 3);
                    break;
                case 51:
                    gornik.getGornikUser().setD4_2(!gornik.getGornikUser().isD4_2());
                    bonuses.getBonusesUser().setDodatkoweobrazenia(gornik.getGornikUser().isD4_2() ? bonuses.getBonusesUser().getDodatkoweobrazenia() + 100 : bonuses.getBonusesUser().getDodatkoweobrazenia() - 100);
                    break;
                case 41:
                    gornik.getGornikUser().setD3_2(!gornik.getGornikUser().isD3_2());
                    bonuses.getBonusesUser().setBlokciosu(gornik.getGornikUser().isD3_2() ? bonuses.getBonusesUser().getBlokciosu() + 2 : bonuses.getBonusesUser().getBlokciosu() - 2);
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataGornik(gornik.getID(), gornik);
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openGornikDrzewko(player, gornik.getID());
            return;
        }

        if (title.contains("Misje » Kolekcjoner - ")) {
            e.setCancelled(true);

            if (item == null) return;

            final KolekcjonerObject kolekcjoner = rpgcore.getKolekcjonerNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(this.getUUIDFromTitle(title));
            final double value = this.getValueFromClickKey(e.getClick());

            switch (slot) {
                case 2:
                    kolekcjoner.getKolekcjonerUser().setMission(kolekcjoner.getKolekcjonerUser().getMission() + (int) value);
                    break;
                case 3:
                    kolekcjoner.getKolekcjonerUser().setDefNaLudzi(kolekcjoner.getKolekcjonerUser().getDefNaLudzi() + value);
                    bonuses.getBonusesUser().setDefnaludzi(kolekcjoner.getKolekcjonerUser().getDefNaLudzi() + value);
                    break;
                case 5:
                    kolekcjoner.getKolekcjonerUser().setSilnyNaLudzi(kolekcjoner.getKolekcjonerUser().getSilnyNaLudzi() + value);
                    bonuses.getBonusesUser().setSilnynaludzi(kolekcjoner.getKolekcjonerUser().getSilnyNaLudzi() + value);
                    break;
                case 6:
                    kolekcjoner.getKolekcjonerUser().setSzczescie(kolekcjoner.getKolekcjonerUser().getSzczescie() + (int) value);
                    bonuses.getBonusesUser().setSzczescie(kolekcjoner.getKolekcjonerUser().getSzczescie() + (int) value);
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    kolekcjoner.getKolekcjonerUser().getMissionProgress().set(slot - 11, !kolekcjoner.getKolekcjonerUser().getMissionProgress().get(slot - 11));
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataKolekcjoner(kolekcjoner.getID(), kolekcjoner);
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openKolekcjoner(player, kolekcjoner.getID());
            return;
        }

        if (title.contains("Misje » Lesnik - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final LesnikObject lesnik = rpgcore.getLesnikNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(this.getUUIDFromTitle(title));
            final double value = this.getValueFromClickKey(e.getClick());


            switch (slot) {
                case 0:
                    lesnik.getUser().setMission(lesnik.getUser().getMission() + (int) value);
                    break;
                case 1:
                    lesnik.getUser().setProgress(lesnik.getUser().getProgress() + (int) value);
                    break;
                case 2:
                    lesnik.getUser().setDefNaLudzi(lesnik.getUser().getDefNaLudzi() + value);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + value);
                    break;
                case 3:
                    lesnik.getUser().setPrzeszycie(lesnik.getUser().getPrzeszycie() + value);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + value);
                    break;
                case 4:
                    lesnik.getUser().setWzmKryta(lesnik.getUser().getWzmKryta() + value);
                    bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() + value);
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataLesnik(lesnik.getId(), lesnik);
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openLesnik(player, lesnik.getId());
            return;
        }

        if (title.contains("Misje » Lowca - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final LowcaObject lowca = rpgcore.getLowcaNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());

            switch (slot) {
                case 0:
                    lowca.getLowcaUser().setMission(lowca.getLowcaUser().getMission() + value);
                    break;
                case 1:
                    lowca.getLowcaUser().setProgress(lowca.getLowcaUser().getProgress() + value);
                    break;
                case 2:
                    lowca.getLowcaUser().setSzybkosc(lowca.getLowcaUser().getSzybkosc() + value);
                    bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + value);
                    break;
                case 3:
                    lowca.getLowcaUser().setSzczescie(lowca.getLowcaUser().getSzczescie() + value);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + value);
                    break;
                case 4:
                    lowca.getLowcaUser().setTruedmg(lowca.getLowcaUser().getTruedmg() + value);
                    bonuses.getBonusesUser().setTruedamage(bonuses.getBonusesUser().getTruedamage() + value);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataLowca(lowca.getId(), lowca);
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openLowca(player, lowca.getId());
            return;
        }

        if (title.contains("Misje » Magazynier - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final MagazynierUser magazynier = rpgcore.getMagazynierNPC().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());

            switch (slot) {
                case 0:
                    magazynier.setPoints(magazynier.getPoints() + value);
                    break;
                case 2:
                    magazynier.setMagazyn1("");
                    magazynier.setUnlocked1(!magazynier.isUnlocked1());
                    break;
                case 3:
                    magazynier.setMagazyn2("");
                    magazynier.setUnlocked2(!magazynier.isUnlocked2());
                    break;
                case 4:
                    magazynier.setMagazyn3("");
                    magazynier.setUnlocked3(!magazynier.isUnlocked3());
                    break;
                case 5:
                    magazynier.setMagazyn4("");
                    magazynier.setUnlocked4(!magazynier.isUnlocked4());
                    break;
                case 6:
                    magazynier.setMagazyn5("");
                    magazynier.setUnlocked5(!magazynier.isUnlocked5());
                    break;
                case 8:
                    magazynier.setRemoteCommand(!magazynier.isRemoteCommand());
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(magazynier.getUuid(), magazynier));
            rpgcore.getUstawieniaKontaManager().openMagazynier(player, magazynier.getUuid());
            return;
        }

        if (title.contains("Misje » Medrzec - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final MedrzecUser medrzec = rpgcore.getMedrzecNPC().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());

            if (slot == 2) {
                medrzec.setBonus(medrzec.getBonus() + value);
                if (Bukkit.getPlayer(medrzec.getUuid()) != null) {
                    Bukkit.getPlayer(medrzec.getUuid()).setMaxHealth(Bukkit.getPlayer(medrzec.getUuid()).getMaxHealth() + value * 2);
                }
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMedrzec(medrzec.getUuid(), medrzec));
                rpgcore.getUstawieniaKontaManager().openMedrzec(player, medrzec.getUuid());
            }

            return;
        }

        if (title.contains("Misje » Metinolog - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final MetinologObject metinolog = rpgcore.getMetinologNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(metinolog.getID());
            final double value = this.getValueFromClickKey(e.getClick());

            switch (slot) {
                case 0:
                    metinolog.getMetinologUser().setPostepKill(metinolog.getMetinologUser().getPostepKill() + (int) value);
                    break;
                case 1:
                    metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + (int) value);
                    break;
                case 2:
                    metinolog.getMetinologUser().setPostepGive(metinolog.getMetinologUser().getPostepGive() + (int) value);
                    break;
                case 3:
                    metinolog.getMetinologUser().setPostepMisjiGive(metinolog.getMetinologUser().getPostepMisjiGive() + (int) value);
                    break;
                case 6:
                    metinolog.getMetinologUser().setSrOdpo(metinolog.getMetinologUser().getSrOdpo() + value);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                    break;
                case 7:
                    metinolog.getMetinologUser().setDodatkowedmg(metinolog.getMetinologUser().getDodatkowedmg() + (int) value);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + (int) value);
                    break;
                case 8:
                    metinolog.getMetinologUser().setPrzeszycie(metinolog.getMetinologUser().getPrzeszycie() + value);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + value);
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataMetinolog(metinolog.getID(), metinolog);
                rpgcore.getMongoManager().saveDataBonuses(metinolog.getID(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openMetinolog(player, metinolog.getID());
            return;
        }

        if (title.contains("Misje » Przyrodnik - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final PrzyrodnikObject przyrodnik = rpgcore.getPrzyrodnikNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(przyrodnik.getId());
            final double value = this.getValueFromClickKey(e.getClick());

            switch (slot) {
                case 0:
                    przyrodnik.getUser().setMission(przyrodnik.getUser().getMission() + (int) value);
                    break;
                case 1:
                    przyrodnik.getUser().setProgress(przyrodnik.getUser().getProgress() + (int) value);
                    break;
                case 3:
                    przyrodnik.getUser().setDmg(przyrodnik.getUser().getDmg() + value);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + value);
                    break;
                case 4:
                    przyrodnik.getUser().setDef(przyrodnik.getUser().getDef() + value);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataPrzyrodnik(przyrodnik.getId(), przyrodnik);
                rpgcore.getMongoManager().saveDataBonuses(przyrodnik.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openPrzyrodnik(player, przyrodnik.getId());
            return;
        }

        if (title.contains("Misje » Rybak - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final RybakObject rybak = rpgcore.getRybakNPC().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(rybak.getId());
            final double value = this.getValueFromClickKey(e.getClick());

            switch (slot) {
                case 0:
                    rybak.getRybakUser().setMission(rybak.getRybakUser().getMission() + (int) value);
                    break;
                case 1:
                    rybak.getRybakUser().setProgress(rybak.getRybakUser().getProgress() + (int) value);
                    break;
                case 2:
                    rybak.getRybakUser().setBlok(rybak.getRybakUser().getBlok() + value);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + value);
                    break;
                case 6:
                    rybak.getRybakUser().setKryt(rybak.getRybakUser().getKryt() + value);
                    bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + value);
                    break;
                case 7:
                    rybak.getRybakUser().setMorskieSzczescie(rybak.getRybakUser().getMorskieSzczescie() + value);
                    break;
                case 8:
                    rybak.getRybakUser().setSrDef(rybak.getRybakUser().getSrDef() + value);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataRybak(rybak.getId(), rybak);
                rpgcore.getMongoManager().saveDataBonuses(rybak.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openRybakM(player, rybak.getId());
            return;
        }

        if (title.contains("Misje » Wyslannik - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final WyslannikObject wyslannik = rpgcore.getWyslannikNPC().find(this.getUUIDFromTitle(title));
            final int value = this.getValueFromClick(e.getClick());

            switch (slot) {
                case 0:
                    wyslannik.getWyslannikUser().setKillMobsMission(wyslannik.getWyslannikUser().getKillMobsMission() + value);
                    break;
                case 1:
                    wyslannik.getWyslannikUser().setKillMobsMissionProgress(wyslannik.getWyslannikUser().getKillMobsMissionProgress() + value);
                    break;
                case 3:
                    wyslannik.getWyslannikUser().setKillBossMission(wyslannik.getWyslannikUser().getKillBossMission() + value);
                    break;
                case 5:
                    wyslannik.getWyslannikUser().setKillBossMissionProgress(wyslannik.getWyslannikUser().getKillBossMissionProgress() + value);
                    break;
                case 7:
                    wyslannik.getWyslannikUser().setOpenChestMission(wyslannik.getWyslannikUser().getOpenChestMission() + value);
                    break;
                case 8:
                    wyslannik.getWyslannikUser().setOpenChestMissionProgress(wyslannik.getWyslannikUser().getOpenChestMissionProgress() + value);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataWyslannik(wyslannik.getUuid(), wyslannik));
            rpgcore.getUstawieniaKontaManager().openWyslannik(player, wyslannik.getUuid());
            return;
        }

        if (title.contains("Misje » Wyszkolenie - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final WyszkolenieUser wyszkolenie = rpgcore.getWyszkolenieManager().find(this.getUUIDFromTitle(title));
            final Bonuses bonuses = rpgcore.getBonusesManager().find(wyszkolenie.getUuid());
            final int value = this.getValueFromClick(e.getClick());

            switch (slot) {
                case 0:
                    wyszkolenie.setTotalPoints(wyszkolenie.getTotalPoints() + value);
                    break;
                case 1:
                    wyszkolenie.setPunkty(wyszkolenie.getPunkty() + value);
                    break;
                case 2:
                    wyszkolenie.setSrDmg(wyszkolenie.getSrDmg() + value);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + value);
                    break;
                case 3:
                    wyszkolenie.setSrDef(wyszkolenie.getSrDef() + value);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + value);
                    break;
                case 4:
                    wyszkolenie.setKryt(wyszkolenie.getKryt() + value);
                    bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + value);
                    break;
                case 5:
                    wyszkolenie.setSzczescie(wyszkolenie.getSzczescie() + value);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + value);
                    break;
                case 6:
                    wyszkolenie.setBlok(wyszkolenie.getBlok() + value);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + value);
                    break;
                case 7:
                    wyszkolenie.setHp(wyszkolenie.getHp() + value);
                    bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + value);
                    if (Bukkit.getPlayer(wyszkolenie.getUuid()) != null) {
                        Bukkit.getPlayer(wyszkolenie.getUuid()).setMaxHealth(Bukkit.getPlayer(wyszkolenie.getUuid()).getMaxHealth() + value * 2);
                    }
                    break;
                case 8:
                    rpgcore.getUstawieniaKontaManager().openDrzewkoRozwoju(player, wyszkolenie);
                    return;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataWyszkolenie(wyszkolenie.getUuid(), wyszkolenie);
                rpgcore.getMongoManager().saveDataBonuses(wyszkolenie.getUuid(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openWyszkolenie(player, wyszkolenie.getUuid());
            return;
        }

        if (title.contains("Misje » Wyszkolenie » Drzewko - ")) {
            e.setCancelled(true);

            if (item == null) return;
            final WyszkolenieUser wyszkolenieUser = rpgcore.getWyszkolenieManager().find(this.getUUIDFromTitle(title));
            final DrzewkoWyszkoleniaUser drzewko = wyszkolenieUser.getDrzewkoWyszkoleniaUser();
            final Bonuses bonuses = rpgcore.getBonusesManager().find(wyszkolenieUser.getUuid());
            final ClickType click = e.getClick();


            switch (slot) {
                case 40:
                    if (!drzewko.getD1().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getD1().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getD1().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getD1().setUpgradeLvl(drzewko.getD1().getUpgradeLvl() + 1);
                        drzewko.getD1().setSrDmg(drzewko.getD1().getSrDmg() + drzewko.getD1().getPerLvl());
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + drzewko.getD1().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getD1().setUpgradeLvl(drzewko.getD1().getUpgradeLvl() - 1);
                        drzewko.getD1().setSrDmg(drzewko.getD1().getSrDmg() - drzewko.getD1().getPerLvl());
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - drzewko.getD1().getPerLvl());
                        break;
                    }
                    break;
                case 31:
                    if (!drzewko.getD2().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getD2().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getD2().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getD2().setUpgradeLvl(drzewko.getD2().getUpgradeLvl() + 1);
                        drzewko.getD2().setSzczescie(drzewko.getD2().getSzczescie() + drzewko.getD2().getPerLvl());
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + drzewko.getD2().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getD2().setUpgradeLvl(drzewko.getD2().getUpgradeLvl() - 1);
                        drzewko.getD2().setSzczescie(drzewko.getD2().getSzczescie() - drzewko.getD2().getPerLvl());
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() - drzewko.getD2().getPerLvl());
                        break;
                    }
                    break;
                case 30:
                    if (!drzewko.getDl1().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDl1().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDl1().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDl1().setUpgradeLvl(drzewko.getDl1().getUpgradeLvl() + 1);
                        drzewko.getDl1().setKrytyk(drzewko.getDl1().getKrytyk() + drzewko.getDl1().getPerLvl());
                        bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + drzewko.getDl1().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDl1().setUpgradeLvl(drzewko.getDl1().getUpgradeLvl() - 1);
                        drzewko.getDl1().setKrytyk(drzewko.getDl1().getKrytyk() - drzewko.getDl1().getPerLvl());
                        bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - drzewko.getDl1().getPerLvl());
                        break;
                    }
                    break;
                case 29:
                    if (!drzewko.getDl2().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDl2().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDl2().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDl2().setUpgradeLvl(drzewko.getDl2().getUpgradeLvl() + 1);
                        drzewko.getDl2().setSrDef(drzewko.getDl2().getSrDef() + drzewko.getDl2().getPerLvl());
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + drzewko.getDl2().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDl2().setUpgradeLvl(drzewko.getDl2().getUpgradeLvl() - 1);
                        drzewko.getDl2().setSrDef(drzewko.getDl2().getSrDef() - drzewko.getDl2().getPerLvl());
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - drzewko.getDl2().getPerLvl());
                        break;
                    }
                    break;
                case 20:
                    if (!drzewko.getDl3().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDl3().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDl3().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDl3().setUpgradeLvl(drzewko.getDl3().getUpgradeLvl() + 1);
                        drzewko.getDl3().setSilnyNaLudzi(drzewko.getDl3().getSilnyNaLudzi() + drzewko.getDl3().getPerLvl());
                        bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + drzewko.getDl3().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDl3().setUpgradeLvl(drzewko.getDl3().getUpgradeLvl() - 1);
                        drzewko.getDl3().setSilnyNaLudzi(drzewko.getDl3().getSilnyNaLudzi() - drzewko.getDl3().getPerLvl());
                        bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() - drzewko.getDl3().getPerLvl());
                        break;
                    }
                    break;
                case 19:
                    if (!drzewko.getDl4().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDl4().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDl4().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDl4().setUpgradeLvl(drzewko.getDl4().getUpgradeLvl() + 1);
                        drzewko.getDl4().setBlok(drzewko.getDl4().getBlok() + drzewko.getDl4().getPerLvl());
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + drzewko.getDl4().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDl4().setUpgradeLvl(drzewko.getDl4().getUpgradeLvl() - 1);
                        drzewko.getDl4().setBlok(drzewko.getDl4().getBlok() - drzewko.getDl4().getPerLvl());
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - drzewko.getDl4().getPerLvl());
                        break;
                    }
                    break;
                case 18:
                    if (!drzewko.getDl5().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDl5().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDl5().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDl5().setUpgradeLvl(drzewko.getDl5().getUpgradeLvl() + 1);
                        drzewko.getDl5().setHp(drzewko.getDl5().getHp() + drzewko.getDl5().getPerLvl());
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + drzewko.getDl5().getPerLvl());
                        if (Bukkit.getPlayer(wyszkolenieUser.getUuid()) != null) {
                            Bukkit.getPlayer(wyszkolenieUser.getUuid()).setMaxHealth(Bukkit.getPlayer(wyszkolenieUser.getUuid()).getMaxHealth() + drzewko.getDl5().getPerLvl() * 2);
                        }
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDl5().setUpgradeLvl(drzewko.getDl5().getUpgradeLvl() - 1);
                        drzewko.getDl5().setHp(drzewko.getDl5().getHp() - drzewko.getDl5().getPerLvl());
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - drzewko.getDl5().getPerLvl());
                        if (Bukkit.getPlayer(wyszkolenieUser.getUuid()) != null) {
                            Bukkit.getPlayer(wyszkolenieUser.getUuid()).setMaxHealth(Bukkit.getPlayer(wyszkolenieUser.getUuid()).getMaxHealth() - drzewko.getDl5().getPerLvl() * 2);
                        }
                        break;
                    }
                    break;
                case 10:
                    if (!drzewko.getDl6().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDl6().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDl6().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDl6().setUpgradeLvl(drzewko.getDl6().getUpgradeLvl() + 1);
                        drzewko.getDl6().setSrDmg(drzewko.getDl6().getSrDmg() + drzewko.getDl6().getPerLvl());
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + drzewko.getDl6().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDl6().setUpgradeLvl(drzewko.getDl6().getUpgradeLvl() - 1);
                        drzewko.getDl6().setSrDmg(drzewko.getDl6().getSrDmg() - drzewko.getDl6().getPerLvl());
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - drzewko.getDl6().getPerLvl());
                        break;
                    }
                    break;
                case 22:
                    if (!drzewko.getDg1().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDg1().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDg1().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDg1().setUpgradeLvl(drzewko.getDg1().getUpgradeLvl() + 1);
                        drzewko.getDg1().setOdpornoscNaMoby(drzewko.getDg1().getOdpornoscNaMoby() + drzewko.getDg1().getPerLvl());
                        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + drzewko.getDg1().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDg1().setUpgradeLvl(drzewko.getDg1().getUpgradeLvl() - 1);
                        drzewko.getDg1().setOdpornoscNaMoby(drzewko.getDg1().getOdpornoscNaMoby() - drzewko.getDg1().getPerLvl());
                        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - drzewko.getDg1().getPerLvl());
                        break;
                    }
                    break;
                case 13:
                    if (!drzewko.getDg2().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDg2().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDg2().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDg2().setUpgradeLvl(drzewko.getDg2().getUpgradeLvl() + 1);
                        drzewko.getDg2().setPrzeszywka(drzewko.getDg2().getPrzeszywka() + drzewko.getDg2().getPerLvl());
                        bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + drzewko.getDg2().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDg2().setUpgradeLvl(drzewko.getDg2().getUpgradeLvl() - 1);
                        drzewko.getDg2().setPrzeszywka(drzewko.getDg2().getPrzeszywka() - drzewko.getDg2().getPerLvl());
                        bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() - drzewko.getDg2().getPerLvl());
                        break;
                    }
                    break;
                case 4:
                    if (!drzewko.getDg3().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDg3().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDg3().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDg3().setUpgradeLvl(drzewko.getDg3().getUpgradeLvl() + 1);
                        drzewko.getDg3().setBlok(drzewko.getDg3().getBlok() + drzewko.getDg3().getPerLvl());
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + drzewko.getDg3().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDg3().setUpgradeLvl(drzewko.getDg3().getUpgradeLvl() - 1);
                        drzewko.getDg3().setBlok(drzewko.getDg3().getBlok() - drzewko.getDg3().getPerLvl());
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - drzewko.getDg3().getPerLvl());
                        break;
                    }
                    break;
                case 32:
                    if (!drzewko.getDp1().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDp1().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDp1().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDp1().setUpgradeLvl(drzewko.getDp1().getUpgradeLvl() + 1);
                        drzewko.getDp1().setHp(drzewko.getDp1().getHp() + drzewko.getDp1().getPerLvl());
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + drzewko.getDp1().getPerLvl());
                        if (Bukkit.getPlayer(wyszkolenieUser.getUuid()) != null) {
                            Bukkit.getPlayer(wyszkolenieUser.getUuid()).setMaxHealth(Bukkit.getPlayer(wyszkolenieUser.getUuid()).getMaxHealth() + drzewko.getDl5().getPerLvl() * 2);
                        }
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDp1().setUpgradeLvl(drzewko.getDp1().getUpgradeLvl() - 1);
                        drzewko.getDp1().setHp(drzewko.getDp1().getHp() - drzewko.getDp1().getPerLvl());
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - drzewko.getDp1().getPerLvl());
                        if (Bukkit.getPlayer(wyszkolenieUser.getUuid()) != null) {
                            Bukkit.getPlayer(wyszkolenieUser.getUuid()).setMaxHealth(Bukkit.getPlayer(wyszkolenieUser.getUuid()).getMaxHealth() - drzewko.getDl5().getPerLvl() * 2);
                        }
                        break;
                    }
                    break;
                case 33:
                    if (!drzewko.getDp2().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDp2().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDp2().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDp2().setUpgradeLvl(drzewko.getDp2().getUpgradeLvl() + 1);
                        drzewko.getDp2().setDodatkowyDmg(drzewko.getDp2().getDodatkowyDmg() + drzewko.getDp2().getPerLvl());
                        bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + drzewko.getDp2().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDp2().setUpgradeLvl(drzewko.getDp2().getUpgradeLvl() - 1);
                        drzewko.getDp2().setDodatkowyDmg(drzewko.getDp2().getDodatkowyDmg() - drzewko.getDp2().getPerLvl());
                        bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() - drzewko.getDp2().getPerLvl());
                        break;
                    }
                    break;
                case 24:
                    if (!drzewko.getDp3().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDp3().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDp3().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDp3().setUpgradeLvl(drzewko.getDp3().getUpgradeLvl() + 1);
                        drzewko.getDp3().setSilnyNaMoby(drzewko.getDp3().getSilnyNaMoby() + drzewko.getDp3().getPerLvl());
                        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + drzewko.getDp3().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDp3().setUpgradeLvl(drzewko.getDp3().getUpgradeLvl() - 1);
                        drzewko.getDp3().setSilnyNaMoby(drzewko.getDp3().getSilnyNaMoby() - drzewko.getDp3().getPerLvl());
                        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - drzewko.getDp3().getPerLvl());
                        break;
                    }
                    break;
                case 25:
                    if (!drzewko.getDp4().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDp4().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDp4().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDp4().setUpgradeLvl(drzewko.getDp4().getUpgradeLvl() + 1);
                        drzewko.getDp4().setOdpornoscNaGraczy(drzewko.getDp4().getOdpornoscNaGraczy() + drzewko.getDp4().getPerLvl());
                        bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + drzewko.getDp4().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDp4().setUpgradeLvl(drzewko.getDp4().getUpgradeLvl() - 1);
                        drzewko.getDp4().setOdpornoscNaGraczy(drzewko.getDp4().getOdpornoscNaGraczy() - drzewko.getDp4().getPerLvl());
                        bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() - drzewko.getDp4().getPerLvl());
                        break;
                    }
                    break;
                case 26:
                    if (!drzewko.getDp5().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDp5().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDp5().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDp5().setUpgradeLvl(drzewko.getDp5().getUpgradeLvl() + 1);
                        drzewko.getDp5().setSzczescie(drzewko.getDp5().getSzczescie() + drzewko.getDp5().getPerLvl());
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + drzewko.getDp5().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDp5().setUpgradeLvl(drzewko.getDp5().getUpgradeLvl() - 1);
                        drzewko.getDp5().setSzczescie(drzewko.getDp5().getSzczescie() - drzewko.getDp5().getPerLvl());
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() - drzewko.getDp5().getPerLvl());
                        break;
                    }
                    break;
                case 16:
                    if (!drzewko.getDp6().isUnlocked()) {
                        if (click == ClickType.SHIFT_LEFT) {
                            drzewko.getDp6().setUnlocked(true);
                            break;
                        }
                    } else {
                        if (click == ClickType.SHIFT_RIGHT) {
                            drzewko.getDp6().setUnlocked(true);
                            break;
                        }
                        break;
                    }
                    if (click == ClickType.LEFT) {
                        drzewko.getDp6().setUpgradeLvl(drzewko.getDp6().getUpgradeLvl() + 1);
                        drzewko.getDp6().setSrDef(drzewko.getDp6().getSrDef() + drzewko.getDp6().getPerLvl());
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + drzewko.getDp6().getPerLvl());
                        break;
                    }
                    if (click == ClickType.RIGHT) {
                        drzewko.getDp6().setUpgradeLvl(drzewko.getDp6().getUpgradeLvl() - 1);
                        drzewko.getDp6().setSrDef(drzewko.getDp6().getSrDef() - drzewko.getDp6().getPerLvl());
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - drzewko.getDp6().getPerLvl());
                        break;
                    }
                    break;
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataWyszkolenie(wyszkolenieUser.getUuid(), wyszkolenieUser);
                rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
            });
            rpgcore.getUstawieniaKontaManager().openDrzewkoRozwoju(player, wyszkolenieUser);
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLvlIExpClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();
        final ClickType click = e.getClick();

        if (title.contains("Poziom i EXP - ")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;

            final User user = rpgcore.getUserManager().find(this.getUUIDFromTitle(title));
            double value = 0;
            switch (slot) {
                case 11:
                    switch (click) {
                        case LEFT:
                            value = 1;
                            break;
                        case RIGHT:
                            value = -1;
                            break;
                        case SHIFT_LEFT:
                            value = 10;
                            break;
                        case SHIFT_RIGHT:
                            value = -10;
                            break;
                    }
                    user.setLvl(user.getLvl() + (int) value);
                    user.setExp(0);
                    break;
                case 13:
                    switch (click) {
                        case LEFT:
                            value = 1_000;
                            break;
                        case RIGHT:
                            value = -1_000;
                            break;
                        case SHIFT_LEFT:
                            value = 10_000;
                            break;
                        case SHIFT_RIGHT:
                            value = -10_000;
                            break;
                        case NUMBER_KEY:
                            value = 100_000;
                            break;
                        case MIDDLE:
                            value = 100;
                            break;
                    }
                    user.setExp(user.getExp() + DoubleUtils.round(value, 0));
                    break;
                case 15:
                    switch (click) {
                        case LEFT:
                            value = 1;
                            break;
                        case RIGHT:
                            value = -1;
                            break;
                        case SHIFT_LEFT:
                            value = 10;
                            break;
                        case SHIFT_RIGHT:
                            value = -10;
                            break;
                        case NUMBER_KEY:
                            value = 0.1;
                            break;
                    }
                    double exp = DoubleUtils.round(value * rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1) / 100, 2);
                    user.setExp(user.getExp() + exp);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            rpgcore.getUstawieniaKontaManager().openLvlIExp(player, user.getId());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKasaIHSClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();
        final ClickType click = e.getClick();

        if (title.contains("$ i HS - ")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            final User user = rpgcore.getUserManager().find(this.getUUIDFromTitle(title));
            double value = 0;
            switch (slot) {
                case 11:
                    switch (click) {
                        case LEFT:
                            value = 100;
                            break;
                        case RIGHT:
                            value = -100;
                            break;
                        case SHIFT_LEFT:
                            value = 1_000;
                            break;
                        case SHIFT_RIGHT:
                            value = -1_000;
                            break;
                        case NUMBER_KEY:
                            value = 100_000;
                            break;
                        case MIDDLE:
                            value = 10_000;
                            break;
                    }
                    user.setKasa(user.getKasa() + value);
                    break;
                case 15:
                    switch (click) {
                        case LEFT:
                            value = 1;
                            break;
                        case RIGHT:
                            value = -5;
                            break;
                        case SHIFT_LEFT:
                            value = 10;
                            break;
                        case SHIFT_RIGHT:
                            value = -10;
                            break;
                        case MIDDLE:
                            value = 100;
                            break;
                    }
                    user.setHellcoins(user.getHellcoins() + (int) value);
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            rpgcore.getUstawieniaKontaManager().openKasaIHS(player, user.getId());
        }
    }

    private UUID getUUIDFromTitle(final String title) {
        final String[] split = title.split(" ");
        return UUID.fromString(split[split.length - 1]);
    }

    private int getValueFromClick(final ClickType click) {
        switch (click) {
            case LEFT:
                return 1;
            case RIGHT:
                return -1;
            case SHIFT_LEFT:
                return 10;
            case SHIFT_RIGHT:
                return -10;
            default:
                return 0;
        }
    }

    private double getValueFromClickKey(final ClickType click) {
        switch (click) {
            case LEFT:
                return 1;
            case RIGHT:
                return -1;
            case SHIFT_LEFT:
                return 10;
            case SHIFT_RIGHT:
                return -10;
            case NUMBER_KEY:
                return 0.1;
            default:
                return 0;
        }
    }
}
