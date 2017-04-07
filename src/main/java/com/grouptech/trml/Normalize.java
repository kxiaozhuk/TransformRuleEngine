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
        "minValue",
        "maxValue"
})
@XmlRootElement(name = "Normalize", namespace = "http://www.dmg.org/PMML-4_3")
public class Normalize extends RuleExpression {

    @XmlAttribute(name = "minValue",required = true)
    private Double minValue;
    @XmlAttribute(name = "maxValue")
    private Double maxValue;

    public Normalize() {
        super();
    }

    public Normalize(final Double minValue, final Double maxValue) {
        super();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public Normalize setField(FieldName field) {
        super.setOutputField(field);
        super.setField(field);
        return this;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Normalize setMinValue(Double minValue) {
        this.minValue = minValue;
        return this;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Normalize setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

}
