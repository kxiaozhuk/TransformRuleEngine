package com.grouptech.trml.utility;

import com.grouptech.trml.ObjectFactory;
import com.grouptech.trml.TRML;
import com.grouptech.trml.TransformationRule;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.jpmml.model.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author zhuwenhong on 2017/3/23.
 * Object<-->XML转换类
 */
public class JAXBUtils {

    private JAXBUtils(){
    }

    /**
     * @see ImportFilter
     * @see ExtensionFilter
     * @see ElementFilter
     */

    public static SAXSource createFilteredSource(InputSource source, XMLFilter... filters) throws SAXException {
        XMLReader reader = XMLReaderFactory.createXMLReader();

        for(XMLFilter filter : filters){
            filter.setParent(reader);

            reader = filter;
        }

        return new SAXSource(reader, source);
    }

    /**
     * <p>
     * Unmarshals a {@link TRML} class model object.
     * </p>
     *
     * @param source Input source containing a complete TRML schema version 4.3 document.
     *
     * @see ImportFilter
     */

    public static TRML unmarshalTRML(Source source) throws JAXBException {
        return (TRML)unmarshal(source);
    }

    /**
     * <p>
     * Unmarshals any class model object.
     * </p>
     *
     * @param source Input source containing a complete TRML schema version 4.3 document or any fragment of it.
     */

    public static Object unmarshal(Source source) throws JAXBException {
        Unmarshaller unmarshaller = createUnmarshaller();

        return unmarshaller.unmarshal(source);
    }

    /**
     * <p>
     * Marshals a {@link TRML} class model object.
     * </p>
     *
     * @see ExportFilter
     */

    public static void marshalTRML(TRML pmml, Result result) throws JAXBException {
        marshal(pmml, result);
    }


    public static String marshalTRML(TRML trml) throws IOException,JAXBException {
        StringWriter result = new StringWriter();
        JAXBUtils.marshalTRML(trml, new StreamResult(result));

        return result.toString();
    }
    /**
     * <p>
     * Marshals any class model object.
     * </p>
     */

    public static void marshal(Object object, Result result) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
            @Override
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                if ("http://www.dmg.org/PMML-4_3".equals(namespaceUri) && !requirePrefix) return "";
                return suggestion;
            }
        });
        marshaller.marshal(object, result);
    }


    public static JAXBContext getContext() throws JAXBException {

        if(context == null){
            context = JAXBContext.newInstance(
                    ObjectFactory.class,
                    TRML.class,
                    TransformationRule.class
            );
        }

        return context;
    }


    public static Marshaller createMarshaller() throws JAXBException {
        JAXBContext context = getContext();

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        return marshaller;
    }


    public static Unmarshaller createUnmarshaller() throws JAXBException {
        JAXBContext context = getContext();

        return context.createUnmarshaller();
    }

    private static JAXBContext context = null;

}
