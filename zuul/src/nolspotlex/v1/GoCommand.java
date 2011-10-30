package nolspotlex.v1;

/**
 * GoCommand Class
 * 
 * @author Alexandre Boursier and Nolan Potier
 * @version 2011.10.24
 */
public class GoCommand extends Command
{
    /**
     * Constructor for objects of class GoCommand
     */
    public GoCommand() {}

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message. 
     * Returns always 'false'.
     */
    @Override
    public boolean execute(Player player)
    {
        if(hasSecondWord()) {
            String direction = getSecondWord();
            player.goRoom(direction);
        }
        else {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
        }
        return false;
    }
}
