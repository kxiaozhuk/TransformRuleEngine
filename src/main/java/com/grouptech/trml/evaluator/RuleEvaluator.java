package com.grouptech.trml.evaluator;

import com.grouptech.trml.TRML;
import com.grouptech.trml.DerivedRule;
import com.grouptech.trml.TransformationRule;
import com.grouptech.trml.utility.DerivedRuleUtils;
import org.dmg.pmml.*;
import org.jpmml.evaluator.FieldValue;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 规则评估器
 * @author mahone on 2017/3/28.
 */
public class RuleEvaluator implements Serializable{

    private TRML trml = null;

    private Map<FieldName, DataField> dataFields = Collections.emptyMap();

    private List<Map<FieldName, DerivedRule>> derivedRules = Collections.emptyList();

    private Map<FieldName, DerivedRule> activeDerivedRules = Collections.emptyMap();

    private Map<FieldName, OutputField> outputFields = Collections.emptyMap();

    private int maxRuleIndex; // 最大规则下标

    private int activeRuleIndex; // 当前激活的规则下标数

    private final int DEFAULT_RULE_INDEX = 0; // 默认的规则下标

    /**
     * 规则评估器构造函数
     * @param trml: transform rule makeup language转化规则标记语言
     */
    public RuleEvaluator(TRML trml){
        setTrml(Objects.requireNonNull(trml));

        // 数据字典
        DataDictionary dataDictionary = trml.getDataDictionary();
        if(dataDictionary == null){
            throw new IllegalArgumentException();
        } // End if

        // 数据字典数据域
        if(dataDictionary.hasDataFields()){
            List<DataField> dataFieldList = dataDictionary.getDataFields();
            this.dataFields = dataFieldList.stream().collect(Collectors.toMap(DataField::getName,dataField -> dataField));
        }

        // 转换规则
        TransformationRule transformationRule = trml.getTransformationRule();
        if(transformationRule != null && transformationRule.hasDerivedRules()){
            // 变量衍生规则
            List<DerivedRule> derivedRuleList = transformationRule.getDerivedRules();
            maxRuleIndex = DerivedRuleUtils.getMaxSequence(derivedRuleList) - 1;
            List<Map<FieldName, DerivedRule>> mapList = new ArrayList<>();
            for(int i = 0; i <= maxRuleIndex; i++) {
                List<DerivedRule> rules = DerivedRuleUtils.getDerivedRuleBySeq(derivedRuleList,i+1);

                Map<FieldName, DerivedRule> fieldDerivedMap = new HashMap<>();
                rules.forEach(rule -> {
                    FieldName fieldName = rule.getRuleExpression().getField();
                    fieldDerivedMap.put(fieldName,rule);
                });
                mapList.add(fieldDerivedMap);
            }
            this.derivedRules = mapList;
            this.activeRuleIndex = DEFAULT_RULE_INDEX; // 设置当前活跃的规则下标
        } // End if

        // 输出数据域
        Output output = transformationRule.getOutput();
        if(output != null && output.hasOutputFields()){
            List<OutputField> outputFieldList = output.getOutputFields();
            this.outputFields = outputFieldList.stream().collect(Collectors.toMap(OutputField::getName,outputField -> outputField));
        }
    }

    public TRML getTrml() {
        return trml;
    }

    public void setTrml(TRML trml) {
        this.trml = trml;
    }

    public Map<FieldName, DataField> getDataFields() {
        return dataFields;
    }

    public DataField getDataField(FieldName name){

        if( name == null){
            return new DataField(name, OpType.CATEGORICAL, DataType.STRING);
        }
        return this.dataFields.get(name);
    }

    public void setDataFields(Map<FieldName, DataField> dataFields) {
        this.dataFields = dataFields;
    }

    public int getActiveRuleIndex() {
        return activeRuleIndex;
    }

    public void setActiveRuleIndex(int activeRuleIndex) {
        this.activeRuleIndex = activeRuleIndex;
        this.activeDerivedRules = getActiveDerivedRules();
    }

    /**
     * 获取当前激活的衍生规则
     */
    public Map<FieldName, DerivedRule> getActiveDerivedRules() {
        if (activeRuleIndex == DEFAULT_RULE_INDEX){
            // activeSequence未激活
            activeDerivedRules = getDerivedRules(DEFAULT_RULE_INDEX);
        } else {
            // activeSequence已设置激活
            activeDerivedRules = getDerivedRules(activeRuleIndex);
        }
        return activeDerivedRules;
    }

    public Map<FieldName, DerivedRule> getDerivedRules() {
        return getActiveDerivedRules();
    }

    public DerivedRule getDerivedRule(FieldName name){
        return getDerivedRules().get(name);
    }

    public Map<FieldName, DerivedRule> getDerivedRules(Integer seqNo) {
        return this.derivedRules.get(seqNo);
    }

    public DerivedRule getDerivedRule(Integer seqNo,FieldName name) {
        return getDerivedRules(seqNo).get(name);
    }

    public OutputField getOutputField(FieldName name){
        return this.outputFields.get(name);
    }

    public void setOutputFields(Map<FieldName, OutputField> outputFields) {
        this.outputFields = outputFields;
    }

    /**
     * 衍生规则评估计算
     * @param arguments: 输入参数
     */
    public Map<FieldName, FieldValue> evaluate(Map<FieldName, FieldValue> arguments){
        RuleEvaluationContext context = new RuleEvaluationContext(this);
        Map<FieldName, FieldValue> argFieldValue = arguments;
        Map<FieldName, FieldName> fieldMap;
        List<FieldName> result;

        for(int i = 0; i <= maxRuleIndex; i++) {
            context.reset(); // 上下文清空
            this.setActiveRuleIndex(i); // 根据循环次数设置激活次数
            fieldMap = DerivedRuleUtils.getDeriveRuleFieldMap(this.activeDerivedRules);

            context.setArguments(argFieldValue); // 设置输入参数
            result = combine(argFieldValue.keySet(),fieldMap); //

            result.forEach( context::evaluate ); // 开始执行衍生规则

            //result = (Map<FieldName, FieldValue>) ((HashMap<FieldName, FieldValue>)context.getFields()).clone();
            argFieldValue = new HashMap<FieldName, FieldValue>(context.getFields()); // 获取衍生规则转换结果（深拷贝）
        }
        // 设置outputField
        Map<FieldName, OutputField> outputFieldMap = new LinkedHashMap<>();
        argFieldValue.entrySet().forEach(entry -> {
            outputFieldMap.put( entry.getKey(),new OutputField(entry.getKey(),entry.getValue().getDataType()) );
        });
        this.setOutputFields(outputFieldMap);

        return argFieldValue;
    }

    /**
     * 输出数据域合并
     * @param fieldValue: 输入参数
     * @param fieldMap: 输入输出域对应
     */
    public static List<FieldName> combine(Set<FieldName> fieldValue, Map<FieldName, FieldName> fieldMap){
        List<FieldName> result = new ArrayList<>();
        result.addAll(fieldValue);

        fieldMap.entrySet().forEach(field -> {
            /*if ( field.getKey() != field.getValue() && result.contains(field.getKey()) ){
                result.remove(field.getKey());
                result.add(field.getValue());
            }else */
            if( !result.contains(field.getKey()) ){
                result.add(field.getKey());
            }
        });
        return result;
    }


}
