package com.blueapps.glpyhconverter.tomdc.exceptions;

public class GlyphXParserException extends RuntimeException {
    // Exceptions
    public static final String WRONG_ROOT_TAG = "Wrong Root Tag Exception: Root Tag should be \"<%s>\" in stead of \"<%s>\".";
    public static final String INVALID_ID_CHARACTER = "Invalid Id Character Exception: Id should only contain digits and letters, but found '%c' in id \"%s\".";

    public GlyphXParserException(String message) {
        super(message);
    }
}
