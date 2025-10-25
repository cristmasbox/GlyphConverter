package com.blueapps.glpyhconverter;

import com.blueapps.glpyhconverter.toglyphx.MdCToGlyphX;
import com.blueapps.glpyhconverter.tomdc.GlyphXToMdC;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
        return convertToString(convertToGlyphXDocument(MdC));
    }

    public static Document convertToGlyphXDocument(String MdC){
        return MdCToGlyphX.convert(MdC);
    }

    private static Document convertToXmlDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    public static String convertToString(Document xmlDocument) {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();

            // Uncomment if you do not require XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            //A character stream that collects its output in a string buffer,
            //which can then be used to construct a string.
            StringWriter writer = new StringWriter();

            //transform document to string
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

            return writer.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}