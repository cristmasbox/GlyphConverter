package com.blueapps.glpyhconverter.tomdc.items;

import com.blueapps.glpyhconverter.tomdc.exceptions.GlyphXParserException;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class SimpleItem extends Item {

    private String id;

    public SimpleItem(String id){
        this.id = id;

        // Check for illegal characters in the id string
        CharacterIterator it = new StringCharacterIterator(id);
        while (it.current() != CharacterIterator.DONE) {
            if (!Character.isDigit(it.current()) && !Character.isAlphabetic(it.current())) {
                throw new GlyphXParserException(String.format(GlyphXParserException.INVALID_ID_CHARACTER, it.current(), id));
            }
            it.next();
        }
    }

    @Override
    public String getMdC() {
        return id;
    }
}
