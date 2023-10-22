package rpg.rpgcore.npc.alchemik.enums;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;
import rpg.rpgcore.utils.globalitems.npc.AlchemikItems;

import java.util.Arrays;
import java.util.List;

/*
LVL ALCH NEWLVL   CHANCE
1    10   55        80
2    15   60        70
3    20   65        55
4    25   70        40
5    30   80       33.3
6    35   90       24.5
7    40   100      12.5
8    45   115      6.25
9    50   130      2.5
*/


@Getter
public enum KrysztalUpgrades {
    KP0("Potegi", 0, 50, 5, 0, 0, 100, null, 0),
    KP1("Potegi", 1, 55, 10, 0.25, 1, 80, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(3).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(4).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(2).toItemStack()
    ), 10_000_000),
    KP2("Potegi", 2, 60, 15, 0.75, 3, 70, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(5).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(4).toItemStack()
    ), 15_000_000),
    KP3("Potegi", 3, 65, 20, 1, 3, 55, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(16).toItemStack()
    ), 25_000_000),
    KP4("Potegi", 4, 70, 25, 1, 8, 40, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(16).toItemStack()
    ), 40_000_000),
    KP5("Potegi", 5, 80, 30, 2, 20, 33.3, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(16).toItemStack()
    ), 50_000_000),
    KP6("Potegi", 6, 90, 35, 2, 25, 24.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(32).toItemStack()
    ), 75_000_000),
    KP7("Potegi", 7, 100, 40, 4, 25, 12.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(48).toItemStack()
    ), 125_000_000),
    KP8("Potegi", 8, 115, 45, 7, 75, 6.25, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(32).toItemStack()
    ), 250_000_000),
    KP9("Potegi", 9, 130, 50, 10, 125, 2.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(256).toItemStack(),
            new ItemBuilder(AlchemikItems.I4.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).setAmount(64).toItemStack()
    ), 500_000_000),


    KO0("Obrony", 0, 50, 5, 0, 0, 100, null, 0),
    KO1("Obrony", 1, 55, 10, 0.5, 0.25, 80, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(3).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(4).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(2).toItemStack()
    ), 10_000_000),
    KO2("Obrony", 2, 60, 15, 1, 0.5, 70, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(5).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(4).toItemStack()
    ), 15_000_000),
    KO3("Obrony", 3, 65, 20, 1.5, 0.75, 55, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(16).toItemStack()
    ), 25_000_000),
    KO4("Obrony", 4, 70, 25, 2, 1.5, 40, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(16).toItemStack()
    ), 40_000_000),
    KO5("Obrony", 5, 80, 30, 4, 1.5, 33.3, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(16).toItemStack()
    ), 50_000_000),
    KO6("Obrony", 6, 90, 35, 4, 2.5, 24.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(32).toItemStack()
    ), 75_000_000),
    KO7("Obrony", 7, 100, 40, 7, 3, 12.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(48).toItemStack()
    ), 125_000_000),
    KO8("Obrony", 8, 115, 45, 8, 4, 6.25, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(32).toItemStack()
    ), 250_000_000),
    KO9("Obrony", 9, 130, 50, 10, 5, 2.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(256).toItemStack(),
            new ItemBuilder(AlchemikItems.I5.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).setAmount(64).toItemStack()
    ), 500_000_000),


    KPO0("Potworow", 0, 50, 5, 0, 0, 100, null, 0),
    KPO1("Potworow", 1, 55, 10, 1, 1, 80, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(3).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(4).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(2).toItemStack()
    ), 10_000_000),
    KPO2("Potworow", 2, 60, 15, 2, 2, 70, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(5).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(4).toItemStack()
    ), 15_000_000),
    KPO3("Potworow", 3, 65, 20, 2, 2, 55, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(16).toItemStack()
    ), 25_000_000),
    KPO4("Potworow", 4, 70, 25, 2, 2, 40, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(16).toItemStack()
    ), 40_000_000),
    KPO5("Potworow", 5, 80, 30, 2, 2, 33.3, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(16).toItemStack()
    ), 50_000_000),
    KPO6("Potworow", 6, 90, 35, 3, 3, 24.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(32).toItemStack()
    ), 75_000_000),
    KPO7("Potworow", 7, 100, 40, 3, 3, 12.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(48).toItemStack()
    ), 125_000_000),
    KPO8("Potworow", 8, 115, 45, 4, 4, 6.25, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(32).toItemStack()
    ), 250_000_000),
    KPO9("Potworow", 9, 130, 50, 8, 8, 2.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(256).toItemStack(),
            new ItemBuilder(AlchemikItems.I6.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).setAmount(64).toItemStack()
    ), 500_000_000),

    KPL0("Ludzi", 0, 50, 5, 0, 0, 100, null, 0),
    KPL1("Ludzi", 1, 55, 10, 1, 1, 80, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(3).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(4).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(2).toItemStack()
    ), 10_000_000),
    KPL2("Ludzi", 2, 60, 15, 2, 2, 70, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(5).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(4).toItemStack()
    ), 15_000_000),
    KPL3("Ludzi", 3, 65, 20, 2, 2, 55, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(6).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(16).toItemStack()
    ), 25_000_000),
    KPL4("Ludzi", 4, 70, 25, 2, 2, 40, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(8).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(16).toItemStack()
    ), 40_000_000),
    KPL5("Ludzi", 5, 80, 30, 2, 2, 33.3, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(10).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(16).toItemStack()
    ), 50_000_000),
    KPL6("Ludzi", 6, 90, 35, 3, 3, 24.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(12).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(32).toItemStack()
    ), 75_000_000),
    KPL7("Ludzi", 7, 100, 40, 3, 3, 12.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(24).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(48).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(48).toItemStack()
    ), 125_000_000),
    KPL8("Ludzi", 8, 115, 45, 4, 4, 6.25, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(16).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(80).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(32).toItemStack()
    ), 250_000_000),
    KPL9("Ludzi", 9, 130, 50, 8, 8, 2.5, Arrays.asList(
            new ItemBuilder(AlchemikItems.I1.getItemStack().clone()).setAmount(256).toItemStack(),
            new ItemBuilder(AlchemikItems.I7.getItemStack().clone()).setAmount(64).toItemStack(),
            new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(GlobalItem.I_OCZYSZCZENIE.getItemStack().clone()).setAmount(32).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(128).toItemStack(),
            new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(96).toItemStack(),
            new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).setAmount(64).toItemStack()
    ), 500_000_000),
    ;

    private final String id;
    private final int lvl;
    private final int newLvl, newAlchLvl;
    private final double toAddVal1, toAddVal2;
    private final double acceptChance;
    private final List<ItemStack> reqItemList;
    private final double reqMoney;

    KrysztalUpgrades(final String id, final int lvl, final int newLvl, final int newAlchLvl, final double toAddVal1, final double toAddVal2, final double acceptChance, final List<ItemStack> reqItemList, final double reqMoney) {
        this.id = id;
        this.lvl = lvl;
        this.newLvl = newLvl;
        this.newAlchLvl = newAlchLvl;
        this.toAddVal1 = toAddVal1;
        this.toAddVal2 = toAddVal2;
        this.acceptChance = acceptChance;
        this.reqItemList = reqItemList;
        this.reqMoney = reqMoney;
    }

    public double getReqMoney() {
        return DoubleUtils.round(reqMoney, 2);
    }

    public double getToAddVal1() {
        return DoubleUtils.round(toAddVal1,2);
    }

    public double getToAddVal2() {
        return DoubleUtils.round(toAddVal2, 2);
    }

    public static KrysztalUpgrades find(final String id, final int lvl) {
        return Arrays.stream(values()).filter(krysztal -> krysztal.getId().equals(id) && krysztal.getLvl() == lvl).findFirst().orElse(null);
    }
}
