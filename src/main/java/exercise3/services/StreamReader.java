package exercise3.services;

import exercise3.implementations.StreamImpl;
import exercise3.interfaces.Stream;

import static exercise3.services.State.*;
import static java.lang.System.*;

public class StreamReader {

    private static final String VOWELS = "AÀÁÂÃÄÅĀĂĄǺȀȂẠẢẤẦẨẪẬẮẰẲẴẶḀÆǼEȄȆḔḖḘḚḜẸẺẼẾỀỂỄỆĒĔĖĘĚÈÉÊËIȈȊḬḮỈỊĨĪĬĮİÌÍÎÏĲOŒØǾȌȎṌṎṐṒỌỎỐỒỔỖỘỚỜỞỠỢŌÒÓŎŐÔÕÖUŨŪŬŮŰŲÙÚÛÜȔȖṲṴṶṸṺỤỦỨỪỬỮỰYẙỲỴỶỸŶŸÝ";

    public static void printSpecialCharacter(String input) {
        Stream stream = new StreamImpl(input);
        Character specialCharacter = getSpecialCharacter(stream);
        if (specialCharacter == null) {
            out.println("The input string '" + input + "' has no special vowel!");
        }
        else {
            out.println("The special vowel for input String " + input + " is '" + specialCharacter + "'");
        }
    }

    public static Character getSpecialCharacter(Stream input) {
        if (input == null) return null;

        int currentState = INITIAL;
        char currentChar;
        while ( input.hasNext()
        ) {
            currentChar = input.getNext();
            currentState = getCurrentState(input, currentState, currentChar);
            if (currentState == FINAL) return currentChar;
        }
        return null;
    }

    private static int getCurrentState(Stream input, int currentState, char currentChar) {
        currentState = StateController.getNextState(currentState, isVowel(currentChar));
        if (currentState == VOWEL_CONSONANT_VOWEL_FOUND) {
            currentState = StateController.getNextState(currentState, input.isDuplicated(currentChar));
        }
        return currentState;
    }

    private static char firstChar(Stream input) {
        return input.getFirstChar();
    }

    private static boolean isVowel(char c) { return VOWELS.indexOf(Character.toUpperCase(c)) >= 0; }
}

