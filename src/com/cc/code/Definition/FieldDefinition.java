package com.cc.code.Definition;

/**
 * @Classname FieldDefinition
 * @Description TODO
 * @Date 2020/12/12 9:41
 * @Created by 2413776263@qq.com
 */
public class FieldDefinition {
    private String columnName;
    private Integer ordinalPosition;
    private String dataType;
    private Integer characterMaximumLength;
    private String columnType;
    public FieldDefinition() {
    }
    public FieldDefinition(String columnName, Integer ordinalPosition, String dataType, Integer characterMaximumLength, String columnType) {
        this.columnName = columnName;
        this.ordinalPosition = ordinalPosition;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
        this.columnType = columnType;
    }
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(Integer characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    @Override
    public String toString() {
        return "FieldDefinition{" +
                "columnName='" + columnName + '\'' +
                ", ordinalPosition=" + ordinalPosition +
                ", dataType='" + dataType + '\'' +
                ", characterMaximumLength=" + characterMaximumLength +
                ", columnType='" + columnType + '\'' +
                '}';
    }
}
