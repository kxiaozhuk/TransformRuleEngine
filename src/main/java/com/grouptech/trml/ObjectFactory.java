package com.grouptech.trml;

/**
 * @author mahone on 2017/3/24.
 */
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.dmg.pmml.tree
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TRML }
     *
     */
    public TRML createTRML() {
        return new TRML();
    }

    /**
     * Create an instance of {@link TransformationRule }
     *
     */
    public TransformationRule createTransformationRule() {
        return new TransformationRule();
    }

    /**
     * Create an instance of {@link DerivedRule }
     *
     */
    public DerivedRule createDerivedRule() {
        return new DerivedRule();
    }

    /**
     * Create an instance of {@link Disperse }
     *
     */
    public Disperse createDisperse() {
        return new Disperse();
    }

    /**
     * Create an instance of {@link EqualValues }
     *
     */
    public EqualValues createEqualValues() {
        return new EqualValues();
    }

    /**
     * Create an instance of {@link MathExpression }
     *
     */
    public MathExpression createMath() {
        return new MathExpression();
    }

    /**
     * Create an instance of {@link MissingMap }
     *
     */
    public MissingMap createMissingMap() {
        return new MissingMap();
    }

    /**
     * Create an instance of {@link Normalize }
     *
     */
    public Normalize createNormalize() {
        return new Normalize();
    }

    /**
     * Create an instance of {@link ZScore }
     *
     */
    public ZScore createZScore() {
        return new ZScore();
    }


}
