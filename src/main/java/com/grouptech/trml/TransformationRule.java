package com.grouptech.trml;

import org.dmg.pmml.Extension;
import org.dmg.pmml.HasExtensions;
import org.dmg.pmml.Output;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mahone on 2017/3/23.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "extensions",
        "derivedRules",
        "output"
})
@XmlRootElement(name = "TransformationRule", namespace = "http://www.dmg.org/PMML-4_3")
public class TransformationRule implements HasExtensions {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_3")
    private List<Extension> extensions;
    @XmlElement(name = "DerivedRule", namespace = "http://www.dmg.org/PMML-4_3")
    private List<DerivedRule> derivedRules;
    @XmlElement(name = "Output", namespace = "http://www.dmg.org/PMML-4_3")
    private Output output;

    public TransformationRule(){
        super();
    }

    public TransformationRule(List<DerivedRule> derivedRules) {
        super();
        this.derivedRules = derivedRules;
    }

    public List<Extension> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<Extension>();
        }
        return this.extensions;
    }

    public List<DerivedRule> getDerivedRules() {
        if (derivedRules == null) {
            derivedRules = new ArrayList<DerivedRule>();
        }
        return this.derivedRules;
    }

    public boolean hasDerivedRules() {
        return ((this.derivedRules!= null)&&(this.derivedRules.size()> 0));
    }

    public TransformationRule addDerivedRules(DerivedRule... derivedRules) {
        getDerivedRules().addAll(Arrays.asList(derivedRules));
        return this;
    }

    public TransformationRule setDerivedRules(List<DerivedRule> derivedRules) {
        this.derivedRules = derivedRules;
        return this;
    }

    public Output getOutput() {
        return output;
    }

    public TransformationRule setOutput(Output output) {
        this.output = output;
        return this;
    }

    public boolean hasExtensions() {
        return ((this.extensions!= null)&&(this.extensions.size()> 0));
    }

    public TransformationRule addExtensions(org.dmg.pmml.Extension... extensions) {
        getExtensions().addAll(Arrays.asList(extensions));
        return this;
    }
}
