package nolspotlex.v1;

public class Door {
    private String name; //peut être remplacer par un enum Direction
    private Boolean isLocked;

    /**
     * Creates a new door.
     * @param name is the name of the door.
     */
    public Door(String name){
        this.setName(name);
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
     * In order to lock / unlock both sides at the same time we have to
     * reverse the name of the door in the exit Room (e.g. north -> south).
     * If we do not use orientation based names for the Doors, then the
     * function assumes that the Door has the same name in both Rooms.
     */
    public String rvDoorName(){
        if (getName().equals("east")){
            return "west";
        }
        else if (getName().equals("west")){
            return "east";
        }
        else if (getName().equals("north")){
            return "south";
        }
        else if (getName().equals("south")){
            return "north";
        }
        // otherwise assume the same doorName for both Rooms
        return getName();
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
