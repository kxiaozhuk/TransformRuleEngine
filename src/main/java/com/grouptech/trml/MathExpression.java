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
        "function"
})
@XmlRootElement(name = "MathExpression", namespace = "http://www.dmg.org/PMML-4_3")
public class MathExpression extends RuleExpression {

    @XmlAttribute(name = "function",required = true)
    private MathExpression.Function function;
    @XmlAttribute(name = "base")
    private int base;
    @XmlAttribute(name = "power")
    private Double power;

    public MathExpression() {
        super();
    }

    public MathExpression(final Function function) {
        super();
        this.function = function;
    }

    public MathExpression(final Function function, final int base) {
        super();
        this.function = function;
        this.base = base;
    }

    public MathExpression(final Function function, final Double power) {
        super();
        this.function = function;
        this.power = power;
    }

    @XmlAttribute(name = "field", required = true)
    @XmlJavaTypeAdapter(FieldNameAdapter.class)
    public FieldName getField() {
        return super.getField();
    }

    public MathExpression setField(FieldName field) {
        super.setOutputField(field);
        super.setField(field);
        return this;
    }

    public Function getFunction() {
        return function;
    }

    public MathExpression setFunction(Function function) {
        this.function = function;
        return this;
    }

    public Double getPower() {
        return power;
    }

    public MathExpression setPower(Double power) {
        this.power = power;
        return this;
    }

    public int getBase() {
        return base;
    }

    public MathExpression setBase(int base) {
        this.base = base;
        return this;
    }

/**
 * log,pow,exp,expm1,log10,log1p,log2,sqrt,cbrt,abs,signum,ceil,floor,round,rint,acos,asin,atan,cos,cosh,sin,sinh,tan,tanh
 * */

    @XmlType(name = "")
    @XmlEnum
    public enum Function {

        @XmlEnumValue("log")
        LOG("log"),
        @XmlEnumValue("pow")
        POW("pow"),
        @XmlEnumValue("exp")
        EXP("exp"),
        @XmlEnumValue("expm1")
        EXPM1("expm1"),
        @XmlEnumValue("log10")
        LOG10("log10"),
        @XmlEnumValue("log1p")
        LOG1p("log1p"),
        @XmlEnumValue("log2")
        LOG2("log2"),
        @XmlEnumValue("sqrt")
        SQRT("sqrt"),
        @XmlEnumValue("cbrt")
        CBRT("cbrt"),
        @XmlEnumValue("abs")
        ABS("abs"),
        @XmlEnumValue("signum")
        SIGNUM("signum"),
        @XmlEnumValue("ceil")
        CEIL("ceil"),
        @XmlEnumValue("floor")
        FLOOR("floor"),
        @XmlEnumValue("round")
        ROUND("round"),
        @XmlEnumValue("rint")
        RINT("rint"),
        @XmlEnumValue("acos")
        ACOS("acos"),
        @XmlEnumValue("asin")
        ASIN("asin"),
        @XmlEnumValue("atan")
        ATAN("atan"),
        @XmlEnumValue("cos")
        COS("cos"),
        @XmlEnumValue("cosh")
        COSH("cosh"),
        @XmlEnumValue("sin")
        SIN("sin"),
        @XmlEnumValue("sinh")
        SINH("sinh"),
        @XmlEnumValue("tan")
        TAN("tan"),
        @XmlEnumValue("tanh")
        TANH("tanh"),;
        private final String value;

        Function(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static Function fromValue(String v) {
            for (Function c: Function.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

    }
}
