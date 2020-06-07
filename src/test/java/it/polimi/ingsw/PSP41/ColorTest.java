package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Player.
 */
public class ColorTest {
    Color color = Color.RED;

    @Test
    public void testNext() {
        color = color.next();
        assertEquals(Color.YELLOW, color);
    }
}
