package rpg.rpgcore.npc.duszolog.enums;

import lombok.Getter;

@Getter
public enum DuszologMissionsTextures {
    M1("Rozbojnik", "", "", "", ""),
    M2("[BOSS] Dowodca Rozbojnikow", "", "", "", ""),

    M3("Goblin", "", "", "", ""),
    M4("[BOSS] Wodz Goblinow", "", "", "", ""),

    M5("Goryl", "", "", "", ""),
    M6("[BOSS] Krol Goryli", "", "", "", ""),

    M7("Zjawa", "", "", "", ""),
    M8("[BOSS] Przekleta Dusza", "", "", "", ""),

    M9("Straznik Swiatyni", "", "", "", ""),
    M10("[BOSS] Tryton", "" ,"", "", ""),

    M11("Mrozny Wilk", "", "", "", ""),

    M12("Lodowy Sluga", "", "", "", ""),
    M13("[BOSS] Krol Lodu", "", "", "", ""),

    M14("Zywiolak Ognia", "" ,"", "", ""),
    M15("[BOSS] Piekielny Rycerz", "" ,"", "", ""),

    M16("Ognisty Duch", "", "", "", ""),

    M17("Mroczna Dusza", "", "", "", ""),
    M18("[BOSS] Przeklety Czarnoksieznik", "", "", "", ""),

    M19("Zapomniany Wojownik", "", "", "", ""),

    M20("Pustynny Ptasznik", "", "", "", ""),
    M21("[BOSS] Mityczny Paja", "", "", "", ""),

    M22("Truposz", "", "", "", ""),

    M23("Podziemna Lowczyni", "", "", "", ""),
    M24("[BOSS] Podziemny Rozpruwacz", "", "", "", ""),

    M25("Demoniczy Lowca", "", "", "", ""),

    M26("Podwodny Straznik", "", "", "", ""),
    M27("[BOSS] Mistyczny Kraken", "", "", "", ""),

    M28("Mrozny Stroz", "", "", "", ""),
    M29("[BOSS] Krysztalowy Wladca", "", "", "", ""),
    ;
    
    private final String entityName, uuid, nick, texture, signature;
    
    DuszologMissionsTextures(final String entityName, final String uuid, final String nick, final String texture, final String signature) {
        this.entityName = entityName;
        this.uuid = uuid;
        this.nick = nick;
        this.texture = texture;
        this.signature = signature;
    }

    public static DuszologMissionsTextures getMission(final String entityName) {
        for (final DuszologMissionsTextures mission : values()) {
            if (mission.getEntityName().equals(entityName)) {
                return mission;
            }
        }
        return null;
    }
}
