package com.blueapps.glpyhconverter.toglyphx;

import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_CHARACTER;
import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_CHARACTER_COMBINATION;
import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_CHARACTER_COUNT;
import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_END_CHARACTER;
import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_START_CHARACTER;

import com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException;
import com.blueapps.glpyhconverter.toglyphx.items.HorizontalGroup;
import com.blueapps.glpyhconverter.toglyphx.items.Item;
import com.blueapps.glpyhconverter.toglyphx.items.SimpleItem;
import com.blueapps.glpyhconverter.toglyphx.items.VerticalGroup;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MdCToGlyphX {

    private static final String TAG = "MdCToGlyphX";
    private static final Logger log = Logger.getLogger(TAG);

    // Constants
    private static final int MODE_START = -1;
    private static final int MODE_ID = 0;
    private static final int MODE_SEPARATOR = 1;

    public static Document convert(String MdC){
        return getGlyphX(checkMdC(MdC));
    }

    private static String checkMdC(String MdC) throws MdCParseException{
        CharacterIterator it = new StringCharacterIterator(MdC);

        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> separators = new ArrayList<>();

        StringBuilder currentString = new StringBuilder();
        int mode = MODE_START;

        // Iterate characters
        while (it.current() != CharacterIterator.DONE) {
            char current = it.current();

            if (isIdChar(current)){
                if (mode == MODE_SEPARATOR){
                    separators.add(currentString.toString());
                    currentString = new StringBuilder();
                    mode = MODE_ID;
                } else if (mode == MODE_START){
                    mode = MODE_ID;
                    separators.add("");
                }
                currentString.append(current);
            } else if (isSeperatorChar(current)){
                if (mode == MODE_ID){
                    ids.add(currentString.toString());
                    currentString = new StringBuilder();
                    mode = MODE_SEPARATOR;
                } else if (mode == MODE_START){
                    mode = MODE_SEPARATOR;
                }
                currentString.append(current);
            } else {
                throw new MdCParseException(String.format(ILLEGAL_CHARACTER, current));
            }

            // Moving onto next element in the object using next() method
            it.next();
        }
        if (mode == MODE_ID){
            ids.add(currentString.toString());
            separators.add("");
        } else if (mode == MODE_SEPARATOR){
            separators.add(currentString.toString());
        }

        log.log(Level.INFO, "Separators raw: " + separators);

        // Check separators
        int counter = 0;
        for (String sep: separators){

            // Ensure that only one * or : is used
            // Ensure that there is no combination like *- or :-
            if (StringUtils.countMatches(sep, ':') > 1){
                throw new MdCParseException(String.format(ILLEGAL_CHARACTER_COUNT, ':', StringUtils.countMatches(sep, ':'), sep));
            } else if (StringUtils.countMatches(sep, '*') > 1){
                throw new MdCParseException(String.format(ILLEGAL_CHARACTER_COUNT, '*', StringUtils.countMatches(sep, '*'), sep));
            } else if (StringUtils.containsAny(sep, '*') && StringUtils.containsAny(sep, ':')) {
                throw new MdCParseException(String.format(ILLEGAL_CHARACTER_COMBINATION, '*', ':', sep));
            } else if (StringUtils.containsAny(sep, '*') && StringUtils.containsAny(sep, '-')){
                throw new MdCParseException(String.format(ILLEGAL_CHARACTER_COMBINATION, '*', '-', sep));
            } else if (StringUtils.containsAny(sep, ':') && StringUtils.containsAny(sep, '-')) {
                throw new MdCParseException(String.format(ILLEGAL_CHARACTER_COMBINATION, ':', '-', sep));
            }

            // Crop "------" and "-- - - -" to "-", "  *   " to "*", "  :  " to ":" and "       " to " "

            if (StringUtils.containsWhitespace(sep)){
                sep = StringUtils.deleteWhitespace(sep);
                if (sep.isEmpty()){
                    sep = " ";
                }
            }
            if (StringUtils.countMatches(sep, '-') > 1){
                sep = "-";
            }

            separators.set(counter, sep);

            counter++;

        }

        if (StringUtils.containsAny(separators.get(0), ':')){
            throw new MdCParseException(String.format(ILLEGAL_START_CHARACTER, ':', separators.get(0)));
        } else if (StringUtils.containsAny(separators.get(0), '*')){
            throw new MdCParseException(String.format(ILLEGAL_START_CHARACTER, '*', separators.get(0)));
        }
        String endString = separators.get(separators.size() - 1);
        if (StringUtils.containsAny(endString, ':')){
            throw new MdCParseException(String.format(ILLEGAL_END_CHARACTER, ':', endString));
        } else if (StringUtils.containsAny(endString, '*')){
            throw new MdCParseException(String.format(ILLEGAL_END_CHARACTER, '*', endString));
        }

        log.log(Level.INFO, "Separators prepared: " + separators);

        int counter2 = 1;
        StringBuilder finalString = new StringBuilder();
        for (String id: ids){
            finalString.append(id);
            if (counter2 != ids.size()) {
                finalString.append(separators.get(counter2));
            }
            counter2++;
        }

        return finalString.toString();
    }

    private static Document getGlyphX(String MdC){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("ancientText");
            doc.appendChild(rootElement);

            ArrayList<String> items = new ArrayList<>(List.of(MdC.split("[- ]")));
            for (String item: items){
                rootElement.appendChild(getElement(doc, item));
            }

            return doc;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Element getElement(Document doc, String MdC){

        Item item = null;
        Element element = null;

        if (StringUtils.containsNone(MdC, ':') && StringUtils.containsNone(MdC, '*')){
            item = new SimpleItem();
        } else if (StringUtils.containsAny(MdC, ':') && StringUtils.containsAny(MdC, '*')){
            item = new VerticalGroup();
        } else if (StringUtils.containsAny(MdC, ':')){
            item = new VerticalGroup();
        } else if (StringUtils.containsAny(MdC, '*')){
            item = new HorizontalGroup();
        }

        if (item == null){
            element = doc.createElement("unknown");
        } else {
            element = item.getElement(doc, MdC);
        }


        return element;
    }


    public static boolean isIdChar(char c){
        return Character.isAlphabetic(c) || Character.isDigit(c);
    }

    public static boolean isSeperatorChar(char c){
        return c == ' ' ||
                c == '-' ||
                c == ':' ||
                c == '*';
    }
    
}
