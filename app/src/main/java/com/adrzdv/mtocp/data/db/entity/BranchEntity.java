package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "branch")
public class BranchEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "s_name")
    private String shortName;
    @NonNull
    @ColumnInfo(name = "is_alive")
    private Boolean isAlive;

    public BranchEntity() {
        id = 0;
        name = "";
        shortName = "";
        isAlive = false;
    }

    public BranchEntity(@NonNull Integer id,
                        @NonNull String name,
                        @NonNull String shortName,
                        @NonNull Boolean isAlive) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.isAlive = isAlive;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getShortName() {
        return shortName;
    }

    public void setShortName(@NonNull String sName) {
        this.shortName = sName;
    }

    @NonNull
    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(@NonNull Boolean alive) {
        isAlive = alive;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BranchEntity branchEntity)) return false;
        return Objects.equals(id, branchEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
