package com.grouptech.trml;

import org.dmg.pmml.*;
import org.dmg.pmml.adapters.FieldNameAdapter;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mahone on 2017/3/23.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "extensions",
        "ruleExpression"
})
@XmlRootElement(name = "DerivedRule", namespace = "http://www.dmg.org/PMML-4_3")
public class DerivedRule implements HasExtensions {

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    private FieldName name;
    @XmlAttribute(name = "displayName")
    private String displayName;
    @XmlAttribute(name = "optype", required = true)
    private OpType opType;
    @XmlAttribute(name = "dataType", required = true)
    private DataType dataType;
    @XmlAttribute(name = "seqNo")
    private int seqNo;
    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_3")
    private List<Extension> extensions;
    @XmlElements({
            @XmlElement(name = "MissingMap", namespace = "http://www.dmg.org/PMML-4_3", type = MissingMap.class),
            @XmlElement(name = "ZScore", namespace = "http://www.dmg.org/PMML-4_3", type = ZScore.class),
            @XmlElement(name = "Normalize", namespace = "http://www.dmg.org/PMML-4_3", type = Normalize.class),
            @XmlElement(name = "Disperse", namespace = "http://www.dmg.org/PMML-4_3", type = Disperse.class),
            @XmlElement(name = "EqualValues", namespace = "http://www.dmg.org/PMML-4_3", type = EqualValues.class),
            @XmlElement(name = "Rename", namespace = "http://www.dmg.org/PMML-4_3", type = Rename.class),
            @XmlElement(name = "MathExpression", namespace = "http://www.dmg.org/PMML-4_3", type = MathExpression.class),
            @XmlElement(name = "SqlExpression", namespace = "http://www.dmg.org/PMML-4_3", type = SqlExpression.class)
    })
    private RuleExpression ruleExpression;

    public DerivedRule() {
        super();
        this.seqNo = 1;
    }

    public DerivedRule(final OpType opType, final DataType dataType) {
        super();
        this.opType = opType;
        this.dataType = dataType;
    }

    public DerivedRule(final OpType opType, final DataType dataType,final int seqNo) {
        super();
        this.opType = opType;
        this.dataType = dataType;
        this.seqNo = seqNo;
    }

    public List<Extension> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<Extension>();
        }
        return this.extensions;
    }

    public boolean hasExtensions() {
        return ((this.extensions!= null)&&(this.extensions.size()> 0));
    }

    public DerivedRule addExtensions(Extension... extensions) {
        getExtensions().addAll(Arrays.asList(extensions));
        return this;
    }

    public FieldName getName() {
        return name;
    }

    public DerivedRule setName(FieldName name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DerivedRule setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public OpType getOpType() {
        return opType;
    }

    public DerivedRule setOpType(OpType opType) {
        this.opType = opType;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public DerivedRule setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public RuleExpression getRuleExpression() {
        return ruleExpression;
    }

    public DerivedRule setRuleExpression(RuleExpression ruleExpression) {
        this.ruleExpression = ruleExpression;
        return this;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public DerivedRule setSeqNo(int seqNo) {
        this.seqNo = seqNo;
        return this;
    }
}
