package nolspotlex.v1;

public class Door {
    private String name; //peut être remplacer par un enum Direction
    private Boolean isLocked;

    /**
     * Creates a new door.
     * @param name is the name of the door.
     */
    public Door(String name){
        this.name = name;
        isLocked = false;
    }

    /**
     * Locks or unlocks the door.
     * @param value true if we want to lock the door, false otherwise.
     */
    public void setLock(Boolean value){
        isLocked = value;
    }

    /**
     * Indicates whether the door is locked or not.
     * @return true if the door is locked, false otherwise.
     */
    public Boolean isLocked(){
        return isLocked;
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
}
