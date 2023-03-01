package rpg.rpgcore.npc.test;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.test.enums.TestMissions;
import rpg.rpgcore.utils.Utils;

import java.util.Map;
import java.util.UUID;

public class TestNPC {

    // WAZNE
    // SCHEMAT NPC GLOWNE PLIKI / RPGCORE / MONGOMANAGER.CLASS / MONGO CONNECTION POOL


    //private final Map<UUID, TestUser> userMap;


    public TestNPC(final RPGCORE rpgcore) {
        //ŁADOWANIE Z BAZY DANYCH ------> PATRZ MongoManager.class
        // PATRZ TEZ RPGCORE.class
        //this.userMap = rpgcore.getMongoManager().loadAllTestUser();
    }

    //OTWIERANIE GUI
    public void openTestGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&c&lTest"));
        //final TestUser user = find(player.getUniqueId());

        //gui.setItem(4, TestMissions.getMission(user.getMission()).getMissionItem());
    }

    //TE 3 SA W KAZDYM NPC PODSTAWOWE, PO PROSTU ZMIENIASZ CO MA ZWRACAC I CO MA POBIERAC
    //TU NP. DLA DUSZOLOGA BYU BYLO public DuszologUser ...
    /*public TestUser find(final UUID uuid) {
        return userMap.get(uuid);
    }*/
    // TU DLA DUSZOLOGA BY BYLO public void add(final DuszologUser user) ...
    /*public void add(final TestUser testUser) {
        userMap.put(testUser.getUuid(), testUser);
    }*/
    //TU DLA DUSZOLOGA BY BYLO public ImmutableSet<DuszologUser> getDuszologUsers() ...
    /*public ImmutableSet<TestUser> getTestUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }*/




}
