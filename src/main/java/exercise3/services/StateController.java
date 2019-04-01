package exercise3.services;

import static exercise3.services.State.*;

class StateController {

    static int getNextState(int currentStatus, Boolean input) {
        if (currentStatus == INITIAL) return firstStateController(input);
        if (currentStatus == VOWEL_FOUND) return secondStateController(input);
        if (currentStatus == VOWEL_CONSONANT_FOUND) return thridStateController(input);
        if (currentStatus == VOWEL_CONSONANT_VOWEL_FOUND) return forthStateController(input);
        if (currentStatus == FINAL) return FINAL;
        else return INITIAL;
    }

    private static int firstStateController(Boolean isVowel) {
        if (isVowel) return VOWEL_FOUND;
        return INITIAL;
    }

    private static int secondStateController(Boolean isVowel) {
        if (isVowel) return INITIAL;
        return VOWEL_CONSONANT_FOUND;
    }

    private static int thridStateController(Boolean isVowel) {
        if (isVowel) return VOWEL_CONSONANT_VOWEL_FOUND;
        return INITIAL;
    }

    private static int forthStateController(Boolean isDuplicated) {
        if (isDuplicated) return VOWEL_FOUND;
        return FINAL;
    }

}

