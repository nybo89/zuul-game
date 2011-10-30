package nolspotlex.v1;

/**
 * TakeCommand Class
 * 
 * @author Alexandre Boursier and Nolan Potier
 * @version 2011.10.24
 */
public class TakeCommand extends Command
{
    /**
     * Constructor for objects of class GoCommand
     */
    public TakeCommand() {}

    /**
     * Try to take an item if present into the room
     * We have to be in the good room
     * Returns always 'false'.
     */
    public boolean execute(Player player)
    {
        if (!hasSecondWord()) {
            System.out.println("take what?");
        } 
        else if (!getSecondWord().equals("key")){
            System.out.println("There is nothing like that to take !");
        }
        else if (!player.getCurrentRoom().getType().equals(Type.DELIVERY_ROOM))
        {
            System.out.println("There is nothing to take there !");             }
        else
        {
            if(!Game.hasGot_key())
            {
                System.out.println("You've just found the Golden Key ! :) ");
                System.out.println("Use it the right way");
                for (Room r : Game.getRooms())
                {
                    if (r.getType().equals(Type.DELIVERY_ROOM))
                        r.setDescription("in the delivery room");
                    if (r.getType().equals(Type.RECEPTION))
                        r.setDescription("in the reception -- Here you can use your GOLDEN KEY ");
                }
                Game.setGot_key(true);
            }
            else 
                System.out.println("The key have already been taken ! Have you lost it ?!");
        }
        return false;
    }
}
