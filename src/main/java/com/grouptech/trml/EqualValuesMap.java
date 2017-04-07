package com.grouptech.trml;

import javax.xml.bind.annotation.*;

/**
 * @author mahone on 2017/3/23.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "mapValue",
        "equalValue"
})
@XmlRootElement(name = "EqualValuesMap", namespace = "http://www.dmg.org/PMML-4_3")
public class EqualValuesMap {

    @XmlAttribute(name = "mapValue", required = true)
    private String mapValue;
    @XmlAttribute(name = "equalValue", required = true)
    private String equalValue;

    public EqualValuesMap() {
        super();
    }
    public EqualValuesMap(final String mapValue, final String equalValue) {
        super();
        this.mapValue = mapValue;
        this.equalValue = equalValue;
    }

    public String getMapValue() {
        return mapValue;
    }

    public EqualValuesMap setMapValue(String mapValue) {
        this.mapValue = mapValue;
        return this;
    }

    public String getEqualValue() {
        return equalValue;
    }

    public EqualValuesMap setEqualValue(String equalValue) {
        this.equalValue = equalValue;
        return this;
    }

}
