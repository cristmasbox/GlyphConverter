package com.blueapps.glpyhconverter;

public class GlyphConverter {

    public static String convertToMdC(String glyphX){
        try {
            return convertToMdC(convertToXmlDocument(glyphX));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToMdC(Document glyphX){
        return "";
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