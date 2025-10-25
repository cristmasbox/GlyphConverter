package com.blueapps.glpyhconverter.toglyphx.items;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SimpleItem extends Item{


    @Override
    public Element getElement(Document doc, String MdC) {
        Element element = doc.createElement("sign");

        element.setAttribute("id", MdC);

        return element;
    }

}
