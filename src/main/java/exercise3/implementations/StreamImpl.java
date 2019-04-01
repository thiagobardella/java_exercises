package exercise3.implementations;

import exercise3.interfaces.Stream;
import org.springframework.stereotype.Service;

@Service
public class StreamImpl implements Stream {

    private String content;
    private int currentPosition = -1;

    public StreamImpl(String content) {
        this.content = content;
    }

    @Override
    public char getNext() {
        if (content.isEmpty()) return Character.MIN_VALUE;

        int lastPosition = content.length() - 1;
        if (currentPosition != lastPosition) {
            currentPosition++;
            return content.toCharArray()[currentPosition];
        }
        return Character.MIN_VALUE;
    }

    @Override
    public boolean hasNext() {
        if (content.isEmpty()) return false;

        int lastPosition = content.length() - 1;
        return currentPosition != lastPosition;
    }

    @Override
    public char getFirstChar() {
        if (content.isEmpty()) return Character.MIN_VALUE;
        return content.charAt(0);
    }

    @Override
    public boolean isDuplicated(char input) {
        return content.indexOf(String.valueOf(input)) !=
                content.lastIndexOf(String.valueOf(input));
    }
}
