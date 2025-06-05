package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.enums.WorkerTypes;

import java.util.Objects;

/**
 * Domain class for worker
 */
public abstract class WorkerDomain {
    private int id;
    private String name;
    private WorkerTypes workerType;

    public WorkerDomain(int id, String name, WorkerTypes workerType) {
        this.id = id;
        this.name = name;
        this.workerType = workerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkerTypes getWorkerType() {
        return workerType;
    }

    public void setWorkerType(WorkerTypes workerType) {
        this.workerType = workerType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkerDomain that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
