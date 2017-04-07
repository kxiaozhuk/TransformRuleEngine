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
        "mapType",
        "mapValue"
})
@XmlRootElement(name = "MissingMap", namespace = "http://www.dmg.org/PMML-4_3")
public class MissingMap extends RuleExpression {

    @XmlAttribute(name = "mapType",required = true)
    private MissingMap.Type mapType;
    @XmlAttribute(name = "mapValue")
    private String mapValue;

    public MissingMap() {
        super();
    }

    public MissingMap(final MissingMap.Type mapType, final String mapValue) {
        super();
        this.mapType = mapType;
        this.mapValue = mapValue;
    }

    public MissingMap.Type getMapType() {
        return mapType;
    }

    public MissingMap setMapType(MissingMap.Type mapType) {
        this.mapType = mapType;
        return this;
    }

    public String getMapValue() {
        return mapValue;
    }

    public MissingMap setMapValue(String mapValue) {
        this.mapValue = mapValue;
        return this;
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public MissingMap setField(FieldName field) {
        super.setOutputField(field);
        super.setField(field);
        return this;
    }

    @XmlType(name = "")
    @XmlEnum
    public enum Type {

        @XmlEnumValue("mode")
        MODE("mode"),
        @XmlEnumValue("any")
        ANY("any"),
        @XmlEnumValue("avg")
        AVG("avg"),
        @XmlEnumValue("min")
        MIN("min"),
        @XmlEnumValue("max")
        MAX("max");
        private final String value;

        Type(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static MissingMap.Type fromValue(String v) {
            for (MissingMap.Type c: MissingMap.Type.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

    }
}
