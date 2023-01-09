package rpg.rpgcore.npc.kupiec.enums;

import rpg.rpgcore.utils.DoubleUtils;

public enum KupiecItems {
    // exp 1
    I1("Helm Najemnika", 10.0),
    I2("Zbroja Najemnika", 10.0),
    I3("Spodnie Najemnika", 10.0),
    I4("Buty Najemnika", 10.0),
    I5("Tepy Miecz Najemnika", 12.0),
    I6("Helm Wygnanca", 15.0),
    I7("Zbroja Wygnanca", 15.0),
    I8("Spodnie Wygnanca", 15.0),
    I9("Buty Wygnanca", 15.0),
    I20("Miecz Wygnanego", 17.0),
    I21("Zwykly Naszyjnik Wygnanca", 32.0),
    I22("Zwykly Diadem Wygnanca", 32.0),
    I23("Zwykly Pierscien Wygnanca", 32.0),
    I24("Zwykla Tarcza Wygnanca", 32.0),
    I25("Ulepszony Naszyjnik Wygnanca", 100.0),
    I26("Ulepszony Diadem Wygnanca", 100.0),
    I27("Ulepszony Pierscien Wygnanca", 100.0),
    I28("Ulepszona Tarcza Wygnanca", 100.0),
    // exp 2
    I29("Helm Goblina", 20.0),
    I30("Zbroja Goblina", 20.0),
    I31("Spodnie Goblina", 20.0),
    I32("Buty Goblina", 20.0),
    I33("Miecz Goblina", 22.0),
    I34("Helm Wodza Goblinow", 30.0),
    I35("Zbroja Wodza Goblinow", 30.0),
    I36("Spodnie Wodza Goblinow", 30.0),
    I37("Buty Wodza Goblinow", 30.0),
    I38("Miecz Wodza Goblinow", 32.0),
    I39("Zwykly Naszyjnik Wodza Globlinow", 64.0),
    I40("Zwykly Diadem Wodza Globlinow", 64.0),
    I41("Zwykly Pierscien Wodza Globlinow", 64.0),
    I42("Zwykla Tarcza Wodza Globlinow", 64.0),
    I43("Ulepszony Naszyjnik Wodza Globlinow", 200.0),
    I44("Ulepszony Diadem Wodza Globlinow", 200.0),
    I45("Ulepszony Pierscien Wodza Globlinow", 200.0),
    I46("Ulepszona Tarcza Wodza Globlinow", 200.0),
    // exp 3
    I47("Helm Goryla", 30.0),
    I48("Zbroja Goryla", 30.0),
    I49("Spodnie Goryla", 30.0),
    I50("Buty Goryla", 30.0),
    I51("Miecz Goryla", 32.0),
    I52("Helm Krola Goryli", 40.0),
    I53("Zbroja Krola Goryli", 40.0),
    I54("Spodnie Krola Goryli", 40.0),
    I55("Buty Krola Goryli", 40.0),
    I56("Miecz Krola Goryli", 42.0),
    I57("Zwykly Naszyjnik Krola Goryli", 96.0),
    I58("Zwykly Diadem Krola Goryli", 96.0),
    I59("Zwykly Pierscien Krola Goryli", 96.0),
    I60("Zwykla Tarcza Krola Goryli", 96.0),
    I61("Ulepszony Naszyjnik Krola Goryli", 300.0),
    I62("Ulepszony Diadem Krola Goryli", 300.0),
    I63("Ulepszony Pierscien Krola Goryli", 300.0),
    I64("Ulepszona Tarcza Krola Goryli", 300.0),
    // exp 4
    I65("Helm Zjawy", 40.0),
    I66("Zbroja Zjawy", 40.0),
    I67("Spodnie Zjawy", 40.0),
    I68("Buty Zjawy", 40.0),
    I69("Miecz Zjawy", 42.0),
    I70("Helm Przekletej Duszy", 50.0),
    I71("Zbroja Przekletej Duszy", 50.0),
    I72("Spodnie Przekletej Duszy", 50.0),
    I73("Buty Przekletej Duszy", 50.0),
    I74("Miecz Przekletej Duszy", 52.0),
    I75("Zwykly Naszyjnik Przekletej Duszy", 128.0),
    I76("Zwykly Diadem Przekletej Duszy", 128.0),
    I77("Zwykly Pierscien Przekletej Duszy", 128.0),
    I78("Zwykla Tarcza Przekletej Duszy", 128.0),
    I79("Ulepszony Naszyjnik Przekletej Duszy", 400.0),
    I80("Ulepszony Diadem Przekletej Duszy", 400.0),
    I81("Ulepszony Pierscien Przekletej Duszy", 400.0),
    I82("Ulepszona Tarcza Przekletej Duszy", 400.0),
    // exp 5
    I83("Helm Straznika Swiatyni", 50.0),
    I84("Zbroja Straznika Swiatyni", 50.0),
    I85("Spodnie Straznika Swiatyni", 50.0),
    I86("Buty Straznika Swiatyni", 50.0),
    I87("Miecz Straznika Swiatyni", 52.0),
    I88("Helm Trytona", 60.0),
    I89("Zbroja Trytona", 60.0),
    I90("Spodnie Trytona", 60.0),
    I91("Buty Trytona", 60.0),
    I92("Miecz Trytona", 62.0),
    I93("Zwykly Naszyjnik Trytona", 160.0),
    I94("Zwykly Diadem Trytona", 160.0),
    I95("Zwykly Pierscien Trytona", 160.0),
    I96("Zwykla Tarcza Trytona", 160.0),
    I97("Ulepszony Naszyjnik Trytona", 500.0),
    I98("Ulepszony Diadem Trytona", 500.0),
    I99("Ulepszony Pierscien Trytona", 500.0),
    I100("Ulepszona Tarcza Trytona", 500.0);
    private final String itemName;
    private final double price;

    KupiecItems(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return DoubleUtils.round(price, 2);
    }

    public static double getItemPrice(String itemName) {
        for (KupiecItems kupiecItems : KupiecItems.values()) {
            if (kupiecItems.getItemName().equals(itemName)) {
                return kupiecItems.getPrice();
            }
        }
        return 0.0;
    }
}
