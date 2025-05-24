package com.adrzdv.mtocp.util.importmanager;

import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;

import java.util.List;

public class ImportData {
    private List<ViolationImport> violationList;
    private List<DepotImport> depotsList;

    public List<ViolationImport> getViolations() {
        return this.violationList;
    }

    public List<DepotImport> getDepotsList() {
        return this.depotsList;
    }
}
