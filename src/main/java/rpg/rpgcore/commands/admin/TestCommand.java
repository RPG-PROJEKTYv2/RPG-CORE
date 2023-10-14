package rpg.rpgcore.commands.admin;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;


public class TestCommand extends CommandAPI {

    public TestCommand() {
        super("test");
        this.setRankLevel(RankType.DEV);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final RPGCORE rpgcore = RPGCORE.getInstance();

        /*final EntityInsentient ksiaze = (EntityInsentient) EntityTypes.spawnEntity(new KsiazeMroku(((org.bukkit.craftbukkit.v1_8_R3.CraftWorld) player.getWorld()).getHandle()), UUID.randomUUID(), player.getLocation(), "&c&lKsiaze Mroku");

        //TODO Do poprawy - FIREBALLE wybuchaja o siebie!
        EntityPigZombie ksiazePig = (EntityPigZombie) ksiaze;
        ksiaze.goalSelector.a(2, new PathfinderGoalMeleeAttack(ksiazePig, EntityHuman.class, 1.0, false));
        ksiaze.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(ksiazePig, 1.0));
        ksiaze.goalSelector.a(7, new PathfinderGoalRandomStroll(ksiazePig, 1.0));
        ksiaze.goalSelector.a(8, new PathfinderGoalLookAtPlayer(ksiaze, EntityHuman.class, 8.0F));
        ksiaze.goalSelector.a(8, new PathfinderGoalRandomLookaround(ksiaze));
        ksiaze.targetSelector.a(1, new PathfinderGoalHurtByTarget(ksiazePig, false));
        ksiaze.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(ksiazePig, EntityHuman.class, true));*/
        for (Player player1 : Bukkit.getWorld("zamekNieskonczonosci").getPlayers()) {
            if (RPGCORE.getInstance().getZamekNieskonczonosciManager().partyLeader.getUniqueId().equals(player1.getUniqueId())) {
                RPGCORE.getInstance().getZamekNieskonczonosciManager().endDungeon(RPGCORE.getInstance().getPartyManager().find(player1.getUniqueId()));
            }
        }
        /*player.sendMessage("zaczeto czyszczenie bazy");
        for (User user : rpgcore.getUserManager().getUserObjects()) {
            if (user.getId().toString().equals("dd3d637b-aff4-4fa5-8484-d120ed492d43") || user.getId().toString().equals("c166a38d-6ddf-47cb-8aed-2b05fb502051") ||
            user.getId().toString().equals("672d510e-083b-39f8-9681-4d8bc892586d") || user.getId().toString().equals("4d335d52-df9f-479c-8d0a-57de4a4cb2fe")) continue;
            player.sendMessage("wyczyszczono: " + user.getName());
            rpgcore.getMongoManager().clearDatabase(user.getId());
        }
        player.sendMessage("zakonczono czyszczenie bazy");*/

    }
}
