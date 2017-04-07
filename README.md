# TransformRuleEngine

基于PMML改成了部分XML元素，生成只处理转换规则的TRML(Transform Rule Makeup Language)，将其中的TransformDictionary改为TransformRule，将DeriveField改为DeriveRule，并重写了Expression，改为RuleExpression，扩展实现了各类型规则Expression，对于evaluator也重写了，改为RuleEvaluator，用于规则的执行。

# Pom.xml部分内容如下

    <groupId>com.grouptech.trml</groupId>
    <artifactId>TransformRuleEngine</artifactId>
    <version>1.0</version>

    <dependencies>
        <dependency>
            <groupId>org.jpmml</groupId>
            <artifactId>pmml-model</artifactId>
            <version>1.3.4</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>2.2.11</version>
        </dependency>
        <dependency>
            <groupId>org.jpmml</groupId>
            <artifactId>pmml-evaluator</artifactId>
            <version>1.3.5</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>org.apache.derby</groupId>
            <artifactId>derby</artifactId>
            <version>10.13.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>2.4</version>
            <classifier>jdk15</classifier>
        </dependency>
    </dependencies>
    
   # Example
   
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
