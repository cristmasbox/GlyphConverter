package com.blueapps.glpyhconverter;

import com.blueapps.glpyhconverter.tomdc.GlyphXToMdC;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GlyphConverter {

    // XML Data
    public static final String XML_ROOT_TAG = "ancientText";
    public static final String XML_SIGN_TAG = "sign";
    public static final String XML_V_TAG = "v";
    public static final String XML_H_TAG = "h";
    public static final String XML_ID_ATTRIBUTE = "id";

    public static String convertToMdC(String glyphX){
        try {
            return convertToMdC(convertToXmlDocument(glyphX));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToMdC(Document glyphX){
        return GlyphXToMdC.convert(glyphX);
    }

    public static String convertToGlyphX(String MdC){
        return "";
    }

    public static Document convertToGlyphXDocument(String MdC){
        try {
            return convertToXmlDocument(convertToGlyphX(MdC));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Document convertToXmlDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

}