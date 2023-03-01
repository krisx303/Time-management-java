package com.relit.timemaangement.database;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateQuery {
    private String whereColumnName;
    private Object whereValue;
    private final Map<String, Object> valuesToSet;

    public UpdateQuery(String where, Object whereValue) {
        valuesToSet = new HashMap<>();
        this.whereColumnName = where;
        this.whereValue = whereValue;
    }

    public void addValueToSet(String name, Object value){
        valuesToSet.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SET ");
        String collect = valuesToSet.entrySet().stream().map(UpdateQuery::valueToString).collect(Collectors.joining(", "));
        builder.append(collect);
        builder.append(" WHERE ").append(whereColumnName);
        builder.append(" = ").append(whereValue);
        return builder.toString();
    }

    private static String valueToString(Map.Entry<String, Object> stringObjectEntry) {
        String str = stringObjectEntry.getKey();
        Object obj = stringObjectEntry.getValue();
        String strVal;
        if(obj instanceof String){
            strVal = "\"" + obj + "\"";
        }else{
            strVal = obj.toString();
        }
        return str + " = " + strVal;
    }

    public void setWhereColumnName(String whereColumnName) {
        this.whereColumnName = whereColumnName;
    }

    public void setWhereValue(String whereValue) {
        this.whereValue = whereValue;
    }
}
