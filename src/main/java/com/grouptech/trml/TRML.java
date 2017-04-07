package com.grouptech.trml;

import org.dmg.pmml.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mahone on 2017/3/23.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "extensions",
        "header",
        "dataDictionary",
        "transformationRule"
})
@XmlRootElement(name = "TRML", namespace = "http://www.dmg.org/PMML-4_3")
public class TRML implements HasExtensions{

    @XmlAttribute(name = "version", required = true)
    private String version;
    @XmlAttribute(name = "x-baseVersion")
    @org.jpmml.schema.Extension
    private String baseVersion;
    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_3")
    private List<Extension> extensions;
    @XmlElement(name = "Header", namespace = "http://www.dmg.org/PMML-4_3", required = true)
    private Header header;
    @XmlElement(name = "DataDictionary", namespace = "http://www.dmg.org/PMML-4_3", required = true)
    private DataDictionary dataDictionary;
    @XmlElement(name = "TransformationRule", namespace = "http://www.dmg.org/PMML-4_3")
    private TransformationRule transformationRule;

    public TRML() {
        super();
    }

    public TRML(final String version, final Header header, final DataDictionary dataDictionary) {
        super();
        this.version = version;
        this.header = header;
        this.dataDictionary = dataDictionary;
    }

    public String getVersion() {
        return version;
    }

    public TRML setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getBaseVersion() {
        return baseVersion;
    }

    public TRML setBaseVersion(String baseVersion) {
        this.baseVersion = baseVersion;
        return this;
    }

    public List<Extension> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<Extension>();
        }
        return this.extensions;
    }

    public Header getHeader() {
        return header;
    }

    public TRML setHeader(Header header) {
        this.header = header;
        return this;
    }

    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }

    public TRML setDataDictionary(DataDictionary dataDictionary) {
        this.dataDictionary = dataDictionary;
        return this;
    }

    public TransformationRule getTransformationRule() {
        return transformationRule;
    }

    public TRML setTransformationRule(TransformationRule transformationRule) {
        this.transformationRule = transformationRule;
        return this;
    }

    public boolean hasExtensions() {
        return ((this.extensions!= null)&&(this.extensions.size()> 0));
    }

    public TRML addExtensions(org.dmg.pmml.Extension... extensions) {
        getExtensions().addAll(Arrays.asList(extensions));
        return this;
    }
}
