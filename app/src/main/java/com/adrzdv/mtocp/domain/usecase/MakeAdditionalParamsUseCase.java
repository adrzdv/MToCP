package com.adrzdv.mtocp.domain.usecase;

import android.util.Pair;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.violation.StaticsParam;

import java.util.HashMap;
import java.util.Map;

public class MakeAdditionalParamsUseCase {

    /**
     * @param revMap Map of revision objects
     * @return Map where the key is the statistic parameter name,
     *         and the value is a HashMap where the key is the coach number,
     *         and the value is a Pair containing the worker name and the parameter note
     */
    public Map<String, Map<String, Pair<String, String>>> execute(Map<String, RevisionObject> revMap) {
        Map<String, Map<String, Pair<String, String>>> res = new HashMap<>();

        for (RevisionObject obj : revMap.values()) {
            if (!obj.getAdditionalParams().isEmpty()) {
                for (StaticsParam staticsParam : obj.getAdditionalParams().values()) {
                    if (!staticsParam.getCompleted()) {
                        Map<String, Pair<String, String>> innerMap = res.computeIfAbsent(
                                staticsParam.getName(), v -> new HashMap<>()
                        );
                        innerMap.put(
                                obj.getNumber(),
                                new Pair<>(obj.getWorker().getName(), staticsParam.getNote())
                        );
                    }
                }
            }
        }
        return res;
    }
}
