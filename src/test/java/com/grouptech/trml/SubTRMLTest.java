package com.grouptech.trml;

import com.grouptech.trml.example.TRMLExample;
import com.grouptech.trml.utility.JAXBUtils;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * @author mahone on 2017/4/5.
 */
public class SubTRMLTest {

    TRMLExample trmlExample = new TRMLExample();

    @Test
    public void toXML() throws JAXBException{
        Rename r2 = trmlExample.newRename();
        StringWriter result = new StringWriter();
        JAXBUtils.marshal(r2, new StreamResult(result));
        System.out.print(result.toString());
        System.out.print("Success.");
    }

}
