package com.grouptech.trml.utility;

import com.grouptech.trml.TRML;
import com.grouptech.trml.evaluator.RuleEvaluator;
import net.sf.json.JSONObject;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.FieldValueUtil;

import java.util.*;

/**
 * @author zhuwenhong on 2017/3/24.
 */
public class TRMLUtils {

    private TRMLUtils(){
    }

    public static Map<FieldName, FieldValue> prepare(Map<String,Object> dataMap){
        Map<FieldName, FieldValue> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            FieldValue fieldValue;
            fieldValue = FieldValueUtil.create(null,null,entry.getValue());

            result.put(FieldName.create(entry.getKey()), fieldValue);
        }
        return result;
    }

    public static Map<FieldName, FieldValue> prepare(String json){
        JSONObject jsonObject = JSONObject.fromObject(json);

        if (jsonObject == null )
            throw new IllegalArgumentException("Error: Input argument is not format for JSON: " + json);

        Iterator it = jsonObject.keys();
        Map<FieldName, FieldValue> result = new LinkedHashMap<>();
        while(it.hasNext()){
            String key = (String) it.next();

            FieldValue fieldValue;
            fieldValue = FieldValueUtil.create(null,null,jsonObject.get(key));

            result.put(FieldName.create(key), fieldValue);
        }
        return result;
    }

    public static Map<FieldName, FieldValue> evaluate(Map<FieldName, FieldValue> arguments, TRML trml){
        RuleEvaluator evaluator = new RuleEvaluator(trml);
        return evaluator.evaluate(arguments);
    }

    public static Map<String,Object> toMap(Map<FieldName, FieldValue> fieldValueMap){
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<FieldName, FieldValue> entry : fieldValueMap.entrySet()) {
            result.put(entry.getKey().getValue(), entry.getValue().getValue());
        }
        return result;
    }

    public static String toJSON(Map<FieldName, FieldValue> fieldValueMap){
        Map<String, Object> result = toMap(fieldValueMap);
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject.toString();
    }


}
