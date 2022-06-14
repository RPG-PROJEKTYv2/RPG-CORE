package rpg.rpgcore.tab;

public enum PlayerInfoAction {
    ADD_PLAYER(0), UPDATE_DISPLAY_NAME(3);

    private final int actionID;

    PlayerInfoAction(final int actionID) {
        this.actionID = actionID;
    }

    public int getActionID() {
        return this.actionID;
    }
}
