package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class PrzekletyCzarnoksieznikBossManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, PrzekletyCzarnoksieznikUser> userMap;

    public PrzekletyCzarnoksieznikBossManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllPrzekletyCzarnoksieznikUser();
    }

    public void LosBONUS(final Player player) {
        UUID uuid = player.getUniqueId();
        final PrzekletyCzarnoksieznikUser user = this.find(uuid);
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - user.getDmgMOB());
        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - user.getDefMOB());
        final int defMOB = ChanceHelper.getRandInt(Bonus70_80.BS.getDefmobyMIN(), Bonus70_80.BS.getDefmobyMAX());
        final int dmgMOB = ChanceHelper.getRandInt(Bonus70_80.BS.getDmgmobyMIN(), Bonus70_80.BS.getDmgmobyMAX());
        user.setDefMOB(defMOB);
        user.setDmgMOB(dmgMOB);
        Bukkit.broadcastMessage(Utils.format("&8&l>> &5&lPrzekleta Moc Czarnoksieznika &8&l<<"));
        Bukkit.broadcastMessage(Utils.format("&e" + player.getName() + " &7wylosowal swoja &5&lPrzekleta Moc&7."));
        Bukkit.broadcastMessage(Utils.format("&cSilny Na Potwory: &e+" + dmgMOB + "%&7/&6" + Bonus70_80.BS.getDmgmobyMAX() + "%"));
        Bukkit.broadcastMessage(Utils.format("&cDefensywa Przeciwko Potworom: &e+" + defMOB + "%&7/&6" + Bonus70_80.BS.getDefmobyMAX() + "%"));
        Bukkit.broadcastMessage(" ");
        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + dmgMOB);
        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + defMOB);
        player.getInventory().removeItem(new ItemBuilder(Bossy.I70_80_BONUS.getItemStack().clone()).setAmount(1).toItemStack());
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
            RPGCORE.getInstance().getMongoManager().saveDataPrzekletyCzarnoksieznikEffect(uuid, RPGCORE.getInstance().getPrzekletyCzarnoksieznikBossManager().find(uuid));
            RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
        });
    }
    public void openWyborGUI(final Player player) {
        UUID uuid = player.getUniqueId();
        final PrzekletyCzarnoksieznikUser user = this.find(uuid);
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&5&lPrzeklety Odlamek &7- wybor"));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }
        gui.setItem(3, new ItemBuilder(Material.WOOL, 1, (short)5).setName("&4&lLosuj").setLore(Arrays.asList(
                "",
                "&7Kliknij aby wylosowac bonusy &5&lPrzekletej Mocy&7.",
                "",
                "&f&lTwoje aktualne bonusy:",
                "&cSilny Na Potwory: &e+" + user.getDmgMOB() + "%&7/&6" + Bonus70_80.BS.getDmgmobyMAX() + "%",
                "&cDefensywa Przeciwko Potworom: &e+" + user.getDefMOB() + "&7/&6" + Bonus70_80.BS.getDefmobyMAX() + "%" ,
                ""

        )).toItemStack().clone());
        gui.setItem(8, new ItemBuilder(Material.BARRIER).setName("&cAnuluj").toItemStack().clone());

        player.openInventory(gui);
    }

    public PrzekletyCzarnoksieznikUser find(final UUID uuid) {
        return userMap.get(uuid);
    }
    public void add(final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser) {
        userMap.put(przekletyCzarnoksieznikUser.getUuid(), przekletyCzarnoksieznikUser);
    }
    public ImmutableSet<PrzekletyCzarnoksieznikUser> getPrzekletyCzarnoksieznikUser() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
