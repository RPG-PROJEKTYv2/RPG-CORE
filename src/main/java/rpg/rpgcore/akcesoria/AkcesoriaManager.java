package rpg.rpgcore.akcesoria;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class AkcesoriaManager {

    private final RPGCORE rpgcore;
    private final Map<UUID, AkcesoriaObject> userMap;

    private final Map<UUID, Inventory> akcesoriaMap = new HashMap<>();

    public AkcesoriaManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllAkcesoria();
    }

    public void openAkcesoriaGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final AkcesoriaUser user = rpgcore.getAkcesoriaManager().find(uuid).getAkcesoriaUser();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lAkcesoria"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        if (user.getTarcza().length() > 0) {
            gui.setItem(1, Utils.deserializeItem(user.getTarcza()));
        } else {
            gui.setItem(1, this.noAkcesoriaItem("Tarczy"));
        }
        if (user.getMedalion().length() > 0) {
            gui.setItem(2, Utils.deserializeItem(user.getMedalion()));
        } else {
            gui.setItem(2, this.noAkcesoriaItem("Medalionu"));
        }
        if (user.getPas().length() > 0) {
            gui.setItem(3, Utils.deserializeItem(user.getPas()));
        } else {
            gui.setItem(3, this.noAkcesoriaItem("Pasa"));
        }
        if (user.getKolczyki().length() > 0) {
            gui.setItem(4, Utils.deserializeItem(user.getKolczyki()));
        } else {
            gui.setItem(4, this.noAkcesoriaItem("Kolczykow"));
        }
        if (user.getSygnet().length() > 0) {
            gui.setItem(5, Utils.deserializeItem(user.getSygnet()));
        } else {
            gui.setItem(5, this.noAkcesoriaItem("Syngetu"));
        }
        if (user.getEnergia().length() > 0) {
            gui.setItem(6, Utils.deserializeItem(user.getEnergia()));
        } else {
            gui.setItem(6, this.noAkcesoriaItem("Energii"));
        }
        if (user.getZegarek().length() > 0) {
            gui.setItem(7, Utils.deserializeItem(user.getZegarek()));
        } else {
            gui.setItem(7, this.noAkcesoriaItem("Zegarka"));
        }

        player.openInventory(gui);
    }


    public final ItemStack noAkcesoriaItem(final String nazwaAkcesorium) {
        return new ItemBuilder(Material.IRON_FENCE).setName("&c&lBrak " + nazwaAkcesorium).addGlowing().toItemStack().clone();
    }



    public void loadAllAkceBonus(final UUID uuid) {

/*
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        ItemStack akce;

        for (int i = 0; i < 7 ; i++) {
            akce = akceGUI.getItem(10 + i);
            if (akce.getType() != Material.BARRIER) {

                if (akce.getType() == Material.ITEM_FRAME) {
                    rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                    System.out.println("Tarcza blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    System.out.println("Tarcza dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                }

                if (akce.getType() == Material.STORAGE_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerPrzeszywka(uuid, rpgcore.getPlayerManager().getPlayerPrzeszywka(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Przeszycie Bloku"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                    System.out.println("Naszyjnik dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                }


                if (akce.getType() == Material.POWERED_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerKryt(uuid, rpgcore.getPlayerManager().getPlayerKryt(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Cios Krytyczny"));
                    rpgcore.getPlayerManager().updatePlayerSrednie(uuid, rpgcore.getPlayerManager().getPlayerSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Srednie Obrazenia"));
                }

                if (akce.getType() == Material.HOPPER_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Dodatkowe HP"));
                    rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Silny przeciwko Ludziom"));
                }

                if (akce.getType() == Material.EXPLOSIVE_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Dodatkowe HP"));
                    System.out.println("Pierek blok -" + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                }

                if (akce.getType() == Material.MINECART) {
                    rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerMinusSrednie(uuid, rpgcore.getPlayerManager().getPlayerMinusSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Srednie Obrazenia"));
                    System.out.println("energia blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                }

                if (akce.getType() == Material.WATCH) {
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                    rpgcore.getPlayerManager().updatePlayerSilnyNaMoby(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaMoby(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Silny przeciwko Potworom"));
                    rpgcore.getPlayerManager().updatePlayerMinusDef(uuid, rpgcore.getPlayerManager().getPlayerMinusDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrona"));
                    System.out.println("Zegarek dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                }

            }
        }*/
    }


    public AkcesoriaObject find(final UUID uuid) {
        userMap.computeIfAbsent(uuid,k -> new AkcesoriaObject(uuid));
        return this.userMap.get(uuid);
    }

    public void add(final UUID uuid, final AkcesoriaObject akcesoriaObject) {
        this.userMap.put(uuid, akcesoriaObject);
    }

    public ImmutableSet<AkcesoriaObject> getAkcesoriaObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

}
