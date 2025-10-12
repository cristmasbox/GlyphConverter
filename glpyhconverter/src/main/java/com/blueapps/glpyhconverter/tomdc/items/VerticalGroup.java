package com.blueapps.glpyhconverter.tomdc.items;

import org.w3c.dom.Element;

public class VerticalGroup extends ItemGroup {

    // Constants
    public static final String VERTICAL_SIGN_SEPARATOR = ":";

    public VerticalGroup(Element element) {
        super(element);
    }

    @Override
    public String getMdC() {
        StringBuilder returnString = new StringBuilder();

        if (subItems.size() > 1){
            returnString.append(GROUP_OPEN);
        }

        int counter = 0;
        for (Item item: subItems){
            if (counter != 0){
                returnString.append(VERTICAL_SIGN_SEPARATOR);
            }
            returnString.append(item.getMdC());
            counter++;
        }

        if (subItems.size() > 1){
            returnString.append(GROUP_CLOSED);
        }

        return returnString.toString();
    }

}
