package com.blueapps.glpyhconverter.toglyphx;

import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_BETWEEN_BRACKET_CHARACTER;
import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_BRACKET_POSITION;
import static com.blueapps.glpyhconverter.toglyphx.exceptions.MdCParseException.ILLEGAL_BRACKET_PROPORTION;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

    public static String checkMdC(String MdC) throws MdCParseException{
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


            // Convert "))()()-(((()))(((" to "))-(((("
            // Get ArrayLists with indexes (ids) from all instances of '(' and ')'
            ArrayList<Integer> closedBracketIds = new ArrayList<>();
            ArrayList<Integer> openedBracketIds = new ArrayList<>();
            ArrayList<Integer> trashIds = new ArrayList<>();
            CharacterIterator it2 = new StringCharacterIterator(sep);
            while (it2.current() != CharacterIterator.DONE){
                char current = it2.current();

                if (current == ')'){
                    closedBracketIds.add(it2.getIndex());
                } else if (current == '(') {
                    openedBracketIds.add(it2.getIndex());
                }

                it2.next();
            }
            // Delete indexes of brackets in this constellation: "( ... )'
            for (Integer id: openedBracketIds){
                if (closedBracketIds.isEmpty()){
                    break;
                }
                int latestCB = Collections.max(closedBracketIds);
                if (latestCB > id){
                    // Put on trashList to delete from sep string
                    trashIds.add(latestCB);
                    trashIds.add(id);
                    // Remove from ArrayLists
                    closedBracketIds.remove(latestCB);
                    openedBracketIds.remove(id);
                }
            }
            // Sort and reverse trashList
            Collections.sort(trashIds);
            Collections.reverse(trashIds);

            // Delete indexes stored in trashList
            StringBuilder stringBuilder = new StringBuilder(sep);
            for (Integer trash: trashIds){
                stringBuilder.deleteCharAt(trash);
            }
            sep = stringBuilder.toString();

            // Split String into 3 substrings: "))-((((" --> ["))", "-", "(((("]
            int lastCB = sep.lastIndexOf(')');
            int firstOB = sep.indexOf('(');
            // Handle case that there are no brackets in the string
            if (lastCB == -1){
                lastCB = 0;
            } else {
                lastCB++;
            }
            if (firstOB == -1){
                firstOB = sep.length();
            }
            String firstString = sep.substring(0, lastCB);
            String secondString = sep.substring(lastCB, firstOB);
            String thirdString = sep.substring(firstOB);

            // Check firstString
            firstString = StringUtils.deleteWhitespace(firstString);
            firstString = StringUtils.remove(firstString, '-');
            if (StringUtils.containsAny(firstString, ':')){
                throw new MdCParseException(String.format(ILLEGAL_BETWEEN_BRACKET_CHARACTER, ':', firstString + secondString + thirdString));
            } else if (StringUtils.containsAny(firstString, '*')){
                throw new MdCParseException(String.format(ILLEGAL_BETWEEN_BRACKET_CHARACTER, '*', firstString + secondString + thirdString));
            }

            // Check thirdString
            thirdString = StringUtils.deleteWhitespace(thirdString);
            thirdString = StringUtils.remove(thirdString, '-');
            if (StringUtils.containsAny(thirdString, ':')){
                throw new MdCParseException(String.format(ILLEGAL_BETWEEN_BRACKET_CHARACTER, ':', firstString + secondString + thirdString));
            } else if (StringUtils.containsAny(thirdString, '*')){
                throw new MdCParseException(String.format(ILLEGAL_BETWEEN_BRACKET_CHARACTER, '*', firstString + secondString + thirdString));
            }

            // Check secondString
            // Convert "" to "-"
            if (secondString.isEmpty()){
                secondString = "-";
            }
            // Crop "------" and "-- - - -" to "-", "  *   " to "*", "  :  " to ":" and "       " to " "
            secondString = StringUtils.deleteWhitespace(secondString);
            if (secondString.isEmpty()){
                secondString = " ";
            }
            if (StringUtils.containsAny(secondString, '-')){
                secondString = "-";
            }

            // Combine strings back
            stringBuilder = new StringBuilder(firstString);
            stringBuilder.append(secondString);
            stringBuilder.append(thirdString);
            separators.set(counter, stringBuilder.toString());

            counter++;

        }

        // Check separators at start or end of MdC Code
        if (StringUtils.containsAny(separators.get(0), ':')){
            throw new MdCParseException(String.format(ILLEGAL_START_CHARACTER, ':', separators.get(0)));
        } else if (StringUtils.containsAny(separators.get(0), '*')){
            throw new MdCParseException(String.format(ILLEGAL_START_CHARACTER, '*', separators.get(0)));
        } else if (StringUtils.containsAny(separators.get(0), ')')){
            throw new MdCParseException(String.format(ILLEGAL_BRACKET_POSITION, ')', "beginning"));
        }
        separators.set(0, StringUtils.remove(separators.get(0), '-'));

        String endString = separators.get(separators.size() - 1);
        if (StringUtils.containsAny(endString, ':')){
            throw new MdCParseException(String.format(ILLEGAL_END_CHARACTER, ':', endString));
        } else if (StringUtils.containsAny(endString, '*')){
            throw new MdCParseException(String.format(ILLEGAL_END_CHARACTER, '*', endString));
        } else if (StringUtils.containsAny(endString, '(')){
            throw new MdCParseException(String.format(ILLEGAL_BRACKET_POSITION, '(', "end"));
        }
        separators.set(separators.size() - 1, StringUtils.remove(endString, '-'));

        log.log(Level.INFO, "Separators prepared: " + separators);

        int counter2 = 0;
        StringBuilder finalString = new StringBuilder();
        for (String id: ids){
            finalString.append(separators.get(counter2));
            finalString.append(id);
            counter2++;
        }

        finalString.append(separators.get(separators.size() - 1));

        // Check if every item has same count of '(' and ')'
        ArrayList<String> items = new ArrayList<>(List.of(finalString.toString().split("[- ]")));
        for (String item: items){
            if (StringUtils.countMatches(item, '(') != StringUtils.countMatches(item, ')')){
                throw new MdCParseException(String.format(ILLEGAL_BRACKET_PROPORTION,
                        StringUtils.countMatches(item, '('),
                        StringUtils.countMatches(item, ')'), item));
            }
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

        // Remove root brackets "((a:b)*c)" --> "(a:b)*c"
        MdC = removeRootBrackets(MdC);

        // Replace brackets with '#'
        ArrayList<String> result = replaceBrackets(MdC);
        String newMdC = result.get(0);

        Item item = null;
        Element element;

        if (StringUtils.containsNone(newMdC, ':') && StringUtils.containsNone(newMdC, '*')){
            item = new SimpleItem();
        } else if (StringUtils.containsAny(newMdC, ':') && StringUtils.containsAny(newMdC, '*')){
            item = new VerticalGroup();
        } else if (StringUtils.containsAny(newMdC, ':')){
            item = new VerticalGroup();
        } else if (StringUtils.containsAny(newMdC, '*')){
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
                c == '*' ||
                c == '(' ||
                c == ')';
    }

    public static ArrayList<String> replaceBrackets(String MdC){
        // Replace brackets with '#'
        StringBuilder newMdC = new StringBuilder();
        ArrayList<String> brackets = new ArrayList<>();
        StringBuilder currentBracket = new StringBuilder();
        CharacterIterator it = new StringCharacterIterator(MdC);
        int level = 0;
        // Iterate characters
        while (it.current() != CharacterIterator.DONE) {
            char current = it.current();

            if (current == '('){
                level++;
                if (level == 1){
                    newMdC.append('#');
                }
                currentBracket.append(current);
            } else if (current == ')'){
                level--;
                currentBracket.append(current);
                if (level == 0){
                    brackets.add(currentBracket.toString());
                    currentBracket = new StringBuilder();
                }
            } else {
                if (level == 0) {
                    newMdC.append(current);
                } else {
                    currentBracket.append(current);
                }
            }

            // Moving onto next element in the object using next() method
            it.next();
        }

        ArrayList<String> result = new ArrayList<>();

        result.add(newMdC.toString());

        result.addAll(brackets);

        return result;
    }

    public static String removeRootBrackets(String MdC){
        if (MdC.charAt(0) == '(' && MdC.charAt(MdC.length() - 1) == ')'){
            StringBuilder stringBuilder = new StringBuilder(MdC);
            stringBuilder.deleteCharAt(MdC.length() - 1);
            stringBuilder.deleteCharAt(0);
            MdC = stringBuilder.toString();
        }
        return MdC;
    }
    
}
