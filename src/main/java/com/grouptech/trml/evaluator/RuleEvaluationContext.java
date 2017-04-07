package com.grouptech.trml.evaluator;

import com.grouptech.trml.DerivedRule;
import com.grouptech.trml.utility.DerivedRuleUtils;
import org.dmg.pmml.DataField;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.*;
import java.util.*;

/**
 * 规则评估上下文，存储规则评估器和变量对
 * @author mahone on 2017/3/27.
 */
public class RuleEvaluationContext{

    private RuleEvaluator ruleEvaluator = null;

    private Map<FieldName, FieldValue> fields = new HashMap<>(); //结果数据存储处

    private Map<FieldName, FieldValue> arguments = Collections.emptyMap(); //输入参数

    public RuleEvaluationContext(RuleEvaluator ruleEvaluator) {
        this.ruleEvaluator = ruleEvaluator;
    }

    public FieldValue evaluate(FieldName name){
    /*
        Map<FieldName, FieldValue> fields = getFields();
        if(fields.size() > 0){
            FieldValue value = fields.get(name);

            if((value != null) || (value == null && fields.containsKey(name))){
                return value;
            }
        }*/

        return resolve(name);
    }

    public FieldValue getField(FieldName name){
        Map<FieldName, FieldValue> fields = getFields();

        return fields.get(name);
    }

    public FieldValue declare(FieldName name, Object value){

        if(value instanceof FieldValue){
            return declare(name, (FieldValue)value);
        }

        return declare(name, createFieldValue(name, value));
    }

    public FieldValue declare(FieldName name, FieldValue value){
        Map<FieldName, FieldValue> fields = getFields();

        boolean declared = fields.containsKey(name);
        if(declared){
            throw new DuplicateValueException(name);
        }

        fields.put(name, value);

        return value;
    }

    public void declareAll(Map<FieldName, ?> values){
        Collection<? extends Map.Entry<FieldName, ?>> entries = values.entrySet();

        for(Map.Entry<FieldName, ?> entry : entries){
            declare(entry.getKey(), entry.getValue());
        }
    }

    public Map<FieldName, FieldValue> getArguments() {
        return arguments;
    }

    public FieldValue getArgField(FieldName name) {
        return arguments.get(name);
    }

    public void setArguments(Map<FieldName, FieldValue> arguments) {
        this.arguments = arguments;
    }

    public Map<FieldName, FieldValue> getFields(){
        return this.fields;
    }

    public RuleEvaluator getRuleEvaluator(){
        return this.ruleEvaluator;
    }

    private void setRuleEvaluator(RuleEvaluator ruleEvaluator){
        this.ruleEvaluator = ruleEvaluator;
    }

    public FieldValue createFieldValue(FieldName name, Object value){
        RuleEvaluator ruleEvaluator = getRuleEvaluator();

        DataField dataField = ruleEvaluator.getDataField(name);
        if(dataField == null){
            throw new MissingFieldException(name);
        }
        return FieldValueUtil.create(dataField.getDataType(),dataField.getOpType(),value);

    }

    /**
     * 衍生规则计算
     * @param name: FieldName 变量名
     * */
    protected FieldValue resolve(FieldName name){
        RuleEvaluator ruleEvaluator = getRuleEvaluator();

        DerivedRule derivedRule = ruleEvaluator.getDerivedRule(name);
        if(derivedRule != null){
            FieldValue fieldValue;
            // 衍生规则计算
            fieldValue = DerivedRuleUtils.evaluate(derivedRule, this);

            return declare(derivedRule.getRuleExpression().getOutputField(), fieldValue);
        }
        else {
            Map<FieldName, FieldValue> arguments = getArguments();

            return declare(name, arguments.get(name));
        }
    }

    /**
     * 重置上下文，清空变量
     * */
    public void reset(){
        this.fields.clear();
        this.arguments = Collections.emptyMap();
    }


}
