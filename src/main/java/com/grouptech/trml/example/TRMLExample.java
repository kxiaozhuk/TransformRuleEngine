package com.grouptech.trml.example;

import com.grouptech.trml.*;
import com.grouptech.trml.*;
import org.dmg.pmml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mahone on 2017/3/28.
 */
public class TRMLExample {

    final public String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<TRML x-baseVersion=\"1.0.0\" xmlns=\"http://www.dmg.org/PMML-4_3\">\n" +
            "    <DataDictionary numberOfFields=\"8\">\n" +
            "        <DataField name=\"age\" optype=\"continuous\" dataType=\"integer\"/>\n" +
            "        <DataField name=\"marital\" optype=\"categorical\" dataType=\"string\"/>\n" +
            "        <DataField name=\"balance\" optype=\"continuous\" dataType=\"double\"/>\n" +
            "        <DataField name=\"y\" optype=\"categorical\" dataType=\"string\"/>\n" +
            "        <DataField name=\"term\" optype=\"continuous\" dataType=\"integer\"/>\n" +
            "        <DataField name=\"capital\" optype=\"continuous\" dataType=\"double\"/>\n" +
            "        <DataField name=\"job\" optype=\"categorical\" dataType=\"string\"/>\n" +
            "        <DataField name=\"living\" optype=\"categorical\" dataType=\"string\"/>\n" +
            "    </DataDictionary>\n" +
            "    <TransformationRule>\n" +
            "        <DerivedRule name=\"newEqualValues\" seqNo=\"1\">\n" +
            "            <EqualValues field=\"marital\" mapMissingTo=\"missing\" defaultValue=\"unknown\" dataType=\"string\">\n" +
            "                <EqualValuesMap mapValue=\"单身\" equalValue=\"single\"/>\n" +
            "                <EqualValuesMap mapValue=\"结婚\" equalValue=\"married\"/>\n" +
            "                <EqualValuesMap mapValue=\"离婚\" equalValue=\"divorced\"/>\n" +
            "            </EqualValues>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newNormalize\" seqNo=\"1\">\n" +
            "            <Normalize field=\"balance\" minValue=\"1000.0\" maxValue=\"50000.0\"/>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newMissingMap\" seqNo=\"1\">\n" +
            "            <MissingMap field=\"y\" mapType=\"mode\" mapValue=\"'yes'\"/>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newMath\" seqNo=\"1\">\n" +
            "            <MathExpression field=\"term\" function=\"abs\" base=\"0\"/>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newZScore\" seqNo=\"1\">\n" +
            "            <ZScore field=\"capital\" meanValue=\"50000.0\" stddevValue=\"10.1\"/>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newDisperse\" seqNo=\"1\">\n" +
            "            <Disperse field=\"age\">\n" +
            "                <DisperseBin binValue=\"young\" closure=\"closedOpen\" leftMargin=\"1.0\" rightMargin=\"18.0\"/>\n" +
            "                <DisperseBin binValue=\"medial\" closure=\"closedOpen\" leftMargin=\"18.0\" rightMargin=\"30.0\"/>\n" +
            "                <DisperseBin binValue=\"older\" closure=\"closedOpen\" leftMargin=\"30.0\"/>\n" +
            "            </Disperse>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newSql\" seqNo=\"2\">\n" +
            "            <SqlExpression field=\"debit\" outputField=\"debit\" sqlText=\"capital - balance\"/>\n" +
            "        </DerivedRule>\n" +
            "        <DerivedRule name=\"newRename\" seqNo=\"2\">\n" +
            "            <Rename field=\"age\" outputField=\"ageGroup\"/>\n" +
            "        </DerivedRule>\n" +
            "    </TransformationRule>\n" +
            "</TRML>\n";

    public Disperse newDisperse(){
        Disperse b = new Disperse();
        b.setField(FieldName.create("age"));
        DisperseBin a1 = new DisperseBin();
        a1.setBinValue("young");
        a1.setClosure(DisperseBin.Closure.CLOSED_OPEN);
        a1.setLeftMargin(1.0);
        a1.setRightMargin(18.0);
        DisperseBin a2 = new DisperseBin();
        b.addDisperseBins(a1);
        a2.setBinValue("medial");
        a2.setClosure(DisperseBin.Closure.CLOSED_OPEN);
        a2.setLeftMargin(18.0);
        a2.setRightMargin(30.0);
        b.addDisperseBins(a2);
        DisperseBin a3 = new DisperseBin();
        a3.setBinValue("older");
        a3.setClosure(DisperseBin.Closure.CLOSED_OPEN);
        a3.setLeftMargin(30.0);
        b.addDisperseBins(a3);
        return b;
    }

    public ZScore newZScore(){
        ZScore a = new ZScore();
        a.setField(FieldName.create("capital"));
        a.setMeanValue(50000.0);
        a.setStddevValue(10.1);
        return a;
    }

    public MathExpression newMath(){
        MathExpression a = new MathExpression();
        a.setField(FieldName.create("term"));
        a.setFunction(MathExpression.Function.ABS);
        return a;
    }

    public MissingMap newMissingMap(){
        MissingMap a = new MissingMap();
        a.setField(FieldName.create("y"));
        a.setMapType(MissingMap.Type.MODE);
        a.setMapValue("'yes'");
        return a;
    }

    public Normalize newNormalize(){
        Normalize a = new Normalize();
        a.setField(FieldName.create("balance"));
        a.setMinValue(1000.0);
        a.setMaxValue(50000.0);
        return a;
    }

    public EqualValues newEqualValues(){
        EqualValues b = new EqualValues();
        b.setField(FieldName.create("marital"));
        b.setDataType(DataType.STRING);
        b.setDefaultValue("unknown");
        b.setMapMissingTo("missing");
        List<EqualValuesMap> equalValuesMaps = new ArrayList<EqualValuesMap>();
        EqualValuesMap a1 = new EqualValuesMap();
        a1.setMapValue("单身");
        a1.setEqualValue("single");
        equalValuesMaps.add(a1);
        EqualValuesMap a2 = new EqualValuesMap();
        a2.setMapValue("结婚");
        a2.setEqualValue("married");
        equalValuesMaps.add(a2);
        EqualValuesMap a3 = new EqualValuesMap();
        a3.setMapValue("离婚");
        a3.setEqualValue("divorced");
        equalValuesMaps.add(a3);
        b.setEqualValuesMaps(equalValuesMaps);
        return b;
    }

    public SqlExpression newSql(){
        SqlExpression a = new SqlExpression();
        a.setField(FieldName.create("debit"));
        a.setOutputField(FieldName.create("debit"));
        a.setSqlText("capital - balance");
        return a;
    }

    public Rename newRename(){
        Rename a = new Rename();
        a.setField(FieldName.create("age"));
        a.setOutputField(FieldName.create("ageGroup"));
        return a;
    }

    public List<DerivedRule> newDerivedRuleList(){
        List<DerivedRule> drs = new ArrayList<DerivedRule>();

        DerivedRule dr1 = new DerivedRule();
        dr1.setName(FieldName.create("newEqualValues"));
        dr1.setRuleExpression(newEqualValues());
        drs.add(dr1);
        DerivedRule dr2 = new DerivedRule();
        dr2.setName(FieldName.create("newNormalize"));
        dr2.setRuleExpression(newNormalize());
        drs.add(dr2);
        DerivedRule dr3 = new DerivedRule();
        dr3.setName(FieldName.create("newMissingMap"));
        dr3.setRuleExpression(newMissingMap());
        drs.add(dr3);
        DerivedRule dr4 = new DerivedRule();
        dr4.setName(FieldName.create("newMath"));
        dr4.setRuleExpression(newMath());
        drs.add(dr4);
        DerivedRule dr5 = new DerivedRule();
        dr5.setName(FieldName.create("newZScore"));
        dr5.setRuleExpression(newZScore());
        drs.add(dr5);
        DerivedRule dr6 = new DerivedRule();
        dr6.setName(FieldName.create("newDisperse"));
        dr6.setRuleExpression(newDisperse());
        drs.add(dr6);
        DerivedRule dr7 = new DerivedRule();
        dr7.setName(FieldName.create("newSql"));
        dr7.setRuleExpression(newSql());
        dr7.setSeqNo(2);
        drs.add(dr7);
        DerivedRule dr8 = new DerivedRule();
        dr8.setName(FieldName.create("newRename"));
        dr8.setRuleExpression(newRename());
        dr8.setSeqNo(2);
        drs.add(dr8);
        return drs;
    }

    public TRML newTRML(){
        TRML trml = new TRML();
        TransformationRule t = new TransformationRule();
        t.setDerivedRules(newDerivedRuleList());
        trml.setTransformationRule(t);
        trml.setBaseVersion("1.0.0");

        List<DataField> dfs = new ArrayList<DataField>();
        dfs.add(new DataField(FieldName.create("age"), OpType.CONTINUOUS,DataType.INTEGER));
        dfs.add(new DataField(FieldName.create("marital"),OpType.CATEGORICAL,DataType.STRING));
        dfs.add(new DataField(FieldName.create("balance"),OpType.CONTINUOUS,DataType.DOUBLE));
        dfs.add(new DataField(FieldName.create("y"),OpType.CATEGORICAL,DataType.STRING));
        dfs.add(new DataField(FieldName.create("term"),OpType.CONTINUOUS,DataType.INTEGER));
        dfs.add(new DataField(FieldName.create("capital"),OpType.CONTINUOUS,DataType.DOUBLE));
        dfs.add(new DataField(FieldName.create("job"),OpType.CATEGORICAL,DataType.STRING));
        dfs.add(new DataField(FieldName.create("living"),OpType.CATEGORICAL,DataType.STRING));
        DataDictionary dd = new DataDictionary(dfs);
        dd.setNumberOfFields(dfs.size());
        trml.setDataDictionary(dd);
        return trml;
    }
}
