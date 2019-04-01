package exercise3.implementations;

import exercise3.interfaces.Stream;
import org.junit.Test;

import static java.lang.Character.*;
import static org.junit.Assert.*;

public class StreamImplTest {

    @Test
    public void shouldGetMINVALUECharForEmpty() {
        Stream stream1 = new StreamImpl("");

        assertEquals(MIN_VALUE, stream1.getNext());
    }

    @Test
    public void shouldGetNextCharForNonEmpty() {
        Stream stream2 = new StreamImpl("abcde");
        stream2.getNext();
        stream2.getNext();
        stream2.getNext();

        assertEquals('d', stream2.getNext());
    }

    @Test
    public void shouldGetMINVALUEIfStreamReachedTheEnd() {
        Stream stream3 = new StreamImpl("aaa");
        stream3.getNext();
        stream3.getNext();
        stream3.getNext();
        stream3.getNext();
        stream3.getNext();

        assertEquals(MIN_VALUE, stream3.getNext());
    }

    @Test
    public void shouldReturnTrueIfStreamDidNotReachTheEnd() {
        Stream stream = new StreamImpl("aaa");

        assertTrue(stream.hasNext());

        stream.getNext();
        stream.getNext();

        assertTrue(stream.hasNext());
    }

    @Test
    public void shouldReturnFalseIfStreamIsEmpty() {
        Stream stream = new StreamImpl("");
        assertFalse(stream.hasNext());
    }

    @Test
    public void shouldReturnFalseIfStreamReachedTheEnd() {
        Stream stream = new StreamImpl("a");
        stream.getNext();
        stream.getNext();

        assertFalse(stream.hasNext());
    }

    @Test
    public void shouldGetFirstChar() {
        Stream stream = new StreamImpl("a");

        assertEquals('a', stream.getFirstChar());
    }

    @Test
    public void shouldGetMINVALUEAsFirstCharForEmptyStream() {
        Stream stream = new StreamImpl("");

        assertEquals(MIN_VALUE, stream.getFirstChar());
    }

    @Test
    public void shouldReturnTrueForDuplicatedCharacter() {
        Stream stream = new StreamImpl("aa");

        assertTrue(stream.isDuplicated('a'));
    }

    @Test
    public void shouldReturnFalseForUniqueCharacter() {
        Stream stream = new StreamImpl("ab");

        assertFalse(stream.isDuplicated('a'));
    }
}