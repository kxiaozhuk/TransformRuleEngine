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
        "outputField",
        "sqlText"
})
@XmlRootElement(name = "SqlExpression", namespace = "http://www.dmg.org/PMML-4_3")
public class SqlExpression extends RuleExpression {

    @XmlAttribute(name = "sqlText",required = true)
    private String sqlText;

    public SqlExpression() {
        super();
    }

    public SqlExpression(final FieldName field, final FieldName outputField) {
        super();
        setField(field);
        setOutputField(outputField);
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public SqlExpression setField(FieldName field) {
        super.setField(field);
        super.setOutputField(field);
        return this;
    }

    @XmlAttribute(name = "outputField",required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getOutputField() {
        return super.getOutputField();
    }

    @Override
    public SqlExpression setOutputField(FieldName outputField) {
        super.setOutputField(outputField);
        return this;
    }

    public String getSqlText() {
        return sqlText;
    }

    public SqlExpression setSqlText(String sqlText) {
        this.sqlText = sqlText;
        return this;
    }

}
