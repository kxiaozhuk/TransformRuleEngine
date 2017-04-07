package com.grouptech.trml;

import com.grouptech.trml.example.TRMLExample;
import com.grouptech.trml.utility.JAXBUtils;
import org.jpmml.model.ImportFilter;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import java.io.*;

import static org.junit.Assert.*;
/**
 * @author mahone on 2017/3/23.
 */
public class JAXBUtilsTest {
    TRMLExample trmlExample = new TRMLExample();

    @Test
    public void unmarshal() throws JAXBException, IOException {
        TRML trml = trmlExample.newTRML();
        String str = JAXBUtils.marshalTRML(trml);

        System.out.print(str);
        assertEquals(str,trmlExample.xml);
    }

    @Test
    public void marshalString() throws SAXException,JAXBException {
        String xml = trmlExample.xml;
        StringReader stringReader = new StringReader(xml);
        Source source = ImportFilter.apply(new InputSource(stringReader));
        TRML trml = JAXBUtils.unmarshalTRML(source);
        trml.toString();
    }

    @Test
    public void marshalFile() throws FileNotFoundException,SAXException,JAXBException {
        String file = "D:\\MyProject\\TransformRuleEngine\\src\\test\\test.trml";
        File inputFilePath = new File(file);
        InputStream in = new FileInputStream(inputFilePath);
        Source source = ImportFilter.apply(new InputSource(in));
        TRML trml = JAXBUtils.unmarshalTRML(source);
        trml.toString();

    }
}
