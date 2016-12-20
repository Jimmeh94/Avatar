package avatar.game.areas;

public enum AreaReferences {

    TEST_PASSIVE("testPassive", 0),
    TEST_HOSTILE("testHostile", 1);

    private String stringID;
    private int intID;

    AreaReferences(String s, int i){
        this.stringID = s;
        this.intID = i;
    }

    public String getStringID() {
        return stringID;
    }

    public int getIntID() {
        return intID;
    }
}
