package com.blueapps.glpyhconverter.tomdc.items;

import org.w3c.dom.Element;

public class HorizontalGroup extends ItemGroup{

    // Constants
    public static final String HORIZONTAL_SIGN_SEPARATOR = "*";

    public HorizontalGroup(Element element) {
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
                returnString.append(HORIZONTAL_SIGN_SEPARATOR);
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
