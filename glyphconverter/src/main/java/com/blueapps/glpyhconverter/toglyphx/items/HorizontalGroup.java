package com.blueapps.glpyhconverter.toglyphx.items;

import static com.blueapps.glpyhconverter.toglyphx.MdCToGlyphX.removeRootBrackets;
import static com.blueapps.glpyhconverter.toglyphx.MdCToGlyphX.replaceBrackets;

import com.blueapps.glpyhconverter.toglyphx.MdCToGlyphX;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HorizontalGroup extends ItemGroup{
    @Override
    public Element getElement(Document doc, String MdC) {
        Element element = doc.createElement("h");

        // Remove root brackets "((a:b)*c)" --> "(a:b)*c"
        MdC = removeRootBrackets(MdC);

        // Replace brackets with '#'
        ArrayList<String> result = replaceBrackets(MdC);
        String newMdC = result.get(0);

        int counter = 1;
        ArrayList<String> items = new ArrayList<>(List.of(newMdC.split("\\*")));
        for (String item: items){

            String newItem = item;
            // Replace '#' with original brackets
            if (Objects.equals(item, "#")){
                newItem = result.get(counter);
                counter++;
            }

            element.appendChild(MdCToGlyphX.getElement(doc, newItem));
        }

        return element;
    }
}
