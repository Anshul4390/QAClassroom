/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;


//import javax.xml.parsers.SAXParser;

/**
 *
 * @author prashantshukla
 */
public class XmlParser {
    
    String xmlDirPath = "./src/test/resources/AA-Artifacts/questions-xmls";
    String xmlPath;
    
    public XmlParser(String xmlName){
        this.xmlPath = this.xmlDirPath + xmlName;
    }
    
    
    
    
}
