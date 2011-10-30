package nolspotlex.v1;

/**
 * UseCommand Class
 * 
 * @author Alexandre Boursier and Nolan Potier
 * @version 2011.10.24
 */
public class UseCommand extends Command
{
    /**
     * Constructor for objects of class GoCommand
     */
    public UseCommand() {}

    /** 
     * Try to take an item if present into the room
     * We have to be in the good room
     * Returns always 'false'.
     */
    @Override
    public boolean execute(Player player)
    {
        if (!hasSecondWord()) {
            System.out.println("use what?");
        } 
        else if (player.getItems().get(getSecondWord().toLowerCase()) == null){
            System.out.println("There is nothing to use like that !");
        }
        else if (!player.getCurrentRoom().getType().equals(Type.RECEPTION))
        {
            System.out.println("There is nothing to use there !");              
        }
        else if (player.getCurrentRoom().getType().equals(Type.RECEPTION) && !Game.hasGot_key()){
            System.out.println("Door can't be opened yet ! try to get the key ;)");             
        }
        else
        {
            System.out.println("Door is unlocked, beware of zombies behind and ruun !!! ");
            Game.setDoor_unlocked(true);
        }
        return false;
    }
}
