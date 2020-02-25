package com.example.usergroup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_table")
public class Group {
    @PrimaryKey(autoGenerate = true)
    private int groupId;
    @ColumnInfo(name = "group_name")
    private String name;
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
