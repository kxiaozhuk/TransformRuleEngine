package com.grouptech.trml;

import com.grouptech.trml.evaluator.RuleEvaluator;
import com.grouptech.trml.example.TRMLExample;
import org.dmg.pmml.DataType;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.FieldValueUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mahone on 2017/3/28.
 */
public class EvaluatorTest {

    @Test
    public void test(){
        TRMLExample trmlExample = new TRMLExample();
        TRML trml = trmlExample.newTRML();

        RuleEvaluator evaluator = new RuleEvaluator(trml);
        Map<FieldName, FieldValue> dataMap = new HashMap<>();
        dataMap.put(FieldName.create("age"), FieldValueUtil.create(DataType.INTEGER,null,42));
        dataMap.put(FieldName.create("marital"), FieldValueUtil.create(DataType.STRING,null,"单身"));
        dataMap.put(FieldName.create("term"), FieldValueUtil.create(DataType.INTEGER,null,-8));
        dataMap.put(FieldName.create("balance"), FieldValueUtil.create(DataType.DOUBLE,null,15000));
        dataMap.put(FieldName.create("y"), FieldValueUtil.create(DataType.STRING,null,"no"));
        dataMap.put(FieldName.create("capital"), FieldValueUtil.create(DataType.DOUBLE,null,30000.0));
        /**
         * dataField: age,marital,term,balance,y,capital,job,living
         * rule01:
         * age -> Disperse -> age
         * marital -> EqualValues -> marital
         * term -> Math -> term
         * balance -> Normalize -> balance
         * y -> MissingMap -> y
         * capital -> ZScore -> capital
         * outputField:age,marital,term,balance,y,capital,job,living
         * rule02:
         * age -> Rename -> ageGroup
         * debit -> Sql -> debit *new column
         * outputField:ageGroup,marital,term,balance,y,capital,job,living,debit
         */
        Map<FieldName, FieldValue> resMap = evaluator.evaluate(dataMap);
        resMap.entrySet().forEach(entry -> {
            System.out.println("field: " + entry.getKey().getValue() + ",dataType: " + entry.getValue().getDataType() + ",value: "+ entry.getValue().getValue());
        });


    }

}
