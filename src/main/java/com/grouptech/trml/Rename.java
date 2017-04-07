package com.grouptech.trml;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.adapters.FieldNameAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author mahone on 2017/3/23.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "", propOrder = {
        "field",
        "outputField"
})
@XmlRootElement(name = "Rename", namespace = "http://www.dmg.org/PMML-4_3")
public class Rename extends RuleExpression {

    public Rename() {
        super();
    }

    public Rename(final FieldName field, final FieldName outputField) {
        setField(field);
        setOutputField(outputField);
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public Rename setField(FieldName field) {
        super.setField(field);
        return this;
    }

    @XmlAttribute(name = "outputField",required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getOutputField() {
        return super.getOutputField();
    }

    @Override
    public Rename setOutputField(FieldName outputField) {
        super.setOutputField(outputField);
        return this;
    }

}
