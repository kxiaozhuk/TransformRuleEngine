package com.grouptech.trml;

import org.dmg.pmml.FieldName;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author mahone on 2017/3/23.
 */
@XmlTransient
abstract
public class RuleExpression {
    private FieldName field = null;
    private FieldName outputField = null;

    public FieldName getField() {
        return field;
    }

    public RuleExpression setField(FieldName field) {
        this.field = field;
        return this;
    }

    public FieldName getOutputField() {
        return outputField;
    }

    public RuleExpression setOutputField(FieldName outputField) {
        this.outputField = outputField;
        return this;
    }

    public RuleExpression() {
    }

}
