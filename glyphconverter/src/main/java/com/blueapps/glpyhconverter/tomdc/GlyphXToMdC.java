package com.blueapps.glpyhconverter.tomdc;

import static com.blueapps.glpyhconverter.GlyphConverter.XML_H_TAG;
import static com.blueapps.glpyhconverter.GlyphConverter.XML_ID_ATTRIBUTE;
import static com.blueapps.glpyhconverter.GlyphConverter.XML_SIGN_TAG;
import static com.blueapps.glpyhconverter.GlyphConverter.XML_V_TAG;
import static com.blueapps.glpyhconverter.GlyphConverter.XML_ROOT_TAG;

import com.blueapps.glpyhconverter.tomdc.items.HorizontalGroup;
import com.blueapps.glpyhconverter.tomdc.items.Item;
import com.blueapps.glpyhconverter.tomdc.items.SimpleItem;
import com.blueapps.glpyhconverter.tomdc.items.VerticalGroup;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlyphXToMdC {

    private static final String TAG = "GlyphXToMdC";
    private static final Logger log = Logger.getLogger(TAG);

    // Constants
    public static final String SIGN_SEPARATOR = "-";

    public static String convert(Document GlyphX){

        ArrayList<Item> structure = getStructure(GlyphX);

        return getMdCFromStructure(structure);
    }

    private static String getMdCFromStructure(ArrayList<Item> structure){
        StringBuilder returnString = new StringBuilder();

        int counter = 0;
        for (Item item: structure){
            if (counter != 0){
                returnString.append(SIGN_SEPARATOR);
            }
            returnString.append(item.getMdC());
            counter++;
        }

        return returnString.toString();
    }

    private static ArrayList<Item> getStructure(Document XMLContent){
        ArrayList<Item> returnStructure = new ArrayList<>();

        if (XMLContent != null) {
            // Loop through all tags
            Element rootElement = XMLContent.getDocumentElement();
            if (rootElement != null) {
                if (Objects.equals(rootElement.getTagName(), XML_ROOT_TAG)) {
                    NodeList nodeList = rootElement.getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (node instanceof Element) {
                            Element element = (Element) node;
                            Item item = getItemFromElement(element);
                            if (item != null) {
                                returnStructure.add(item);
                            }
                        } else if (node instanceof Comment) {
                            log.log(Level.INFO, "Node is a Comment");
                        } else if (node instanceof Text) {
                            log.log(Level.INFO, "Node is a Text");
                        } else {
                            log.log(Level.INFO, "Node Type unknown");
                        }
                    }
                } else {
                    log.log(Level.WARNING, "Wrong Root Element. Root Element should be '<\" + XML_ROOT_TAG + \">' in stead of '<\" + rootElement.getTagName() + \">'.");
                }
            }
        }

        return returnStructure;
    }

    public static Item getItemFromElement(Element element){
        if (Objects.equals(element.getTagName(), XML_SIGN_TAG)) {

            String id = element.getAttribute(XML_ID_ATTRIBUTE);
            return new SimpleItem(id);

        } else if (Objects.equals(element.getTagName(), XML_H_TAG)) {

            return new HorizontalGroup(element);

        } else if (Objects.equals(element.getTagName(), XML_V_TAG)) {

            return new VerticalGroup(element);

        }

        return null;
    }

}
