package com.grouptech.trml;

import org.dmg.pmml.DataType;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.adapters.FieldNameAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mahone on 2017/3/23.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = {
        "field",
        "equalValuesMaps"
})
@XmlRootElement(name = "EqualValues", namespace = "http://www.dmg.org/PMML-4_3")
public class EqualValues extends RuleExpression {

    @XmlAttribute(name = "mapMissingTo")
    private String mapMissingTo;
    @XmlAttribute(name = "defaultValue")
    private String defaultValue;
    @XmlAttribute(name = "dataType")
    private DataType dataType;
    @XmlElement(name = "EqualValuesMap", namespace = "http://www.dmg.org/PMML-4_3")
    private List<EqualValuesMap> equalValuesMaps;

    public EqualValues() {
        super();
    }

    public EqualValues(final FieldName field) {
        super();
        setField(field);
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public EqualValues setField(FieldName field) {
        super.setOutputField(field);
        super.setField(field);
        return this;
    }

    public String getMapMissingTo() {
        return mapMissingTo;
    }

    public EqualValues setMapMissingTo(String mapMissingTo) {
        this.mapMissingTo = mapMissingTo;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public EqualValues setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public EqualValues setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public List<EqualValuesMap> getEqualValuesMaps() {
        if (equalValuesMaps == null) {
            equalValuesMaps = new ArrayList<EqualValuesMap>();
        }
        return this.equalValuesMaps;
    }

    public EqualValues setEqualValuesMaps(List<EqualValuesMap> equalValuesMaps) {
        this.equalValuesMaps = equalValuesMaps;
        return this;
    }

    public boolean hasEqualValuesMaps() {
        return ((this.equalValuesMaps!= null)&&(this.equalValuesMaps.size()> 0));
    }

    public EqualValues addEqualValuesMaps(EqualValuesMap... equalValuesMapses) {
        getEqualValuesMaps().addAll(Arrays.asList(equalValuesMapses));
        return this;
    }
}
