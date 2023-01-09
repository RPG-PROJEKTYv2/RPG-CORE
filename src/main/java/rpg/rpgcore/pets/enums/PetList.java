package rpg.rpgcore.pets.enums;


import rpg.rpgcore.utils.DoubleUtils;

public enum PetList {
    DUSZEK_ZWYKLY("Duszek - Zwykly", 1, 0.03, 1, 0.03, 1, 0, 1, 0),
    DUSZEK_RZADKI("Duszek - Rzadki", 1, 0.05, 1, 0.065, 1, 0, 1, 0),
    DUSZEK_EPICKI("Duszek - Epicki", 1, 0.07, 1, 0.07, 1, 0, 1, 0),
    DUSZEK_LEGENDARNY("Duszek - Legendarny", 1, 0.1, 1, 0.1, 1, 0, 1, 0),
    ZLOTA_RYBKA_ZWYKLY("Zlota Rybka - Zwykly", 1, 0.1, 1, 0.05, 1, 0, 1, 0),
    ZLOTA_RYBKA_RZADKI("Zlota Rybka - Rzadki", 1, 0.25, 1, 0.1, 1, 0, 1, 0),
    ZLOTA_RYBKA_EPICKI("Zlota Rybka - Epicki", 1, 0.5, 1, 0.15, 1, 0, 1, 0),
    ZLOTA_RYBKA_LEGENDARNY("Zlota Rybka - Legendarny", 1, 0.75, 1, 0.2, 1, 0, 1, 0),
    PANCERNIK_ZWYKLY("Pancernik - Zwykly", 1, 0.05, 1, 0.05, 1, 0, 1, 0),
    PANCERNIK_RZADKI("Pancernik - Rzadki", 1, 0.075, 1, 0.075, 1, 0, 1, 0),
    PANCERNIK_EPICKI("Pancernik - Epicki", 1, 0.1, 1, 0.1, 1, 0, 1, 0),
    PANCERNIK_LEGENDARNY("Pancernik - Legendarny", 1, 0.15, 1, 0.15, 1, 0, 1, 0),
    FOKA_ZWYKLY("Foka - Zwykly", 1, 0.01, 1, 0.01, 1, 0, 1, 0),
    FOKA_RZADKI("Foka - Rzadki", 1, 0.025, 1, 0.025, 1, 0, 1, 0),
    FOKA_EPICKI("Foka - Epicki", 1, 0.035, 1, 0.035, 1, 0, 1, 0),
    FOKA_LEGENDARNY("Foka - Legendarny", 1, 0.5, 1, 0.5, 1, 0, 1, 0),
    NIETOPERZ_ZWYKLY("Nietoperz - Zwykly", 1, 0.025, 1, 0.025, 1, 0, 1, 0),
    NIETOPERZ_RZADKI("Nietoperz - Rzadki", 1, 0.05, 1, 0.05, 1, 0, 1, 0),
    NIETOPERZ_EPICKI("Nietoperz - Epicki", 1, 0.075, 1, 0.075, 1, 0, 1, 0),
    NIETOPERZ_LEGENDARNY("Nietoperz - Legendarny", 1, 0.1, 1, 0.1, 1, 0, 1, 0),
    BOBR_ZWYKLY("Bobr - Zwykly", 1, 0.025, 1, 0.025, 1, 0, 1, 0),
    BOBR_RZADKI("Bobr - Rzadki", 1, 0.05, 1, 0.05, 1, 0, 1, 0),
    BOBR_EPICKI("Bobr - Epicki", 1, 0.075, 1, 0.075, 1, 0, 1, 0),
    BOBR_LEGENDARNY("Bobr - Legendarny", 1, 0.1, 1, 0.1, 1, 0, 1, 0),
    OGNISTY_SMOK_ZWYKLY("Ognisty Smok - Zwykly", 1, 0.05, 1, 1.5, 1, 0, 1, 0),
    OGNISTY_SMOK_RZADKI("Ognisty Smok - Rzadki", 1, 0.075, 1,2.5, 1, 0, 1, 0),
    OGNISTY_SMOK_EPICKI("Ognisty Smok - Epicki", 1, 0.1, 1, 3.5, 1, 0, 1, 0),
    OGNISTY_SMOK_LEGENDARNY("Ognisty Smok - Legendarny", 1, 0.15, 1, 5, 1, 0, 1, 0),
    DEMON_ZWYKLY("Demon - Zwykly", 1, 0.075, 1, 0.05, 1, 0, 1, 0),
    DEMON_RZADKI("Demon - Rzadki", 1, 0.1, 1, 0.075, 1, 0, 1, 0),
    DEMON_EPICKI("Demon - Epicki", 1, 0.15, 1, 0.1, 1, 0, 1, 0),
    DEMON_LEGENDARNY("Demon - Legendarny", 1, 0.2, 1, 0.15, 1, 0, 1, 0),
    WAMPIR_ZWYKLY("Wampir - Zwykly", 1, 0.015, 5, 0.5, 1, 0, 1, 0),
    WAMPIR_RZADKI("Wampir - Rzadki", 1, 0.025, 5, 1, 1, 0, 1, 0),
    WAMPIR_EPICKI("Wampir - Epicki", 1, 0.035, 5, 1.5, 1, 0, 1, 0),
    WAMPIR_LEGENDARNY("Wampir - Legendarny", 1, 0.05, 5, 1, 1, 0, 1, 0);

    private final String name;
    private final int per1, per2, per3, per4;
    private final double ab1, ab2, ab3, ab4;

    PetList(String name, int per1, double ab1, int per2, double ab2, int per3, double ab3, int per4, double ab4) {
        this.name = name;
        this.per1 = per1;
        this.ab1 = ab1;
        this.per2 = per2;
        this.ab2 = ab2;
        this.per3 = per3;
        this.ab3 = ab3;
        this.per4 = per4;
        this.ab4 = ab4;
    }

    public String getName() {
        return name;
    }

    private static int getPer(final String rarity, final int abNumber) {
        if (getByName(rarity) == null) {
            return 0;
        }
        switch (abNumber) {
            case 1:
                return getByName(rarity).per1;
            case 2:
                return getByName(rarity).per2;
            case 3:
                return getByName(rarity).per3;
            case 4:
                return getByName(rarity).per4;
            default:
                return 0;
        }
    }

    public static PetList getByName(final String petNamePlusRarity) {
        for (PetList pet : PetList.values()) {
            if (pet.getName().equalsIgnoreCase(petNamePlusRarity)) {
                return pet;
            }
        }
        return null;
    }

    public static double getAbilityIncrease(final String petNamePlusRarity, final int abNumber, final int petLvl) {
        if (getPer(petNamePlusRarity, abNumber) == 0) {
            return 0;
        }
        final PetList pet = getByName(petNamePlusRarity);
        if (pet == null) return 0;
        if (petLvl % getPer(petNamePlusRarity, abNumber) == 0) {
            switch (abNumber) {
                case 1:
                    return DoubleUtils.round(pet.ab1, 2);
                case 2:
                    return DoubleUtils.round(pet.ab2, 2);
                case 3:
                    return DoubleUtils.round(pet.ab3, 2);
                case 4:
                    return DoubleUtils.round(pet.ab4, 2);
                default:
                    return 0;
            }
        }
        return 0;
    }
}
