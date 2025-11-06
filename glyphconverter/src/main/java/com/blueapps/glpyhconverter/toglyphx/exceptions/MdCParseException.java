package com.blueapps.glpyhconverter.toglyphx.exceptions;

public class MdCParseException extends RuntimeException {
    // Exceptions
    public static final String ILLEGAL_CHARACTER = "Illegal Character Exception: The char '%c' is not allowed in MdC Code!";
    public static final String ILLEGAL_CHARACTER_COUNT = "Illegal Character Count Exception: The char '%c' can be used only once between two ids, but was used %d times: \"%s\"";
    public static final String ILLEGAL_CHARACTER_COMBINATION = "Illegal Character Combination Exception: The chars '%c' and '%c' can't be used together between two ids: \"%s\"";
    public static final String ILLEGAL_START_CHARACTER = "Illegal Start Character Exception: The char '%c' can not be used before the first id: \"%s\"";
    public static final String ILLEGAL_END_CHARACTER = "Illegal End Character Exception: The char '%c' can not be used after the last id: \"%s\"";
    public static final String ILLEGAL_BRACKET_PROPORTION = "Illegal Bracket Proportion Exception: The char '(' (counted %d times) has to be used in the same number as the char ')' (counted %d times): \"%s\"";
    public static final String ILLEGAL_SINGLE_BRACKET = "Illegal Single Bracket Exception: The char '(' or ')' cannot be used without ':', '*', '-' or ' ': \"%s\"";

    public static final String NOTE_LEARN_MORE = "\nTo learn more about MdC Codes visit: https://en.wikipedia.org/wiki/Manuel_de_Codage";

    public MdCParseException(String message) {
        super(message + NOTE_LEARN_MORE);
    }
}
