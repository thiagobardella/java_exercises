package exercise3.services;

import exercise3.implementations.StreamImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StreamReaderTest {

    @Test
    public void shouldFindSpecialCharacter() {
        StreamImpl stream1 = new StreamImpl("aAbBABacafe");
        char expected1 = 'e';

        StreamImpl stream2 = new StreamImpl("Bacafidbasdafe");
        char expected2 = 'i';

        assertEquals(Character.valueOf(expected1), StreamReader.getSpecialCharacter(stream1));
        assertEquals(Character.valueOf(expected2), StreamReader.getSpecialCharacter(stream2));
    }

    @Test
    public void shouldNotFindSpecialCharacter() {
        StreamImpl stream1 = new StreamImpl("aAbBABacafa");
        StreamImpl stream2 = new StreamImpl("");

        assertNull(StreamReader.getSpecialCharacter(stream1));
        assertNull(StreamReader.getSpecialCharacter(stream2));
        assertNull(StreamReader.getSpecialCharacter(null));

    }




}