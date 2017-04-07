package com.grouptech.trml;

import javax.xml.bind.annotation.*;

/**
 * @author mahone on 2017/3/23.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "binValue",
        "closure"
})
@XmlRootElement(name = "DisperseBin", namespace = "http://www.dmg.org/PMML-4_3")
public class DisperseBin {

    @XmlAttribute(name = "binValue", required = true)
    private String binValue;
    @XmlAttribute(name = "closure", required = true)
    private DisperseBin.Closure closure;
    @XmlAttribute(name = "leftMargin")
    private Double leftMargin;
    @XmlAttribute(name = "rightMargin")
    private Double rightMargin;

    public DisperseBin() {
        super();
    }
    public DisperseBin(String binValue, Closure closure) {
        super();
        this.binValue = binValue;
        this.closure = closure;
    }

    public String getBinValue() {
        return binValue;
    }

    public DisperseBin setBinValue(String binValue) {
        this.binValue = binValue;
        return this;
    }

    public Closure getClosure() {
        return closure;
    }

    public DisperseBin setClosure(Closure closure) {
        this.closure = closure;
        return this;
    }

    public Double getLeftMargin() {
        return leftMargin;
    }

    public DisperseBin setLeftMargin(Double leftMargin) {
        this.leftMargin = leftMargin;
        return this;
    }

    public Double getRightMargin() {
        return rightMargin;
    }

    public DisperseBin setRightMargin(Double rightMargin) {
        this.rightMargin = rightMargin;
        return this;
    }

    @XmlType(name = "")
    @XmlEnum
    public enum Closure {

        @XmlEnumValue("openClosed")
        OPEN_CLOSED("openClosed"),
        @XmlEnumValue("openOpen")
        OPEN_OPEN("openOpen"),
        @XmlEnumValue("closedOpen")
        CLOSED_OPEN("closedOpen"),
        @XmlEnumValue("closedClosed")
        CLOSED_CLOSED("closedClosed");
        private final String value;

        Closure(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static DisperseBin.Closure fromValue(String v) {
            for (DisperseBin.Closure c: DisperseBin.Closure.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

    }

}
