package rpg.rpgcore.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.armor.ArmorEffectsHelper;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.entities.PetArmorStand.PetArmorStand;
import rpg.rpgcore.pets.objects.Pet;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final World world = Bukkit.getWorld("1-10map");
    private final List<Location> spawnLocations1_10 = Arrays.asList(
            new Location(world, -68.5, 76, -296.5),
            new Location(world, -95.5, 76, -346.5),
            new Location(world, -160.5, 76, -306.5),
            new Location(world, -28.5, 76, -283.5),
            new Location(world, 31.5, 76, -290.5),
            new Location(world, 51.5, 76, -210.5),
            new Location(world, 83.5, 78, -157.5),
            new Location(world, 96.5, 78, -61.5)
    );


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinListener(final PlayerJoinEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        final String playerName = player.getName();

        e.setJoinMessage(null);

        if (!rpgcore.getUserManager().isUser(uuid)) {
            if (rpgcore.getUserManager().isUserName(e.getPlayer().getName())) {
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> e.getPlayer().kickPlayer(Utils.format(
                        Utils.CLEANSERVERNAME + "\n" +
                                "&c&lCos poszlo nie tak! :(\n\n" +
                                "&8Skontaktuj Sie z &4Administracja &8z ss'em tego bledu.\n" +
                                "&8(&c&lKod Bledu: #USER_ALREADY_IN_DB&8)\n" +
                                "&8UUID: " + rpgcore.getUserManager().find(e.getPlayer().getName()).getId().toString())), 1L);
                return;
            }
            player.getOpenInventory().getTopInventory().clear();

            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[4]);
            player.getEnderChest().clear();

            player.getInventory().addItem(ItemHelper.createArmor("&8Helm Poczatkujacego", Material.LEATHER_HELMET, 1, 0));
            player.getInventory().addItem(ItemHelper.createArmor("&8Zbroja Poczatkujacego", Material.LEATHER_CHESTPLATE, 1, 0));
            player.getInventory().addItem(ItemHelper.createArmor("&8Spodnie Poczatkujacego", Material.LEATHER_LEGGINGS, 1, 0));
            player.getInventory().addItem(ItemHelper.createArmor("&8Buty Poczatkujacego", Material.LEATHER_BOOTS, 1, 0));
            player.getInventory().addItem(ItemHelper.createSword("&7Startowa Maczeta", Material.WOOD_SWORD, 1, 0, false));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().createPlayer(player, uuid, player.getName(), player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getEnderChest().getContents());
                rpgcore.getBackupMongoManager().getPool().firstJoin(uuid);
            });

            player.setLevel(1);
            player.setExp(0);
            player.teleport(rpgcore.getSpawnManager().getSpawn());
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.kickPlayer(Utils.format(Utils.CLEANSERVERNAME + "\n&aPomyslnie stworzono twoje konto!\n&aWejdz Jeszcze Raz i daj sie wciagnac w emocjonujaca rywalizacje")), 10L);
            return;
        }

        player.getOpenInventory().getTopInventory().clear();
        player.setWalkSpeed(0.2F);


        final User user = rpgcore.getUserManager().find(uuid);

        user.incrementServerJoins();

        if (user.isTworca() && !rpgcore.getKodTworcyManager().isTworca(user.getId()))
            rpgcore.getKodTworcyManager().addTworca(user.getId());

        if (player.hasPermission("mv.bypass.gamemode.*")) {
            player.removeAttachment(player.addAttachment(rpgcore, "mv.bypass.gamemode.*", false));
        }


        if (user.getLvl() <= 5) {
            player.teleport(spawnLocations1_10.get(ChanceHelper.getRandInt(0, spawnLocations1_10.size() - 1)));
        } else {
            player.teleport(rpgcore.getSpawnManager().getSpawn());
        }

        user.setHellCodeLogin(false);
        user.setAdminCodeLogin(false);

        if (!user.getRankUser().isHighStaff()) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (rpgcore.getChatManager().find(online.getUniqueId()).isJoinMessageEnabled()) {
                    online.sendMessage(Utils.format("&8[&a+&8] &7" + playerName + " &8(" + Bukkit.getOnlinePlayers().size() + "/1000)"));
                }
            }
        }

        if (user.getInventoriesUser() != null) {
            if (user.getInventoriesUser().getArmor() != null)
                player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
            if (user.getInventoriesUser().getInventory() != null) player.getInventory().clear();
            if (user.getInventoriesUser().getEnderchest() != null) player.getEnderChest().clear();
        }


        try {
            if (user.getInventoriesUser() != null && user.getInventoriesUser().getArmor() != null && user.getInventoriesUser().getInventory() != null && user.getInventoriesUser().getEnderchest() != null
                    && !user.getInventoriesUser().getInventory().isEmpty() && !user.getInventoriesUser().getEnderchest().isEmpty() && !user.getInventoriesUser().getArmor().isEmpty()) {
                player.getInventory().setArmorContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getArmor()));
                player.getInventory().setContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory()));
                player.getEnderChest().setContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getEnderchest()));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        rpgcore.getUserSaveManager().savePlayer(player, uuid);
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        final int playerLvl = user.getLvl();
        final double playerExp = user.getExp() / rpgcore.getLvlManager().getExpForLvl(playerLvl + 1);

        player.setLevel(playerLvl);
        player.setExp((float) playerExp);

        rpgcore.getLvlManager().updateLvlBelowName(player, playerName, playerLvl);

        player.setMaxHealth(rpgcore.getBonusesManager().find(uuid).getBonusesUser().getDodatkowehp() * 2);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);

        NameTagUtil.setPlayerNameTag(player, "updatePrefix");

        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        int dmg = 0;
        if (!rpgcore.getDodatkiManager().find(uuid).getBony().getDmgMetiny().getType().equals(Material.AIR)) {
            switch (Utils.removeColor(rpgcore.getDodatkiManager().find(uuid).getBony().getDmgMetiny().getItemMeta().getDisplayName())) {
                case "Bon Zwiekszonych Obrazen W Kamienie Metin +2":
                    dmg = 2;
                    break;
                case "Bon Zwiekszonych Obrazen W Kamienie Metin +3":
                    dmg = 3;
                    break;
                case "Bon Zwiekszonych Obrazen W Kamienie Metin +5":
                    dmg = 5;
                    break;
            }
        }
        bonuses.getBonusesUser().setDmgMetiny(rpgcore.getMetinologNPC().find(uuid).getMetinologUser().getDmgMetiny() + dmg);


        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&fWitaj na &4Hell&8RPG&f!", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("", 5, 20, 5)));
        if (!player.hasPlayedBefore()) {
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWitamy Cie, na serwerze typu &6metin2 &aw minecraft. Pod &6/pomoc &aznajdziesz najwazniejsze informacje i przydatne komendy."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZachecamy tez do dolaczenia na nasz server discord &6dc.hellrpg.pl &ana ktorym znajdziesz giveaway'e, informacje o eventach, nadchodzacych aktualizacjach oraz kanaly pomocy."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZyczymy milej gry i udanej rywalizacji! &cZespol Hellrpg.pl"));
            }, 20L);

        }

        TabManager.addPlayer(player);
        TabManager.add(player);
        TabManager.update(player.getUniqueId());
        ArmorEffectsHelper.addEffectsArmor(player);


        final Pet pet = rpgcore.getPetyManager().findActivePet(uuid).getPet();

        if (pet.getItem() != null) {
            EntityTypes.spawnEntity(new PetArmorStand(((CraftWorld) player.getLocation().getWorld()).getHandle(), player), player.getUniqueId(), player.getLocation(), pet.getItem().clone().getItemMeta().getDisplayName()); //.substring(0, item.clone().getItemMeta().getDisplayName().indexOf(" "))
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> EntityTypes.addEquipment(EntityTypes.getEntity(player.getUniqueId()), pet.getItem().clone()), 20L);
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLoginListener(final AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();
        if (rpgcore.getSerwerWhiteListManager().getWhitelist().isEnabled() && !rpgcore.getSerwerWhiteListManager().isWhiteListed(e.getUniqueId())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Utils.format("&4&lHELL&8&lRPG.PL\n\n" +
                    "&7Witaj &6" + e.getName() + "&7!\n" +
                    "&7Niestety aktualnie serwer jest nie dostepny dla graczy\n" +
                    "&7Powod: &6Przerwa Techniczna\n" +
                    "\n" +
                    "&7Zapraszamy ponownie wkrotce!\n\n" +
                    "&8Po wiecej informacji zapraszamy na\n" +
                    "  &6dc.hellrpg.pl  &8|  &6fb.com/HELLRPGPL"));
            return;
        }


        if (rpgcore.getUserManager().find(uuid) != null && rpgcore.getUserManager().find(uuid).isBanned()) {

            final String[] banInfo = rpgcore.getUserManager().find(uuid).getBanInfo().split(";");

            if (!banInfo[2].equals("Pernamentny")) {
                try {
                    final Date teraz = new Date();
                    final Date dataWygasnieciaBana = Utils.dateFormat.parse(banInfo[2]);

                    if (teraz.after(dataWygasnieciaBana)) {
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().unBanPlayer(e.getUniqueId()));
                        return;
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Utils.banMessage(banInfo[0], banInfo[1], banInfo[2], banInfo[3]));
        }
    }
}
