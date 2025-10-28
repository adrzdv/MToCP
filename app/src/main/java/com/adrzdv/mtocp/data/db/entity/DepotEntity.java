package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "depots",
        foreignKeys = @ForeignKey(
                entity = BranchEntity.class,
                parentColumns = "id",
                childColumns = "branch_id"
        )
)
public class DepotEntity {
    @NonNull
    @PrimaryKey()
    //@ColumnInfo(name = "id")
    private Integer id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "s_name")
    private String shortName;
    @NonNull
    @ColumnInfo(name = "branch_id")
    private Integer branchId;
    @NonNull
    @ColumnInfo(name = "is_active")
    private Boolean isActive;
    @NonNull
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    @Nullable
    @ColumnInfo(name = "is_dinner_depot")
    private Boolean isDinnerDepot;

    public DepotEntity() {
        id = 0;
        name = "";
        shortName = "";
        branchId = 0;
        isActive = false;
        phoneNumber = "";
        isDinnerDepot = false;
    }

    public DepotEntity(@NonNull Integer id,
                       @NonNull String name,
                       @NonNull String shortName,
                       @NonNull Integer branchId,
                       @NonNull Boolean isActive,
                       @NonNull String phoneNumber,
                       @Nullable Boolean isDinnerDepot) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.branchId = branchId;
        this.isActive = isActive;
        this.phoneNumber = phoneNumber;
        this.isDinnerDepot = isDinnerDepot;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DepotEntity that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(shortName, that.shortName)
                && Objects.equals(branchId, that.branchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, branchId);
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
    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(@NonNull Integer branchId) {
        this.branchId = branchId;
    }

    @NonNull
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(@NonNull Boolean active) {
        isActive = active;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    public Boolean getDinnerDepot() {
        return isDinnerDepot;
    }

    public void setDinnerDepot(@Nullable Boolean dinnerDepot) {
        isDinnerDepot = dinnerDepot;
    }
}
