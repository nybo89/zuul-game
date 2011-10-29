package nolspotlex.v1;

import static org.junit.Assert.*;

import nolspotlex.v1.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CharacterTest {
    
    Character person;

    @Before
    public void setUp() throws Exception {
        person = new Character("George", "Hello !");        
    }

    @After
    public void tearDown() throws Exception {
    }

    
    @Test
    public void testGetName() {
        assertEquals("George", person.getName());
    }

    @Test
    public void testSetName() {
        person.setName("Harvey");
        assertEquals("Harvey", person.getName());
    }

    @Test
    public void testGetDialogue() {
        assertEquals("Hello !", person.getDialogue());
    }

    @Test
    public void testSetDialogue() {
        person.setDialogue("Bye !");
        assertEquals("Bye !", person.getDialogue());
    }

    @Test
    public void testHasSpoken() {
        assertEquals(false, person.hasSpoken());
    }

    @Test
    public void testSetHasSpoken() {
        person.setHasSpoken(true);
        assertEquals(true, person.hasSpoken());
    }

}
