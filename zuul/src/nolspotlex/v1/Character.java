package nolspotlex.v1;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * A "Character" represents one person in the scenery of the game. He can give you some indication.
 * 
 * @author Nolan Potier
 * @version 25.10.2011
 */
public class Character {

    private String name;
    private String dialogue;
    private boolean hasSpoken;

    public Character(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
        this.hasSpoken = false;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dialogue
     */
    public String getDialogue() {
        return dialogue;
    }

    /**
     * @param dialogue the dialogue to set
     */
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    /**
     * @return the hasSpoken
     */
    public boolean hasSpoken() {
        return hasSpoken;
    }

    /**
     * @param hasSpoken the hasSpoken to set
     */
    public void setHasSpoken(boolean hasSpoken) {
        this.hasSpoken = hasSpoken;
    }

}
