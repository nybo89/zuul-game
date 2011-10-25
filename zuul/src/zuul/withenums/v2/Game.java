package zuul.withenums.v2;

import java.util.ArrayList;
import java.util.Random;

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
    private Room beamerRoom;
    private ArrayList<Room> rooms;
    private Room randomRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        rooms = new ArrayList<Room>();
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

        reception.setExit("north", parking);
        reception.setExit("east", wait_room);
        reception.setExit("south", outside);

        wait_room.setExit("north", corridor);
        wait_room.setExit("east", restaurant);
        wait_room.setExit("west", reception);

        restaurant.setExit("west", wait_room);        

        corridor.setExit("north", bedroom);
        corridor.setExit("east", toilets);
        corridor.setExit("south", wait_room);

        bedroom.setExit("east", intense_care_room);
        bedroom.setExit("south", corridor);
        bedroom.setExit("west", delivery_room);

        intense_care_room.setExit("south", toilets);
        intense_care_room.setExit("west", bedroom);

        delivery_room.setExit("east", bedroom);

        toilets.setExit("north", intense_care_room);
        toilets.setExit("west", corridor);
        
        //Create character
        //
        Character John = new Character("John", "Don't go to the east.");
        
        corridor.addCharacter(John);

        currentRoom = bedroom; // start game outside
        beamerRoom = bedroom;
        randomRoom = toilets;
        
        //Add rooms in list of all rooms
        //
        rooms.add(delivery_room);
        rooms.add(intense_care_room);
        rooms.add(bedroom);
        rooms.add(restaurant);
        rooms.add(reception);
        rooms.add(corridor);
        rooms.add(wait_room);
        rooms.add(parking);
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out
        .println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command
     *            The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.BEAMER) {
            beamer(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
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
        parser.showCommands();
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
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            //Check if there are Character in the room
            //
            Character person = currentRoom.getCharacter(); 
            if(person != null && !person.hasSpoken()) {
                System.out.println("\nThere are a person in this Room...\n" + person.getName() + ": " + person.getDialogue());
                person.setHasSpoken(true);
            }
            //Check that is randomRoom
            //
            if(currentRoom.equals(randomRoom)) {
                goRandomRoom();
            }
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

    /**
     * 
     */

    private void beamer(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to do...
            System.out.println("Beamer what ? (charged or fired)");
            return;
        }
        
        String action = command.getSecondWord();

        
        if (action.equals("charged")) {
            beamerRoom = currentRoom;
            System.out.println("This room is charged to beamer !");
        } else if (action.equals("fired")) {
            currentRoom = beamerRoom;
            System.out.println(currentRoom.getLongDescription());
        } else {
            System.out.println("Invalid command beamer !");
            return;
        }
        
    }
    
    private void goRandomRoom(){
        int nbRoom = rooms.size();
        
        int random = (int)(Math.random() * (nbRoom));
        
        System.out.println("\n ------- Aaaaah !! you're sucked into a black hole -------\n");
        
        currentRoom = (Room) rooms.get(random);
        System.out.println(currentRoom.getLongDescription());              
        
    }
}
