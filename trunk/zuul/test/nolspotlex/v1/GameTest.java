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
    boolean found,ok;
    TakeCommand take;
    UseCommand use;
    Command command;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        G = new Game();
        rooms = Game.getRooms();
        chosen_rooms = new ArrayList<Type>();
        chosen_rooms.add(Type.INTENSE_CARE_ROOM);
        chosen_rooms.add(Type.CORRIDOR);
        chosen_rooms.add(Type.RESTAURANT);
        chosen_rooms.add(Type.PARKING);
        chosen_rooms.add(Type.RECEPTION);
        chosen_rooms.add(Type.WAITING_ROOM);
        found = false;
        ok = false;
        G.setLimitOfMoves(50);
        take = new TakeCommand();
        use = new UseCommand();
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
        ok = false;
        take = null;
        use = null;
        command = null;
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

        ok = Game.getPlayer().goRoom("east");
        // Inc one time with counting a moove
        assertTrue(Game.countMove());
        ok = Game.getPlayer().goRoom("east");
        assertEquals(G.getNumberOfMoves(), G.getLimitOfMoves());
        assertFalse(Game.countMove());
    }
    
    /**
     * Test method for {@link nolspotlex.v1.Game#takeKey()}.
     */
    @Test
    public void testTakeKey() {

        assertFalse(Game.getPlayer().getItems().get("key") != null);

        // Go into the delivery_room (at the west of the bedroom (the key is there))
        ok = Game.getPlayer().goRoom("west");
        take.setSecondWord("key");
        take.execute(Game.getPlayer());
        assertTrue(Game.getPlayer().getItems().get("key") != null);
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
                Trap.setChosen_trap(r);
        }
        
        // Go into the delivery_room (at the west of the bedroom (the key is there))
        ok = Game.getPlayer().goRoom("west");
        take.setSecondWord("key");
        take.execute(Game.getPlayer());

        // Go into reception and unlock the door 
        ok = Game.getPlayer().goRoom("east");
        ok = Game.getPlayer().goRoom("south");
        ok = Game.getPlayer().goRoom("south");
        ok = Game.getPlayer().goRoom("west");
        use.setSecondWord("key");
        use.execute(Game.getPlayer());
        
        assertFalse(Game.getPlayer().getCurrentRoom().getDoor("south").isLocked());
    }

}