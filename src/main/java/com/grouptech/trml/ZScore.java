package com.grouptech.trml;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.adapters.FieldNameAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author mahone on 2017/3/23.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = {
        "field",
        "meanValue",
        "stddevValue"
})
@XmlRootElement(name = "ZScore", namespace = "http://www.dmg.org/PMML-4_3")
public class ZScore extends RuleExpression {

    @XmlAttribute(name = "meanValue",required = true)
    private Double meanValue;
    @XmlAttribute(name = "stddevValue")
    private Double stddevValue;

    public ZScore() {
        super();
    }

    public ZScore(final Double meanValue, final Double stddevValue) {
        super();
        this.meanValue = meanValue;
        this.stddevValue = stddevValue;
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public ZScore setField(FieldName field) {
        super.setOutputField(field);
        super.setField(field);
        return this;
    }

    public Double getMeanValue() {
        return meanValue;
    }

    public ZScore setMeanValue(Double meanValue) {
        this.meanValue = meanValue;
        return this;
    }

    public Double getStddevValue() {
        return stddevValue;
    }

    public ZScore setStddevValue(Double stddevValue) {
        this.stddevValue = stddevValue;
        return this;
    }

}
