package nolspotlex.v1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the main class of the "World of NolsPotLex" application.
 * "World of NolsxPotLe" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initializes all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * - A level can be chosen, which determine the number of moves a player can have.
 * - The trap door will slows the game if the user tries to go through it.
 * - To access the last door, the player must take the key placed in the "DELIVERY_ROOM"
 * - A beamer can be used to teleport the player wherever he had chosen
 * - The room "TOILETS" randomly teleports the player into another.
 * - To win the game, you have to reach the room "OUTSIDE".
 * 
 * @author Michael Kolling and David J. Barnes and Alexandre Boursier and Nolan Potier
 * @version 2011.10.24
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    // Count the number of current number of moves
    private int numberOfMoves;
    // Fix a limit to the number of moves
    private int limitOfMoves;
    // Check if the player fell into the trap
    private static boolean is_catched_by_trap = false;
    // Attribute dedicated to the trap room
    private Room chosen_trap;
    // Fix a number of rooms for choosing the trap
    private static final int NB_ROOM_TRAP = 6;
    // Build a list which contains all the current rooms of the game
    private static ArrayList<Room> rooms;
    // Check if the player owns the key
    private boolean got_key = false;
    // Check if the final door is unlocked
    private boolean door_unlocked = false;	
    private Room randomRoom;
    private Room beamerRoom;

    /**
     * Create the game and initialize its internal map.
     */
    public Game() {
        rooms = new ArrayList<Room>();
        createRooms();
        numberOfMoves = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     * Create a random trap door to make the game harder.
     * 
     */
    private void createRooms() {
        Room bedroom, delivery_room, intense_care_room, corridor, parking,
        toilets, reception, waiting_room, restaurant, outside,
        trap_room,way_to_go;

        // Create the rooms
        bedroom = new Room("in the bedroom", Type.BEDROOM);
        delivery_room = new Room("in the delivery room --- There is a key on the table...", Type.DELIVERY_ROOM);
        intense_care_room = new Room("In the intense care room", Type.INTENSE_CARE_ROOM);
        corridor = new Room("in the corridor", Type.CORRIDOR);
        parking = new Room("in the parking, but not outside...", Type.PARKING);
        toilets = new Room("in the toilets", Type.TOILETS);
        reception = new Room("in the reception", Type.RECEPTION);
        waiting_room = new Room("in the waiting room", Type.WAITING_ROOM);
        restaurant = new Room("in the restaurant", Type.RESTAURANT);
        outside = new Room("outside !!", Type.OUTSIDE);
        trap_room = new Room("in a dark room with strange noises.. Get out of here now !", Type.TRAP_ROOM);
        way_to_go = new Room("in another room close to a such familiar place...", Type.WAY_TO_GO);

        // Initialise room exits
        bedroom.setExit("east", intense_care_room);
        bedroom.setExit("west", delivery_room);
        bedroom.setExit("south", corridor);

        delivery_room.setExit("east", bedroom);

        intense_care_room.setExit("west", bedroom);
        intense_care_room.setExit("south", toilets);

        toilets.setExit("north", intense_care_room);
        toilets.setExit("west", corridor);

        corridor.setExit("north", bedroom);
        corridor.setExit("east", toilets);
        corridor.setExit("south", waiting_room);

        waiting_room.setExit("north", corridor);	
        waiting_room.setExit("east", restaurant);
        waiting_room.setExit("west", reception);

        reception.setExit("east", waiting_room);
        reception.setExit("north", parking);
        reception.setExit("south", outside);

        parking.setExit("south", reception);

        restaurant.setExit("west", waiting_room);

        // Initialize the trap way
        trap_room.setExit("north", way_to_go);

        way_to_go.setExit("south", trap_room);
        way_to_go.setExit("north", bedroom);

        //Create character
        //
        Character John = new Character("John", "Don't go to the east.");

        corridor.addCharacter(John);

        // start game in the bedroom
        currentRoom = bedroom; 
        beamerRoom = bedroom;
        randomRoom = toilets;

        createRandomTrap();
    }

    /**
     * CreateRandomTrap : The goal of this method is to choose amongst 
     * rooms a trap which consist in falling into a new room we can cross
     * one way. The following way brings the player to the bedroom.
     * 
     */
    public void createRandomTrap()
    {
        int random = (int)(Math.random() * NB_ROOM_TRAP);
        // Select a random room
        Type trap = Type.values()[random];
        for(Room r : rooms){
            if(r.getType().equals(trap)){
                chosen_trap = r;
            }
        }
    }

    /**
     * Adding a room to the dictionary
     * @param r
     */
    public static void addRoom(Room r){
        rooms.add(r);
    }	

    /**
     * Main play routine. Loops until end of play.
     * 
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
    }

    /**
     * Print out the opening message for the player. 
     * New form of time limit : a level is asked at the beginning
     * of the game defined by the maximum tolerated number of moves.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the NolsPotLex awesome game ! !");
        System.out.println("This platform is dedicated to the Zork's PGM.");

        chooseLevel();

        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());

        // Instantiate a parser which will read the command words
        parser = new Parser();
    }

    /**
     * Choosing the level of the game :
     * - Easy is for beginners 
     * - Medium brings a little bit more challenge
     * - Hard is the "no-mistake way"
     * 
     */
    private void chooseLevel()
    {
        // Choosing a level (asking to the user through the terminal)
        Scanner reader = new Scanner(System.in);
        System.out.println("Please choose a level : Easy for peasant(0) - Medium for warriors (1) - Hard for gods (2)");
        // Find the chosen level and alter the number of moves accorfing to the chosen one
        try {
            switch (reader.nextInt()) {
            case 0:
                limitOfMoves = 20;
                System.out.println("You've chosen the easy way to win ! - Number of moves : " + limitOfMoves);
                break;
            case 1:
                limitOfMoves = 16;
                System.out.println("You've chosen the medium level :)- Number of moves : " + limitOfMoves);
                break;
            case 2:
                limitOfMoves = 14;
                System.out.println("It's gonna be hard this way :@  - Number of moves : " + limitOfMoves);
                break;
            default:
                limitOfMoves = 20;
                System.out.println("Unkown command - Default level : Easy - Number of moves : " + limitOfMoves);
                break;
            }
        } catch(Exception e){
            limitOfMoves = 20;
            System.out.println("Unkown command - Default level : Easy - Number of moves : " + limitOfMoves);
        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command
     *            The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            if (!goRoom(command))
            {
                // Quit the game after having reached the maximum number of moves
                wantToQuit = quit(new Command(CommandWord.QUIT, null));
            }
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.TAKE)
        {
            takeKey(command);
        }
        else if (commandWord == CommandWord.USE)
        {
            useKey(command);
        }
        else if (commandWord == CommandWord.BEAMER) {
            beamer(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are in the world of ZUUL no way to get out instead of finding the EXIT DOOR ! MUHAHAHA");
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     * 
     * New form of time limit : Each time a player uses the command "GO" 
     * with a good given direction (ex : go east), it is counted as a move
     * 
     * All cases are checked in order to prevent the player from going outside without
     * the key, or without having opened the door, etc.
     * 
     * If the number of moves equals to the limit number of moves,
     *  the player loses the game.
     *  
     */
    public boolean goRoom(Command command) {

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return true;
        }

        // Get the direction to go
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door! -- Why are you so stupid ?!");
            // Count a move because the player is stupid
            boolean decision = countMove();	
            return decision;
        }
        // Check if the player doesn't owns the key at the reception
        if(nextRoom.getType().equals(Type.OUTSIDE) && !got_key && !door_unlocked )
        {
            System.out.println("Sorry but the door is locked ! you have to find the key somewhere over the rainbow..");
            return true;
        }
        // Check if the player owns the key at the reception but haven't opened it
        else if(nextRoom.getType().equals(Type.OUTSIDE) && got_key && !door_unlocked)
        {
            System.out.println("You have to open the door to get out of there !");
            return true;
        }
        else {
            currentRoom = nextRoom;
            //Check if there are Character in the room
            //
            Character person = currentRoom.getCharacter(); 
            if(person != null && !person.hasSpoken()) {
                System.out.println("\nThere is a person in this Room...\n" + person.getName() + ": " + person.getDialogue());
                person.setHasSpoken(true);
            }
            //Check that is randomRoom
            //
            if(currentRoom.equals(randomRoom)) {
                goRandomRoom();
            }
            // Check if the player is in the trap room and if he has already been there
            // Yes : the game continues normally
            // No  : he falls into the trap
            if (!is_catched_by_trap && currentRoom.getType().equals(chosen_trap.getType()))
            {
                System.out.println();
                System.out.println("------ Aaaahh !! You are falling into a trap -----------");
                System.out.println();

                for(Room r : rooms){
                    if(r.getType().equals(Type.TRAP_ROOM)){
                        currentRoom = r;
                    }
                }	
                is_catched_by_trap = true;
            }
            // Check if the nextroom is the final exit
            if (currentRoom.getType().equals(Type.OUTSIDE)) {
                System.out.println("The outside checkpoint has been reached ! ");
                System.out.println("You have won the game, well done Indiana ;)");
                return false;
            }
            System.out.println(currentRoom.getLongDescription());
        }
        boolean decision = countMove();	
        return decision;
    }

    /**
     * Counting the current move of the player
     * @return false if the player has executed too many moves, true otherwise
     */
    public boolean countMove(){
        // Count a move
        numberOfMoves++;

        // Give some informations concerning the number of moves
        if (numberOfMoves < limitOfMoves) {
            System.out.println("Current number of moves : " + numberOfMoves);
            System.out.println("Moves left : " + (limitOfMoves - numberOfMoves));
            return true;
            // Ending the game if the number of moves is reached
        } else {
            System.out.println("You have reached the maximum number of moves");
            System.out.println("By the way, GAME OVER ! ");
            return false;
        }
    }

    /**
     * "take" was entered. Check the rest of the command to see wether
     * we take the key or not
     * 
     * @param command
     */
    public void takeKey(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("take what?");
        } 
        else if (!command.getSecondWord().equals("key")){
            System.out.println("There is nothing like that to take !");
        }
        else if (!currentRoom.getType().equals(Type.DELIVERY_ROOM))
        {
            System.out.println("There is nothing to take there !");		}
        else
        {
            if(!got_key)
            {
                System.out.println("You've just found the Golden Key ! :) ");
                System.out.println("Use it the right way");
                for (Room r : rooms)
                {
                    if (r.getType().equals(Type.DELIVERY_ROOM))
                        r.setDescription("in the delivery room");
                    if (r.getType().equals(Type.RECEPTION))
                        r.setDescription("in the reception -- Here you can use your GOLDEN KEY ");
                }
                got_key = true;
            }
            else 
                System.out.println("The key have already been taken ! Have you lost it ?!");
        }
    }

    /**
     * "use" was entered. Check the rest of the command to see wether
     * we use the key or not
     * 
     * @param command
     */
    private void useKey(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("use what?");
        } 
        else if (!command.getSecondWord().equals("key")){
            System.out.println("There is nothing to use like that !");
        }
        else if (!currentRoom.getType().equals(Type.RECEPTION))
        {
            System.out.println("There is nothing to use there !");		
        }
        else if (currentRoom.getType().equals(Type.RECEPTION) && !got_key){
            System.out.println("Door can't be opened yet ! try to get the key ;)");		
        }
        else
        {
            System.out.println("Door is unlocked, beware of zombies behind and ruun !!! ");
            door_unlocked = true;
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
     * Try to use beamer device. When you charge the beamer, it memorizes the current room.
     * When you fire the beamer, it transports you immediately back to the room it was
     * charged in.
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

    /**
     * Randomly transported into one of the other rooms.
     */
    private void goRandomRoom(){
        int nbRoom = rooms.size();

        int random = (int)(Math.random() * (nbRoom));

        System.out.println("\n ------- Aaaaah !! you're sucked into a black hole -------\n");

        // Removing toilets and outside
        rooms.remove(4);
        rooms.remove(7);
        
        System.out.println(rooms.get(4).getShortDescription());
        System.out.println(rooms.get(7).getShortDescription());
        currentRoom = (Room) rooms.get(random);
        System.out.println(currentRoom.getLongDescription()); 
    }

    /**
     * @return the numberOfMoves
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    /**
     * @return the limitOfMoves
     */
    public int getLimitOfMoves() {
        return limitOfMoves;
    }

    /**
     * @return the rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * @return the chosen_trap
     */
    public Room getChosen_trap() {
        return chosen_trap;
    }

    /**
     * @param limitOfMoves the limitOfMoves to set
     */
    public void setLimitOfMoves(int lom) {
        limitOfMoves = lom;
    }

    /**
     * @return the got_key
     */
    public boolean isGot_key() {
        return got_key;
    }

    /**
     * @return the door_unlocked
     */
    public boolean isDoor_unlocked() {
        return door_unlocked;
    }

    /**
     * @param chosen_trap the chosen_trap to set
     */
    public void setChosen_trap(Room chosen_trap) {
        this.chosen_trap = chosen_trap;
    }
}
