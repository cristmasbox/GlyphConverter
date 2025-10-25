package com.blueapps.glpyhconverter.tomdc.items;

import static com.blueapps.glpyhconverter.tomdc.GlyphXToMdC.SIGN_SEPARATOR;
import static com.blueapps.glpyhconverter.tomdc.GlyphXToMdC.getItemFromElement;

import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemGroup extends Item{

    private static final String TAG = "ItemGroup";
    private static final Logger log = Logger.getLogger(TAG);

    protected ArrayList<Item> subItems = new ArrayList<>();
    protected Element element;

    // Constants
    public static final String GROUP_OPEN = "(";
    public static final String GROUP_CLOSED = ")";

    public ItemGroup(Element element){
        this.element = element;

        // Fill subItems
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); i++){
            Node node = children.item(i);
            if (node instanceof Element) {

                Element child = (Element) node;
                Item item = getItemFromElement(child);
                if (item != null) {
                    subItems.add(item);
                }

            } else if (node instanceof Comment) {
                log.log(Level.INFO, "Node is a Comment");
            } else if (node instanceof Text) {
                log.log(Level.INFO, "Node is a Text");
            } else {
                log.log(Level.INFO, "Node Type unknown");
            }
        }

    }

    @Override
    public String getMdC() {
        StringBuilder returnString = new StringBuilder();

        int counter = 0;
        for (Item item: subItems){
            if (counter != 0){
                returnString.append(SIGN_SEPARATOR);
            }
            returnString.append(item.getMdC());
            counter++;
        }

        return returnString.toString();
    }

}
