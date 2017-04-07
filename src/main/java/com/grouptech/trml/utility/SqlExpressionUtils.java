package com.grouptech.trml.utility;

import com.grouptech.trml.evaluator.RuleEvaluationContext;
import org.dmg.pmml.DataType;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.FieldValueUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author zhuwenhong on 2017/3/30.
 */
public class SqlExpressionUtils {

    public SqlExpressionUtils() {
    }

    public static FieldValue execute(RuleEvaluationContext context, String sqlText){
        String tableName = "table_" + context.hashCode();
        Map<FieldName, FieldValue> fields = context.getArguments();
        StringBuffer sqlBuffer = new StringBuffer("create table " + tableName + "(");
        StringBuffer insertBuffer = new StringBuffer("insert into " + tableName + "(");
        StringBuffer valuesBuffer = new StringBuffer("values(");
        for (Map.Entry<FieldName, FieldValue> entry : fields.entrySet()) {
            // create table sql buffer
            sqlBuffer.append(entry.getKey().getValue() + " " + entry.getValue().getDataType().value() + ",");
            // insert table sql buffer
            insertBuffer.append(entry.getKey().getValue() + ",");
            if (entry.getValue().getDataType() == DataType.STRING){
                valuesBuffer.append("'" + entry.getValue().getValue()+ "',");
            }else
                valuesBuffer.append(entry.getValue().getValue()+ ",");
        }
        String createSql = sqlBuffer.substring(0,sqlBuffer.length() - 1 ).replace("string","char(50)") + ")";
        String insertSql = insertBuffer.substring(0,insertBuffer.length() - 1 ) + ") " + valuesBuffer.substring(0,valuesBuffer.length() - 1 ) + ")";
        // select query sql
        sqlBuffer.setLength(0); // Buffer清空
        sqlBuffer.append("select " + sqlText + " from " + tableName);
        // sql execute
        FieldValue fieldValue = null;
        try {
            if( !DerbyUtils.isTableExist(tableName) ){
                // execute create table sql
                DerbyUtils.execute(createSql);
            } else
                DerbyUtils.execute("truncate table " + tableName);
            // execute insert sql
            DerbyUtils.execute(insertSql);
            // execute select sql
            ResultSet resultSet = DerbyUtils.executeQuery(sqlBuffer.toString());
            if(resultSet.next()){
                fieldValue = FieldValueUtil.create(null,null,resultSet.getObject(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return fieldValue;
    }

}
