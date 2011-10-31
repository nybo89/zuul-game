
package nolspotlex.v1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing for the class Player
 * @author Alexandre Boursier & Nolan Potier
 * @version 2011.10.28
 */
public class PlayerTest {

    Player P;
    Room R;
    Game G;
    boolean move;
    ArrayList<Room> rooms;
    TakeCommand take;
    UseCommand use;
    HashMap<String, Item> H;
    Item I; 

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        G = new Game();
        rooms = Game.getRooms();
        P = new Player();
        R = new Room("In my home",Type.BEDROOM);
        move = false;
        take = new TakeCommand();
        use = new UseCommand();
        H = new HashMap<String,Item>();
        I = new Item("key","useful");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        G = null;
        P = null;
        R = null;
        rooms = null;
        take = null;
        use = null;
        H = null;
        I = null;
    }

    /**
     * Test method for {@link nolspotlex.v1.Player#Player()}.
     */
    @Test
    public void testPlayer() {
        assertNotNull(P);
        assertNull(P.getCurrentRoom());
        assertNotNull(P.getItems());
    }

    /**
     * Test method for {@link nolspotlex.v1.Player#getCurrentRoom()}.
     */
    @Test
    public void testGetCurrentRoom() {
        P.setCurrentRoom(R);
        assertEquals(Type.BEDROOM,P.getCurrentRoom().getType());
        P.setCurrentRoom(null);
        assertNull(P.getCurrentRoom());
    }

    /**
     * Test method for {@link nolspotlex.v1.Player#goRoom(java.lang.String)}.
     */
    @Test
    public void testGoRoom() {
        // Ensure to do lots of moves !
        G.setLimitOfMoves(50);

        // Ensure there is no trap on the way
        for(Room r : rooms)
        {
            if (r.getType().equals(Type.PARKING))
                Trap.setChosen_trap(r);
        }

        // Go nowhere (no door)
        move = Game.getPlayer().goRoom("north");
        assertEquals(Type.BEDROOM, Game.getPlayer().getCurrentRoom().getType());
        assertNotNull(move);

        // We are going to the intense_care_room
        move = Game.getPlayer().goRoom("east");
        assertEquals(Type.INTENSE_CARE_ROOM, Game.getPlayer().getCurrentRoom().getType());
        assertNotNull(move);

        // Going to the teleporter (toilets)
        move = Game.getPlayer().goRoom("south");
        assertNotSame(Type.TOILETS, Game.getPlayer().getCurrentRoom().getType());
        assertNotNull(move);

        // Try to go OUTSIDE without the key
        Game.getPlayer().setCurrentRoom(rooms.get(6));
        move = Game.getPlayer().goRoom("south");
        assertNotNull(move);
        assertEquals(Type.RECEPTION, Game.getPlayer().getCurrentRoom().getType());

        // Quitting game because too many moves
        G.setLimitOfMoves(1);
        move = Game.getPlayer().goRoom("east");
        assertNotNull(move);

    }

    /**
     * Test method for {@link nolspotlex.v1.Player#getItems()}.
     */
    @Test
    public void testGetItems() {

        // Ensure to do lots of moves !
        G.setLimitOfMoves(50);

        // Go into the delivery_room (at the west of the bedroom (the key is there))
        Game.getPlayer().setCurrentRoom(rooms.get(1));
        take.setSecondWord("key");
        take.execute(Game.getPlayer());
        assertTrue(Game.getPlayer().getItems().get("key") != null);

        // Ensure there is no trap on the way
        for(Room r : rooms)
        {
            if (r.getType().equals(Type.PARKING))
                Trap.setChosen_trap(r);
        }

        move = Game.getPlayer().goRoom("east");
        move = Game.getPlayer().goRoom("south");
        move = Game.getPlayer().goRoom("south");
        move = Game.getPlayer().goRoom("west");
        use.setSecondWord("key");
        use.execute(Game.getPlayer());
        Game.getPlayer().setItems(H);
        assertTrue(Game.getPlayer().getItems().get("key") == null);  
    }

    /**
     * Test method for {@link nolspotlex.v1.Player#addItem(nolspotlex.v1.Item)}.
     */
    @Test
    public void testAddItem() {
        P.addItem(I);
        assertEquals(1,P.getItems().size());
        // Adding the same object
        P.addItem(I);
        assertEquals(1,P.getItems().size());
        I.setName("blue_key");
        P.addItem(I);
        assertEquals(2,P.getItems().size());
    }

}
