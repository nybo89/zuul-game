package zuul.bad;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game {
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, reception, parking, wait_room, restaurant,
        corridor, bedroom, intense_care_room, delivery_room, toilets;

        // create the rooms
        outside = new Room("outside, you're free, RUN !");
        reception = new Room("in the reception");
        parking = new Room("in the parking, there are too many zombies !");
        wait_room = new Room("in a waiting room");
        restaurant = new Room("in the restaurant");
        corridor = new Room("in a scary corridor");
        bedroom = new Room("in bedroom, the air is impure");
        intense_care_room = new Room("in a intense care room, a scary nurse is seeing you");
        delivery_room = new Room("in the delivery room");
        toilets = new Room("in the toilets, small place.. you should leave");


        // initialise room exits
        outside.setExits(reception, null, null, null);
        reception.setExits(parking, wait_room, null, null);
        parking.setExits(null, null, null, null);
        wait_room.setExits(corridor, restaurant, null, reception);
        restaurant.setExits(null, null, null, wait_room);

        corridor.setExits(bedroom, toilets, wait_room, null);
        bedroom.setExits(null, intense_care_room, corridor, delivery_room);
        intense_care_room.setExits(null, null, toilets, bedroom);
        delivery_room.setExits(null, bedroom, null, null);
        toilets.setExits(intense_care_room, null, null, corridor);

        currentRoom = bedroom; // start game outside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the location infos and possible exits
     */
    private void printLocationInfo(Room room){
        currentRoom = room;        
        System.out.println(room.getLongDescription());
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo(currentRoom);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command
     * The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(" go quit help");
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            printLocationInfo(nextRoom);
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }
}
