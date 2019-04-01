package exercise3.services;

import exercise3.implementations.StreamImpl;
import exercise3.interfaces.Stream;

import static exercise3.services.State.*;

public class StreamReader {

    private static String VOWELS = "AÀÁÂÃÄÅĀĂĄǺȀȂẠẢẤẦẨẪẬẮẰẲẴẶḀÆǼEȄȆḔḖḘḚḜẸẺẼẾỀỂỄỆĒĔĖĘĚÈÉÊËIȈȊḬḮỈỊĨĪĬĮİÌÍÎÏĲOŒØǾȌȎṌṎṐṒỌỎỐỒỔỖỘỚỜỞỠỢŌÒÓŎŐÔÕÖUŨŪŬŮŰŲÙÚÛÜȔȖṲṴṶṸṺỤỦỨỪỬỮỰYẙỲỴỶỸŶŸÝ";

    public static void printSpecialCharacter(String input) {
        Stream stream = new StreamImpl(input);
        Character specialCharacter = getSpecialCharacter(stream);
        if (specialCharacter == null) {
            System.out.println("The input string '" + input + "' has no special vowel!");
        }
        else {
            System.out.println("The special vowel for input String " + input + " is '" + specialCharacter + "'");
        }
    }

    public static Character getSpecialCharacter(Stream input) {
        if (input == null) return null;

        char firstChar = firstChar(input);
        int currentState = INITIAL;
        char currentChar = firstChar;
        while ( input.hasNext()
        ) {
            currentChar = input.getNext();
            currentState = StateController.getNextStatus(currentState, isVowel(currentChar));
            if (currentState == VOWEL_CONSONANT_VOWEL_FOUND) {
                currentState = StateController.getNextStatus(currentState, input.isDuplicated(currentChar));
            }
            if (currentState == FINAL) return currentChar;
        }
        return null;
    }

    public static char firstChar(Stream input) {
        return input.getFirstChar();
    }

    private static boolean isVowel(char c)
    {
        return VOWELS.indexOf(Character.toUpperCase(c)) >= 0;
    }

}

class StateController {

    public static int getNextStatus(int currentStatus, Boolean input) {
        if (currentStatus == INITIAL) return firstStateController(input);
        if (currentStatus == VOWEL_FOUND) return secondStateController(input);
        if (currentStatus == VOWEL_CONSONANT_FOUND) return thridStateController(input);
        if (currentStatus == VOWEL_CONSONANT_VOWEL_FOUND) return forthStateController(input);
        return FINAL;
    }

    private static int firstStateController(Boolean input) {
        if (input) return VOWEL_FOUND;
        return INITIAL;
    }

    private static int secondStateController(Boolean input) {
        if (input) return INITIAL;
        return VOWEL_CONSONANT_FOUND;
    }

    private static int thridStateController(Boolean input) {
        if (input) return VOWEL_CONSONANT_VOWEL_FOUND;
        return INITIAL;
    }

    private static int forthStateController(Boolean input) {
        if (input) return VOWEL_FOUND;
        return FINAL;
    }

}
