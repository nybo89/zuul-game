package nolspotlex.v1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing for the class Game
 * @author Alexandre Boursier & Nolan Potier
 * @version 2011.10.28
 */
public class GameTest {

    Game G;
    ArrayList<Type> chosen_rooms;
    ArrayList<Room> rooms;
    boolean found;
    Command go_EAST,go_SOUTH,go_WEST,take,use;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        G = new Game();
        rooms = G.getRooms();
        chosen_rooms = new ArrayList<Type>();
        chosen_rooms.add(Type.INTENSE_CARE_ROOM);
        chosen_rooms.add(Type.CORRIDOR);
        chosen_rooms.add(Type.RESTAURANT);
        chosen_rooms.add(Type.PARKING);
        chosen_rooms.add(Type.RECEPTION);
        chosen_rooms.add(Type.WAITING_ROOM);
        found = false;
        go_EAST = new Command(CommandWord.GO, "east");
        go_SOUTH = new Command(CommandWord.GO, "south");
        go_SOUTH = new Command(CommandWord.GO, "south");
        go_WEST = new Command(CommandWord.GO, "west");
        take = new Command(CommandWord.TAKE, "key");
        use = new Command(CommandWord.USE, "key");
        G.setLimitOfMoves(50);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        G = null;
        rooms = null;
        chosen_rooms = null;
        found = false;
        go_EAST = null;
        go_SOUTH = null;
        go_WEST = null;
        take = null;
        use = null;
    }

    /**
     * Test method for {@link nolspotlex.v1.Game#Game()}.
     */
    @Test
    public void testGame() {
        // Check the object was correctly created
        assertNotNull(G);
        assertEquals(0, G.getNumberOfMoves());
        // 12 rooms have been created
        assertEquals(12, rooms.size());
    }

    /**
     * Test method for {@link nolspotlex.v1.Game#createRandomTrap()}.
     */
    @Test
    public void testCreateRandomTrap() {

        G.createRandomTrap();
        for(Type r : chosen_rooms)
        {
            if (r.equals(G.getChosen_trap().getType()))
                found = true;
        }
        assertTrue(found);
    }

    /**
     * Test method for {@link nolspotlex.v1.Game#addRoom(nolspotlex.v1.Room)}.
     */
    @Test
    public void testAddRoom() {
        Game.addRoom(new Room("Test number 1", Type.BEDROOM));
        assertEquals("Test number 1",rooms.get(rooms.size()-1).getShortDescription());
        assertEquals(Type.BEDROOM,rooms.get(rooms.size()-1).getType());
    }

    /**
     * Test method for {@link nolspotlex.v1.Game#countMove()}.
     */
    @Test
    public void testCountMove() {
        G.setLimitOfMoves(3);

        G.processCommand(go_WEST);
        // Inc one time with counting a moove
        assertTrue(G.countMove());
        G.processCommand(go_WEST);
        assertEquals(G.getNumberOfMoves(), G.getLimitOfMoves());
        assertFalse(G.countMove());
    }

    /**
     * Test method for {@link nolspotlex.v1.Game#takeKey()}.
     */
    @Test
    public void testTakeKey() {

        assertFalse(G.isGot_key());

        // Go into the delivery_room (at the west of the bedroom (the key is there))
        G.processCommand(go_WEST);
        G.processCommand(take);
        assertTrue(G.isGot_key());
    }

    /**
     * Test method for {@link nolspotlex.v1.Game#useKey()}.
     */
    @Test
    public void testUseKey() {

        // Ensure there is no trap on the way
        for(Room r : rooms)
        {
            if (r.getType().equals(Type.PARKING))
                G.setChosen_trap(r);
        }
        assertFalse(G.isDoor_unlocked());
        // Go into the delivery_room (at the west of the bedroom (the key is there))
        G.processCommand(go_WEST);
        G.processCommand(take);
        assertTrue(G.isGot_key());

        // Go into reception and unlock the door 
        G.processCommand(go_EAST);
        G.processCommand(go_SOUTH);
        G.processCommand(go_SOUTH);
        G.processCommand(go_WEST);
        G.processCommand(use);
        assertTrue(G.isDoor_unlocked());
    }

}