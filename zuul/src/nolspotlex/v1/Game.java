package nolspotlex.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is the main class of the "World of NolsPotLex" application.
 * "World of NolsPotLex" is a very simple, text based adventure game. Users can walk
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
    private static Player player;
    // Count the number of current number of moves
    private static int numberOfMoves;
    // Fix a limit to the number of moves
    private static int limitOfMoves;
    // Fix a number of rooms for choosing the teleport room
    private static final int NB_ROOM_TELEPORT = 8;
    // Build a list which contains all the current rooms of the game
    private static ArrayList<Room> rooms;

    private static Room randomRoom;
    private static Room beamerRoom;
    private HashMap<String, Item> items;
    private ArrayList<Door> doors;

    private Room bedroom, delivery_room, intense_care_room, corridor, parking,
    toilets, reception, waiting_room, restaurant, outside,
    trap_room,way_to_go;

    /**
     * Create the game and initialize its internal map.
     */
    public Game() {
        rooms = new ArrayList<Room>();
        items = new HashMap<String, Item>();  
        doors = new ArrayList<Door>();
        numberOfMoves = 0;

        createItems();
        createDoors();        
        setPlayer(new Player());
        createRooms();
        setRoomsDoors();
        addItemsToRooms();
        new Trap();
    }

    /**
     * Create all the rooms and link their exits together.
     * Create a random trap door to make the game harder.
     * 
     */
    private void createRooms() {

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


        //Create character
        //
        Character John = new Character("John", "Don't go to the east.");

        corridor.addCharacter(John);

        // start game in the bedroom
        getPlayer().setCurrentRoom(bedroom); 
        beamerRoom = bedroom;
        randomRoom = toilets;
    }

    private void createItems() {
        Item key;
        key = new Item("Key", "This key can open a door...");

        items.put(key.getName().toLowerCase(), key);
    }

    /**
     * Initialise room doors and respective locks
     */
    private void setRoomsDoors(){        

        // Initialise room exits
        bedroom.setDoor("east", intense_care_room, false);
        bedroom.setDoor("west", delivery_room, false);
        bedroom.setDoor("south", corridor, false);

        delivery_room.setDoor("east", bedroom, false);

        intense_care_room.setDoor("west", bedroom, false);
        intense_care_room.setDoor("south", toilets, false);

        toilets.setDoor("north", intense_care_room, false);
        toilets.setDoor("west", corridor, false);

        corridor.setDoor("north", bedroom, false);
        corridor.setDoor("east", toilets, false);
        corridor.setDoor("south", waiting_room, false);

        waiting_room.setDoor("north", corridor, false);        
        waiting_room.setDoor("east", restaurant, false);
        waiting_room.setDoor("west", reception, false);

        reception.setDoor("east", waiting_room, false);
        reception.setDoor("north", parking, false);
        reception.setDoor("south", outside, true);

        parking.setDoor("south", reception, false);

        restaurant.setDoor("west", waiting_room, false);

        // Initialize the trap way
        trap_room.setDoor("up", way_to_go, false);
        way_to_go.setDoor("up", bedroom, false);
    }

    /**
     * Create the Doors for the game.
     */
    private void createDoors(){
        Door north, east, south, west,up;

        north = new Door("north");
        east = new Door("east");
        south = new Door("south");
        west = new Door("west");
        // To get out of the trap
        up = new Door("up");

        //add each door to doors collection
        doors.add(north);
        doors.add(east);
        doors.add(south);
        doors.add(west);
        doors.add(up);
    }

    /**
     * Add items to the rooms
     */
    private void addItemsToRooms(){
        delivery_room.addItem(items.get("key"));
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

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while(! finished) {
            Command command = parser.getCommand();
            if(command == null) {
                System.out.println("I don't understand...");
            } else {
                finished = command.execute(getPlayer());
            }
        }
    }

    /**
     * Print out the opening message for the player. 
     * New form of time limit : a level is asked at the beginning
     * of the game defined by the maximum tolerated number of moves.
     * @return 
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the NolsPotLex awesome game ! !");
        System.out.println("This platform is dedicated to the Zork's PGM.");

        chooseLevel();

        System.out.println("Type help if you need help.");
        System.out.println();
        System.out.println(getPlayer().getCurrentRoom().getLongDescription());

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
     * Counting the current move of the player
     * @return false if the player has executed too many moves, true otherwise
     */
    public static boolean countMove(){
        // Count a move
        numberOfMoves++;

        // Give some informations concerning the number of moves
        if (numberOfMoves < limitOfMoves) {
            System.out.println("Current number of moves : " + numberOfMoves);
            System.out.println("Moves left : " + (limitOfMoves - numberOfMoves));
            return false;
            // Ending the game if the number of moves is reached
        } else {
            System.out.println("You have reached the maximum number of moves");
            System.out.println("By the way, GAME OVER ! ");
            System.out.println();
            System.out.println();
            return true;
        }
    }

    /**
     * Randomly transported into one of the other rooms.
     */
    public static void goRandomRoom(){

        int random = (int)(Math.random() * NB_ROOM_TELEPORT);
        // Select a random room
        Type teleport = Type.values()[random];
        for(Room r : rooms){
            if(r.getType().equals(teleport)){
                getPlayer().setCurrentRoom(r);
            }
        }
        System.out.println("\n ------- Aaaaah !! you're sucked into a black hole -------\n");
        System.out.println(getPlayer().getCurrentRoom().getLongDescription()); 
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
    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * @param limitOfMoves the limitOfMoves to set
     */
    public void setLimitOfMoves(int lom) {
        limitOfMoves = lom;
    }

    /**
     * @return the randomRoom
     */
    public static Room getRandomRoom() {
        return randomRoom;
    }


    /**
     * @param randomRoom the randomRoom to set
     */
    public static void setRandomRoom(Room random) {
        randomRoom = random;
    }


    /**
     * @return the beamerRoom
     */
    public static Room getBeamerRoom() {
        return beamerRoom;
    }


    /**
     * @param beamerRoom the beamerRoom to set
     */
    public static void setBeamerRoom(Room beamer) {
        beamerRoom = beamer;
    }

    /**
     * @return the player
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public static void setPlayer(Player player) {
        Game.player = player;
    }

}
