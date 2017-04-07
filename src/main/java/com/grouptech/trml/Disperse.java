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
        "disperseBins"
})
@XmlRootElement(name = "Disperse", namespace = "http://www.dmg.org/PMML-4_3")
public class Disperse extends RuleExpression {

    @XmlAttribute(name = "mapMissingTo")
    private String mapMissingTo;
    @XmlAttribute(name = "defaultValue")
    private String defaultValue;
    @XmlAttribute(name = "dataType")
    private DataType dataType;
    @XmlElement(name = "DisperseBin", namespace = "http://www.dmg.org/PMML-4_3")
    private List<DisperseBin> disperseBins;

    public Disperse() {
        super();
    }

    public Disperse(final FieldName field) {
        super();
        setField(field);
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public Disperse setField(FieldName field) {
        super.setField(field);
        super.setOutputField(field);
        return this;
    }

    public String getMapMissingTo() {
        return mapMissingTo;
    }

    public Disperse setMapMissingTo(String mapMissingTo) {
        this.mapMissingTo = mapMissingTo;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Disperse setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Disperse setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public List<DisperseBin> getDisperseBins() {
        if (disperseBins == null) {
            disperseBins = new ArrayList<DisperseBin>();
        }
        return this.disperseBins;
    }

    public boolean hasDisperseBins() {
        return ((this.disperseBins!= null)&&(this.disperseBins.size()> 0));
    }

    public Disperse addDisperseBins(DisperseBin... disperseBins) {
        getDisperseBins().addAll(Arrays.asList(disperseBins));
        return this;
    }

}
