package rpg.rpgcore.npc.handlarz.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

public enum HandlarzSellItems {
    // 1-10 MOB
    I1("Helm Rozbojnika", Material.LEATHER_HELMET, 50.0),
    I2("Zbroja Rozbojnika", Material.LEATHER_CHESTPLATE,10.0),
    I3("Spodnie Rozbojnika", Material.LEATHER_LEGGINGS,  10.0),
    I4("Buty Rozbojnika", Material.LEATHER_BOOTS, 10.0),
    I5("Tepy Miecz Rozbojnika", Material.WOOD_SWORD, 12.0),
    // 1-10 BOSS
    I6("Helm Dowodcy Rozbojnikow", Material.LEATHER_HELMET, 12.0),
    I7("Zbroja Dowodcy Rozbojnikow", Material.LEATHER_CHESTPLATE, 12.0),
    I8("Spodnie Dowodcy Rozbojnikow", Material.LEATHER_LEGGINGS, 12.0),
    I9("Buty Dowodcy Rozbojnikow", Material.LEATHER_BOOTS, 12.0),
    I10("Miecz Dowodcy Rozbojnikow", Material.WOOD_SWORD, 15.0),
    // 1-10 BOSS AKCE
    I11("Naszyjnik Dowodcy Rozbojnikow", Material.STORAGE_MINECART, 100.0),
    I12("Diadem Dowodcy Rozbojnikow", Material.WATCH, 100.0),
    I13("Pierscien Dowodcy Rozbojnikow", Material.EXPLOSIVE_MINECART, 100.0),
    I14("Tarcza Dowodcy Rozbojnikow", Material.ITEM_FRAME, 100.0),

    
    // 10-20 MOB
    I15("Helm Goblina", Material.LEATHER_HELMET, 15.0),
    I16("Zbroja Goblina", Material.LEATHER_CHESTPLATE,17.0),
    I17("Spodnie Goblina", Material.LEATHER_LEGGINGS,  16.0),
    I18("Buty Goblina", Material.LEATHER_BOOTS, 14.0),
    I19("Miecz Goblina", Material.WOOD_SWORD, 18.0),
    // 10-20 BOSS
    I20("Helm Wodza Goblinow", Material.LEATHER_HELMET, 20.0),
    I21("Zbroja Wodza Goblinow", Material.LEATHER_CHESTPLATE, 22.0),
    I22("Spodnie Wodza Goblinow", Material.LEATHER_LEGGINGS, 21.0),
    I23("Buty Wodza Goblinow", Material.LEATHER_BOOTS, 19.0),
    I24("Miecz Wodza Goblinow", Material.STONE_SWORD, 23.0),
    // 10-20 BOSS AKCE 
    I25("Naszyjnik Wodza Goblinow", Material.STORAGE_MINECART, 120.0),
    I26("Diadem Wodza Goblinow", Material.WATCH, 120.0),
    I27("Pierscien Wodza Goblinow", Material.EXPLOSIVE_MINECART, 120.0),
    I28("Tarcza Wodza Goblinow", Material.ITEM_FRAME, 120.0),

    // 20-30 MOB
    I29("Helm Goryla", Material.LEATHER_HELMET, 17.0),
    I30("Zbroja Goryla", Material.CHAINMAIL_CHESTPLATE,19.0),
    I31("Spodnie Goryla", Material.LEATHER_LEGGINGS,  18.0),
    I32("Buty Goryla", Material.LEATHER_BOOTS, 16.0),
    I33("Miecz Goryla", Material.STONE_SWORD, 20.0),
    // 20-30 BOSS
    I34("Helm Krola Goryli", Material.GOLD_HELMET, 22.0),
    I35("Zbroja Krola Goryli", Material.GOLD_CHESTPLATE, 24.0),
    I36("Spodnie Krola Goryli", Material.GOLD_LEGGINGS, 23.0),
    I37("Buty Krola Goryli", Material.GOLD_BOOTS, 21.0),
    I38("Miecz Krola Goryli", Material.GOLD_SWORD, 25.0),
    // 20-30 BOSS AKCE 
    I39("Naszyjnik Krola Goryli", Material.STORAGE_MINECART, 200.0),
    I40("Diadem Krola Goryli", Material.WATCH, 200.0),
    I41("Pierscien Krola Goryli", Material.EXPLOSIVE_MINECART, 200.0),
    I42("Tarcza Krola Goryli", Material.ITEM_FRAME, 200.0),


    // 30-40 MOB
    I43("Helm Zjawy", Material.GOLD_HELMET, 30.0),
    I44("Zbroja Zjawy", Material.GOLD_CHESTPLATE,39.0),
    I45("Spodnie Zjawy", Material.GOLD_LEGGINGS,  38.0),
    I46("Buty Zjawy", Material.GOLD_BOOTS, 36.0),
    I47("Miecz Zjawy", Material.GOLD_SWORD, 30.0),
    // 30-40 BOSS
    I48("Helm Przekletej Duszy", Material.CHAINMAIL_HELMET, 52.0),
    I49("Zbroja Przekletej Duszy", Material.CHAINMAIL_CHESTPLATE, 74.0),
    I50("Spodnie Przekletej Duszy", Material.CHAINMAIL_LEGGINGS, 53.0),
    I51("Buty Przekletej Duszy", Material.CHAINMAIL_BOOTS, 41.0),
    I52("Miecz Przekletej Duszy", Material.STONE_SWORD, 65.0),
    // 30-40 BOSS AKCE 
    I53("Naszyjnik Przekletej Duszy", Material.STORAGE_MINECART, 300.0),
    I54("Diadem Przekletej Duszy", Material.WATCH, 300.0),
    I55("Pierscien Przekletej Duszy", Material.EXPLOSIVE_MINECART, 300.0),
    I56("Tarcza Przekletej Duszy", Material.ITEM_FRAME, 300.0),


    // 40-50 MOB
    I57("Helm Straznika Swiatyni", Material.GOLD_HELMET, 67.0),
    I58("Zbroja Straznika Swiatyni", Material.IRON_CHESTPLATE,79.0),
    I59("Spodnie Straznika Swiatyni", Material.IRON_LEGGINGS,  58.0),
    I60("Buty Straznika Swiatyni", Material.GOLD_BOOTS  , 66.0),
    I61("Miecz Straznika Swiatyni", Material.GOLD_SWORD, 70.0),
    // 40-50 BOSS
    I62("Helm Trytona", Material.IRON_HELMET, 62.0),
    I63("Zbroja Trytona", Material.IRON_CHESTPLATE, 84.0),
    I64("Spodnie Trytona", Material.IRON_LEGGINGS, 73.0),
    I65("Buty Trytona", Material.IRON_BOOTS, 84.0),
    I66("Miecz Trytona", Material.IRON_SWORD, 95.0),
    // 40-50 BOSS AKCE 
    I67("Naszyjnik Trytona", Material.STORAGE_MINECART, 500.0),
    I68("Diadem Trytona", Material.WATCH, 500.0),
    I69("Pierscien Trytona", Material.EXPLOSIVE_MINECART, 500.0),
    I70("Tarcza Trytona", Material.ITEM_FRAME, 500.0),


    // 50-60 MOB
    I71("Helm Lodowego Slugi", Material.IRON_HELMET, 107.0),
    I72("Zbroja Lodowego Slugi", Material.IRON_CHESTPLATE,128.0),
    I73("Spodnie Lodowego Slugi", Material.IRON_LEGGINGS,  118.0),
    I74("Buty Lodowego Slugi", Material.IRON_BOOTS, 106.0),
    I75("Miecz Lodowego Slugi", Material.IRON_SWORD, 130.0),
    // 50-60 MOB ICETOWER
    I76("Helm Mroznego Wilka", Material.GOLD_HELMET, 137.0),
    I77("Zbroja Mroznego Wilka", Material.IRON_CHESTPLATE,149.0),
    I78("Spodnie Mroznego Wilka", Material.IRON_LEGGINGS,  138.0),
    I79("Buty Mroznego Wilka", Material.GOLD_BOOTS, 126.0),
    I80("Wilczy Kiel", Material.IRON_SWORD, 170.0),
    // 50-60 BOSS ICETOWER
    I81("Lodowy Sztylet", Material.DIAMOND_SWORD, 195.0),
    // 50-60 BOSS AKCE  ICETOWER
    I82("Naszyjnik Krola Lodu", Material.STORAGE_MINECART, 800.0),
    I83("Diadem Krola Lodu", Material.WATCH, 800.0),
    I84("Pierscien Krola Lodu", Material.EXPLOSIVE_MINECART, 800.0),
    I85("Tarcza Krola Lodu", Material.ITEM_FRAME, 800.0),
    I86("Kolczyki Krola Lodu", Material.HOPPER_MINECART, 800.0),



    // 60-70 MOB
    I87("Helm Zywiolaka Ognia", Material.IRON_HELMET, 267.0),
    I88("Zbroja Zywiolaka Ognia", Material.DIAMOND_CHESTPLATE,279.0),
    I89("Spodnie Zywiolaka Ognia", Material.IRON_LEGGINGS,  258.0),
    I90("Buty Zywiolaka Ognia", Material.DIAMOND_BOOTS, 266.0),
    I91("Miecz Zywiolaka Ognia", Material.IRON_SWORD, 270.0),
    // 60-70 BOSS
    I92("Helm Piekielnego Rycerza", Material.DIAMOND_HELMET, 282.0),
    I93("Zbroja Piekielnego Rycerza", Material.DIAMOND_CHESTPLATE, 284.0),
    I94("Spodnie Piekielnego Rycerza", Material.DIAMOND_LEGGINGS, 273.0),
    I95("Buty Piekielnego Rycerza", Material.DIAMOND_BOOTS, 284.0),
    I96("Miecz Piekielnego Rycerza", Material.DIAMOND_SWORD, 295.0),
    // 60-70 BOSS AKCE 
    I97("Naszyjnik Piekielnego Rycerza", Material.STORAGE_MINECART, 1200.0),
    I98("Diadem Piekielnego Rycerza", Material.WATCH, 1200.0),
    I99("Pierscien Piekielnego Rycerza", Material.EXPLOSIVE_MINECART, 1200.0),
    I100("Tarcza Piekielnego Rycerza", Material.ITEM_FRAME, 1200.0),
    I101("Kolczyki Piekielnego Rycerza", Material.HOPPER_MINECART, 1200.0),

    // 70-80 MOB
    I102("Helm Mrocznej Duszy", Material.DIAMOND_HELMET, 267.0),
    I103("Zbroja Mrocznej Duszy", Material.DIAMOND_CHESTPLATE,279.0),
    I104("Spodnie Mrocznej Duszy", Material.DIAMOND_LEGGINGS,  258.0),
    I105("Buty Mrocznej Duszy", Material.DIAMOND_BOOTS, 266.0),
    I106("Miecz Mrocznej Duszy", Material.DIAMOND_SWORD, 270.0),
    // 70-80 BOSS
    I107("Helm Przekletego Czarnoksieznika", Material.DIAMOND_HELMET, 282.0),
    I108("Zbroja Przekletego Czarnoksieznika", Material.DIAMOND_CHESTPLATE, 284.0),
    I109("Spodnie Przekletego Czarnoksieznika", Material.DIAMOND_LEGGINGS, 273.0),
    I110("Buty Przekletego Czarnoksieznika", Material.DIAMOND_BOOTS, 284.0),
    I111("Miecz Przekletego Czarnoksieznika", Material.DIAMOND_SWORD, 295.0),
    // 70-80 BOSS AKCE
    I112("Naszyjnik Przekletego Czarnoksieznika", Material.STORAGE_MINECART, 1200.0),
    I113("Diadem Przekletego Czarnoksieznika", Material.WATCH, 1200.0),
    I114("Pierscien Przekletego Czarnoksieznika", Material.EXPLOSIVE_MINECART, 1200.0),
    I115("Tarcza Przekletego Czarnoksieznika", Material.ITEM_FRAME, 1200.0),
    I116("Kolczyki Przekletego Czarnoksieznika", Material.HOPPER_MINECART, 1200.0),

    // 80-90 BOSS
    I117("Helm Mitycznego Pajaka", Material.DIAMOND_HELMET, 282.0),
    I118("Zbroja Mitycznego Pajaka", Material.DIAMOND_CHESTPLATE, 284.0),
    I119("Spodnie Mitycznego Pajaka", Material.DIAMOND_LEGGINGS, 273.0),
    I120("Buty Mitycznego Pajaka", Material.DIAMOND_BOOTS, 284.0),
    I121("Miecz Mitycznego Pajaka", Material.DIAMOND_SWORD, 295.0),
    // 80-90 BOSS AKCE
    I122("Naszyjnik Mitycznego Pajaka", Material.STORAGE_MINECART, 1200.0),
    I123("Diadem Mitycznego Pajaka", Material.WATCH, 1200.0),
    I124("Pierscien Mitycznego Pajaka", Material.EXPLOSIVE_MINECART, 1200.0),
    I125("Tarcza Mitycznego Pajaka", Material.ITEM_FRAME, 1200.0),
    I126("Kolczyki Mitycznego Pajaka", Material.HOPPER_MINECART, 1200.0),


    // 90-100 BOSS
    I127("Helm Podziemnego Rozpruwacza", Material.DIAMOND_HELMET, 282.0),
    I128("Zbroja Podziemnego Rozpruwacza", Material.DIAMOND_CHESTPLATE, 284.0),
    I129("Spodnie Podziemnego Rozpruwacza", Material.DIAMOND_LEGGINGS, 273.0),
    I130("Buty Podziemnego Rozpruwacza", Material.DIAMOND_BOOTS, 284.0),
    I131("Miecz Podziemnego Rozpruwacza", Material.DIAMOND_SWORD, 295.0),
    // 90-100 BOSS AKCE
    I132("Naszyjnik Podziemnego Rozpruwacza", Material.STORAGE_MINECART, 1200.0),
    I133("Diadem Podziemnego Rozpruwacza", Material.WATCH, 1200.0),
    I134("Pierscien Podziemnego Rozpruwacza", Material.EXPLOSIVE_MINECART, 1200.0),
    I135("Tarcza Podziemnego Rozpruwacza", Material.ITEM_FRAME, 1200.0),
    I136("Kolczyki Podziemnego Rozpruwacza", Material.HOPPER_MINECART, 1200.0),

    // 100-110 BOSS
    // 100-110 BOSS AKCE
    // 146

    // 110-120 BOSS
    // 110-120 BOSS AKCE
    // 156

    // 120-130 BOSS
    // 120-130 BOSS AKCE
    // 166 + 1

    // TAJEMNICZA CHEST
    I166("Tajemniczy Helm", Material.IRON_HELMET, 282.0),
    I167("Tajemnicza Zbroja", Material.IRON_CHESTPLATE, 284.0),
    I168("Tajemnicze Spodnie", Material.IRON_LEGGINGS, 273.0),
    I169("Tajemnicze Buty", Material.IRON_BOOTS, 284.0),

    // NIES 1-10
    I170("Zaginiona Czapka", Material.LEATHER_HELMET, 282.0),
    I171("Zaginiona Kurtka", Material.LEATHER_CHESTPLATE, 284.0),
    I172("Zaginione Spodnie", Material.LEATHER_LEGGINGS, 273.0),
    I173("Zaginione Buty", Material.LEATHER_BOOTS, 284.0),
    I174("Zaginiona Brzytwa", Material.WOOD_SWORD, 270.0),
    // NIES 10-20
    I175("Zielony Beret", Material.LEATHER_HELMET, 267.0),
    I176("Zielony Kubrak", Material.LEATHER_CHESTPLATE,279.0),
    I177("Zielone Spodnie ", Material.LEATHER_LEGGINGS,  258.0),
    I178("Zielone Buty", Material.LEATHER_BOOTS, 266.0),
    I179("Zielona Maczeta", Material.STONE_SWORD, 270.0),
    // NIES 20-30
    I180("Tropikalny Kask", Material.CHAINMAIL_HELMET, 267.0),
    I181("Tropikalna Zbroja", Material.CHAINMAIL_CHESTPLATE,279.0),
    I182("Tropikalne Spodnie", Material.CHAINMAIL_LEGGINGS,  258.0),
    I183("Tropikalne Sandaly", Material.CHAINMAIL_BOOTS, 266.0),
    I184("Tropikalny Miecz", Material.STONE_SWORD, 270.0),
    // NIES 30-40
    I185("Przeklety Helm Mrocznej Duszy", Material.GOLD_HELMET, 267.0),
    I186("Przekleta Zbroja Mrocznej Duszy", Material.GOLD_CHESTPLATE,279.0),
    I187("Przeklete Spodnie Mrocznej Duszy", Material.GOLD_LEGGINGS,  258.0),
    I188("Przeklete Trepy Mrocznej Duszy", Material.GOLD_BOOTS, 266.0),
    I189("Przekleta Kosa Mrocznej Duszy", Material.GOLD_SWORD, 270.0),
    // NIES 40-50
    I190("Pradawny Helm", Material.IRON_HELMET, 267.0),
    I191("Pradawny Kaftan", Material.IRON_CHESTPLATE,279.0),
    I192("Pradawne Portki", Material.IRON_LEGGINGS,  258.0),
    I193("Pradawne Buty", Material.IRON_BOOTS, 266.0),
    I194("Pradawny Sztylet", Material.IRON_SWORD, 270.0),
    // NIES 50-60
    I195("Sniezny Helm", Material.IRON_HELMET, 267.0),
    I196("Sniezna Zbroja", Material.DIAMOND_CHESTPLATE,279.0),
    I197("Sniezne Kalesony", Material.DIAMOND_LEGGINGS,  258.0),
    I198("Sniezne Kozaki", Material.IRON_BOOTS, 266.0),
    I199("Sniezny Sztylet", Material.DIAMOND_SWORD, 270.0),
    // NIES 60-70
    I200("Ognisty Kask", Material.DIAMOND_HELMET, 267.0),
    I201("Ognista Kurtka", Material.DIAMOND_CHESTPLATE,279.0),
    I202("Ogniste Spodnie", Material.DIAMOND_LEGGINGS,  258.0),
    I203("Ogniste Buty", Material.DIAMOND_BOOTS, 266.0),
    I204("Ognista Szpada", Material.DIAMOND_SWORD, 270.0),
    // NIES 70-80
    I205("&7&lMglisty Helm", Material.DIAMOND_HELMET, 267.0),
    I206("&7&lMglista Wiatrowka", Material.DIAMOND_CHESTPLATE,279.0),
    I207("&7&lMgliste Galoty", Material.DIAMOND_LEGGINGS,  258.0),
    I208("&7&lMgliste Buty", Material.DIAMOND_BOOTS, 266.0),
    I209("&7&lMglisty Noz", Material.DIAMOND_SWORD, 270.0),
    // NIES 80-90
    I210("Sloneczny Kapelusz", Material.DIAMOND_HELMET, 267.0),
    I211("Sloneczna Zbroja", Material.DIAMOND_CHESTPLATE,279.0),
    I212("Sloneczne Spodenki", Material.DIAMOND_LEGGINGS,  258.0),
    I213("Sloneczne Klapki", Material.DIAMOND_BOOTS, 266.0),
    I214("Sloneczna Szabla", Material.DIAMOND_SWORD, 270.0),
    // NIES 90-100
    I215("Skradziony Helm", Material.DIAMOND_HELMET, 267.0),
    I216("Skradziona Kamizelka", Material.DIAMOND_CHESTPLATE,279.0),
    I217("Skradzione Spodnie", Material.DIAMOND_LEGGINGS,  258.0),
    I218("Skradzione Trzewiki", Material.DIAMOND_BOOTS, 266.0),
    I219("Skradziony Miecz", Material.DIAMOND_SWORD, 270.0),
    // NIES 100-110
    // 224

    // NIES 110-120
    // 229

    // NIES 120-130
    // 234 + 1

    // AKCESORIUM 1-10
    I235("Zaginiony Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I236("Zaginiony Diadem", Material.WATCH, 1200.0),
    I237("Zaginiony Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I238("Zaginiona Tarcza", Material.ITEM_FRAME, 1200.0),
    // AKCESORIUM 10-20
    I239("Zielony Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I240("Zielony Diadem", Material.WATCH, 1200.0),
    I241("Zielony Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I242("Zielona Tarcza", Material.ITEM_FRAME, 1200.0),
    // AKCESORIUM 20-30
    I243("Tropikalny Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I244("Tropikalny Diadem", Material.WATCH, 1200.0),
    I245("Tropikalny Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I246("Tropikalna Tarcza", Material.ITEM_FRAME, 1200.0),
    // AKCESORIUM 30-40
    I247("Przeklety Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I248("Przeklety Diadem", Material.WATCH, 1200.0),
    I249("Przeklety Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I250("Przekleta Tarcza", Material.ITEM_FRAME, 1200.0),
    // AKCESORIUM 40-50
    I251("Pradawny Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I252("Pradawny Diadem", Material.WATCH, 1200.0),
    I253("Pradawny Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I254("Pradawna Tarcza", Material.ITEM_FRAME, 1200.0),
    // AKCESORIUM 50-60
    I255("Sniezny Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I256("Sniezny Diadem", Material.WATCH, 1200.0),
    I257("Sniezny Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I258("Sniezna Tarcza", Material.ITEM_FRAME, 1200.0),
    I259("Sniezne Kolczyki", Material.HOPPER_MINECART, 1200.0),
    // AKCESORIUM 60-70
    I260("Ognisty Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I261("Ognisty Diadem", Material.WATCH, 1200.0),
    I262("Ognisty Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I263("Ognista Tarcza", Material.ITEM_FRAME, 1200.0),
    I264("Ogniste Kolczyki", Material.HOPPER_MINECART, 1200.0),
    // AKCESORIUM 70-80
    I265("Mglisty Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I266("Mglisty Diadem", Material.WATCH, 1200.0),
    I267("Mglisty Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I268("Mglista Tarcza", Material.ITEM_FRAME, 1200.0),
    I269("Mgliste Kolczyki", Material.HOPPER_MINECART, 1200.0),
    // AKCESORIUM 80-90
    I270("Sloneczny Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I271("Sloneczny Diadem", Material.WATCH, 1200.0),
    I272("Sloneczny Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I273("Sloneczna Tarcza", Material.ITEM_FRAME, 1200.0),
    I274("Sloneczne Kolczyki", Material.HOPPER_MINECART, 1200.0),
    // AKCESORIUM 90-100
    I275("Skradziony Naszyjnik", Material.STORAGE_MINECART, 1200.0),
    I276("Skradziony Diadem", Material.WATCH, 1200.0),
    I277("Skradziony Pierscien", Material.EXPLOSIVE_MINECART, 1200.0),
    I278("Skradziona Tarcza", Material.ITEM_FRAME, 1200.0),
    I279("Skradzione Kolczyki", Material.HOPPER_MINECART, 1200.0),
    // AKCESORIUM 100-110
    // 284
    // AKCESORIUM 110-120
    // 289
    // AKCESORIUM 120-130
    // 294 + 1


    // DUNGEON 60-70
    I295("Helm Piekielnego Wladcy", Material.DIAMOND_HELMET, 267.0),
    I296("Zbroja Piekielnego Wladcy", Material.DIAMOND_CHESTPLATE,279.0),
    I297("Spodnie Piekielnego Wladcy", Material.DIAMOND_LEGGINGS,  258.0),
    I298("Buty Piekielnego Wladcy", Material.DIAMOND_BOOTS, 266.0),
    I299("Miecz Piekielnego Wladcy", Material.DIAMOND_SWORD, 270.0),
    I300("Energia Piekielnego Wladcy", Material.MINECART, 1200.0),
    I301("Medalion Piekielnego Wladcy", Material.FIREBALL, 1200.0),
    // DUNGEON 70-80
    I302("Helm Czempiona Areny", Material.DIAMOND_HELMET, 267.0),
    I303("Zbroja Czempiona Areny", Material.DIAMOND_CHESTPLATE,279.0),
    I304("Spodnie Czempiona Areny", Material.DIAMOND_LEGGINGS,  258.0),
    I305("Buty Czempiona Areny", Material.DIAMOND_BOOTS, 266.0),
    I306("Miecz Czempiona Areny", Material.DIAMOND_SWORD, 270.0),
    I307("Energia Czempiona Areny", Material.MINECART, 1200.0),
    I308("Medalion Czempiona Areny", Material.FIREBALL, 1200.0),
    // DUNNGEON 80-90
    I309("Helm Cesarza Pustyni", Material.DIAMOND_HELMET, 267.0),
    I310("Zbroja Cesarza Pustyni", Material.DIAMOND_CHESTPLATE,279.0),
    I311("Spodnie Cesarza Pustyni", Material.DIAMOND_LEGGINGS,  258.0),
    I312("Buty Cesarza Pustyni", Material.DIAMOND_BOOTS, 266.0),
    I313("Miecz Cesarza Pustyni", Material.DIAMOND_SWORD, 270.0),
    I314("Energia Cesarza Pustyni", Material.MINECART, 1200.0),
    I315("Medalion Cesarza Pustyni", Material.FIREBALL, 1200.0),
    // DUNGEON 90-100
    I316("Helm Demona Ciemnosci", Material.DIAMOND_HELMET, 267.0),
    I317("Zbroja Demona Ciemnosci", Material.DIAMOND_CHESTPLATE,279.0),
    I318("Spodnie Demona Ciemnosci", Material.DIAMOND_LEGGINGS,  258.0),
    I319("Buty Demona Ciemnosci", Material.DIAMOND_BOOTS, 266.0),
    I320("Miecz Demona Ciemnosci", Material.DIAMOND_SWORD, 270.0),
    I321("Energia Demona Ciemnosci", Material.MINECART, 1200.0),
    I322("Medalion Demona Ciemnosci", Material.FIREBALL, 1200.0),
    // DUNGEON 100-110
    // 329
    // DUNGEON 110-120
    // 336
    // DUNGEON 120-130
    // 343 + 1

    // ulepszacze
    I323("Szata Rozbojnika",Material.RABBIT_HIDE,1),
    I324("Oko Goblina",Material.EYE_OF_ENDER,1),
    I325("Skora Goryla",Material.INK_SACK,1),
    I326("Zlamana Kosc",Material.BONE,1),
    I327("Lza Oceanu",Material.GHAST_TEAR,1),
    I328("Wilcze Futro",Material.LEATHER,1),
    I329("Ognisty Pyl",Material.BLAZE_POWDER,1),
    I330("Trujaca Roslina",Material.RED_ROSE,1),
    I331("Jad Ptasznika",Material.SPIDER_EYE,1),
    I332("Mroczny Material",Material.NETHER_BRICK_ITEM,1),
    I333("Szafirowy Skrawek",Material.INK_SACK,1),
    I334("Zaklety Lod",Material.ICE,1),
    I335("Niebianki Material",Material.INK_SACK,1),
    // materialy
    I336("Zloto",Material.GOLD_INGOT,1000.0),
    I337("Brylant",Material.DIAMOND,1500.0),
    I338("Szmaragd",Material.EMERALD,2000.0),
    I339("Pyl",Material.REDSTONE,3000.0),
    I340("Kamien",Material.STONE,4000.0),
    I341("Stal",Material.IRON_INGOT,5000.0),
    I342("Proch",Material.SULPHUR,6000.0),
    // fragment metalu
    I343("Fragment Stali Kowala",Material.PRISMARINE_CRYSTALS,250_000),
    // fragment magii
    I344("Czastka Magii",Material.BLAZE_POWDER,500_000),

    
    I_ERROR("ERROR", Material.AIR, 0);
    private final String name;
    private final Material material;
    private final double price;

    HandlarzSellItems(String name, Material material, double price) {
        this.name = name;
        this.material = material;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public double getPrice(final ItemStack item) {
        double dodatek = 0;
        if (item.getType().toString().contains("_SWORD")) {
            final int sharp = Utils.getTagInt(item, "dmg");
            int mnoznik = 0;
            if (sharp > 20) {
                mnoznik = 50;
            }
            if (sharp > 60) {
                mnoznik = 100;
            }
            if (sharp > 250) {
                mnoznik = 200;
            }
            if (sharp > 500) {
                mnoznik = 300;
            }
            if (sharp > 1000) {
                mnoznik = 400;
            }
            if (sharp > 2000) {
                mnoznik = 500;
            }
            if (sharp >= 2500) {
                mnoznik = 600;
            }
            dodatek += sharp * mnoznik;
            final int moby = Utils.getTagInt(item, "moby");
            if (moby > 20) {
                mnoznik = 50;
            }
            if (moby > 60) {
                mnoznik = 100;
            }
            if (moby > 250) {
                mnoznik = 200;
            }
            if (moby > 500) {
                mnoznik = 300;
            }
            if (moby > 1000) {
                mnoznik = 400;
            }
            if (moby > 2000) {
                mnoznik = 500;
            }
            if (moby >= 2500) {
                mnoznik = 600;
            }
            dodatek += moby * mnoznik;
            if (Utils.getTagString(item, "type").equals("ks")) {
                int mobyProcentKS = Utils.getTagInt(item, "mobyProcentKS");
                if (mobyProcentKS > 10) {
                    mnoznik = 50;
                }
                if (mobyProcentKS > 20) {
                    mnoznik = 100;
                }
                if (mobyProcentKS > 30) {
                    mnoznik = 200;
                }
                // ...

                dodatek += mobyProcentKS * mnoznik;
            } else if (Utils.getTagString(item, "type").equals("tyra")) {
                int ludzieProcentTYRA = Utils.getTagInt(item, "ludzieProcentTYRA");
                if (ludzieProcentTYRA > 10) {
                    mnoznik = 50;
                }
                if (ludzieProcentTYRA > 20) {
                    mnoznik = 100;
                }
                if (ludzieProcentTYRA > 30) {
                    mnoznik = 200;
                }
                // ...

                dodatek += ludzieProcentTYRA * mnoznik;
            }
        } else if (item.getType().toString().contains("_HELMET") || item.getType().toString().contains("_CHESTPLATE") || item.getType().toString().contains("_LEGGINGS") || item.getType().toString().contains("_BOOTS")) {
            final int prot = Utils.getTagInt(item, "prot");
            int mnoznik = 0;
            if (prot > 20) {
                mnoznik = 50;
            }
            if (prot > 50) {
                mnoznik = 75;
            }
            if (prot > 60) {
                mnoznik = 100;
            }
            if (prot > 80) {
                mnoznik = 125;
            }
            if (prot > 100) {
                mnoznik = 150;
            }
            if (prot > 120) {
                mnoznik = 175;
            }
            if (prot > 140) {
                mnoznik = 200;
            }
            if (prot > 160) {
                mnoznik = 225;
            }
            if (prot > 180) {
                mnoznik = 250;
            }
            if (prot > 200) {
                mnoznik = 275;
            }
            if (prot > 220) {
                mnoznik = 300;
            }
            if (prot > 240) {
                mnoznik = 325;
            }
            if (prot >= 250) {
                mnoznik = 350;
            }
            dodatek += prot * mnoznik;

            final double thorns = Utils.getTagDouble(item, "thorns");
            if (thorns > 10) {
                mnoznik = 50;
            }
            if (thorns > 20) {
                mnoznik = 100;
            }
            if (thorns > 30) {
                mnoznik = 150;
            }
            if (thorns > 40) {
                mnoznik = 200;
            }
            if (thorns >= 50) {
                mnoznik = 250;
            }
            dodatek += thorns * mnoznik;
        }
        return DoubleUtils.round(price + dodatek, 2);
    }

    public static HandlarzSellItems checkIfSellItem(final ItemStack item) {
        for (final HandlarzSellItems items : HandlarzSellItems.values()) {
            if (items.getMaterial() == item.getType() && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && Utils.removeColor(item.getItemMeta().getDisplayName()).equals(items.getName())) {
                return items;
            }
        }
        return null;
    }
}
