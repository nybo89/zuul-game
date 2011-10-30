/**
 * 
 */
package nolspotlex.v1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author aboursie
 *
 */
public class TrapTest {

    Game G;
    ArrayList<Type> chosen_rooms;
    boolean found;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        G = new Game();
        chosen_rooms = new ArrayList<Type>();
        chosen_rooms.add(Type.INTENSE_CARE_ROOM);
        chosen_rooms.add(Type.CORRIDOR);
        chosen_rooms.add(Type.RESTAURANT);
        chosen_rooms.add(Type.PARKING);
        chosen_rooms.add(Type.RECEPTION);
        chosen_rooms.add(Type.WAITING_ROOM);
        found = false;
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        G = null;
        chosen_rooms = null;
        found = false;
    }

    /**
     * Test method for {@link nolspotlex.v1.Trap#Trap()}.
     */
    @Test
    public void testTrap() {
        for(Type r : chosen_rooms)
        {
            if (r.equals(Trap.getChosen_trap().getType()))
                found = true;
        }
        assertTrue(found);
    }

}
