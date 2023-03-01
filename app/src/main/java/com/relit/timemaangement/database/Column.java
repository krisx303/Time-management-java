package com.relit.timemaangement.database;

import androidx.annotation.NonNull;

public class Column {
    private final String name;
    private final ColumnType type;
    private ColumnOption[] options;

    public Column(String name, ColumnType type, ColumnOption[] options) {
        this.name = name;
        this.type = type;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(" ");
        builder.append(type).append(" ");
        boolean isAutoincrement = false;
        boolean isPrimaryKey = false;
        for (ColumnOption option : options) {
            if(option == ColumnOption.AUTOINCREMENT) isAutoincrement = true;
            if(option == ColumnOption.PRIMARY_KEY) isPrimaryKey = true;
        }
        if(isPrimaryKey)
            builder.append("PRIMARY KEY ");
        if(isAutoincrement)
            builder.append("AUTOINCREMENT ");
        return builder.toString();
    }
}
