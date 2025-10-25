package com.blueapps.glpyhconverter.toglyphx.items;

import com.blueapps.glpyhconverter.toglyphx.MdCToGlyphX;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class VerticalGroup extends ItemGroup{
    @Override
    public Element getElement(Document doc, String MdC) {
        Element element = doc.createElement("v");

        ArrayList<String> items = new ArrayList<>(List.of(MdC.split(":")));
        for (String item: items){
            element.appendChild(MdCToGlyphX.getElement(doc, item));
        }
        
        return element;
    }
}
