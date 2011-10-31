package nolspotlex.v1;

public class Item {

    private String name;
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean executeItem(Player player) {

        if(this.name.equals("Key")) {
            for(Door r : player.getCurrentRoom().getDoors()) {
                if(r.isLocked()) {
                    r.setLock(false);
                    System.out.println(r.getName() + " is open !");
                    return true;
                }                                    
            }
        }
        return false;
    }

}
