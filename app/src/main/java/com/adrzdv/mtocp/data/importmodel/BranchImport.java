package com.adrzdv.mtocp.data.importmodel;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Branch import model
 * This model is using for import branch data from JSON
 */
public class BranchImport {
    private Integer id;
    private String name;
    private String shortName;
    private Boolean isActive;

    public BranchImport(@NonNull Integer id,
                        @NonNull String name,
                        @NonNull String shortName,
                        @NonNull Boolean isActive) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.isActive = isActive;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @NonNull
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BranchImport that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
