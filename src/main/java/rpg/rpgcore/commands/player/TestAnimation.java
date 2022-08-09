package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestAnimation implements CommandExecutor {

    private final ItemBuilder item1 = new ItemBuilder(Material.DIRT);
    private final ItemBuilder item2 = new ItemBuilder(Material.STONE);
    private final ItemBuilder item3 = new ItemBuilder(Material.DIAMOND);
    private final ItemBuilder item4 = new ItemBuilder(Material.DIAMOND_SWORD);
    private final ItemBuilder item5 = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
    private final ItemBuilder item6 = new ItemBuilder(Material.GOLDEN_APPLE);
    private final ItemBuilder item7 = new ItemBuilder(Material.BRICK);
    private final ItemBuilder item8 = new ItemBuilder(Material.BEACON);
    private final ItemBuilder item9 = new ItemBuilder(Material.BOOK);
    private final ItemBuilder item10 = new ItemBuilder(Material.GRAVEL);
    private final ItemBuilder item11 = new ItemBuilder(Material.EXP_BOTTLE);
    private final ItemBuilder item12 = new ItemBuilder(Material.MINECART);
    private final ItemBuilder item13 = new ItemBuilder(Material.GRASS);
    private final ItemBuilder item14 = new ItemBuilder(Material.EXPLOSIVE_MINECART);
    private final ItemBuilder item15 = new ItemBuilder(Material.HOPPER_MINECART);
    private final ItemBuilder item16 = new ItemBuilder(Material.POWERED_MINECART);
    private final ItemBuilder item17 = new ItemBuilder(Material.STORAGE_MINECART);
    private final ItemBuilder item18 = new ItemBuilder(Material.COMMAND_MINECART);
    private final ItemBuilder item19 = new ItemBuilder(Material.COBBLESTONE);
    private final ItemBuilder item20 = new ItemBuilder(Material.COBBLE_WALL);
    private final ItemBuilder item21 = new ItemBuilder(Material.MOSSY_COBBLESTONE);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);
    private final ItemBuilder select = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4);

    private final RPGCORE rpgcore;
    private final List<ItemStack> listaItemow = new ArrayList<>();
    int task = 0;

    public TestAnimation(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        final Player player = (Player) sender;


        if (args.length == 0) {
            player.openInventory(this.testAnimationGUI());
            this.runAnimation(player.getOpenInventory().getTopInventory(), player);
        }

        return false;
    }


    private Inventory testAnimationGUI() {
        final Inventory testAnimation = Bukkit.createInventory(null, 27, Utils.format("&4&lTestowa Animacja"));
        listaItemow.add(item1.toItemStack());
        listaItemow.add(item2.toItemStack());
        listaItemow.add(item3.toItemStack());
        listaItemow.add(item4.toItemStack());
        listaItemow.add(item5.toItemStack());
        listaItemow.add(item6.toItemStack());
        listaItemow.add(item7.toItemStack());
        listaItemow.add(item8.toItemStack());
        listaItemow.add(item9.toItemStack());
        listaItemow.add(item10.toItemStack());
        listaItemow.add(item11.toItemStack());
        listaItemow.add(item12.toItemStack());
        listaItemow.add(item13.toItemStack());
        listaItemow.add(item14.toItemStack());
        listaItemow.add(item15.toItemStack());
        listaItemow.add(item16.toItemStack());
        listaItemow.add(item17.toItemStack());
        listaItemow.add(item18.toItemStack());
        listaItemow.add(item19.toItemStack());
        listaItemow.add(item20.toItemStack());
        listaItemow.add(item21.toItemStack());
        fill.setName(" ");
        fill.hideFlag();

        for (int i = 0; i < testAnimation.getSize(); i++) {
            testAnimation.setItem(i, fill.toItemStack());
            if (i == 4 || i == 22) {
                testAnimation.setItem(i, select.toItemStack());
            }
        }

        final Random random = new Random();

        for (int i = 9; i < 18; i++) {
            testAnimation.setItem(i, this.rollRandomItem());
        }

        return testAnimation;
    }

    private ItemStack rollRandomItem() {
        final Random random = new Random();
        final int item = random.nextInt(10);

        return listaItemow.get(item);
    }

    private void runAnimation(final Inventory inv, final Player player) {
        final Random random = new Random();
        final int[] max = {0};
        task = player.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> {

            if (max[0] == 30) {
                stopAnimation(inv, player);
                return;
            }

            final int item = random.nextInt(250)+1;
            if (item <= 10) {
                stopAnimation(inv, player);
                return;
            }
            for (int i = 17; i >= 9; i--) {
                inv.setItem(i, inv.getItem(i-1));
                if (i == 9) {
                    inv.setItem(i, this.rollRandomItem());
                }
            }
            player.updateInventory();
            max[0] += 1;
        }, 10L, 10L);


    }


    private void stopAnimation(final Inventory inv, final Player player) {
        player.getServer().getScheduler().cancelTask(task);

        player.getInventory().addItem(inv.getItem(13));
        player.closeInventory();

    }
}
