package com.example.basak.monkeyme;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by basak on 2015-06-26.
 */
public class SaxParser {
}

class SaxHandler extends DefaultHandler{
    public void startDocument(){}
    public void enddocument(){}

    public void startElement(String uri, String localName, String qName, Attributes attrs){

    }

    public void endElement(String uri, String localName, String qName){}
}