package nolspotlex.v1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DoorTest {
    
    Door door;

    @Before
    public void setUp() throws Exception {
        door = new Door("door");
    }

    @After
    public void tearDown() throws Exception {
    }
   
    @Test
    public void testIsLocked() {
        assertFalse(door.isLocked());
    }
    
    @Test
    public void testSetLock() {
        assertFalse(door.isLocked());
        door.setLock(true);
        assertTrue(door.isLocked());
    }

    @Test
    public void testGetName() {
        assertEquals("door", door.getName());
    }

    @Test
    public void testSetName() {
        door.setName("north");
        assertEquals("north", door.getName());
    }

}
