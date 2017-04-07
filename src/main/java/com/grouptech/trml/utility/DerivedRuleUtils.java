package com.grouptech.trml.utility;

import com.grouptech.trml.*;
import com.grouptech.trml.evaluator.RuleEvaluationContext;
import com.habot.trml.*;
import org.dmg.pmml.DataType;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.FieldValueUtil;

import java.util.*;

import static com.grouptech.trml.DisperseBin.Closure.*;


/**
 * @author zhuwenhong on 2017/3/27.
 */
public class DerivedRuleUtils {

    private DerivedRuleUtils(){
    }

    /**
     * 获取最大次序
     * @param derivedRules: 衍生规则列表
     * */
    public static int getMaxSequence(List<DerivedRule> derivedRules){
        List<Integer> seqList = new ArrayList<>();
        derivedRules.forEach(derivedRule -> {
            seqList.add(derivedRule.getSeqNo());
        });
        int maxSeq = Collections.max(seqList);
        return maxSeq;
    }

    /**
     * 制定次序获取衍生规则
     * @param derivedRules: 衍生规则列表
     * @param seqNo：次序
     * */
    public static List<DerivedRule> getDerivedRuleBySeq(List<DerivedRule> derivedRules, int seqNo){
        List<DerivedRule> derivedRuleList = new ArrayList<>();

        derivedRules.forEach(derivedRule -> {
            if (derivedRule.getSeqNo() ==  seqNo){
                derivedRuleList.add(derivedRule);
            }
        });

        return derivedRuleList;
    }

    /**
     * 获取衍生规则的输入输入域对应关系
     * @param derivedRuleMap: 衍生规则列表
     * */
    public static Map<FieldName, FieldName> getDeriveRuleFieldMap(Map<FieldName, DerivedRule> derivedRuleMap){
        Map<FieldName, FieldName> dataFields = new LinkedHashMap<>();
        //List<FieldName> dataFields = new ArrayList<>();

        for(Map.Entry<FieldName, DerivedRule> entry: derivedRuleMap.entrySet()){
            FieldName field = entry.getValue().getRuleExpression().getOutputField();
            dataFields.put(entry.getKey(),field);
            //dataFields.add(field);
        }
        return dataFields;
    }

    /**
     * 根据衍生规则类型转换结果并返回
     * @param derivedRule: 一条衍生规则
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluate(DerivedRule derivedRule, RuleEvaluationContext context){
        RuleExpression expression = derivedRule.getRuleExpression();

        // 离散化
        if(expression instanceof Disperse){
            return evaluateDisperse((Disperse)expression, context);
        } else
            // 等值替换
        if(expression instanceof EqualValues){
            return evaluateEqualValues((EqualValues)expression, context);
        } else
            // 数学转换
        if(expression instanceof MathExpression){
            return evaluateMathExpression((MathExpression)expression, context);
        } else
            // 空值转换
        if(expression instanceof MissingMap){
            return evaluateMissingMap((MissingMap)expression, context);
        } else
            // 最大最小化
        if(expression instanceof Normalize){
            return evaluateNormalize((Normalize)expression, context);
        } else
            // 重命名
        if(expression instanceof Rename){
            return evaluateRename((Rename)expression, context);
        } else
            // SQL转换
        if(expression instanceof SqlExpression){
            return evaluateSqlExpression((SqlExpression)expression, context);
        } else
            // 标准化
        if(expression instanceof ZScore){
            return evaluateZScore((ZScore)expression, context);
        } else
            // 抛出异常
        throw new IllegalArgumentException("Error: Rule Expression is invalid: " + expression.toString());

    }

    /**
     * 离散化规则评估
     * @param disperse: 离散化
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateDisperse(Disperse disperse, RuleEvaluationContext context){

        String value = null;
        FieldValue fieldValue = context.getArgField(disperse.getField());
        Double originalValue =  fieldValue.asDouble();
        List<DisperseBin> disperseBins = disperse.getDisperseBins();
        String defualtvalue = disperse.getDefaultValue();
        for(DisperseBin disperseBin : disperseBins){
            Double leftMargin = disperseBin.getLeftMargin();
            Double rightMargin = disperseBin.getRightMargin();
            // 左右界限值判定
            // "The leftMargin and rightMargin attributes are optional, but at least one value must be defined"
            if(leftMargin == null && rightMargin == null){
                throw new IllegalArgumentException("Error: LeftMargin or rightMargin is either required in Disperse rule.");
            } // End if
            if(leftMargin != null && rightMargin != null && (leftMargin).compareTo(rightMargin) > 0){
                throw new IllegalArgumentException("Error: LeftMargin cannot greater than rightMargin in Disperse rule.");
            }
            if(leftMargin == null) leftMargin = Double.NEGATIVE_INFINITY;
            if(rightMargin == null) rightMargin = Double.POSITIVE_INFINITY;

            DisperseBin.Closure closure = disperseBin.getClosure();
            if( closure == OPEN_OPEN && originalValue > leftMargin && originalValue < rightMargin) {
                value = disperseBin.getBinValue();
                break;
            }
            else if ( closure == OPEN_CLOSED && originalValue > leftMargin && originalValue <= rightMargin ) {
                value = disperseBin.getBinValue();
                break;
            }
            else if ( closure == CLOSED_OPEN && originalValue >= leftMargin && originalValue < rightMargin ) {
                value = disperseBin.getBinValue();
                break;
            }
            else if ( closure == CLOSED_CLOSED && originalValue >= leftMargin && originalValue <= rightMargin ) {
                value = disperseBin.getBinValue();
                break;
            }
            else continue;
        }
        // 默认值判定
        value = (value == null) ? defualtvalue : value;
        return FieldValueUtil.create(DataType.STRING, null, value);
    }

    /**
     * 等值转换规则评估
     * @param equalValues: 等值转换
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateEqualValues(EqualValues equalValues, RuleEvaluationContext context){
        FieldValue fieldValue = context.getArgField(equalValues.getField());
        Object value = null;
        DataType dataType = equalValues.getDataType();
        List<EqualValuesMap> equalValuesMaps = equalValues.getEqualValuesMaps();
        for(EqualValuesMap valueMap: equalValuesMaps){
            String mapValue = valueMap.getMapValue();
            String equalValue = valueMap.getEqualValue();

            // 数据类型判定
            if (dataType == DataType.STRING && fieldValue.asString() == mapValue){
                value = equalValue;
                break;
            }else if ( (dataType == DataType.DOUBLE || dataType == DataType.FLOAT) && fieldValue.asDouble() == Double.valueOf(mapValue)){
                value = Double.valueOf(equalValue);
                break;
            } else if (dataType == DataType.INTEGER && fieldValue.asInteger() == Integer.valueOf(mapValue)){
                value = Integer.valueOf(equalValue);
                break;
            }else continue;
        }

        return FieldValueUtil.create(equalValues.getDataType(), null, value);
    }

    /**
     * 数学函数转换规则评估
     * @param mathExpression: 数学函数转换
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateMathExpression(MathExpression mathExpression, RuleEvaluationContext context){
        FieldValue fieldValue = context.getArgField(mathExpression.getField());
        Double originalValue;
        Double value;
        // 数据类型判定
        if (fieldValue.getDataType() == DataType.INTEGER) {
            originalValue = fieldValue.asInteger().doubleValue();
        } else if (fieldValue.getDataType() == DataType.DOUBLE || fieldValue.getDataType() == DataType.FLOAT){
            originalValue = fieldValue.asDouble();
        } else throw new IllegalArgumentException("Error: Field value data type for math is invalid: "+ fieldValue.getDataType());

        // 匹配数学函数
        MathExpression.Function function = mathExpression.getFunction();
        switch(function){
            case LOG:{
                Integer base = mathExpression.getBase(); // LOG底数
                value = Math.log10(originalValue) / Math.log10(base.doubleValue());}
                break;
            case POW:{
                Double power = mathExpression.getPower(); // POW幂数
                value = Math.pow(originalValue,power);}
                break;
            case EXP:
                value = Math.exp(originalValue);
                break;
            case EXPM1:
                value = Math.expm1(originalValue);
                break;
            case LOG1p:
                value = Math.log1p(originalValue);
                break;
            case LOG10:
                value = Math.log10(originalValue);
                break;
            case LOG2:
                value = Math.log10(originalValue) / Math.log10(2.0);
                break;
            case SQRT:
                value = Math.sqrt(originalValue);
                break;
            case CBRT:
                value = Math.cbrt(originalValue);
                break;
            case ABS:
                value = Math.abs(originalValue);
                break;
            case SIGNUM:
                value = Math.signum(originalValue);
                break;
            case CEIL:
                value = Math.ceil(originalValue);
                break;
            case FLOOR:
                value = Math.floor(originalValue);
                break;
            case ROUND:{
                Long roundValue = Math.round(originalValue);
                value = roundValue.doubleValue();}
                break;
            case RINT:
                value = Math.rint(originalValue);
                break;
            case ACOS:
                value = Math.acos(originalValue);
                break;
            case ASIN:
                value = Math.asin(originalValue);
                break;
            case ATAN:
                value = Math.atan(originalValue);
                break;
            case COS:
                value = Math.cosh(originalValue);
                break;
            case COSH:
                value = Math.cosh(originalValue);
                break;
            case SIN:
                value = Math.sin(originalValue);
                break;
            case SINH:
                value = Math.sinh(originalValue);
                break;
            case TAN:
                value = Math.tan(originalValue);
                break;
            case TANH:
                value = Math.tanh(originalValue);
                break;
            default:
                throw new IllegalArgumentException("Error: Math expression is invalid: "+ function);
        }

        return FieldValueUtil.create(DataType.DOUBLE, null, value);
    }

    /**
     * 空值替换规则评估
     * @param missingMap: 空值替换
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateMissingMap(MissingMap missingMap, RuleEvaluationContext context){
        FieldValue fieldValue = context.getArgField(missingMap.getField());
        FieldValue result;
        // 空数值判定
        if ( fieldValue.getValue() == null){
            DataType dataType = fieldValue.getDataType();
            String mapValue = missingMap.getMapValue();

            // 数据类型判定
            if (dataType == DataType.STRING){
                result = FieldValueUtil.create(dataType,null,mapValue);

            }else if (dataType == DataType.DOUBLE || dataType == DataType.FLOAT){
                result = FieldValueUtil.create(dataType,null,Double.valueOf(mapValue));

            } else if (dataType == DataType.INTEGER){
                result = FieldValueUtil.create(dataType,null,Integer.valueOf(mapValue));

            }else
                throw new IllegalArgumentException("Error: Field value data type for MissingMap is invalid: "+dataType);

        } else
            result = FieldValueUtil.create(fieldValue.getDataType(),null,fieldValue.getValue());

        return result;
    }

    /**
     * 归一化规则评估  ( x - min ) / ( max - min )
     * @param normalize: 最大最小化
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateNormalize(Normalize normalize, RuleEvaluationContext context){
        FieldValue fieldValue = context.getArgField(normalize.getField());
        Double min = normalize.getMinValue();
        Double max = normalize.getMaxValue();
        Double value;
        // 数据类型判定
        if (fieldValue.getDataType() == DataType.INTEGER) {
            Integer originalValue = fieldValue.asInteger();
            value = ( originalValue - min ) / (max - min);

        } else if (fieldValue.getDataType() == DataType.DOUBLE || fieldValue.getDataType() == DataType.FLOAT){
            Double originalValue = fieldValue.asDouble();
            value = ( originalValue - min ) / (max - min);

        } else throw new IllegalArgumentException("Error: Field value data type for normalize is invalid: "+ fieldValue.getDataType());

        return FieldValueUtil.create(DataType.DOUBLE,null,value);
    }

    /**
     * 重命名规则评估
     * @param rename: 重命名
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateRename(Rename rename, RuleEvaluationContext context){
        FieldValue fieldValue = context.getArgField(rename.getField());
        return FieldValueUtil.create(fieldValue.getDataType(),null,fieldValue.getValue()) ;
    }

    /**
     * SQL转换规则评估
     * @param sqlExpression: SQL转换
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateSqlExpression(SqlExpression sqlExpression, RuleEvaluationContext context){
        String sqlText = sqlExpression.getSqlText();
        FieldValue fieldValue = SqlExpressionUtils.execute(context,sqlText);
        return fieldValue;
    }

    /**
     * 标准化规则评估 ( x - mean ) / stddev
     * @param zScore: 标准化
     * @param context：规则评估上下文
     * */
    public static FieldValue evaluateZScore(ZScore zScore, RuleEvaluationContext context){
        FieldValue fieldValue = context.getArgField(zScore.getField());
        Double mean = zScore.getMeanValue();
        Double stddev = zScore.getStddevValue();
        Double originalValue = fieldValue.asDouble();
        Double value = ( originalValue - mean ) / stddev;

        return FieldValueUtil.create(DataType.DOUBLE,null,value);
    }


}
